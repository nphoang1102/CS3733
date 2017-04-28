package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import com.sun.org.apache.xpath.internal.operations.Bool;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import database.UserManufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by ${mrfortmeyer} on 4/18/2017.
 */

public class ApplicationPage5Manager extends Screen{
    public ApplicationPage5Manager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private TextField serial_number_field;

    @FXML
    private TextArea add_info_field;

    @FXML
    private Button label_button;

    @FXML
    private Button submit_button1;

    @FXML
    private Button cancel_button1;

    @FXML
    private Label image_name;

    Stage primaryStage = new Stage();
    String filePath = "";

    private ImageView imagePreviewView;
    private Image imagePreview;
    private Application app;
    private Boolean allFilled;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.app = (Application) dataSet;
        image_name.setVisible(false);

        serial_number_field.setText(app.SerialNo);
        add_info_field.setText(app.AdditionalInfo);
    }

    @FXML
    void goBack() {
        app.SerialNo = serial_number_field.getText();
        app.AdditionalInfo = add_info_field.getText();

        Main.screenManager.closeCurrentPopOut();

        if(app.AlcoholType.equals("Wine")) {
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_WINE, "Wine Info", 1020, 487, app);
        } else{
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_3, "Page 3", 1020, 487, app);
        }
    }

    @FXML
    void submit() {
        allFilled = true;
        app.SerialNo = serial_number_field.getText();
        app.AdditionalInfo = add_info_field.getText();

        if(app.SerialNo == null || app.SerialNo.equals("")){
            allFilled = false;
            serial_number_field.setStyle("-fx-border-color: #ff0800;");
        } else{
            serial_number_field.setStyle(null);
        }

        if(allFilled) {
            app.ApplicationStatus = "PENDING";
            app.DateOfSubmission = StringUtilities.getDate();
            LogManager.println(app.DateOfSubmission);
            app.ManufacturerUsername = Main.getUsername();
            app.AgentUsername = "";
            app.ApprovedTTBID = DatabaseManager.generateTTBID();

            submit_button1.setDisable(true);

            database.DatabaseManager.submitApplication(app);

            LogManager.println("Submitting Application");


            FTPClient client = new FTPClient();
            FileInputStream fis = null;
            try {
                client.connect(Main.getConfigData("FTPIP")+"");
                client.login(Main.getConfigData("FTPUsername")+"", Main.getConfigData("FTPPassword")+"");
                client.setFileType(FTPClient.BINARY_FILE_TYPE);

                fis = new FileInputStream(filePath);
                client.storeFile("TTB/alcohol/" + app.ApprovedTTBID + ".png", fis);
                client.logout();
                fis.close();
                LogManager.println("Uploading image as:" + "TTB/alcohol/" + app.ApprovedTTBID + ".png");

            } catch (IOException e) {
                e.printStackTrace();
            }

            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        }
    }

    @FXML
    public void addLabel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String filename = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();


        if(!filename.endsWith(".png")){
            return;
        }

        LogManager.println("File:"+filename);
        filePath = filename;
        imagePreview = new Image("File:"+filename, 100.0, 100.0, true, false);
        if(imagePreviewView == null) {
            imagePreviewView = new ImageView();
            imagePreviewView.setImage(imagePreview);
            imagePreviewView.setLayoutX(image_name.getLayoutX());
            imagePreviewView.setLayoutY(image_name.getLayoutY() + 20.0);
            ((AnchorPane) image_name.getParent()).getChildren().add(imagePreviewView);
        } else {
            int index = ((AnchorPane) image_name.getParent()).getChildren().indexOf(imagePreviewView);
            ((ImageView)(((AnchorPane) image_name.getParent()).getChildren().get(index))).setImage(imagePreview);
        }


        image_name.setText(filename.split("/")[filename.split("/").length-1]);
        image_name.setVisible(true);

    }
}
