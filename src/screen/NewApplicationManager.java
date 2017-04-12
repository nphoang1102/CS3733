package screen;

import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Application;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.Date;
import java.util.Calendar;

import javafx.collections.*;

import java.lang.*;

/**
 * Created by tj on 4/3/2017.
 */
public class NewApplicationManager extends Screen {

    public NewApplicationManager() {
        super(EnumScreenType.LOG_IN);
    }

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
    public void initialize() {

    }
    @Override
    public void onScreenFocused(DataSet dataSet) {
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
    }

    public void onTypeSelected() {
        switch(app_type_box.getValue()) {
            case "Certificate of Label Approval":
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
        app.DateOfSubmission = "1234-12-11";
        app.AgentName = applicantName;
        app.ABV = abv;
        app.VintageDate = vintageYear;
        app.PH = ph;
        app.ApplicationStatus = "PENDING";
        app.ApplicationNo = StringUtilities.getTTBID();
        app.DateOfExpiration = StringUtilities.getExpirationDate();
        app.ManufacturerUsername = manufacturer;
        //app.AgentUsername = "";

        database.DatabaseManager.submitApplication(app);

        LogManager.println("Submitting Application");

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
