package screen;

import base.LogManager;
import database.DataSet;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<DataSet> Table;

    @FXML
    private TableColumn TTBIDColumn;

    @FXML
    private TableColumn NameColumn;

    @FXML
    private TableColumn StatusColumn;

    @FXML
    private TableColumn DateColumn;

    @FXML
//    private LinkedList<Result> resultList = new LinkedList<>();

    private String manufacturer;

    //constructer for the screen
    public ManufacturerInboxManager() {
        super(EnumScreenType.MANUFACTURER_SCREEN);
        initialize();
    }

    public void initialize(){
        LinkedList<database.DataSet> appList = DatabaseManager.queryManufacturers(manufacturer);

        ObservableList tableList = FXCollections.observableArrayList();

        tableList.addAll(appList);

        Table.setItems(tableList);

        TTBIDColumn.setCellValueFactory(
                new PropertyValueFactory<DataSet, String>("TTBID")
        );

        NameColumn.setCellValueFactory(
                new PropertyValueFactory<DataSet, String>("Name")
        );

        StatusColumn.setCellValueFactory(
                new PropertyValueFactory<DataSet, String>("Status")
        );

        DateColumn.setCellValueFactory(
                new PropertyValueFactory<DataSet, String>("Date")
        );
    }

    public void newApplication(){
        LogManager.println("Creating a new application");
        ScreenManager.setScreen(EnumScreenType.MANUFACTURER_ADD_FORM);
        return;
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    public Screen getScreen(){
        return this;
    }
}
