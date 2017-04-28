package screen;

import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Application;
import database.DataSet;
import database.images.ProxyImage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import sun.rmi.runtime.Log;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by $(mrfortmeyer) on 4/4/2017.
 */
public class EditableApplicationManager extends Screen {

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
    private Label RejectionLabel;

    @FXML
    private Label RejectionField;

    @FXML
    private TextField app_type_field;

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;

    @FXML
    private Button label_button;

    @FXML
    private ImageView image;

    @FXML
    private Label agent_label;

    @FXML
    private TextField agent_field;

    public Application data;

    String manufacturer = Main.getUsername();

    Stage primaryStage = new Stage();

    public EditableApplicationManager() {
        super(EnumScreenType.MANUFACTURER_EDIT);
    }

    public void onScreenFocused(DataSet dataSet){
        this.data = (Application) dataSet;
        setBoxes((Application) dataSet);

        if(((Application) dataSet).ApplicationStatus.equals("APPROVED")) {
            disableAll();
            agent_field.setText(((Application) dataSet).AgentName);

            if(((Application) dataSet).revisionNo == 1){
                LogManager.println("Edit Number 1");
                label_button.setDisable(false);
                label_button.setStyle("-fx-border-color: #34a88b;" + "-fx-background-color: #939393;");
            } else if(((Application) dataSet).revisionNo == 2){
                LogManager.println("Edit Number 2");
                label_button.setDisable(false);
                label_button.setStyle("-fx-border-color: #34a88b;" + "-fx-background-color: #939393;");
            } else if(((Application) dataSet).revisionNo == 3){
                LogManager.println("Edit Number 3");
                label_button.setDisable(false);
                label_button.setStyle("-fx-border-color: #34a88b;" + "-fx-background-color: #939393;");
            } else if(((Application) dataSet).revisionNo == 4){
                LogManager.println("Edit Number 4");
                grapes_field.setDisable(false);
                grapes_field.setEditable(true);
                grapes_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");

                appellation_field.setDisable(false);
                appellation_field.setEditable(true);
                appellation_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");
            } else if(((Application) dataSet).revisionNo == 5){
                LogManager.println("Edit Number 5");
                vintage_field.setDisable(false);
                vintage_field.setEditable(true);
                vintage_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");
            } else if(((Application) dataSet).revisionNo == 6){
                LogManager.println("Edit Number 6");
                label_button.setDisable(false);
                label_button.setStyle("-fx-border-color: #34a88b;" + "-fx-background-color: #939393;");
            } else if(((Application) dataSet).revisionNo == 7){
                LogManager.println("Edit Number 7");
                ph_field.setDisable(false);
                ph_field.setEditable(true);
                ph_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");
            } else if(((Application) dataSet).revisionNo == 8){
                LogManager.println("Edit Number 8");
            } else if(((Application) dataSet).revisionNo == 9){
                LogManager.println("Edit Number 9");
            } else if(((Application) dataSet).revisionNo == 10){
                LogManager.println("Edit Number 10");
                add_info_field.setDisable(false);
                add_info_field.setEditable(true);
                add_info_field.setStyle("-fx-background-color: #34a88b;");
            } else if(((Application) dataSet).revisionNo == 11){
                LogManager.println("Edit Number 11");
                abv_field.setDisable(false);
                abv_field.setEditable(true);
                abv_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");
            } else if(((Application) dataSet).revisionNo == 12){
                LogManager.println("Edit Number 12");
                abv_field.setDisable(false);
                abv_field.setEditable(true);
                abv_field.setStyle("-fx-background-color: #34a88b;" + "-fx-text-inner-color: #ffffff");
            }

            //setVisibility of additional elements
        } else if(((Application) dataSet).ApplicationStatus.equals("PENDING")) {
            disableAll();
            agent_field.setVisible(false);
            agent_label.setVisible(false);
            submit_button.setVisible(false);
        } else{
            agent_field.setVisible(false);
            agent_label.setVisible(false);
        }
        ProxyImage pImage = new ProxyImage(("alcohol/"+((Application) dataSet).ApprovedTTBID)+".png");
        pImage.displayImage(image);
    }

