package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Alcohol;
import database.BasicDataSet;
import database.DataSet;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import misc.ColaResult;
import misc.ResultPopupManager;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaSearchResultManager extends Screen{
    /* Class attributes */
    private String keywords = "";
    private String searchType = "";
    private LinkedList<DataSet> databaseResult = new LinkedList();
    private ObservableList<ColaResult> resultTable = FXCollections.observableArrayList();
    private DataSet tempSet = new BasicDataSet();

    /* Class constructor */
    public ColaSearchResultManager() {
        super(EnumScreenType.COLA_SEARCH_RESULT);
    }

    /* FXML objects */
    @FXML
    private TableView<ColaResult> searchResult;
    @FXML
    private TableColumn<ColaResult, String> coLid, coLsource, coLalcoholType, coLname;
    @FXML
    private Button saveToCsv;
    @FXML
    private Pane colaSearchPanel;

    /* Class methods */
    @Override
    public void onScreenFocused(DataSet data){
        /* Retrieve search information from TopBarManager */
        this.keywords = data.getValueForKey("Keywords");
        this.searchType = data.getValueForKey("AlcoholType");

        /* Get the TableView stuff and result setup */
        this.initializeTable();
        this.databaseQuery();

        /* Configuration for the mouse click event */
        this.initializeMouseEvent();
    }

    /* Setup properties for the columns in tableview */
    public void initializeTable() {
        this.coLid.setCellValueFactory(new PropertyValueFactory("id"));
        this.coLsource.setCellValueFactory(new PropertyValueFactory("source"));
        this.coLalcoholType.setCellValueFactory(new PropertyValueFactory("type"));
        this.coLname.setCellValueFactory(new PropertyValueFactory("name"));
    }

    /* Initialize the mouse click event on table rows */
    public void initializeMouseEvent() {
        searchResult.setRowFactory( tv -> {
            TableRow<ColaResult> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 2) && (! row.isEmpty())) {
                    ColaResult rowData = row.getItem();
                    this.initializePopup(rowData);
                }
            });
            return row;
        });
    }

    /* Setup popup window here */
    public void initializePopup(ColaResult rowData) {
        DataSet data = new BasicDataSet();
        String title = "Additional information for " + rowData.getName() + " - " + rowData.getFname();
        data.addField("TTBID", rowData.getId());
        data.addField("PermitNo", rowData.getPermit());
        data.addField("SerialNo", rowData.getSerial());
        data.addField("CompletedDate", rowData.getDate());
        data.addField("FancifulName", rowData.getFname());
        data.addField("BrandName", rowData.getName());
        data.addField("Origin", rowData.getSource());
        data.addField("Class", rowData.getAclass());
        data.addField("Type", rowData.getType());
        data.addField("AlcoholContent", rowData.getAlCon());
        data.addField("VintageYear", rowData.getYear());
        data.addField("PH", rowData.getPh());
        Main.screenManager.popoutScreen(EnumScreenType.COLA_RESULT_POPUP, title, 680, 300, data);
    }

    /* Send the search keywords to the database and display reply from database */
    public void databaseQuery() {
        this.databaseResult = DatabaseManager.queryDatabase(EnumTableType.ALCOHOL, "BrandName" , this.keywords);
        LogManager.println("I passed!!!!");
        /* Please remove this line whenever during actual implementation */
        this.resultTable.clear();
//        this.resultTable.add(new ColaResult("123", "41928", "asd21","4/8/17", "100% Pure alcohol", this.keywords, "Mass", "Beer", this.searchType, "7.8", "", "" ));
        if (this.databaseResult.isEmpty()) {
            for (DataSet tempSet: this.databaseResult) {
                Alcohol data = (Alcohol) tempSet;
                String tempID = data.TTBID;
                String tempPermit = data.PermitNo;
                String tempSerial = data.SerialNo;
                String tempDate = data.CompletedDate;
                String tempName = data.FancifulName;
                String tempBrand = data.BrandName;
                String tempSource = data.Origin;
                String tempClass = data.Class;
                String tempType = data.Type;
                String tempAlCon = data.AlcoholContent;
                String tempVinYear = data.VintageYear;
                String tempPh = data.PH;

                /*String tempID = tempSet.getValueForKey("TTBID");
                String tempPermit = tempSet.getValueForKey("PermitNo");
                String tempSerial = tempSet.getValueForKey("SerialNo");
                String tempDate = tempSet.getValueForKey("CompletedDate");
                String tempName = tempSet.getValueForKey("FancifulName");
                String tempBrand = tempSet.getValueForKey("BrandName");
                String tempSource = tempSet.getValueForKey("Origin");
                String tempClass = tempSet.getValueForKey("Class");
                String tempType = tempSet.getValueForKey("Type");
                String tempAlCon = tempSet.getValueForKey("AlcoholContent");
                String tempVinYear = tempSet.getValueForKey("VintageYear");
                String tempPh = tempSet.getValueForKey("PH");*/
                this.resultTable.add(new ColaResult(tempID, tempPermit, tempSerial, tempDate, tempName, tempBrand, tempSource, tempClass, tempType, tempAlCon, tempVinYear, tempPh));
                LogManager.println(tempName);
            }
        }
        else {
            LogManager.println("Database is empty");
        }
//        LogManager.println(this.databaseResult.isEmpty() + "");

        this.searchResult.setEditable(false);
        this.searchResult.getItems().setAll(resultTable);
    }

    /* Print search result into a CSV file on button click */
    public void onButtonClicked() {
        String columnHeaders = "TTB ID" + ","
                + "Permit number" + ","
                + "Serial number" + ","
                + "Date approved" + ","
                + "Fancy name" + ","
                + "Brand name" + ","
                + "Source" + ","
                + "Class" + ","
                + "Type";
        String columns = "";
        String outputPath = "./searchResult.csv";
        for (ColaResult data : this.resultTable){
            columns += data.getId() + ","
                    + data.getPermit() + ","
                    + data.getSerial() + ","
                    + data.getDate() + ","
                    + data.getFname() + ","
                    + data.getName() + ","
                    + data.getSource() + ","
                    + data.getAclass() + ","
                    + data.getType() + "\n";
        }
        StringUtilities.saveData(outputPath, new String[] {columnHeaders, columns});
        LogManager.println("Search result saved to ./searchResult.csv");
    }
}
