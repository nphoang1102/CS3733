package screen;

import base.LogManager;
import base.Main;
import base.User;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

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
        String userType = DatabaseManager.getUserType(userName);
        LogManager.println(userName+" wants to sign in, he is a "+userType);

        /* To be replaced in the future with actual database query */
        if (userType.equalsIgnoreCase("publicUser")) {
            Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
            LogManager.println("Public user "+ userName +" has signed in");
        }
        // Currently not implemented since manufacturerScreen is not made
        else if (userType.equalsIgnoreCase("manufacturer")) {
            //build a manufacturer and store it globally
            User currentUser = new User(EnumUserType.MANUFACTURER, userName, "");
            Main.setUser(currentUser);
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
            LogManager.println("Manufacturer " + userName + " has signed in");
//            ((ManufacturerInboxManager) ScreenManager.getCurrentScreen()).initialize();
        }
        else if (userType.equalsIgnoreCase("agent")) {
            //build an agent and store it globally
            LogManager.println("we have an agent!");
            User currentUser = new User(EnumUserType.AGENT, userName, "");
            Main.setUser(currentUser);
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
    public void onScreenFocused() {
        usernameField.clear();
    }
}
