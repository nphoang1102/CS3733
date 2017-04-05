package screen;

import base.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import misc.ColaResult;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaSearchResultManager extends Screen{

    /* Class constructor */
    public ColaSearchResultManager() {
        super(EnumScreenType.COLA_SEARCH_RESULT);
    }

    /* FXML objects */
    @FXML
    TextField entryField;
    @FXML
    Button searchButton, backButton;
    @FXML
    ChoiceBox type;
    @FXML
    TableView<ColaResult> searchResult;
    @FXML
    TableColumn<ColaResult, String> id, source, alcoholType, name;

    /* Class methods */
    public void onEnter() {

    }

    public void buttonPressed() {

    }

    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchResultScreen");
        ScreenManager.setScreen((EnumScreenType.COLA_SEARCH));
    }
}
