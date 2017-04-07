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
    TableColumn<ColaResult, String> coLid, coLsource, coLalcoholType, coLname;

    /* Class methods */
    @FXML
    public void initialize() {
        /* Initialize the choice box */
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Other");
        type.setItems(typeList);
        type.setValue("Beer");

        /* Setup properties for the columns in tableview */
        this.coLid.setCellValueFactory(new PropertyValueFactory("id"));
        this.coLsource.setCellValueFactory(new PropertyValueFactory("source"));
        this.coLalcoholType.setCellValueFactory(new PropertyValueFactory("type"));
        this.coLname.setCellValueFactory(new PropertyValueFactory("name"));

        /* Allow the application to receive the row information on click, important shit right here */
        searchResult.setRowFactory( tv -> {
            TableRow<ColaResult> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 1) && (! row.isEmpty())) {
                    ColaResult rowData = row.getItem();
                    LogManager.println(rowData.getName());
                }
            });
            return row;
        });
    }

    /* When the enter button is hit, it is the same as a mouse click on the search button */
    public void onEnter() {
        this.buttonPressed();
    }

    /* What to do when the search button is pressed */
    public void buttonPressed() {
        /* Getting the keywords entered from user */
        resultTable = FXCollections.observableArrayList();
        this.keywords = entryField.getText();
        this.searchType = type.getValue() + "";

        /* Print into our nifty log manager for debug purpose and clear the entry field */
        String toPrint = "User searches for " + this.keywords + " under type " + this.searchType;
        LogManager.println(toPrint);
        this.entryField.clear();

        /* Send and receive result from the database and display them into the TableView */
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

    /* Hit back will bring you to the login screen */
    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchResultScreen");
        ScreenManager.setScreen((EnumScreenType.LOG_IN));
    }
}
