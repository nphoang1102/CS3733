package screen;

import base.LogManager;
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
        /* To be replaced in the future with actual database query */
        if (this.userName.equals("user")) {
            ScreenManager.setScreen(EnumScreenType.COLA_SEARCH);
            LogManager.println("Public user sign in");
        }
        // Currently not implemented since manufacturerScreen is not made
        else if (this.userName.equals("manufacturer")) {
            ScreenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
            LogManager.println("Manufacturer sign in");
        }
        else if (this.userName.equals("agent")) {
            ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);
            LogManager.println("Agent sign in");
        }
        return;
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
        ScreenManager.setScreen(EnumScreenType.COLA_SEARCH);
        return;
    }

    @FXML
    void userSignUp(){
        //tell the screen manager to go to the create account screen
        ScreenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        return;
    }
}
