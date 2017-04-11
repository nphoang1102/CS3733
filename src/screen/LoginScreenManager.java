package screen;

import base.LogManager;
import base.Main;
import database.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import static base.Main.screenManager;

public class LoginScreenManager extends Screen{
    /* Class attributes */
    private EnumScreenType type;
    private String userName;

    /* Constructor */
    public LoginScreenManager() {
        super(EnumScreenType.LOG_IN);
    }

    /* FXML objects */
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button newUser;
    @FXML
    private Button editAccountButton;
    @FXML
    private Text noUser;
    //fxml methods
    @FXML
    void loginClicked() {
        this.userName = usernameField.getText();
        if(userName.equals("")){
            return;
        }
        this.usernameField.clear();

        User curUser = null;
        try {
            curUser = Main.databaseManager.login(userName,"");
        } catch (DatabaseManager.UserNotFoundException e) {
            e.printStackTrace();
        } catch (DatabaseManager.IncorrectPasswordException e) {
            e.printStackTrace();
        } catch (PasswordStorage.InvalidHashException e) {
            e.printStackTrace();
        } catch (PasswordStorage.CannotPerformOperationException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        //checks if the user is a super Agent and sets stuff appopriatly
        if(userType.getType().equals(EnumUserType.AGENT)){
            UserAgent tempAgent = (UserAgent) userType;
            if(tempAgent.getSuperAgent().equals("true")){
                tempAgent.setUserType(EnumUserType.SUPER_AGENT);
                userType = tempAgent;
            }
        }
=======

        Enum userType = curUser.getType();
>>>>>>> 1644b1f473692e2874b47d14b02f4061b2e6b0ed
        LogManager.println(userName+" wants to sign in, he is a "+userType);
        Main.setUser(curUser);
        LogManager.println(curUser.getUsername()+" is the current user");

        if(userName.equals("")){
            //print to screen, tell user to enter username, exit
            noUser.visibleProperty().setValue(true);
            noUser.setText("PLEASE ENTER A USERNAME");

        }else {
            if (userType.equals(EnumUserType.PUBLIC_USER)) {
                Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
                LogManager.println("Public user " + userName + " has signed in");
            }
            // Currently not implemented since manufacturerScreen is not made
            else if (userType.equals(EnumUserType.MANUFACTURER)) {
                //build a manufacturer and store it globally
                //User currentUser = new User(EnumUserType.MANUFACTURER, userName, "");
                //Main.setUser(currentUser);
                Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
                LogManager.println("Manufacturer " + userName + " has signed in");
            } else if (userType.equals(EnumUserType.AGENT)) {
                //build an agent and store it globally
                LogManager.println("we have an agent!");
                //User currentUser = new User(EnumUserType.AGENT, userName, "");
                //Main.setUser(currentUser);
                Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
                LogManager.println("Agent " + userName + " has signed in");
            }
        }
    }


    @FXML
    void enterHit() {
        this.loginClicked();
        return;
    }

    @FXML
    void userSignUp(){
        //tell the screen manager to go to the create account screen
        //just in case, clear the text field when you leave
        Main.screenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        return;
    }

    @FXML
    void editAccount(){
        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
    }

    @Override
    public void onScreenFocused(DataSet data) {
        usernameField.clear();
        noUser.visibleProperty().setValue(false);
    }
}
