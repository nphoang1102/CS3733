package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import base.*;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import database.PasswordStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import static base.Main.screenManager;


public class AgentInboxManager extends Screen{

    @FXML
    private Button pullNewBatch;
    @FXML
    private Polygon backButton;
    @FXML
    private TextArea results;
    @FXML
    private ChoiceBox typeBox;
    @FXML
    private TableView inboxData;
    @FXML
    private TableColumn<Application, String>  specificBrandName, manufacturerName;


    private ObservableList<Application> inboxInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> uuidCodes = new LinkedList<>();
    private AgentInboxResult testApp = new AgentInboxResult("budweiser", "summer Ale", "12345768");

    //constructer for the screen
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);

    }

    /*
        sets up the entire screent including the choice box and the specific agents inbox
     */

    @Override
    public void onScreenFocused(DataSet data){
        System.out.println("type of alc box: " + typeBox);

       // inboxInfo.add(testApp);

        //add the Label to the pane
        manufacturerName.setCellValueFactory(
                new PropertyValueFactory<>("ManufacturerUsername")
        );
        manufacturerName.setStyle("-fx-alignment: Center; -fx-background-color: #dbdbdb; -fx-font: 16px 'Telugu Sangam MN'");
        specificBrandName.setCellValueFactory(
                new PropertyValueFactory<>("Brand")
        );
        specificBrandName.setStyle("-fx-alignment: Center; -fx-background-color: #dbdbdb; -fx-font: 16px 'Telugu Sangam MN'");

        inboxData.setRowFactory( tv -> {
            TableRow<AgentInboxResult> row = new TableRow<>();
            row.setOnMouseClicked( event-> {
                if(event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Application tempResult = (Application) row.getUserData();
                    screenManager.popoutScreen(EnumScreenType.AGENT_APP_SCREEN, "Review Application",tempResult);
                }

            });
            return row;
        });
        //query database for UUID's that current Agent has in inbox
        uuidCodes =  DatabaseManager.queryDatabase(EnumTableType.APPLICATION, "AgentUsername", Main.getUsername());

        for(DataSet tempData: uuidCodes){
            inboxInfo.add((Application) tempData);

        }

        this.inboxData.setItems(inboxInfo);
    }

    @FXML
    void goBack() {
        LogManager.println("Back button pressed from AgentInboxScreen");
        screenManager.setScreen(EnumScreenType.LOG_IN);

    }

    /*
        fills the users inbox with new information
     */
    @FXML
    public void pullNewBatch(){
        //check if the inbox is empty if its not dont pull new batch
        System.out.println("typeBoxinfo" + typeBox.getValue());
        if(inboxInfo.size() <= 9){
            int numAppsNeeded = 10 - inboxInfo.size();
            String tempType = (String) typeBox.getValue();
            String currentUser = Main.getUsername();

            //call Database to get number of applications
            LinkedList<Application> results  = DatabaseManager.addApplicationToInbox(tempType,  currentUser, numAppsNeeded);
            if(results.size() > 0){
                for(Application tempSet: results) {
                    uuidCodes.add(tempSet);
                }
            }
            System.out.println("datesets: " + results);
        }
        else {
            LogManager.println("Agent Inbox is not empty no new applications can be added");
        }
        screenManager.setScreen(EnumScreenType.AGENT_INBOX);


    }


    public void removeId(String rString){
        uuidCodes.remove(rString);
    }

}

