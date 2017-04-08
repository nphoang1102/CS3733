package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import base.*;
import database.DataSet;
import database.DatabaseManager;
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

import static screen.ScreenManager.getCurrentScreen;


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
    private TableColumn<AgentInboxResult, String>  specificBrandName, manufacturerName;





    private ObservableList<AgentInboxResult> inboxInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> uuidCodes = new LinkedList<>();

    //constructer for the screen
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);
        initialize();
    }

    /*
        sets up the entire screent including the choice box and the specific agents inbox
     */
    @FXML
    public void initialize() {


        uuidCodes = DatabaseManager.getApplicationsInitialAgent(Main.getUsername());

        //query database for UUID's that current Agent has in inbox

        int i = 0;
        //uuidCodes = DatabaseManager.
        for(DataSet tempData: uuidCodes){


            //fill Manuefacturer and BrandName from temp
            String Manufacturer = tempData.getValueForKey("Manufacturer");
            String BrandName = tempData.getValueForKey("Brand");

            AgentInboxResult tempResult = new AgentInboxResult(Manufacturer, BrandName);

            //add the Label to the pane
            manufacturerName.setCellValueFactory(
                    new PropertyValueFactory<>("manufacturerName")
            );
            specificBrandName.setCellValueFactory(
                    new PropertyValueFactory<>("brandName")
            );
        }

    }

    @FXML
    void goBack() {
        LogManager.println("Back button pressed from AgentInboxScreen");
        ScreenManager.setScreen(EnumScreenType.LOG_IN);

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
            LinkedList<DataSet> results  = DatabaseManager.getApplications(tempType, numAppsNeeded, currentUser);
            if(results.size() > 0){
                for(DataSet tempSet: results) {
                    uuidCodes.add(tempSet);
                }
            }
            System.out.println("datesets: " + results);
        }
        else {
            LogManager.println("Agent Inbox is not empty no new applications can be added");
        }
        ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);

    }


    public void removeId(String rString){
        uuidCodes.remove(rString);
    }



}

