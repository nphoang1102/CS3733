package screen;

import base.LogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import misc.ColaResult;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaSearchResultManager extends Screen{
    /* Class attributes */
    private String keywords;
    private String searchType;

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
    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Both");
        type.setItems(typeList);
        type.setValue("Beer");
        this.id.setCellValueFactory(new PropertyValueFactory("id"));
        this.source.setCellValueFactory(new PropertyValueFactory("source"));
        this.alcoholType.setCellValueFactory(new PropertyValueFactory("type"));
        this.name.setCellValueFactory(new PropertyValueFactory("name"));
    }

    public void onEnter() {
        this.buttonPressed();
    }

    public void buttonPressed() {
        this.keywords = entryField.getText();
        this.searchType = type.getValue() + "";
        String toPrint = "User searches for " + this.keywords + " under type " + this.searchType;
        LogManager.println(toPrint);
        this.entryField.clear();
        this.displayToTable();
    }

    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchResultScreen");
        ScreenManager.setScreen((EnumScreenType.LOG_IN));
    }

    public void displayToTable() {
        ObservableList<ColaResult> resultTable = FXCollections.observableArrayList(
                new ColaResult("", "", this.searchType+"", this.keywords+"")
        );
        this.searchResult.setEditable(false);

        this.searchResult.setItems(resultTable);
    }
}
