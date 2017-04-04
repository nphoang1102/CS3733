package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;

public class CreateAccountManager extends Screen{
    public CreateAccountManager() {
        super(EnumScreenType.CREATE_ACCOUNT);
    }

    /* FXML objects */
    @FXML
    private Button createAccount;
    @FXML
    private TextField username;
    @FXML
    private Polygon backButton;


    /* Class methods */
    @FXML
    private void goBack(){
        //go back to the login screen
        username.clear();
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    @FXML
    private void makeAccount(){
        String user = username.getText();
        username.clear();
        //query database to see if username is taken
        if(user != "") { //placeholder for now
            //tell the system who made an account
            LogManager.println(user + " just made an account");
            //record the account in the database
            //TODO add user to database
            //assume everyone is an agent for iteration 1
            //go to the agent inbox
            ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }else {
            //if name is taken, return to the make account screen
            ScreenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        }
        return;
    }
    @FXML
    private void enterHit(){
        makeAccount();
        return;
    }
}

