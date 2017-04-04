package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import base.LogManager;
import database.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;




public class AgentInboxManager extends Screen{

    @FXML
    private Button pullNewBatch;

    @FXML
    private Polygon backButton;

    @FXML
    private TextArea results;

    @FXML
    private Button newApplication;

    @FXML
    private ChoiceBox typeOfAlcBox;

    @FXML
    private Pane Inbox;


    //lists for keeping track of data sets
    private LinkedList<Result> inboxData = new LinkedList<>();




    //constructer for the screen
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);
    }

    /*
        sets up the entire screent including the choice box and the specific agents inbox
     */
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine");
        typeOfAlcBox.setItems(typeList);
        typeOfAlcBox.setValue("Beer");

        ArrayList<String> uuidCodes = new ArrayList<>();

        //query database for UUID's that current Agent has in inbox

        int i = 0;
        while(true){

            String tempCode = uuidCodes.get(i);
            if(tempCode != null) {

                Label tempLabel = new Label();

                //query database for said UUID Code
                DataSet tempData = new DataSet();

                //get tempData from database
                String Manufacturer = null;
                String BrandName = null;
                //fill Manuefacturer and BrandName from temp
                tempLabel.setText(Manufacturer + "  |  " + BrandName);

                //add the Label to the pane
                Inbox.getChildren().add(tempLabel);

                Result tempResult = new Result(tempLabel, tempData);

                //add the label to the linked list of possible labels
                inboxData.add(tempResult);

                //set an onclick command to send screen to application screen
                //tempLabel.setOnMouseClicked();

            }
            else{
                break;
            }
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
        if(inboxData.size() == 0){
            //fill linked list of data with correct type
            String key = typeOfAlcBox.getAccessibleText();

            //create temps for getting stuff from the data base and filling the table
            DataSet tempData = new DataSet();
            Label tempLabel = new Label();

            for(int i = 0; i < 10; i++){
                //get tempData from database
                String Manufacturer = null;
                String BrandName = null;
                //fill Manuefacturer and BrandName from temp
                tempLabel.setText(Manufacturer + "  |  " + BrandName);

                //holds  the UUID code for the pulled Data from the DataSet

                String tempCode;
                //add UUID to Specific Agents Database of codes

                //add the Label to the pane
                Inbox.getChildren().add(tempLabel);

                //add the result to the linked list
                Result tempResult = new Result(tempLabel, tempData);
                inboxData.add(tempResult);

                //set an onclick command to send screen to application screen
                //tempLabel.setOnMouseClicked();


            }
        }
        else {
            LogManager.println("Agent Inbox is not empty no new applications can be added");
        }
    }

    @FXML
    public void newApplication(){



    }





}

