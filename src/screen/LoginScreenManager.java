package screen;

import base.LogManager;
import base.Main;
import database.DataSet;
import database.DatabaseManager;
import database.PasswordStorage;
import database.User;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    @FXML
    private Rectangle background;

    //fxml methods
    @FXML
    void loginClicked() {
        //clear any previous error messages
        error.visibleProperty().setValue(false);
        if (Main.getUsername().equals("")) {
            this.userName = usernameField.getText();
            String tempPassword = password.getText();
            this.usernameField.clear();

            if (userName.equals("")) {
                //print to screen, tell user to enter username, exit
                error.visibleProperty().setValue(true);
                error.setText("PLEASE ENTER A USERNAME");
                LogManager.println("need a username");
            } else {
                User curUser = null;
                try {
                    curUser = Main.databaseManager.login(userName, "");
                } catch (DatabaseManager.UserNotFoundException e) {
                    error.visibleProperty().setValue(true);
                    error.setText("Username does not exist");
                    e.printStackTrace();
                    return;
                } catch (DatabaseManager.IncorrectPasswordException e) {
                    e.printStackTrace();
                } catch (PasswordStorage.InvalidHashException e) {
                    e.printStackTrace();
                } catch (PasswordStorage.CannotPerformOperationException e) {
                    e.printStackTrace();
                }

                Enum userType = curUser.getType();
                Main.setUser(curUser);
                if (userType.equals(EnumUserType.PUBLIC_USER)) {
                    Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
                }
                // Currently not implemented since manufacturerScreen is not made
                else if (userType.equals(EnumUserType.MANUFACTURER)) {
                    //build a manufacturer and store it globally
                    //User currentUser = new User(EnumUserType.MANUFACTURER, userName, "");
                    //Main.setUser(currentUser);
                    Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
                } else if (userType.equals(EnumUserType.AGENT)) {
                    //build an agent and store it globally
                    //User currentUser = new User(EnumUserType.AGENT, userName, "");
                    //Main.setUser(currentUser);
                    Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
                }
            }
        }else{
            //print there is already a user signed in
            error.visibleProperty().setValue(true);
            //error.setText("NICE TRY "+ usernameField.getText().toUpperCase()+" BUT THERE'S ALREADY SOMEONE SIGNED IN, LOGOUT FIRST");
            error.setText("Sorry, there's already someone signed in");
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

    public void centerError(){
        double center;
        center = (background.getWidth()-error.getWrappingWidth())/2;
        error.setTextAlignment(TextAlignment.CENTER);
    }
}
