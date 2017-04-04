package screen;

import base.LogManager;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by ${mrfortmeyer} on 4/3/2017.
**/
public class ManufacturerInboxManager extends Screen{

    @FXML
    private Button NewApplication;

    @FXML
    private Button EditButton;

    @FXML
    private Polygon BackButton;

    //constructer for the screen
    public ManufacturerInboxManager() { super(EnumScreenType.MANUFACTURER_SCREEN);}

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }
}
