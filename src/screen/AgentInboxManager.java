package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import base.*;
import database.*;
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
    private Button pullNewBatch, EditAccount, clearInboxButton, setStatusButton;
    @FXML
    private TextArea results;
    @FXML
    private ChoiceBox typeBox, agentStatus;
    @FXML
    private TableView inboxData;
    @FXML
    private TableColumn<Application, String>  specificBrandName, manufacturerName;


    private ObservableList<Application> inboxInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> uuidCodes = new LinkedList<>();
    private UserAgent thisUser;
    private UserAgent tempUser;

    //constructer for the screen
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);

    }

    /*
        sets up the entire screent including the choice box and the specific agents inbox
     */

    @Override
    public void onScreenFocused(DataSet data){
        if(data instanceof UserAgent) {
            tempUser = (UserAgent) data;
        }
        //set everything invisible and then make them reappear based on the account
        pullNewBatch.setVisible(false);
        EditAccount.setVisible(false);
        clearInboxButton.setVisible(false);
        setStatusButton.setVisible(false);
        typeBox.setVisible(false);
        agentStatus.setVisible(false);
        inboxData.setVisible(false);


        typeBox.setValue("Beer");
        agentStatus.setValue("Activate");

       // inboxInfo.add(testApp);

        //add the Label to the pane
        manufacturerName.setCellValueFactory(new PropertyValueFactory<>("ManufacturerUsername"));
        specificBrandName.setCellValueFactory(new PropertyValueFactory<>("Brand"));

        //wipes the table
        inboxInfo.clear();


        //query database for UUID's that current Agent has in inbox
        thisUser = (UserAgent) Main.getUser();
        if(thisUser.getSuperAgent().equals("false")) {
            uuidCodes = DatabaseManager.getApplicationsByAgent(thisUser.getUsername());

            //fills the table
            for (DataSet tempData : uuidCodes) {
                inboxInfo.add((Application) tempData);

            }

            this.inboxData.setItems(inboxInfo);


            inboxData.setRowFactory(tv -> {
                TableRow<Application> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Application tempResult = row.getItem();
                        System.out.println(tempResult);
                        screenManager.popoutScreen(EnumScreenType.AGENT_APP_SCREEN, "Review Application", tempResult);
                    }
                });
                return row;
            });
            if(thisUser.getstatus().equals("active")){
                pullNewBatch.setVisible(true);
                EditAccount.setVisible(true);
                typeBox.setVisible(true);
                inboxData.setVisible(true);
            }
        }else{
            uuidCodes = DatabaseManager.getApplicationsByAgent(tempUser.getUsername());

            //fills the table
            for (DataSet tempData : uuidCodes) {
                inboxInfo.add((Application) tempData);

            }

            this.inboxData.setItems(inboxInfo);
            clearInboxButton.setVisible(true);
            setStatusButton.setVisible(true);
            agentStatus.setVisible(true);
            inboxData.setVisible(true);
        }


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
    @FXML
    public void editAccount(){
        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
    }


    public void setAgentStatus(MouseEvent mouseEvent) {
        String statusType = (String) agentStatus.getValue();
        if(statusType.equals("REMOVE") || statusType.equals("Suspend")){
            Main.databaseManager.clearInbox(thisUser.getUsername());
        }
        if (statusType.equals("Activate")) {
            statusType = "active";
        }else if(statusType.equals("Suspend")){
            statusType = "suspended";
        }else{
            statusType = "REMOVE";
        }
        DatabaseManager.setAgentStatus(tempUser.getUsername(), statusType);
        Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT);
    }

    public void wipeInbox(MouseEvent mouseEvent) {
        Main.databaseManager.clearInbox(tempUser.getUsername());
        screenManager.setScreen(EnumScreenType.AGENT_INBOX, tempUser);
    }
}

