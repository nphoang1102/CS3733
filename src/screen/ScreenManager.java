package screen;

import base.EnumWarningType;
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
    private static Screen[] screens;
    private static Screen screen;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        screens = new Screen[]{
                new AgentAppScreenManager(),
                new AgentInboxManager(),
                new ColaSearchResultManager(),
                new ColaSearchScreenManager(),
                new CreateAccountManager(),
                new EditableApplicationManager(),
                new LoginScreenManager(),
                new ManufacturerInboxManager(),
                new NewApplicationManager()
        };
    }

    public static void setScreen(EnumScreenType type){
        for(Screen screen : screens) {
            if (screen.getType().equals(type)) {
                LogManager.println("Setting screen to:" + type.toString());
                stage.setScene(type.getFXMLFile().getScene());
                screen = screen;
                return;
            }
        }
        LogManager.println("Screen:"+type.toString(), EnumWarningType.ERROR);
    }

    public static Screen getCurrentScreen(){
        return screen;
    }
}
