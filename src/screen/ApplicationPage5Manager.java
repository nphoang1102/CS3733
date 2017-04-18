package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import database.UserManufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    private Application app;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.app = (Application) dataSet;
    }

    @FXML
    void clearFields() {

    }

    @FXML
    void submit() {
        app.SerialNo = serial_number_field.getText();
        app.AdditionalInfo = add_info_field.getText();

        app.ApplicationStatus = "PENDING";
        app.DateOfSubmission = StringUtilities.getDate();
        app.DateOfExpiration = StringUtilities.getExpirationDate();
        app.ManufacturerUsername = Main.getUsername();
        app.AgentUsername = "";

        database.DatabaseManager.submitApplication(app);

        LogManager.println("Submitting Application");

        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
    }

}
