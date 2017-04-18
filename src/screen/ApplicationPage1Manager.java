package screen;

import base.EnumTableType;
import base.Main;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import database.UserManufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by ${mrfortmeyer} on 4/18/2017.
 */

public class ApplicationPage1Manager extends Screen{
    public ApplicationPage1Manager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private TextField repid_field;

    @FXML
    private TextField plant_number_field;

    @FXML
    private TextField phone_num_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField applicant_name_field;

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;

    @FXML
    private Label app_type_label;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        UserManufacturer man = (UserManufacturer) DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", Main.getUsername()).get(0);

        applicant_name_field.setText(man.name);
        plant_number_field.setText(man.PlantRegistry);
        email_field.setText(man.email);
        repid_field.setText(man.RepID);
        phone_num_field.setText(man.PhoneNo);
    }

    @FXML
    void clearFields() {

    }

    @FXML
    void submit() {
        Application app = new Application();
        app.RepID = repid_field.getText();
        app.PhoneNo = phone_num_field.getText();
        app.Email = email_field.getText();
        app.AgentName = applicant_name_field.getText();
        app.PlantRegistry = plant_number_field.getText();

        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_2, "Page 2", 1020, 487, app);
    }

}
