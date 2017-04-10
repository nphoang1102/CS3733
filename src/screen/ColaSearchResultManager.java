package screen;

import base.LogManager;
import base.Main;
import base.StringUtilities;
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
        Main.screenManager.popoutScreen(EnumScreenType.COLA_RESULT_POPUP, 680, 245, data, title);
    }



/*        LogManager.println("User clicked on item ID " + rowData.getId());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screen/fxml/ResultPopup.fxml"));
//        Parent root1 = FXMLLoader.<Parent>load(ResultPopupManager.class.getResource("./fxml/ResultPopup.fxml/"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Additional information for " + rowData.getName());
//        stage.setScene(new Scene(root1));
//                    ResultPopupManager controller = fxmlLoader.getController();
//                    controller.initData(rowData);

        stage.show();

//                    return stage;*/

    /* Send the search keywords to the database and display reply from database */
    public void databaseQuery() {
//        this.databaseResult = DatabaseManager.Search(this.keywords, this.searchType);
        /* Please remove this line whenever during actual implementation */
//        this.resultTable.clear();
        this.resultTable.add(new ColaResult("123", "41928", "asd21","4/8/17", "100% Pure alcohol", this.keywords, "Mass", "Beer", this.searchType));
        /*for (DataSet tempSet: databaseResult) {
            String tempID = tempSet.getValueForKey("TTBID");
            String tempPermit = tempSet.getValueForKey("PermitNo");
            String tempSerial = tempSet.getValueForKey("SerialNo");
            String tempDate = tempSet.getValsueForKey("CompletedDate");
            String tempName = tempSet.getValueForKey("FancifulName");
            String tempBrand = tempSet.getValueForKey("BrandName");
            String tempSource = tempSet.getValueForKey("Origin");
            String tempClass = tempSet.getValueForKey("Class");
            String tempType = tempSet.getValueForKey("Type");
            this.resultTable.add(new ColaResult(tempID, tempPermit, tempSerial, tempDate, tempName, tempBrand, tempSource, tempClass, tempType));
        }*/
//        this.searchResult.setEditable(false);
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
