package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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
    private Pane Inbox;


    //lists for keeping track of data sets
    private LinkedList<DataSet> pull = new LinkedList<>();
    private LinkedList<Label> clicks = new LinkedList<>();



    //constructer for the screen
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);
    }


    @FXML
    void goBack() {

    }

    @FXML
    void pullNewBatch(){
        //check if the inbox is empty if its not dont pull new batch
        //for loop 10 times
            //get a data set
            //populate table with said data set
            //fill in linked list with the actual data set
    }

    @FXML
    void newApplication(){


    }


    /*
        adds a new label to the pane
     */
    @FXML
    public void addLabel(){
        Label newLabel = new Label();
        Inbox.getChildren().add(newLabel);
    }

    /*
        remove a label from a pane
     */
    public void removeLabel(Label rLabel){
        Inbox.getChildren().remove(rLabel);
    }

    /*
        gets applications from the database
     */
    private LinkedList<DataSet> getPendingApps(String key){
        //creates a temp DataSet to store information
        DataSet temp = new DataSet();

        for(int i = 0; i < 10; i++){
            //get temp from database
            pull.add(temp);

        }


        //get one of right kind of key (beer or wine) set from the data base

        return pull;
    }






}

