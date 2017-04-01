package screen;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public abstract class Screen {
    private EnumScreenType type;

    public Screen(EnumScreenType type){
        this.type = type;
    }

    /*
    @return returns an EnumScreenType object representing the type of screen
     */
    public EnumScreenType getType(){
        return this.type;
    }

    /*
        This method is overriden in the child classes of screen, It is called whenver the red close button
        on the window is pressed. This method is called before the window closes to allow for saves to be
        generated, or in progress forms to be backed up.
     */
    public void shutdown(){
        return;
    }
}
