package screen;

import base.LogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * Created by Hoang Nguyen on 4/2/2017.
 */
public class ColaSearchScreenManager extends Screen{
    /* Class attributes */
    private String keywords;
    private String searchType = "Beer";

    /* Class constructor */
    public ColaSearchScreenManager() {
        super(EnumScreenType.COLA_SEARCH);
    }

    /* Building the control objects */
    @FXML
    Pane colaSearchPanel;
    @FXML
    TextField entryField;
    @FXML
    Button searchButton;
    @FXML
    Button backButton;
    @FXML
    ChoiceBox type;

    /* Class methods */

    // Initialize the choicebox
    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Both");
        type.setItems(typeList);
        type.setValue("Beer");
        return;
    }

    // Whenever the enter key is hit, it is the same as clicking the search button
    public void onEnter() {
        this.buttonPressed();
        return;
    }

    // What to do when the search button is pressed
    public void buttonPressed() {
        this.keywords = entryField.getText();
        this.searchType = type.getValue() + "";
        String toPrint = "User searches for " + this.keywords + " under type " + this.searchType;
        LogManager.println(toPrint);
        this.entryField.clear();
        ScreenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
        return;
    }

    // What to do when the back button is pressed
    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchScreen");
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    // Return entered keyword
    public String getKeyword() {
        return this.keywords;
    }

    // Return chosen type
    public String getSearchType() {
        return this.searchType;
    }
}
