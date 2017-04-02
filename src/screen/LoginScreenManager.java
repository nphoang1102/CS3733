package screen;

import base.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.awt.*;

public class LoginScreenManager extends Screen{

    private EnumScreenType type;
    //set up the fxml fields and stuff
    @FXML
    private TextField usernameField;

    @FXML
    private Ellipse loginButton;

    @FXML
    private Polygon backButton;

    @FXML
    private Text loginLabel;

    @FXML
    private Button signSup;

    //constructor
    public LoginScreenManager() {
        super(EnumScreenType.LOG_IN);
    }

    //fxml methods
    @FXML
    void userLogin() {
        String username = usernameField.getText();
    }

    @FXML
    void goBack() {
        //tell the screen manager to set the screen to COLA screen
        LogManager.println("Back Button");
        ScreenManager.setScreen(EnumScreenType.AGENT_APP_SCREEN);
    }

    @FXML
    void userSignUp(){
        //tell the screen manager to go to the create account screen
    }
}
