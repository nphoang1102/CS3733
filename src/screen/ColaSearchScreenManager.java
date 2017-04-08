package screen;

import base.LogManager;
import base.Main;
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
    private Pane colaSearchPanel;
    @FXML
    private TextField entryField;
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox type;

    /* Class methods */

    // Initialize the choicebox
    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Both");
        type.setItems(typeList);
        type.setValue("Beer");
        return;
    }

    public void onScreenFocused(){
        LogManager.println("VICTOR -- remember you didn't move anything over from the initialize method to see if FXCollections.observableArrayList is okay where it is");
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
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
        return;
    }

    // What to do when the back button is pressed
    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchScreen");
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
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
