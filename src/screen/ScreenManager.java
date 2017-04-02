package screen;

import base.LogManager;
import base.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class ScreenManager {

    private static Stage stage;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
    }

    public static void setScreen(EnumScreenType type){
        LogManager.println("Setting screen to:"+type.toString());
        stage.setScene(new Scene(type.getFXMLFile(), Main.WIDTH, Main.HEIGHT));
    }
}
