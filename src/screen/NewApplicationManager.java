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
import java.util.Date;
import java.util.Calendar;

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
        app.Source = source;
        app.SerialNo = serialNumber;
        app.AlcoholType = productType;
        app.Brand = brandName;
        app.FanicifulName = fancifulName;
        app.Address = addressText;
        app.Address2 = address2Text;
        app.Formula = formulaText;
        app.Grapes = grapeVarietals;
        app.WineAppelation = appellationText;
        app.PhoneNo = phoneNum;
        app.Email = email;
        app.AppType = appType;
        app.AdditionalInfo = addInfo;
        app.Date = dateSubmitted;
        app.PrintName = applicantName;
        app.ABV = abv;
        app.VintageDate = vintageYear;
        app.PH = ph;
        app.Status = "PENDING";
        app.ApplicationNo = StringUtilities.getTTBID();
        app.DateOfExpiration = StringUtilities.getExpirationDate();
        app.ManufacturerUsername = manufacturer;

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

    @Override
    public void onScreenFocused(DataSet dataSet) {

    }
}
