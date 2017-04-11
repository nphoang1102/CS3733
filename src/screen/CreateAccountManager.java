package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.*;
import database.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import sun.management.Agent;

import javax.xml.crypto.Data;
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

    String userType="";
    String curUserName="";

    private void clearFields(){
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
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    @FXML
    private void makeAccount(){
        curUserName = username.getText();
        //tell the system who made an account
        LogManager.println(curUserName + " just made an account");
        clearFields();
        //query database to get all usernames
        //check if user is in the list

        if(username.getText()!=null) { //placeholder for now, in future check if they're the only user

            //record the account in the database
            if(userType.equalsIgnoreCase( "AGENT")) {
                //create the user
                UserAgent u = new UserAgent(curUserName, curUserName, "", "", false);

                //create new agent, no password
                Main.databaseManager.addUser(u,"", EnumUserType.AGENT);
                Main.screenManager.setScreen(EnumScreenType.LOG_IN);
            }else if(userType.equalsIgnoreCase( "MANUFACTURER")){
                //create the user
                UserManufacturer u = new UserManufacturer(curUserName);
                //create new manufacturer, no password
                Main.databaseManager.addUser(u,"", EnumUserType.MANUFACTURER);
                Main.screenManager.setScreen(EnumScreenType.LOG_IN);
            }else if(userType.equalsIgnoreCase("publicUser")){
//                //tell the system what typ of user they are
//                LogManager.println(curUserName+" is a "+userType);
//                //create new manufacturer, no password
//                DatabaseManager.addUser(user,"",EnumUserType.PUBLIC_USER);
//                Main.screenManager.setScreen(EnumScreenType.LOG_IN);
            } else{ //they didn't select a box
                //tell the system they didn't select a box
                LogManager.println(curUserName+" didn't select a user type");
                //repopulate the field with their name
                accountError.setText(curUserName+" select a box");
            }
        }else {
            //if name is taken, return to the make account screen
            accountError.setText("I'm sorry" + curUserName+ ", that account is taken");
            Main.screenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
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

    @Override
    public void onScreenFocused(DataSet data) {
        clearFields();
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

