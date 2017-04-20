package screen.cola_search;

import base.Main;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import screen.EnumScreenType;
import screen.Screen;

/**
 * Created by Hoang Nguyen on 4/20/2017.
 */
public class ColaCharSelectorManager extends Screen {
    /* Class constructor */
    public ColaCharSelectorManager() {
        super(EnumScreenType.COLA_CHARACTER_SELECTION);
    }

    /* FXML objects */
    @FXML private TextField deChar;
    @FXML private Button export;
    @FXML private Label warning;

    /* Initialize */
    @Override
    public void onScreenFocused(DataSet data) {

    }

    /* Clear the error message */
    public void onCheck() {

    }

    /* Export the desired character delimiter to a text file */
    public void onClick() {
        Main.screenManager.closeCurrentPopOut();
    }
}
