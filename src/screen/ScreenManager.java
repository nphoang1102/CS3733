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
public class ScreenManager extends Application {


    private LinkedList<Screen> screens = new LinkedList<Screen>();
    private boolean isOpen = false;

    public ScreenManager(){
        //start the screen manager
        if(!isOpen) {
            launch();
            isOpen = true;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    @Override
    public void stop(){
        isOpen = false;
    }

    public boolean isOpen(){
        return isOpen;
    }


    public void setScreen(EnumScreenType type){
        for(Screen screen : screens){
            if(screen.getType().equals(type)){
                //Code to set the main screen here
                break;
            }
        }
    }
}
