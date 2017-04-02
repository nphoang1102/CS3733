package screen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class ScreenManager {


    private static LinkedList<Screen> screens = new LinkedList<Screen>();

    public ScreenManager(){

    }



    public static void setScreen(EnumScreenType type){
        for(Screen screen : screens){
            if(screen.getType().equals(type)){
                //Code to set the main screen here
                break;
            }
        }
    }
}
