package screen;

import base.LogManager;
import base.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.lang.*;

/**
 * Created by tj on 4/3/2017.
 */
public class NewApplicationManager extends Screen {

    public NewApplicationManager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private TextField REPID;

    @FXML
    private TextField Source;

    @FXML
    private TextField BrandName;

    @FXML
    private TextField ApplicantName;

    @FXML
    private TextField PhoneNum;

    @FXML
    private TextField Registry;

    @FXML
    private TextField Type;

    @FXML
    private TextArea Address;

    @FXML
    private TextField AlternateAddress;

    @FXML
    private TextField Email;

    @FXML
    private TextField Date;

    @FXML
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

    @FXML
    private Button SubmitButton;

    @FXML
    private Button CancelButton;

    public void submit(){
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
