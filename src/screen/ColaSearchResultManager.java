package screen;

import base.LogManager;
import database.DataSet;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import misc.ColaResult;

import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaSearchResultManager extends Screen{
    /* Class attributes */
    private String keywords;
    private String searchType;
    private LinkedList<DataSet> databaseResult;
    private ObservableList<ColaResult> resultTable = FXCollections.observableArrayList();
    private DataSet tempSet;

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
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Other");
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
        resultTable = FXCollections.observableArrayList();
        this.keywords = entryField.getText();
        this.searchType = type.getValue() + "";
        String toPrint = "User searches for " + this.keywords + " under type " + this.searchType;
        LogManager.println(toPrint);
        this.entryField.clear();
        this.databaseResult = DatabaseManager.Search(this.keywords, this.searchType);
        for (DataSet tempSet: databaseResult) {
            String tempID = tempSet.getValueForKey("TTBID");
            String tempSource = tempSet.getValueForKey("Origin");
            String tempType = tempSet.getValueForKey("Type");
            String tempBrand = tempSet.getValueForKey("BrandName");
            this.resultTable.add(new ColaResult(tempID, tempSource, tempType, tempBrand));
        }
        this.searchResult.setEditable(false);
        this.searchResult.setItems(resultTable);
    }

    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchResultScreen");
        ScreenManager.setScreen((EnumScreenType.LOG_IN));
    }

    @Override
    public void onScreenFocused() {
        
    }
}