    public void submit(){
        String repID = repid_field.getText();
        String registryNumber = plant_number_field.getText();
        String source = product_source_box.getValue();
        String serialNumber = serial_number_field.getText();
        String productType = product_type_box.getValue();
        String brandName = brand_name_field.getText();
        String fancifulName = fanciful_field.getText();
        String addressText = address_field.getText();
        String address2Text = address_field_2.getText();
        String formulaText = formula_field.getText();
        String appellationText = appellation_field.getText();
        String grapeVarietals = grapes_field.getText();
        String phoneNum = phone_num_field.getText();
        String email = email_field.getText();
        String appType = app_type_box.getValue();
        String addInfo = add_info_field.getText();
        String applicantName = applicant_name_field.getText();
        String ph = ph_field.getText();
        String vintageYear = vintage_field.getText();
        String abv = abv_field.getText();

        String manufacturer = Main.getUsername();

        Application app = new Application();

        app.RepID = repID;
        app.PlantRegistry = registryNumber;
        app.Locality = source;
        app.SerialNo = serialNumber;
        app.AlcoholType = productType;
        app.Brand = brandName;
        app.FancifulName = fancifulName;
        app.Address = addressText;
        app.Address2 = address2Text;
        app.Formula = formulaText;
        app.Grapes = grapeVarietals;
        app.WineAppelation = appellationText;
        app.PhoneNo = phoneNum;
        app.Email = email;
        app.ApplicationType = appType;
        app.AdditionalInfo = addInfo;
        app.DateOfSubmission = StringUtilities.getDate();
        app.RepName = applicantName;
        app.ABV = abv;
        app.VintageDate = vintageYear;
        app.PH = ph;
        app.DateOfExpiration = StringUtilities.getExpirationDate();
        app.ManufacturerUsername = manufacturer;
        app.AgentUsername = "";
        app.ApplicationNo = data.ApplicationNo;
        app.ApprovedTTBID = data.ApprovedTTBID;

        if(data.ApplicationStatus.equals("REJECTED") || data.ApplicationStatus.equals("NEEDS WORK")){
            app.ApplicationStatus = "PENDING";
        } else {
            app.ApplicationStatus = data.ApplicationStatus;
        }

        database.DatabaseManager.editApplication(app);
        LogManager.println("Submitting Application");

        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
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

        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        try {
            client.connect(Main.getConfigData("FTPIP")+"");
            client.login(Main.getConfigData("FTPUsername")+"", Main.getConfigData("FTPPassword")+"");
            client.setFileType(FTP.BINARY_FILE_TYPE);

            fis = new FileInputStream(filename);
            client.storeFile("TTB/alcohol/"+data.ApplicationNo+".png", fis);
            client.logout();
            fis.close();
            LogManager.println("Uploading image as:"+"TTB/alcohol/"+data.ApplicationNo+".png");

            ScreenManager.updateUserIcon();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        return;
    }

    public void disableAll(){
        email_field.setDisable(true);
        app_type_box.setDisable(true);
        applicant_name_field.setDisable(true);
        plant_number_field.setDisable(true);
        serial_number_field.setDisable(true);
        phone_num_field.setDisable(true);
        fanciful_field.setDisable(true);
        vintage_field.setDisable(true); //NOT DISABLED
        ph_field.setDisable(true); //NOT DISABLED
        abv_field.setDisable(true); //NOT DISABLED
        formula_field.setDisable(true);
        brand_name_field.setDisable(true);
        repid_field.setDisable(true);
        add_info_field.setDisable(true);
        address_field.setDisable(true);
        address_field_2.setDisable(true);
        add_info_field.setDisable(true);
        appellation_field.setDisable(true);
        grapes_field.setDisable(true);
        product_source_box.setDisable(true);
        product_type_box.setDisable(true);
        label_button.setDisable(true);
        agent_field.setDisable(true);

        app_type_box.setDisable(true);
        app_type_field.setDisable(true);

        RejectionField.setVisible(false);
        RejectionLabel.setVisible(false);

        label_button.setDisable(true);
    }

    public void surrenderApp(){
        LogManager.println("Surrendering Application");
    }

    public void setBoxes(Application dataSet){
        if(dataSet instanceof Application) {
            LogManager.println("This application is: " + ((Application) dataSet).ApplicationStatus);

            Application application = (Application) dataSet;
            repid_field.setText(application.RepID);
            plant_number_field.setText(application.PlantRegistry);
            brand_name_field.setText(application.Brand);
            applicant_name_field.setText(application.RepName);
            LogManager.println("Rep Name " + application.RepName);
            address_field.setText(application.Address);
            address_field_2.setText(application.Address2);
            phone_num_field.setText(application.PhoneNo);
            email_field.setText(application.Email);
            add_info_field.setText(application.AdditionalInfo);
            fanciful_field.setText(application.FancifulName);
            formula_field.setText(application.Formula);
            grapes_field.setText(application.Grapes);
            appellation_field.setText(application.WineAppelation);
            abv_field.setText(application.ABV);
            ph_field.setText(application.PH);
            vintage_field.setText(application.VintageDate);
            RejectionField.setText(((Application) dataSet).ReasonForRejection);

            app_type_box.setValue(application.ApplicationType);
            product_source_box.setValue(application.Locality);
            product_type_box.setValue(application.AlcoholType);
        } else {
            LogManager.println("Error: DataSet dataSet passed to EditableApplicationManager was not Application");
        }
    }

    public Screen getScreen(){
        return this;
    }

}