package screen;


/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.*;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import database.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import sun.management.Agent;

import java.util.Arrays;
import java.util.LinkedList;

public class CreateAccountManager extends Screen{
    public CreateAccountManager() {
        super(EnumScreenType.CREATE_ACCOUNT);
    }

    /* FXML objects */
    @FXML private Button createAccount;
    @FXML private TextField username;
    @FXML private PasswordField password, passwordVerify;
    @FXML private CheckBox tickAgent;
    @FXML private CheckBox tickManufacturer;
    @FXML private Text accountError;
    @FXML private ProgressBar security;

    EnumUserType userType;

    //observer stuff
    private Subject subject;


    public void startObserver(){
        subject = new Subject();
        new SecurityBarObserver(subject, security);
    }

    /**
     * Value entered into the celsius box, update observers to convert into the other data types
     */
    public void notifyObservers(){
        // Write this method
        // You need to set the value of the subject to the input celsius value
        subject.setValue(password.getText(), username.getText());
    }



    private void clearFields(){
        username.clear();
        password.clear();
        passwordVerify.clear();
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
        String user = username.getText();
        String curPassword = password.getText();
        String verPassword = passwordVerify.getText();
        //clear any previous error messages
        accountError.setText("");

        //query database to get all usernames
        //check if user is in the list
        if(!user.equals("")&&!curPassword.equals("")) {
            if (userType != null) {
                if(curPassword.equals(verPassword)) {
                    //record the account in the database
                    if (userType.equals(EnumUserType.AGENT)) {
                        UserAgent tempUser = new UserAgent("",user,"",Math.random()+"","false","pending");
                        //String name, String username, String email, String ID, String isSuper, String status
                        //UserAgent tempUser = new UserAgent(user);
                        //create new agent, with password
                        //DatabaseManager.addUser(tempUser, password.getText(), userType);
                        //set agent to pending
                        //tempUser.setStatus("pending");
                        tempUser.PasswordHash = password.getText();

                        //send a new agent to the edit account screen, clear all fields
                        clearFields();
                        Main.screenManager.popoutScreen(EnumScreenType.AGENT_VERIFY, "Agent Verify", 800, 400,tempUser);
                    } else if (userType.equals(EnumUserType.MANUFACTURER)) {
                        UserManufacturer tempUser = new UserManufacturer(user);
                        //create new manufacturer, no password
                        try {
                            DatabaseManager.addUser(tempUser, password.getText(), userType);
                        }catch(DatabaseManager.DuplicateUserException e){
                            LogManager.println("caught DuplicateUserException");
                            clearFields();
                            accountError.setText("Sorry, that username is already taken.");
                            return;
                        }

                        Main.setUser(tempUser);
                        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
                        //Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
                    }
                }else{//passwords don't match
                    accountError.setText("Passwords do not match.");
                    password.clear();
                    passwordVerify.clear();
                }
            } else { //they didn't select a box
                //repopulate the field with their name
                accountError.setText( "Please select an account type.");
            }
        }else {//user didn't enter a username or password
            accountError.setText("Please enter a username and a password.");
        }
        /* {
            //if name is taken, return to the make account screen
            accountError.setText("I'm sorry" + user+ ", that account is taken");
            Main.screenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        }*/

    }

    @FXML
    private void enterHit(){
        makeAccount();
        return;
    }
    @FXML
    private void enterPassword(){
        makeAccount();
        return;
    }
    @FXML
    private void enterPasswordVerify(){
        makeAccount();
        return;
    }
    @FXML
    private void selectAgent(){
        //check if the agent box is currently selected
        if(tickAgent.selectedProperty().getValue()) {
            //untick others
            tickManufacturer.setSelected(false);
            tickManufacturer.setIndeterminate(false);
            userType = EnumUserType.AGENT;
        }else{
            userType = null;
        }
    }
    @FXML
    private void selectManufacturer(){
        //check if manufacturer box is currently selected
        if(tickManufacturer.selectedProperty().getValue()) {
            //untick others
            tickAgent.setSelected(false);
            tickAgent.setIndeterminate(false);
            userType = EnumUserType.MANUFACTURER;
        }else{
            userType = null;
        }
    }

    @FXML
    private void updateSecurity(){
        notifyObservers();
    }

    @Override
    public void onScreenFocused(DataSet data) {
        //fire up the observer
        startObserver();
        //hide the security bar
        security.setVisible(false);
        //clear all
        clearFields();
    }
}