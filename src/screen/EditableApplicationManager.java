package screen;

import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Button submit_button;

    @FXML
    private Button cancel_button;

    public DataSet data;

    String manufacturer = Main.getUsername();

    public EditableApplicationManager() {
        super(EnumScreenType.MANUFACTURER_EDIT);
    }

    public void onScreenFocused(DataSet dataSet){
        //Load all field text
        if(dataSet instanceof Application) {
            Application application = (Application) dataSet;
            repid_field.setText(application.RepID);
            // brewNo exists as PlantRegistry in Data Set
            plant_number_field.setText(application.PlantRegistry);
            brand_name_field.setText(application.Brand);
            applicant_name_field.setText(application.AgentName);
            address_field.setText(application.Address);
            address_field_2.setText(application.Address2);
            phone_num_field.setText(application.PhoneNo);
            email_field.setText(application.Email);
            date_submitted_field.setText(application.DateOfSubmission);
            //What is TTBID in Dataset?
            // ttbId.setText(application.SerialNo);
            fanciful_field.setText(application.FancifulName);
            formula_field.setText(application.Formula);
            grapes_field.setText(application.Grapes);
            appellation_field.setText(application.WineAppelation);
            // alcoholContent exists as ABV in Data Set
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

        if(((Application) dataSet).ApplicationStatus.equals("APPROVED")) {
            // Disabled
            email_field.setDisable(true);
            app_type_box.setDisable(true);
            date_submitted_field.setDisable(true);
            applicant_name_field.setDisable(true);
            plant_number_field.setDisable(true);
            serial_number_field.setDisable(true);
            phone_num_field.setDisable(true);
            fanciful_field.setDisable(true);
            //vintage_field.setDisable(true); //NOT DISABLED
            //ph_field.setDisable(true); //NOT DISABLED
            //abv_field.setDisable(true); //NOT DISABLED
            formula_field.setDisable(true);
            brand_name_field.setDisable(true);
            repid_field.setDisable(true);
            //add_info_field.setDisable(true);
            address_field.setDisable(true);
            address_field_2.setDisable(true);

            RejectionField.setVisible(false);
            RejectionLabel.setVisible(false);

            //setVisibility of additional elements
        }
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
        String dateSubmitted = date_submitted_field.getText();
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
        app.DateOfSubmission = dateSubmitted;
        app.AgentName = applicantName;
        app.ABV = abv;
        app.VintageDate = vintageYear;
        app.PH = ph;
        app.ApplicationStatus = "PENDING";
        app.ApplicationNo = StringUtilities.getTTBID();
        app.DateOfExpiration = StringUtilities.getExpirationDate();
        app.ManufacturerUsername = manufacturer;
        app.AgentUsername = "";

        database.DatabaseManager.submitApplication(app);
        LogManager.println("Submitting Application");
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        return;
    }

    public Screen getScreen(){
        return this;
    }

}
