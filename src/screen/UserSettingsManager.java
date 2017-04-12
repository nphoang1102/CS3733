package screen;

import base.LogManager;
import base.Main;
import database.Application;
import database.DataSet;
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
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

        //set screen to manufacturer
        Main.screenManager.back();
    }



    @FXML
    private void EditProfilePic(){
        if(Main.getUsername().isEmpty()){
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String filename = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();


        if(!filename.endsWith(".jpg")){
            return;
        }

        LogManager.println("File:"+filename);

        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        try {
            client.connect("72.93.244.26");
            client.login("cadbo", "seafoamgreen");

            fis = new FileInputStream(filename);
            client.storeFile("TTB/users/"+Main.getUsername()+".jpg", fis);
            client.logout();
            fis.close();
            LogManager.println("Uploading image as:"+"TTB/users/"+Main.getUsername()+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScreenFocused(DataSet data) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.showOpenDialog(stage);
    }
}