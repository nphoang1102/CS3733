package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
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

    private Application app;

    private Boolean allFilled;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.app = (Application) dataSet;

        LogManager.println(Main.getUsername());
        UserManufacturer man = (UserManufacturer) DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", Main.getUsername()).get(0);

        applicant_name_field.setText(man.name);
        plant_number_field.setText(man.PlantRegistry);
        email_field.setText(man.email);
        repid_field.setText(man.RepID);
        phone_num_field.setText(man.PhoneNo);
    }

    @FXML
    void goBack() {
    }

    @FXML
    void submit() {
        allFilled = true;
        app.RepID = repid_field.getText();
        app.PhoneNo = phone_num_field.getText();
        app.Email = email_field.getText();
        app.AgentName = applicant_name_field.getText();
        app.PlantRegistry = plant_number_field.getText();

        if(app.RepID == null || app.RepID.equals("")){
            allFilled = false;
            repid_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.PhoneNo == null || app.PhoneNo.equals("")){
            allFilled = false;
            phone_num_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.Email == null || app.Email.equals("")){
            allFilled = false;
            email_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.AgentName == null || app.AgentName.equals("")){
            allFilled = false;
            applicant_name_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.PlantRegistry == null || app.PlantRegistry.equals("")){
            allFilled = false;
            plant_number_field.setStyle("-fx-border-color: #ff0800;");
        }

        if(allFilled) {
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_2, "Page 2", 1020, 487, app);
        }
    }
}
