package screen;

import base.Main;
import database.BasicDataSet;
import database.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class AdvanceColaSearchManager extends Screen {
    /* Class attributes */
    private String category1 = "";
    private String category2 = "";
    private String category3 = "";

    /* Declaring the FXML objects */
    @FXML private TextField field1, field2, field3;
    @FXML private Button clearButton, searchButton;
    @FXML private ChoiceBox drop1, drop2, drop3;
    @FXML private Pane contain;

    /* Class constructor */
    public AdvanceColaSearchManager() {
        super(EnumScreenType.COLA_ADVANCE_SEARCH);
    }

    /* What to do on screen initialize */
    @Override
    public void onScreenFocused(DataSet data) {
        this.searchFieldInitialize();
        this.searchByInitialize();
    }

    /* Initialize the choice boxes */
    public void searchByInitialize() {
        ObservableList<String> searchByList = FXCollections.observableArrayList("","TTB ID", "Permit number", "Serial number", "Date approved", "Fancy name", "Brand name", "Origin", "Class", "Type");
        drop1.setItems(searchByList);
        drop2.setItems(searchByList);
        drop3.setItems(searchByList);
        drop1.setValue("Brand name");
        drop2.setValue("");
        drop3.setValue("");
    }

    /* Initialize the search fields */
    public void searchFieldInitialize() {
        field1.clear();
        field2.clear();
        field2.setDisable(true);
        field3.clear();
        field3.setDisable(true);
    }

    /* Check for the choice box selection to enable/disable search fields */
    public void onCheck() {
        if (drop2.getValue().equals("")) {
            field2.setDisable(true);
            field2.clear();
        }
        else field2.setDisable(false);
        if (drop3.getValue().equals("")) {
            field3.setDisable(true);
            field3.clear();
        }
        else field3.setDisable(false);
    }

    /* What to do when the clear button is clicked */
    public void clearClicked() {
        searchByInitialize();
        searchFieldInitialize();
    }

    /* What to do when the search button is clicked */
    public void searchClicked() {
        DataSet searchFields = new BasicDataSet();
        searchFields.addField("isAdvance", "true");
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT, searchFields);
    }
}
