package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.Application;
import database.BasicDataSet;
import database.DataSet;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;
import sun.rmi.runtime.Log;

import java.util.LinkedList;

/**
 * Created by ${mrfortmeyer} on 4/3/2017.
 **/
public class ManufacturerInboxManager extends Screen{

    @FXML
    private Button NewApplication;

    @FXML
    private Button EditButton;

    @FXML
    private Polygon BackButton;

    @FXML
    TableView<ManufacturerInboxResult> Table = new TableView<>();

    @FXML
    TableColumn<ManufacturerInboxResult, String> TTBIDColumn = new TableColumn<>();

    @FXML
    TableColumn<ManufacturerInboxResult, String> NameColumn= new TableColumn<>();

    @FXML
    TableColumn<ManufacturerInboxResult, String> StatusColumn= new TableColumn<>();

    @FXML
    TableColumn<ManufacturerInboxResult, String> DateColumn= new TableColumn<>();

    @FXML
    TableColumn<ManufacturerInboxResult, String> TypeColumn= new TableColumn<>();

    private Application selected;

    private String manufacturer;

    private ObservableList<ManufacturerInboxResult> tableList = FXCollections.observableArrayList();

    //constructer for the screen
    public ManufacturerInboxManager() {
        super(EnumScreenType.MANUFACTURER_SCREEN);
    }

    @FXML
    public void initialize(){

    }

    public void newApplication(){
        LogManager.println("Creating a new application");
        Application app = new Application();
        Main.screenManager.popoutScreen(EnumScreenType.PICK_FORM, "Pick Form Type", 600, 400, app);
        return;
    }

    public void editApplication(){
        LogManager.println("Editing an application");
        Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_EDIT, selected.FancifulName, 1025, 700, selected);
        return;
    }

    public Screen getScreen(){
        return this;
    }

    @Override
    public void onScreenFocused(DataSet dataSet){
        manufacturer = Main.getUsername();
        LogManager.println("Current user is "+manufacturer); //move
        LinkedList<database.DataSet> appList = DatabaseManager.queryDatabase(EnumTableType.APPLICATION, "ManufacturerUsername", manufacturer);

        LogManager.println("appList: "+Integer.toString(appList.size()));

        tableList.clear();

        this.StatusColumn.setCellValueFactory(new PropertyValueFactory("ApplicationStatus"));
        this.DateColumn.setCellValueFactory(new PropertyValueFactory("DateOfSubmission"));
        this.TTBIDColumn.setCellValueFactory(new PropertyValueFactory("ApplicationNo"));
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("Brand"));
        this.TypeColumn.setCellValueFactory(new PropertyValueFactory("AlcoholType"));

        for(DataSet tempData : appList) {
            Application data = (Application) tempData;
            String tempApplicationNo = data.ApplicationNo;
            String tempBrand = data.Brand;
            String tempStatus = data.ApplicationStatus;
            String tempDate = data.DateOfSubmission;
            String tempType = data.AlcoholType;

            /*LogManager.println(tempApplicationNo);
            LogManager.println(tempBrand);
            LogManager.println(tempStatus);
            LogManager.println(tempDate);*/

            ManufacturerInboxResult tempName = new ManufacturerInboxResult(tempApplicationNo, tempBrand, tempStatus, tempDate, tempType, data);
            tableList.add(tempName);

            LogManager.println("tableList: "+Integer.toString(tableList.size()));
        }
        if(tableList.size()>0) {
            LogManager.println("TTBID 1: " + tableList.get(0).getApplicationNo());
            //LogManager.println("TTBID 1: "+ tableList.get(0).getStatus());
            this.Table.setItems(tableList);

            this.initializeMouseEvent();
        }
    }

    @FXML
    void editAccount(){
        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
    }

    public void initializeMouseEvent() {
        Table.setRowFactory( tv -> {
            TableRow<ManufacturerInboxResult> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 2) && (! row.isEmpty())) {
                    ManufacturerInboxResult rowData = row.getItem();
                    if(rowData.app.ApplicationStatus.equals("APPROVED")){
                        Main.screenManager.popoutScreen(EnumScreenType.EDIT_APPLICATION_LIST, "Edit Application", 850, 720, rowData.app);
                    } /*else if(rowData.app.ApplicationStatus.equals("REJECTED")){
                        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_1, "Edit Application", 1020, 487, rowData.app);
                    }*/ else {
                        Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_EDIT, "Edit Application", 1025, 700, rowData.app);
                    }
                }
            });
            return row;
        });
    }
}
