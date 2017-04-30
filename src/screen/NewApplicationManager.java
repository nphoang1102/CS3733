package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

import javafx.collections.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import sun.plugin.javascript.navig.Anchor;

import java.lang.*;

/**
 * Created by tj on 4/3/2017.
 */
public class NewApplicationManager extends Screen {

    public NewApplicationManager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private AnchorPane top_pane;

    @FXML
    private TextField repid_field;

    @FXML
    private TextField plant_number_field;

    @FXML
    private ComboBox<String> product_source_box;

    @FXML
    private TextField serial_number_field;

    @FXML
    private ComboBox<String> product_type_box;

    @FXML
    private TextField brand_name_field;

    @FXML
    private TextField fanciful_field;

    @FXML
    private TextArea address_field;

    @FXML
    private TextArea address_field_2;

    @FXML
    private TextField formula_field;

    @FXML
    private TextField appellation_field;

    @FXML
    private TextField grapes_field;

    @FXML
    private TextField phone_num_field;

    @FXML
    private TextField email_field;

    @FXML
    private ComboBox<String> app_type_box;

    @FXML
    private TextArea add_info_field;

    @FXML
    private TextField date_submitted_field;

    @FXML
    private TextField applicant_name_field;

    @FXML
    private TextField ph_field;

    @FXML
    private TextField vintage_field;

    @FXML
    private TextField abv_field;

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;

    @FXML
    private Label app_type_label;

    @FXML
    private TextField app_type_field;

    @FXML
    private Label ph_label;

    @FXML
    private Label abv_label;

    @FXML
    private Label vintage_label;

    @FXML
    private Button label_button;

    private Application newApplication;
    private String filePath = "";
    private Label image_name = new Label();
    private Image imagePreview;
    private ImageView imagePreviewView;

    @FXML
    public void initialize() {

    }
    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.newApplication = (Application) dataSet;
        ObservableList<String> product_source = FXCollections.observableArrayList(
                "Domestic",
                "Imported");
        ObservableList<String> product_type = FXCollections.observableArrayList(
                "Beer",
                "Wine",
                "Distilled Spirits");
        ObservableList<String> application_type = FXCollections.observableArrayList(
                "Certificate of Label Approval",
                "Certificate of Exemption From Label Approval",
                "Distinctive Liqour Bottle Approval",
                "Resubmission After Rejection");
        product_type_box.setItems(product_type);
        product_source_box.setItems(product_source);
        app_type_box.setItems(application_type);
        app_type_field.setVisible(false);
        app_type_label.setVisible(false);
        app_type_label.setMaxWidth(Double.MAX_VALUE);

        UserManufacturer man = (UserManufacturer) DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", Main.getUsername()).get(0);

        //applicant_name_field.setText(man.name);
        //plant_number_field.setText(man.PlantRegistry);
        //email_field.setText(man.email);
        //repid_field.setText(man.RepID);
        //phone_num_field.setText(man.PhoneNo);
        //Fill out from PDF
        repid_field.setText(newApplication.RepID);
        plant_number_field.setText(newApplication.PlantRegistry);
        applicant_name_field.setText(newApplication.RepName);
        if(!(newApplication.Locality == null)) {
            product_source_box.setValue(newApplication.Locality);
        }
        if(!(newApplication.AlcoholType == null)) {
            product_type_box.setValue(newApplication.AlcoholType);
            if(newApplication.AlcoholType.equals("Wine")) {
                appellation_field.setText( newApplication.WineAppelation);
                grapes_field.setText(newApplication.Grapes);
            }
        }

        switch(newApplication.ApplicationType) {
            case "Certificate of Label Approval":
                app_type_box.setValue(newApplication.ApplicationType);
                break;
            default :
                break;
        }
        serial_number_field.setText(newApplication.SerialNo);
        brand_name_field.setText(newApplication.Brand);
        fanciful_field.setText(newApplication.FancifulName);
        address_field.setText(newApplication.Address);
        address_field_2.setText(newApplication.Address2);
        formula_field.setText(newApplication.Formula);
        add_info_field.setText(newApplication.AdditionalInfo);
        email_field.setText(newApplication.Email);
        phone_num_field.setText(newApplication.PhoneNo);
        image_name.setLayoutX(label_button.getLayoutX());
        image_name.setLayoutY(label_button.getLayoutY() + 20.0);
        top_pane.getChildren().addAll(image_name);
        //top_pane.getChildren().addAll(imagePreviewView);
    }

    public void onTypeSelected() {
        switch(app_type_box.getValue()) {
            case "Certificate of Label Approval":
                app_type_field.setVisible(false);
                app_type_label.setVisible(false);
                break;
            case "Certificate of Exemption From Label Approval":
                app_type_label.setText("For Sale Only in: ");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
            case "Distinctive Liqour Bottle Approval":
                app_type_label.setText("Total Bottle Capacity Before Closure:");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
            case "Resubmission After Rejection":
                app_type_label.setText("TTB ID: ");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
        }
    }

    public void submit() {
        Application app = newApplication;
        app.AlcoholType = product_type_box.getValue();
        app.Locality = product_source_box.getValue();
        app.ApplicationType = app_type_box.getValue();
        app.ABV = abv_field.getText();
        app.AdditionalInfo = add_info_field.getText();
        app.Address = address_field.getText();
        app.Address2 = address_field_2.getText();
        app.Brand = brand_name_field.getText();
        app.Email = email_field.getText();
        app.FancifulName = fanciful_field.getText();
        app.Formula = formula_field.getText();
        if(app.AlcoholType.equals("Wine")) {
            app.Grapes = grapes_field.getText();
            app.WineAppelation = appellation_field.getText();
            app.PH = ph_field.getText();
        }
        app.SerialNo = serial_number_field.getText();
        app.AdditionalInfo = add_info_field.getText();
        app.RepID = applicant_name_field.getText();

        Boolean allFilled = true;

        if(app.SerialNo == null || app.SerialNo.equals("")){
            allFilled = false;
            serial_number_field.setStyle("-fx-border-color: #ff0800;");
        } else{
            serial_number_field.setStyle(null);
        }
        if(app.Brand == null || app.Brand.equals("")) {
            allFilled = false;
            brand_name_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.PlantRegistry == null || app.PlantRegistry.equals("")) {
            allFilled = false;
            plant_number_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.Address == null || app.Address.equals("")) {
            allFilled = false;
            address_field.setStyle("-fx-border-color: #ff0800;");
        }

        if(allFilled) {
            app.ApplicationStatus = "PENDING";
            app.DateOfSubmission = StringUtilities.getDate();
            LogManager.println(app.DateOfSubmission);
            app.ManufacturerUsername = Main.getUsername();
            app.AgentUsername = "";
            app.ApprovedTTBID = DatabaseManager.generateTTBID();
            app.ApplicationNo = app.ApprovedTTBID;

            submit_button.setDisable(true);

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

    public void clearFields() {
        app_type_field.clear();
        repid_field.clear();
        address_field_2.clear();
        address_field.clear();
        serial_number_field.clear();
        abv_field.clear();
        appellation_field.clear();
        fanciful_field.clear();
        add_info_field.clear();
        applicant_name_field.clear();
        brand_name_field.clear();
        date_submitted_field.clear();
        email_field.clear();
        formula_field.clear();
        grapes_field.clear();
        ph_field.clear();
        phone_num_field.clear();
        plant_number_field.clear();
        vintage_field.clear();
    }

    public void addLabel(){
        Stage primaryStage = new Stage();
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

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");

        return;
    }
    public Screen getScreen(){
        return this;
    }


}
