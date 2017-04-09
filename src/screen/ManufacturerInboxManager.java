package screen;

import base.LogManager;
import base.Main;
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

    private DataSet selected;

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
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_ADD_FORM);
        return;
    }

    public void editApplication(){
        LogManager.println("Editing an application");
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_EDIT);
//        EditableApplicationManager currentScreen = (EditableApplicationManager) getCurrentScreen();
//        currentScreen.data = selected;
        return;
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    public Screen getScreen(){
        return this;
    }

    @Override
    public void onScreenFocused(){
        manufacturer = Main.getUsername(); //move
        LogManager.print("Current user is "+manufacturer); //move
        LinkedList<database.DataSet> appList = DatabaseManager.queryManufactures(manufacturer); //move

        ObservableList tableList = FXCollections.observableArrayList(); //move
        LogManager.println("appList: "+Integer.toString(appList.size()));

        this.StatusColumn.setCellValueFactory(new PropertyValueFactory("Status"));
        this.DateColumn.setCellValueFactory(new PropertyValueFactory("Date"));
        this.TTBIDColumn.setCellValueFactory(new PropertyValueFactory("TTBID"));
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("BrandName"));

        for(DataSet data : appList) {
            String tempTTBID = data.getValueForKey("TTBID");
            String tempName = data.getValueForKey("BrandName");
            String tempStatus = data.getValueForKey("Status");
            String tempDate = data.getValueForKey("CompletedDate");

            this.tableList.add(new ManufacturerInboxResult(tempTTBID, tempName, tempStatus, tempDate));

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
}
