package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by ${Victor} on 4/9/2017.
 */
public class UserSettingsManager extends Screen {
    /* Class attributes */
    private EnumScreenType type;
    private String userName;
    private Enum mode;
    Stage primaryStage = new Stage();
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
    private Button saveChangesButton, editProfilePic;

    @FXML
    private void saveChanges() {
        //talk to database to save all the changes
        String username = Main.getUsername();
        //UserManufacturer man = (UserManufacturer) DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", username).get(0);

        UserManufacturer man = (UserManufacturer) Main.getUser();

        LogManager.println(man.name);
        LogManager.println(man.PhoneNo);
        LogManager.println(man.RepID);
        LogManager.println(man.PlantRegistry);
        LogManager.println(man.email);

        man.name = firstName.getText()+" "+lastName.getText();
        man.PhoneNo = phoneNumber.getText();
        man.RepID = representativeIdNumber.getText();
        man.PlantRegistry = plantRegistryBasicPermitNumber.getText();
        man.email = email.getText();

        LogManager.println(man.name);
        LogManager.println(man.PhoneNo);
        LogManager.println(man.RepID);
        LogManager.println(man.PlantRegistry);
        LogManager.println(man.email);

        DatabaseManager.updateManufacturer(man);

        //set screen to manufacturer
        Main.screenManager.back();
    }



    @FXML
    private void EditProfilePic(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(primaryStage);
    }

    @Override
    public void onScreenFocused(DataSet data) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.showOpenDialog(stage);
        if(Main.getUserType()!=null){
            if(Main.getUserType().equalsIgnoreCase("manufacturer")){
                //if we have a manufacturer fire up all the fields
                firstName.visibleProperty().setValue(true);
                lastName.visibleProperty().setValue(true);
                email.visibleProperty().setValue(true);
                phoneNumber.visibleProperty().setValue(true);
                representativeIdNumber.visibleProperty().setValue(true);
                plantRegistryBasicPermitNumber.visibleProperty().setValue(true);
            }
            if(Main.getUserType().equalsIgnoreCase("agent")){
                //if we have an agent, hide manufacturer fields
                email.visibleProperty().setValue(false);
                phoneNumber.visibleProperty().setValue(false);
                representativeIdNumber.visibleProperty().setValue(false);
                plantRegistryBasicPermitNumber.visibleProperty().setValue(false);
            }
        }

        UserManufacturer man = (UserManufacturer) Main.getUser();
        firstName.setText(man.name.split(" ")[0]);
        lastName.setText(man.name.split(" ")[1]);
        email.setText(man.email);
        phoneNumber.setText(man.PhoneNo);
        representativeIdNumber.setText(man.RepID);
        plantRegistryBasicPermitNumber.setText(man.PlantRegistry);
    }
}