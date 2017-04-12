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
    private Button editAccountButton;
    @FXML
    private Text error;

    //fxml methods
    @FXML
    void loginClicked() {
        if (Main.getUsername().equals("")) {
            this.userName = usernameField.getText();
            String tempPassword = password.getText();
            this.usernameField.clear();

            User curUser = null;
            try {
                curUser = Main.databaseManager.login(userName, tempPassword);
            } catch (DatabaseManager.UserNotFoundException e) {
                LogManager.println("NO FUCKING USERS");
                e.printStackTrace();
                return;
            } catch (DatabaseManager.IncorrectPasswordException e) {
                LogManager.println("incorrect password");
                e.printStackTrace();
                return;
            } catch (PasswordStorage.InvalidHashException e) {
                LogManager.println("SOME WIERD ASS SHIT");
                e.printStackTrace();
                return;
            } catch (PasswordStorage.CannotPerformOperationException e) {
                LogManager.println("LAZY MOTHA FUCKA");
                e.printStackTrace();
                return;
            }

            Enum userType = curUser.getType();
            LogManager.println(userName + " wants to sign in, he is a " + userType);
            Main.setUser(curUser);
            LogManager.println(curUser.getUsername() + " is the current user");

            if (userName.equals("")) {
                //print to screen, tell user to enter username, exit
                error.visibleProperty().setValue(true);
                error.setText("PLEASE ENTER A USERNAME");

            } else {
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
        }else{
            //print there is already a user signed in
            error.visibleProperty().setValue(true);
            error.setText("NICE TRY "+ usernameField.getText().toUpperCase()+" BUT THERE's ALREADY SOMEONE SIGNED IN");
        }
    }


    @FXML
    void enterHit() {
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
        error.visibleProperty().setValue(false);
    }
}
