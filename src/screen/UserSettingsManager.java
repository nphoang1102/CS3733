package screen;

import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by ${Victor} on 4/9/2017.
 */
public class UserSettingsManager extends Screen{
    /* Class attributes */
    private EnumScreenType type;
    private String userName;
    private Enum mode;
    /* Constructor */
    public UserSettingsManager() {
        super(EnumScreenType.LOG_IN);
    }
    /*
    String Username;
    String Password;
    String Company;
    String Name;
    String BreweryPermitNumber
    String email
    String phoneNumber
    String representativeIdNumber
    String plantRegistryBasicPermitNumber
     */
    @FXML
    private TextField newUsername, firstName, lastName;

    @FXML
    private void newUsernameEnterHit(){

    }
    @FXML
    private void firstNameEnterHit(){

    }
    @FXML
    private void lastNameEnterHit(){

    }
    @FXML
    private void newUsernameClearField(){
        if(newUsername.getText().equals("Insert new username")){
            newUsername.clear();
        }
    }
    @FXML
    private void firstNameClearField(){
        if(firstName.getText().equals("Insert your first name")){
            firstName.clear();
        }
    }
    @FXML
    private void lastNameClearField(){
        if(lastName.getText().equals("Insert your last name")) {
            lastName.clear();
        }
    }

    @Override
    public void onScreenFocused(DataSet data) {
        //check for user type from the database: if manufacturer
        mode = EnumUserType.MANUFACTURER;
        //newUsername.setVisible(false);
        newUsername.setText("Insert new username");
        firstName.setText("Insert your first name");
        lastName.setText("Insert your last name");
    }
}