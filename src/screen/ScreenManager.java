package screen;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
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
    private static HashMap<String, Scene> loadedScenes = new HashMap<String, Scene>();
    private static HashMap<String, Screen> loadedScreens = new HashMap<String, Screen>();

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        setScreen(EnumScreenType.TOP_BAR);
    }

    public void setScreen(EnumScreenType type){
        LogManager.println("Setting screen to:" + type.toString());
        Scene scene;
        if(loadedScenes.containsKey(type.toString())){
            scene = loadedScenes.get(type.toString());
            loadedScreens.get(type.toString()).onScreenFocused();
        }else{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screen/fxml/" + type.getFXMLFile()));
            try {
                scene = new Scene(loader.load(), Main.WIDTH, Main.HEIGHT);
                loadedScenes.put(type.toString(), scene);
                stage.setScene(scene);
                Screen theScreen = (Screen)loader.getController();
                loadedScreens.put(type.toString(), theScreen);
                theScreen.onScreenFocused();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
