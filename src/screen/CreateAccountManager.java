package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.*;
import base.User;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import sun.management.Agent;

import java.util.LinkedList;

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
    @FXML
    private CheckBox tickAgent;
    @FXML
    private CheckBox tickManufacturer;
    //@FXML
    //private CheckBox tickPublicUser;
    @FXML
    private Text accountError;

    String userType;

    public void clearFields(){
        username.clear();
        accountError.setText(null);
        tickManufacturer.setIndeterminate(false);
        tickManufacturer.setSelected(false);
        tickAgent.setIndeterminate(false);
        tickAgent.setSelected(false);
    }
    /* Class methods */
    @FXML
    private void goBack(){
        //go back to the login screen
        clearFields();
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    @FXML
    private void makeAccount(){
        String user = username.getText();
        clearFields();
        //query database to get all usernames
        //check if user is in the list

        if(user!=null) { //placeholder for now
            //tell the system who made an account
            LogManager.println(user + " just made an account");

            //record the account in the database
            if(userType == "AGENT") {
                //tell the system what typ of user they are
                LogManager.println(user+" is a "+userType);
                //create new agent, no password
                DatabaseManager.addUser(user,"", userType);
                ScreenManager.setScreen(EnumScreenType.LOG_IN);
            }else if(userType == "MANUFACTURER"){
                //tell the system what typ of user they are
                LogManager.println(user+" is a "+userType);
                //create new manufacturer, no password
                DatabaseManager.addUser(user,"", userType);
                ScreenManager.setScreen(EnumScreenType.LOG_IN);
            }else if(userType == "publicUser"){
                //tell the system what typ of user they are
                LogManager.println(user+" is a "+userType);
                //create new manufacturer, no password
                DatabaseManager.addUser(user,"","publicUser");
                ScreenManager.setScreen(EnumScreenType.LOG_IN);
            } else{ //they didn't select a box
                //tell the system they didn't select a box
                LogManager.println(user+" didn't select a user type");
                //repopulate the field with their name
                accountError.setText(user+" select a box");
            }
        }else {
            //if name is taken, return to the make account screen
            accountError.setText("I'm sorry" + user+ ", that account is taken");
            ScreenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        }
        return;
    }
    @FXML
    private void enterHit(){
        makeAccount();
        return;
    }
    @FXML
    private void selectAgent(){
        //untick others
        tickManufacturer.setSelected(false);
        tickManufacturer.setIndeterminate(false);
        /*tickPublicUser.setSelected(false);
        tickPublicUser.setIndeterminate(false);*/
        userType = "AGENT";

    }
    @FXML
    private void selectManufacturer(){
        //untick others
        tickAgent.setSelected(false);
        tickAgent.setIndeterminate(false);
        /*tickPublicUser.setSelected(false);
        tickPublicUser.setIndeterminate(false);*/
        userType = "MANUFACTURER";
    }
   /* @FXML
    private void selectPublicUser(){
        //untick others
        tickAgent.setSelected(false);
        tickAgent.setIndeterminate(false);
        tickManufacturer.setSelected(false);
        tickManufacturer.setIndeterminate(false);
        userType = "publicUser";
    }*/
}

