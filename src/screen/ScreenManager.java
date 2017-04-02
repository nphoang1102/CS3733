package screen;

import base.LogManager;

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
                LogManager.println("Setting screen to:"+type.toString());
                break;
            }
        }
    }
}
