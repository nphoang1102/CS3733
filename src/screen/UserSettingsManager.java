package screen;

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
    private void newUsernameEnterHit(){

    }
    @FXML
    private void firstNameEnterHit(){

    }
    @FXML
    private void lastNameEnterHit(){

    }
    @FXML
    private void companyEnterHit(){

    }
    @FXML
    private void emailEnterHit(){

    }
    @FXML
    private void breweryPermitNumberEnterHit(){

    }
    @FXML
    private void phoneNumberEnterHit(){

    }
    @FXML
    private void representativeIdNumberEnterHit(){

    }
    @FXML
    private void plantRegistryBasicPermitNumberEnterHit(){

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
    @FXML
    private void companyClearField(){
        if(company.getText().equals("Insert company name")) {
            company.clear();
        }
    }
    @FXML
    private void emailClearField(){
        if(email.getText().equals("Insert your email")) {
            email.clear();
        }
    }
    @FXML
    private void breweryPermitNumberClearField(){
        if(breweryPermitNumber.getText().equals("Insert brewery permit number")) {
            breweryPermitNumber.clear();
        }
    }
    @FXML
    private void phoneNumberClearField(){
        if(phoneNumber.getText().equals("Insert phone number")) {
            phoneNumber.clear();
        }
    }
    @FXML
    private void representativeIdNumberClearField(){
        if(representativeIdNumber.getText().equals("Insert rep Id Number")) {
            representativeIdNumber.clear();
        }
    }
    @FXML
    private void plantRegistryBasicPermitNumberClearField(){
        if(plantRegistryBasicPermitNumber.getText().equals("Insert plant registry basic permit number")) {
            plantRegistryBasicPermitNumber.clear();
        }
    }

    @FXML
    private void saveChanges(){
        //talk to database to save all the changes
    }

    @Override
    public void onScreenFocused(DataSet data) {
        //check for user type from the database: if manufacturer
        mode = EnumUserType.MANUFACTURER;
        //if user is manufacturer, hide all agent fields
        newUsername.setVisible(false);
        newUsername.setText("Insert new username");
        firstName.setText("Insert your first name");
        lastName.setText("Insert your last name");
        company.setText("Insert company name");
        email.setText("Insert your email");
        breweryPermitNumber.setText("Insert brewery permit number");
        phoneNumber.setText("Insert phone number");
        representativeIdNumber.setText("Insert rep Id Number");
        plantRegistryBasicPermitNumber.setText("Insert plant registry basic permit number");
    }
}