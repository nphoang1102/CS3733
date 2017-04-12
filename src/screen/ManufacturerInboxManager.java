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
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.util.LinkedList;
import javafx.scene.control.TableColumn;

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
        Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_ADD_FORM, "New Application", 1025, 700, new BasicDataSet());
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

        ObservableList tableList = FXCollections.observableArrayList(); //move
        LogManager.println("appList: "+Integer.toString(appList.size()));

        this.StatusColumn.setCellValueFactory(new PropertyValueFactory("ApplicationStatus"));
        this.DateColumn.setCellValueFactory(new PropertyValueFactory("DateOfSubmission"));
        this.TTBIDColumn.setCellValueFactory(new PropertyValueFactory("ApplicationNo"));
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("Brand"));

        for(DataSet tempData : appList) {
            Application data = (Application) tempData;
            String tempApplicationNo = data.ApplicationNo;
            String tempBrand = data.Brand;
            String tempStatus = data.ApplicationStatus;
            String tempDate = data.DateOfSubmission;

            this.tableList.add(new ManufacturerInboxResult(tempApplicationNo, tempBrand, tempStatus, tempDate));

            LogManager.println("tableList: "+Integer.toString(tableList.size()));

            /*tempName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int tempPlace = tableList.indexOf(tempName);
                    selected = data;
                    EditButton.setDisable(false);
                }
            });*/
        }
        //LogManager.println("TTBID 1: "+ tableList.get(0).getTTBID());
        this.Table.setItems(tableList);
    }

    @FXML
    void editAccount(){
        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
    }
}
