package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import base.*;
import database.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField username, password;
    @FXML
    private Polygon backButton;
    @FXML
    private CheckBox tickAgent;
    @FXML
    private CheckBox tickManufacturer;
    @FXML
    private Slider securitySlider;
    //@FXML
    //private CheckBox tickPublicUser;
    @FXML
    private Text accountError;
    @FXML
    private ProgressBar progressBar;

    EnumUserType userType;

    private void clearFields(){
        username.clear();
        password.clear();
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
        clearFields();
        //query database to get all usernames
        //check if user is in the list
        if(!user.equals("")) {
            if ((userType != null)&&(curPassword!=null)) { //placeholder for now
                //tell the system who made an account
                LogManager.println(user + " just made an account");

                //record the account in the database
                if (userType.equals(EnumUserType.AGENT)) {
                    UserAgent tempUser = new UserAgent(user);
                    //tell the system what type of user they are
                    LogManager.println(user + " is a " + userType);
                    //create new agent, no password
                    DatabaseManager.addUser(tempUser, password.getText(), userType);
                    Main.setUser(tempUser);
                    LogManager.println(tempUser + "logged in");
                    Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);

                } else if (userType.equals(EnumUserType.MANUFACTURER)) {
                    UserManufacturer tempUser = new UserManufacturer(user);
                    //tell the system what typ of user they are
                    LogManager.println(user + " is a " + userType);
                    //create new manufacturer, no password
                    DatabaseManager.addUser(tempUser, password.getText(), userType);

                    Main.setUser(tempUser);
                    System.out.println(Main.getUsername());

                    Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
                }/*else if(userType.equalsIgnoreCase("publicUser")){
                User tempUser =  new User(username.getText());
                //tell the system what typ of user they are
                LogManager.println(user+" is a "+userType);
                //create new manufacturer, no password
                DatabaseManager.addUser(user,"",EnumUserType.MANUFACTURER);
                Main.screenManager.setScreen(EnumScreenType.LOG_IN);*/
            } else { //they didn't select a box
                //tell the system they didn't select a box
                LogManager.println(user + " didn't select a user type");
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
    private void checkPassword(){
        LogManager.println(String.valueOf(securitySlider.getBlockIncrement()));
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
    private void selectAgent(){
        //untick others
        tickManufacturer.setSelected(false);
        tickManufacturer.setIndeterminate(false);
        userType = EnumUserType.AGENT;

    }
    @FXML
    private void selectManufacturer(){
        //untick others
        tickAgent.setSelected(false);
        tickAgent.setIndeterminate(false);
        userType = EnumUserType.MANUFACTURER;
    }

    @Override
    public void onScreenFocused(DataSet data) {
        clearFields();
    }
}
