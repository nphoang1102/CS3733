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
        }

        if(allFilled) {
            app.ApplicationStatus = "PENDING";
            app.DateOfSubmission = StringUtilities.getDate();
            LogManager.println(app.DateOfSubmission);
            app.DateOfExpiration = StringUtilities.getExpirationDate();
            app.ManufacturerUsername = Main.getUsername();
            app.AgentUsername = "";

            database.DatabaseManager.submitApplication(app);

            LogManager.println("Submitting Application");


            FTPClient client = new FTPClient();
            FileInputStream fis = null;
            try {
                client.connect("72.93.244.26");
                client.login("cadbo", "seafoamgreen");

                fis = new FileInputStream(filePath);
                client.storeFile("TTB/alcohol/" + app.ApprovedTTBID + ".jpg", fis);
                client.logout();
                fis.close();
                LogManager.println("Uploading image as:" + "TTB/alcohol/" + app.ApprovedTTBID + ".jpg");

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


        if(!filename.endsWith(".jpg")){
            return;
        }

        LogManager.println("File:"+filename);
        filePath = filename;
        image_name.setText(filename.split("/")[filename.split("/").length-1]);
        image_name.setVisible(true);

    }
}
