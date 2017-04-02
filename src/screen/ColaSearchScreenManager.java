package screen;

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
    /* Building the control objects */
    @FXML
    Pane colaSearchPanel;
    @FXML
    TextField entryField;
    @FXML
    Ellipse searchButton;
    @FXML
    Text searchLabel;
    @FXML
    Polygon backButton;
    @FXML
    ChoiceBox type;

    /* Class attributes */
    private String keywords;
    private String searchType = "Beer";

    public ColaSearchScreenManager() {
        super(EnumScreenType.COLA_SEARCH);
    }

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
        this.entryField.clear();
        return;
    }

    // What to do when the search button is pressed
    public void buttonPressed() {
        this.keywords = entryField.getText();
        this.searchType = type.getValue() + "";
        System.out.println(this.keywords);
        System.out.println(this.searchType);
        this.entryField.clear();
        return;
    }

    // What to do when the back button is pressed
    public void backPressed() {
        System.out.println("Back button pressed");
        return;
    }
}
