package screen;

import base.Main;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    /* notes for self
    String Username; -
    String Password;
    String Company;  -
    String Name;     -
    String BreweryPermitNumber -
    String email    -
    String phoneNumber -
    String representativeIdNumber -
    String plantRegistryBasicPermitNumber-
     */
    @FXML
    private TextField newUsername, firstName, lastName, company, email, breweryPermitNumber, phoneNumber, representativeIdNumber, plantRegistryBasicPermitNumber;
    @FXML
    private Button saveChangesButton;

    @FXML
    private void saveChanges(){
        //talk to database to save all the changes

        //set screen to manufacturer
        Main.screenManager.back();
    }

    @Override
    public void onScreenFocused(DataSet data) {

    }
}