package screen;

import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class ScreenManager {


    private LinkedList<Screen> screens = new LinkedList<Screen>();

    public ScreenManager(){

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
