package screen;

import base.LogManager;
import database.DataSet;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by $(mrfortmeyer) on 4/4/2017.
 */
public class EditableApplicationManager extends Screen {

    @FXML
    private TextField REPID;

    @FXML
    private TextField Source;

    @FXML
    private TextField BrandName;

    @FXML
    private TextField ApplicantName;

    @FXML
    private TextField DBAorTradeName;

    @FXML
    private TextField PhoneNum;

    @FXML
    private TextField Registry;

    @FXML
    private TextField Type;

    @FXML
    private TextField Address;

    @FXML
    private TextField AlternateAddress;

    @FXML
    private TextField Email;

    @FXML
    private TextField Date;

    @FXML
    private RadioButton BeerSelect;

    @FXML
    private RadioButton WineSelect;

    @FXML
    private RadioButton OtherSelect;

    @FXML
    private TextField AlcoholContent;

    @FXML
    private TextField VintageYear;

    @FXML
    private TextField PHLevel;

    @FXML
    private Button SubmitButton;

    @FXML
    private Button CancelButton;

    public DataSet data;

    String manufacturer = "";

    public EditableApplicationManager() {
        super(EnumScreenType.MANUFACTURER_EDIT);
        initialize();
    }

    public void initialize(){

        REPID.setText(data.getValueForKey("AgentID"));
        Source.setText(data.getValueForKey("Source"));
        BrandName.setText(data.getValueForKey("Brand"));
        ApplicantName.setText("");
        DBAorTradeName.setText("");
        PhoneNum.setText(data.getValueForKey("PhoneNo"));
        Registry.setText("");
        Type.setText(data.getValueForKey("AlcoholType"));
        Address.setText(data.getValueForKey("Address"));
        AlternateAddress.setText(data.getValueForKey("Address2"));
        Email.setText("");
        Date.setText(data.getValueForKey("Completed Date"));
        AlcoholContent.setText(data.getValueForKey("ABV"));
        VintageYear.setText(data.getValueForKey("VintageDate"));
        PHLevel.setText(data.getValueForKey("PH"));

        REPID.setDisable(true);
        Source.setDisable(true);
        BrandName.setDisable(true);
        ApplicantName.setDisable(true);
        DBAorTradeName.setDisable(true);
        Registry.setDisable(true);
        Address.setDisable(true);
        AlternateAddress.setDisable(true);
        Email.setDisable(true);
        Date.setDisable(true);
        AlcoholContent.setDisable(true);
        VintageYear.setDisable(true);
        PHLevel.setDisable(true);
        BeerSelect.setDisable(true);
        WineSelect.setDisable(true);
        OtherSelect.setDisable(true);
    }

    public void submit(){
        String repID = REPID.getText();
        String source = Source.getText();
        String brandName = BrandName.getText();
        String applicantName = ApplicantName.getText();
        String dba_or_tradeName = DBAorTradeName.getText();
        String phoneNum = PhoneNum.getText();
        String registry = Registry.getText();
        String type = Type.getText();
        String address = Address.getText();
        String alternateAddress = AlternateAddress.getText();
        String email = Email.getText();
        String date = Date.getText();
        String alcoholContent = AlcoholContent.getText();
        String vintageYear = VintageYear.getText();
        String phLevel = PHLevel.getText();
        Boolean beerSelect = BeerSelect.isSelected();
        Boolean wineSelect = WineSelect.isSelected();
        Boolean otherSelect = OtherSelect.isSelected();

        //Databasessssssssssss
        String TTBID = DatabaseManager.generateTTBID();

        database.DatabaseManager.submitApplication(TTBID, manufacturer, Registry.getText(), DBAorTradeName.getText(), Type.getText(), REPID.getText(), Source.getText(), BrandName.getText(), Address.getText(), AlternateAddress.getText(), "", AlcoholContent.getText(), PhoneNum.getText(), "", VintageYear.getText(), PHLevel.getText());

        LogManager.println("Submitting Application");
        ScreenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
    }

    public void goBack() {
        LogManager.println("Back button pressed from ManufacturerInboxScreen");
        ScreenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        return;
    }

    public Screen getScreen(){
        return this;
    }
}
