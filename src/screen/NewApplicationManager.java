package screen;

import base.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    }

}
