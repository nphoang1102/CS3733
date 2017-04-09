package screen;

import base.LogManager;
import base.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
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
<<<<<<< HEAD
    private ComboBox<String> product_type_box;

    @FXML
    private TextField brand_name_field;
=======
    private TextField PhoneNum;
>>>>>>> 53ab168f2ec7b3139efd1c380d59bea9f4faaaa9

    @FXML
    private TextField fanciful_field;

    @FXML
    private TextField address_field;

    @FXML
<<<<<<< HEAD
    private TextField address_field_2;
=======
    private TextArea Address;
>>>>>>> 53ab168f2ec7b3139efd1c380d59bea9f4faaaa9

    @FXML
    private TextField formula_field;

    @FXML
    private TextField appelation_field;

    @FXML
    private TextField grapes_field;

    @FXML
<<<<<<< HEAD
    private TextField phone_num_field;

    @FXML
    private TextField email_field;

    @FXML
    private ComboBox<String> app_type_box;

    @FXML
    private TextField add_info_field;

    @FXML
    private TextField date_submitted_field;

    @FXML
    private TextField applicant_name_field;
=======
    private TextField AlcoholContent;

    @FXML
    private TextField VintageYear;

    @FXML
    private TextField PHLevel;

    @FXML
    private TextField SerialNo;

    @FXML
    private TextField FancifulName;

    @FXML
    private TextField Formula;

    @FXML
    private TextField WineAppellation;

    @FXML
    private TextField GrapeVarietals;

    @FXML
    private TextField AppType;

    @FXML
    private TextArea AdditionalInfo;
>>>>>>> 53ab168f2ec7b3139efd1c380d59bea9f4faaaa9

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;

    public void submit(){
<<<<<<< HEAD
        String repID = repid_field.getText();
        String dba_or_tradeName = plant_number_field.getText();
        String source = product_source_box.getValue();
        String serialNumber = serial_number_field.getText();
        String productType = product_type_box.getValue();
        String brandName = brand_name_field.getText();
        String fancifulName = fanciful_field.getText();
        String addressText = address_field.getText();
        String address2Text = address_field_2.getText();
        String formulaText = formula_field.getText();
        String appelationText = appelation_field.getText();
        String grapeVarietals = grapes_field.getText();
        String phoneNum = phone_num_field.getText();
        String email = email_field.getText();
        String appType = app_type_box.getValue();
        String addInfo = add_info_field.getText();
        String applicantName = applicant_name_field.getText();
        String dateSubmitted = date_submitted_field.getText();
=======
        String repID = REPID.getText();
        String registry = Registry.getText();
        String source = Source.getText();
        String serialNo = SerialNo.getText();
        String type = Type.getText();
        String brandName = BrandName.getText();
        String fancifulName = FancifulName.getText();
        String address = Address.getText();
        String alternateAddress = AlternateAddress.getText();
        String formula = Formula.getText();
        String grapeVarietals = GrapeVarietals.getText();
        String wineAppellation = WineAppellation.getText();
        String phoneNum = PhoneNum.getText();
        String email = Email.getText();
        String appType = AppType.getText();
        String additionalInfo = AdditionalInfo.getText();
        String date = Date.getText();
        String applicantName = ApplicantName.getText();
        String vintageYear = VintageYear.getText();
        String alcoholContent = AlcoholContent.getText();
        String phLevel = PHLevel.getText();

>>>>>>> 53ab168f2ec7b3139efd1c380d59bea9f4faaaa9
        String manufacturer = Main.getUsername();

        //Databasessssssssssss

        database.DatabaseManager.submitApplication(manufacturer, Registry.getText(), "PENDING", Type.getText(), REPID.getText(), Source.getText(), BrandName.getText(), Address.getText(), AlternateAddress.getText(), "", AlcoholContent.getText(), PhoneNum.getText(), "", VintageYear.getText(), PHLevel.getText(), ApplicantName.getText(), Date.getText(), "", Email.getText());

        LogManager.println("Submitting Application");
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
    }
    public Screen getScreen(){
        return this;
    }

    @Override
    public void onScreenFocused() {

    }
}
