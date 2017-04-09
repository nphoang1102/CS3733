package screen;

import base.LogManager;
import base.Main;
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
    Button backButton;
    @FXML
    TableView<ColaResult> searchResult;
    @FXML
    TableColumn<ColaResult, String> coLid, coLsource, coLalcoholType, coLname;

    /* Class methods */
    @Override
    public void onScreenFocused(){
        /* Get the TableView stuff and result setup */
        this.initializeTable();
        this.databaseQuery();

        /* Configuration for the mouse click event */
        this.initializeMouseEvent();
    }


    /* Setup properties for the columns in tableview */
    public void initializeTable() {
        this.coLid.setCellValueFactory(new PropertyValueFactory("id"));
//        this.coLid.setStyle("-fx-alignment: CENTER; -fx-background-color: #dbdbdb;-fx-font: 16px 'Telugu Sangam MN'; -fx-border-color: #373737;");
        this.coLsource.setCellValueFactory(new PropertyValueFactory("source"));
//        this.coLsource.setStyle("-fx-alignment: CENTER; -fx-background-color: #dbdbdb;-fx-font: 16px 'Telugu Sangam MN'; -fx-border-color: #373737;");
        this.coLalcoholType.setCellValueFactory(new PropertyValueFactory("type"));
//        this.coLalcoholType.setStyle("-fx-alignment: CENTER; -fx-background-color: #dbdbdb;-fx-font: 16px 'Telugu Sangam MN'; -fx-border-color: BLACK;");
        this.coLname.setCellValueFactory(new PropertyValueFactory("name"));
//        this.coLname.setStyle("-fx-alignment: CENTER; -fx-background-color: #dbdbdb;-fx-font: 16px 'Telugu Sangam MN'; -fx-border-color: BLACK;");
    }

    /* Initialize the mouse click event on table rows */
    public void initializeMouseEvent() {
        searchResult.setRowFactory( tv -> {
            TableRow<ColaResult> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 1) && (! row.isEmpty())) {
                    ColaResult rowData = row.getItem();
                    this.initializePopup(rowData);
                }
            });
            return row;
        });
    }

    /* Setup popup window here */
    public void initializePopup(ColaResult rowData) {
//        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ResultPopup.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Additional information for " + rowData.getName());
//            stage.setScene(new Scene(root1));
            stage.show();
            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//            LogManager.println("Something is wrong with the FXML loader");
//        }



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
    }

    /* Send the search keywords to the database and display reply from database */
    public void databaseQuery() {
//        this.databaseResult = DatabaseManager.Search(this.keywords, this.searchType);
        /* Please remove this line whenever during actual implementation */
        this.resultTable.add(new ColaResult("123", "41928", "asd21","4/8/17", "100% Pure alcohol", "Alcohol", "Mass", "Beer", "Beer"));
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

    /* Hit back will bring you to the login screen */
    public void backPressed() {
        LogManager.println("Back button pressed from ColaSearchResultScreen");
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
    }
}
