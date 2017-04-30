package screen;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
import database.*;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sun.rmi.runtime.Log;

import static base.Main.screenManager;
import static base.Main.user;

public class LoginScreenManager extends Screen {
    /* Class attributes */
    private EnumScreenType type;
    private String userName;

    /* Constructor */
    public LoginScreenManager() {
        super(EnumScreenType.LOG_IN);
    }

    /* FXML objects */
    @FXML
    private TextField usernameField, password;
    @FXML
    private Button loginButton;
    @FXML
    private Button newUser;
    @FXML
    private Button aboutUs;
    @FXML
    private Button editAccountButton;
    @FXML
    private Text error;
    @FXML
    private Rectangle background;

    //fxml methods
    @FXML
    void loginClicked() {
        //clear any previous error messages
        error.visibleProperty().setValue(false);

        this.userName = usernameField.getText();
        String curPass = password.getText();
        this.usernameField.clear();
        this.password.clear();

        if (userName.equals("")) {
            //print to screen, tell user to enter username, exit
            error.visibleProperty().setValue(true);
            error.setText("Please enter a username");
            LogManager.println("need a username");
            return;
        }

        User curUser = null;

        //login
        try {
            curUser = Main.databaseManager.login(userName, curPass);
        } catch (DatabaseManager.UserNotFoundException e) {
            error.visibleProperty().setValue(true);
            error.setText("Username does not exist");
//                    e.printStackTrace();
            return;
        } catch (DatabaseManager.IncorrectPasswordException e) {
            error.visibleProperty().setValue(true);
            error.setText("Incorrect password");
            //e.printStackTrace();
            return;
        } catch (PasswordStorage.InvalidHashException e) {
//                    e.printStackTrace();
            LogManager.println(e.getMessage(), EnumWarningType.ERROR);
        } catch (PasswordStorage.CannotPerformOperationException e) {
//                    e.printStackTrace();
            LogManager.println(e.getMessage(), EnumWarningType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.println("Something has gone horribly wrong with the login: " + e.getMessage());
            error.visibleProperty().setValue(true);
            error.setText("Login failed. Please try again.");
            return;
        }
        //set user as current user in main
        if (curUser == null) {
            error.visibleProperty().setValue(true);
            error.setText("Login failed. Please try again.");
            return;
        }

        Main.setUser(curUser);

        Enum userType = curUser.getType();
        if (userType.equals(EnumUserType.PUBLIC_USER)) {
            Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
        } else if (userType.equals(EnumUserType.MANUFACTURER)) {
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        } else if (userType.equals(EnumUserType.AGENT)) {
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        } else if (userType.equals(EnumUserType.SUPER_AGENT)) {
            Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT);
        }
    }

    @FXML
    void aboutUsClicked() {
        Main.screenManager.setScreen(EnumScreenType.STATUS_SCREEN);
    }

    @FXML
    void enterHit() {
        this.loginClicked();
        return;
    }

    @FXML
    void enterPassword() {
        this.loginClicked();
        return;
    }

    @FXML
    void userSignUp() {
        //tell the screen manager to go to the create account screen
        //just in case, clear the text field when you leave
        Main.screenManager.setScreen(EnumScreenType.CREATE_ACCOUNT);
        return;
    }

    @FXML
    void editAccount() {
        Main.screenManager.setScreen(EnumScreenType.EDIT_ACCOUNT);
    }

    @Override
    public void onScreenFocused(DataSet data) {
        usernameField.clear();
        password.clear();
        error.visibleProperty().setValue(false);
        //check if user has a type
        if (Main.getUserType().equals(null)) {
            return;
        } else if (Main.getUserType().equalsIgnoreCase("MANUFACTURER")) {
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
            return;
        } else if (Main.getUserType().equalsIgnoreCase("AGENT")) {
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
            return;
        }
    }
}
