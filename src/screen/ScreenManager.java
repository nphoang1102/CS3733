package screen;

import base.LogManager;
import base.Main;
import database.BasicDataSet;
import database.DataSet;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class ScreenManager {

    private static Stage stage;

    //any additional scenes and screens that are loaded
    private static HashMap<String, Scene> loadedScenes = new HashMap<String, Scene>();
    private static HashMap<String, Screen> loadedScreens = new HashMap<String, Screen>();

    private static LinkedList<Stage> popOutWindows = new LinkedList<Stage>();

    private LinkedList<State> screenStates = new LinkedList<State>();

    //The top bar, the most important screen
    private static Scene topBarScene;
    private static TopBarManager topBarScreen;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screen/fxml/" + EnumScreenType.TOP_BAR.getFXMLFile()));
        try {
            topBarScene = new Scene(loader.load(), Main.WIDTH, Main.HEIGHT);
            stage.setScene(topBarScene);
            topBarScreen = (TopBarManager) loader.getController();
            topBarScreen.onScreenFocused(new BasicDataSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScreen(EnumScreenType.LOG_IN);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                closeAllPopOuts();
            }
        });
    }

    public void setScreen(EnumScreenType type){
        DataSet data = new BasicDataSet();
        setScreen(type, data);
    }

    public void setScreen(EnumScreenType type, DataSet data){
        if(Main.getUser()!=null){
            if(type.equals(EnumScreenType.LOG_IN)){
                return;
            }
            if(type.equals(EnumScreenType.CREATE_ACCOUNT)){
                return;
            }
        }
        DataSet topBarData = topBarScreen.generateTopBarData();
        if(!type.equals(EnumScreenType.TOP_BAR)){
            LogManager.println("Setting screen to:" + type.toString());
            Scene scene;
            if(loadedScenes.containsKey(type.toString())){
                scene = loadedScenes.get(type.toString());
                topBarScreen.setScreen(loadedScenes.get(type.toString()));
                loadedScreens.get(type.toString()).onScreenFocused(data);
            }else{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screen/fxml/" + type.getFXMLFile()));
                try {
                    scene = new Scene(loader.load(), Main.WIDTH, Main.HEIGHT);
                    loadedScenes.put(type.toString(), scene);
                    topBarScreen.setScreen(loadedScenes.get(type.toString()));
                    Screen theScreen = (Screen)loader.getController();
                    loadedScreens.put(type.toString(), theScreen);
                    theScreen.onScreenFocused(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            topBarScreen.onScreenFocused(new BasicDataSet());
        }
        screenStates.addFirst(new State(type, data, topBarData));
    }

    public void popoutScreen(EnumScreenType type, String name, DataSet data){
        popoutScreen(type, name, Main.WIDTH, Main.HEIGHT, data);
    }

    public void popoutScreen(EnumScreenType type, String title, int width, int height, DataSet data){
        //get all pop outs, check if they are not on top and are still active
        for(Stage stage: popOutWindows){
            if(stage.getOwner()!=null){
                stage.toFront();
            }
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        Scene scene;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screen/fxml/" + type.getFXMLFile()));
        try {
            scene = new Scene(loader.load(), width, height);
            stage.setScene(scene);
            Screen theScreen = (Screen)loader.getController();
            theScreen.onScreenFocused(data);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    theScreen.shutdown();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
        popOutWindows.add(stage);
    }

    private void closeAllPopOuts(){
        LogManager.println("Closing all Windows");
        for(Stage stage: popOutWindows){
            if(stage!=null) {
                stage.close();
            }
        }
    }

    public void closeCurrentPopOut(){
        LogManager.println("Closing current popout");
        for(Stage stage: popOutWindows){
            if(stage!=null) {
                if(stage.isFocused()) {
                    stage.close();
                    popOutWindows.remove(stage);
                }
            }
        }
    }

    public void back(){
        if(screenStates.size()>1){
            DataSet topBarData = screenStates.getFirst().topBarData;
            screenStates.removeFirst();
            setScreen(screenStates.getFirst().type, screenStates.getFirst().data);
            topBarScreen.onScreenFocused(topBarData);
            screenStates.removeFirst();
        }
    }

    public void clearScreenHistory(){
        screenStates.clear();
    }

    public String getSearchTerm(){
        return topBarScreen.getSearchTerm();
    }

    public static void updateUserIcon() {
        topBarScreen.updateUserIcon();
    }

    public void initializeTopBar() {
        topBarScreen.initSuggestiveSearch();
    }
}

class State{
    EnumScreenType type;
    DataSet data;
    DataSet topBarData;
    public State(EnumScreenType type, DataSet data, DataSet topBarData){
        this.type = type;
        this.data = data;
        this.topBarData = topBarData;
    }
}