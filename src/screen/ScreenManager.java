package screen;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
import database.BasicDataSet;
import database.DataSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
            topBarScreen.onScreenFocused();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScreen(EnumScreenType.LOG_IN);
    }

    public void setScreen(EnumScreenType type){
        DataSet data = new BasicDataSet();
        setScreen(type, data);
    }

    public void setScreen(EnumScreenType type, DataSet data){
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
            topBarScreen.onScreenFocused();
        }

//        LogManager.println("Setting screen to:" + type.toString());
//        Scene scene;
//        if(loadedScenes.containsKey(type.toString())){
//            scene = loadedScenes.get(type.toString());
//            stage.setScene(scene);
//            loadedScreens.get(type.toString()).onScreenFocused();
//        }else{
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screen/fxml/" + type.getFXMLFile()));
//            try {
//                scene = new Scene(loader.load(), Main.WIDTH, Main.HEIGHT);
//                loadedScenes.put(type.toString(), scene);
//                stage.setScene(scene);
//                Screen theScreen = (Screen)loader.getController();
//                loadedScreens.put(type.toString(), theScreen);
//                theScreen.onScreenFocused();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if(type.equals(EnumScreenType.TOP_BAR)){
//            ((TopBarManager)loadedScreens.get(type.toString())).setScreen(loadedScenes.get(EnumScreenType.LOG_IN.toString()));
//        }
    }

    public static void popoutScreen(EnumScreenType type, DataSet data){
        popoutScreen(type, Main.WIDTH, Main.HEIGHT, data);
    }

    public static void popoutScreen(EnumScreenType type, int width, int height, DataSet data){

    }
}
