package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import base.LogManager;
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




public class AgentInboxManager extends Screen{
    /* JavaFX objects */
    @FXML
    private Button pullNewBatch, newApplication;
    @FXML
    private Polygon backButton;
    @FXML
    private TextArea results;
    @FXML
    private ChoiceBox typeOfAlcBox;
    @FXML
    private TableView inboxData;
    @FXML
    private TableColumn manufacturerName, specificBrandName;

    /* Class attributes */
    private ObservableList<AgentInboxResult> inboxInfo = FXCollections.observableArrayList();
    private LinkedList<String> uuidCodes = new LinkedList<>();

   /* Class constructor */
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);
    }

    /*
        sets up the entire screen including the choice box and the specific agents inbox
     */
/*    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine");
        System.out.println("type of alc box: " + typeOfAlcBox);
        System.out.println();
        typeOfAlcBox.setItems(typeList);
        typeOfAlcBox.setValue("Beer");

        //query database for UUID's that current Agent has in inbox
        int i = 0;
        for(String tempCode: uuidCodes){

                Label tempLabel = new Label();

                //query database for said UUID Code
                //DataSet tempData = DatabaseManager.queryAlcohol(tempCode);

                //get tempData from database
                String Manufacturer = null;
                String BrandName = null;
                //fill Manuefacturer and BrandName from temp
                tempLabel.setText(Manufacturer);

                AgentInboxResult tempResult = new AgentInboxResult(tempLabel, BrandName);

                //add the Label to the pane
                manufacturerName.setCellValueFactory(
                        new PropertyValueFactory<AgentInboxResult, Label>("manufacturerName")
                );
                manufacturerName.setCellValueFactory(
                        new PropertyValueFactory<AgentInboxManager, String>("brandName")
                );


                //Result tempResult = new Result(tempLabel, tempData);

                //add the label to the linked list of possible labels

                //set an onclick command to send screen to application screen
                tempLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ScreenManager.setScreen(EnumScreenType.AGENT_APP_SCREEN);
                    }
                });

                //highlight the label that can be clicked
                tempLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tempLabel.setTextFill(Color.web("#0000FF"));
                    }
                });
                tempLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tempLabel.setTextFill(Color.web("#000000"));
                    }
                });
        }

    }*/

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
        if(inboxInfo.size() <= 9){
            int numAppsNeeded = 10 - inboxInfo.size();
            String tempType = (String) typeOfAlcBox.getValue();

            //call Database to get number of applications
            LinkedList<DataSet> tempData = DatabaseManager.getApplications(tempType, numAppsNeeded);
            for(DataSet tempSet: tempData){
                String tempString = tempSet.getValueForKey("ApplicationNo");
            }
        }
        else {
            LogManager.println("Agent Inbox is not empty no new applications can be added");
        }
        ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);
    }


    public void removeId(String rString){
        uuidCodes.remove(rString);
    }
    public void newApplication() {

    }





}

