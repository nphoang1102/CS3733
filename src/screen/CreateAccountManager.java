package screen;


/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.*;
import com.mysql.jdbc.StringUtils;
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

    String oldPassword="";

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
                        UserAgent tempUser = new UserAgent(user);
                        //create new agent, no password
                        DatabaseManager.addUser(tempUser, password.getText(), userType);
                        //set agent to pending
                        tempUser.setStatus("PENDING");
                        Main.setUser(tempUser);
                        //Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
                        //send a new agent to the edit account screen
                        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);

                    } else if (userType.equals(EnumUserType.MANUFACTURER)) {
                        UserManufacturer tempUser = new UserManufacturer(user);
                        //create new manufacturer, no password
                        DatabaseManager.addUser(tempUser, password.getText(), userType);

                        Main.setUser(tempUser);

                        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
                    }
                }else{//passwords don't match
                    accountError.setText(user + ", make sure you enter the same password");
                    password.clear();
                    passwordVerify.clear();
                }
            } else { //they didn't select a box
                //repopulate the field with their name
                accountError.setText(user + ", select a box.");
            }
        }else {//user didn't enter a username
            accountError.setText("No username or password");
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
        //show the progress bar
        security.setVisible(true);
        //record current password
        String curPassword = password.getText();
        //reset security level
        double securityLevel = 0.0;
        //check the password actually changed
        if(!curPassword.equals(oldPassword)) {

            LinkedList<String> badPasswords = new LinkedList<String>(Arrays.asList(
                    "1234567890",
                    "password",
                    "qwerty",
                    this.username.getText()
            ));

            //not a bad password
            for(String s: badPasswords){
                if(s.contains(curPassword)){
                    securityLevel -= .15;
                }
            }
            securityLevel += curPassword.length() / 10.0;

            if (curPassword.matches(".*\\d+.*")) {
                //password contains a number
                //award quite a few points
                securityLevel += .1;
            }
            if (curPassword.matches("^.*[^a-zA-Z0-9 ].*$")) {
                //password has a symbol
                //award a lot of points
                securityLevel += .15;
            }

            //keep level in bounds
            if(securityLevel < 0){
                securityLevel = 0;
            }else if(securityLevel > 1){
                securityLevel = 1;
            }
            security.setProgress(securityLevel);
            if (securityLevel <= .25) {
                //low security
                //make bar red
                security.setStyle("-fx-accent: #ff0000; -fx-focus-color: #34a88b;");
            } else if (securityLevel <= .7) {
                security.setStyle("-fx-accent: #FFF100; -fx-focus-color: #34a88b;");
            } else if (securityLevel > .7) {
                security.setStyle("-fx-accent:  #34a88b; -fx-focus-color: #34a88b;");
            }
            //update old password
            oldPassword = curPassword;
        }
    }

    @Override
    public void onScreenFocused(DataSet data) {
        security.setVisible(false);
        clearFields();
    }
}
