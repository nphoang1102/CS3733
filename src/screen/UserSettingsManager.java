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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private CheckBox tickSuperAgent;
    @FXML
    private Pane screen_pane;

    @FXML
    private void saveChanges() {
        //talk to database to save all the changes
        updateUser(Main.user.getType());
        String username = Main.getUsername();





        //set screen to correct home page
        goHome(Main.user.getType());
    }

    private void updateUser(EnumUserType type){
        if(type.equals(EnumUserType.MANUFACTURER)){
            UserManufacturer man = (UserManufacturer) Main.getUser();

            LogManager.println(man.name + "<- name");
            LogManager.println(man.PhoneNo + "<- phone number");
            LogManager.println(man.RepID + "<-rep ID");
            LogManager.println(man.PlantRegistry + "<- plant reg");
            LogManager.println(man.email+ "<- email");

            man.name = firstName.getText()+" "+lastName.getText();
            man.PhoneNo = phoneNumber.getText();
            man.RepID = representativeIdNumber.getText();
            man.PlantRegistry = plantRegistryBasicPermitNumber.getText();
            man.Company = company.getText();
            man.BreweryPermitNumber = breweryPermitNumber.getText();
            man.email = email.getText();

            LogManager.println(man.name);
            LogManager.println(man.PhoneNo);
            LogManager.println(man.RepID);
            LogManager.println(man.PlantRegistry);
            LogManager.println(man.email);

            DatabaseManager.updateManufacturer(man);
        }else if(type.equals(EnumUserType.AGENT)){
            UserAgent agent = (UserAgent) Main.getUser();

            LogManager.println(agent.name + "<- name");
            LogManager.println(agent.email+ "<- email");

            agent.email = email.getText();
            agent.name = (firstName.getText() + " " + lastName.getText());
            agent.setSuperAgent(String.valueOf(tickSuperAgent.selectedProperty().getValue()));
            DatabaseManager.updateAgents(agent);
        }
    }

    private void goHome(EnumUserType type){
        if(type.equals(EnumUserType.MANUFACTURER)){
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        }else if(type.equals(EnumUserType.AGENT)){
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }else if(type.equals(EnumUserType.SUPER_AGENT)){
            Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT);
        }
    }

    @FXML
    private void EditProfilePic(){
        if(Main.getUsername().isEmpty()){
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String filename = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();


        if(!filename.endsWith(".png")){
            return;
        }

        //System.out.println("file:" + filename);
        Image profilePic = new Image("file:" + filename, 100.0, 100.0, false, false);
        ImageView profilePicView = new ImageView(profilePic);
        profilePicView.setX(editProfilePic.getLayoutX() + (editProfilePic.getWidth() / 2.0) );
        profilePicView.setY(editProfilePic.getLayoutY() + editProfilePic.getHeight());
        screen_pane.getChildren().add(profilePicView);

        LogManager.println("File:"+filename);

        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        try {
            client.connect(Main.getConfigData("FTPIP")+"");
            client.login(Main.getConfigData("FTPUsername")+"", Main.getConfigData("FTPPassword")+"");
            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            fis = new FileInputStream(filename);

            client.storeFile("TTB/users/"+Main.getUsername()+".png", fis);
            client.logout();
            fis.close();
            LogManager.println("Uploading image as:"+"TTB/users/"+Main.getUsername()+".png");

            ScreenManager.updateUserIcon();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScreenFocused(DataSet data) {
        clearFields();
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
                tickSuperAgent.setVisible(false);
            }
            if(Main.getUserType().equalsIgnoreCase("agent")){
                //if we have an agent, hide manufacturer fields
                phoneNumber.visibleProperty().setValue(false);
                representativeIdNumber.visibleProperty().setValue(false);
                plantRegistryBasicPermitNumber.visibleProperty().setValue(false);
                breweryPermitNumber.visibleProperty().setValue(false);
                company.setVisible(false);
                email.setLayoutX(breweryPermitNumber.getLayoutX());
                email.setLayoutY(breweryPermitNumber.getLayoutY());

                //if an agent has already been approved hide the super agent box
                tickSuperAgent.setVisible(false);
            }
        }

        if(Main.getUser() instanceof  UserManufacturer) {
            UserManufacturer man = (UserManufacturer) Main.getUser();
            if(!man.name.equals("") && man.name != null) {
                firstName.setText(man.name.split(" ")[0]);
                //try to fill in the last name field
                try {
                    lastName.setText(man.name.split(" ")[1]);
                }catch(java.lang.ArrayIndexOutOfBoundsException e){
                    LogManager.println(" user does not have a last name");
                }
            }
            email.setText(man.email);
            phoneNumber.setText(man.PhoneNo);
            company.setText(man.Company);
            representativeIdNumber.setText(man.RepID);
            breweryPermitNumber.setText(man.BreweryPermitNumber);
            plantRegistryBasicPermitNumber.setText(man.PlantRegistry);
        }

        if(Main.getUser() instanceof  UserAgent){
            UserAgent agent = (UserAgent) Main.getUser();
            if(!agent.name.equals("") && agent.name != null) {
                firstName.setText(agent.name.split(" ")[0]);
                //try to fill in the last name field
                try {
                    lastName.setText(agent.name.split(" ")[1]);
                }catch(java.lang.ArrayIndexOutOfBoundsException e){
                    LogManager.println(" user does not have a last name");
                }
            }
            email.setText(agent.email);
        }
    }

    @FXML
    private void selectSuperAgent(){
        //check if manufacturer box is currently selected
        if(tickSuperAgent.selectedProperty().getValue()) {
            Main.getUser().setType(EnumUserType.SUPER_AGENT);
        }else{
            Main.getUser().setType(null);
        }
    }

    private void clearFields(){
        firstName.clear();
        lastName.clear();
        company.clear();
        email.clear();
        breweryPermitNumber.clear();
        phoneNumber.clear();
        representativeIdNumber.clear();
        plantRegistryBasicPermitNumber.clear();
    }
}