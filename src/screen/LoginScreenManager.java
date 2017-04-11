package screen;

import base.LogManager;
import base.Main;
import database.DataSet;
import database.DatabaseManager;
import database.PasswordStorage;
import database.User;
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
    private Polygon backButton;
    @FXML
    private Button newUser;

    //fxml methods
    @FXML
    void loginClicked() {
        this.userName = usernameField.getText();
        this.usernameField.clear();
        User userType = null;
        try {
            userType = Main.databaseManager.login(userName, "");
        } catch (DatabaseManager.UserNotFoundException e) {
            e.printStackTrace();
        } catch (DatabaseManager.IncorrectPasswordException e) {
            e.printStackTrace();
        } catch (PasswordStorage.InvalidHashException e) {
            e.printStackTrace();
        } catch (PasswordStorage.CannotPerformOperationException e) {
            e.printStackTrace();
        }
        LogManager.println(userName+" wants to sign in, he is a "+userType);

        /* To be replaced in the future with actual database query */
/*        if (userType.equalsIgnoreCase("publicUser")) {
            Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
            LogManager.println("Public user "+ userName +" has signed in");
        }
        // Currently not implemented since manufacturerScreen is not made*/
        if (userType.getType().equals(EnumUserType.MANUFACTURER)) {
            //build a manufacturer and store it globally
            Main.setUser(userType);
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
            LogManager.println("Manufacturer " + userName + " has signed in");
//            ((ManufacturerInboxManager) ScreenManager.getCurrentScreen()).initialize();
        }
        else if (userType.getType().equals(EnumUserType.AGENT)) {
            //build an agent and store it globally
            LogManager.println("we have an agent!");
            Main.setUser(userType);
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
            LogManager.println("Agent " + userName + " has signed in");
        }
    }


    @FXML
    void enterHit() {
        this.loginClicked();
        return;
    }

    @FXML
    void goBack() {
        // Tell the screen manager to set the screen to COLA screen
        LogManager.println("Back Button");
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
        return;
    }

    @FXML
    void userSignUp(){
        //tell the screen manager to go to the create account screen
        //just in case, clear the text field when you leave
        Main.screenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        return;
    }

    @Override
    public void onScreenFocused(DataSet data) {
        usernameField.clear();
    }
}
