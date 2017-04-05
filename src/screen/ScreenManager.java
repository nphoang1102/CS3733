package screen;

import base.LogManager;
import base.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class ScreenManager {

    private static Stage stage;
    private static HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    private static Screen screen;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
    }

    public static void setScreen(EnumScreenType type){
        LogManager.println("Setting screen to:"+type.toString());
        if(scenes.containsKey(type.toString())){
            stage.setScene(scenes.get(type.toString()));
        }else {
            scenes.put(type.toString(), new Scene(type.getFXMLFile(), Main.WIDTH, Main.HEIGHT));
            screen = type.getScreen();
            stage.setScene(scenes.get(type.toString()));
        }
    }

    public static Screen getCurrentScreen(){
        return screen;
    }
}
