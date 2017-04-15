package screen;

import base.LogManager;
import database.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class AdvanceColaSearchManager extends Screen {
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

    @Override
    public void onScreenFocused(DataSet data) {
        this.searchFieldInitialize();
        this.searchByInitialize();
        onCheck();
    }

    public void searchByInitialize() {
        ObservableList<String> searchByList = FXCollections.observableArrayList("","TTB ID", "Permit number", "Serial number", "Date approved", "Fancy name", "Brand name", "Origin", "Class", "Type");
        drop1.setItems(searchByList);
        drop2.setItems(searchByList);
        drop3.setItems(searchByList);
        drop1.setValue("Brand name");
        drop2.setValue("");
        drop3.setValue("");
    }

    public void searchFieldInitialize() {
        field1.clear();
        field2.clear();
        field3.clear();
    }

    public void onCheck() {
        if (drop2.getValue().equals("")) field2.setDisable(true);
        else field2.setDisable(false);
        if (drop3.getValue().equals("")) field3.setDisable(true);
        else field3.setDisable(false);
    }

    public void clearClicked() {
        searchByInitialize();
        searchFieldInitialize();

    }

    public void searchClicked() {

    }
}
