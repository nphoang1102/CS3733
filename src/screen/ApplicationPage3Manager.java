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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by ${mrfortmeyer} on 4/18/2017.
 */

public class ApplicationPage3Manager extends Screen{
    public ApplicationPage3Manager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private TextField serial_number_field;

    @FXML
    private TextArea address_field;

    @FXML
    private TextArea address_field_2;

    @FXML
    private TextField abv_field;

    @FXML
    private Button submit_button;

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
        app.Address = address_field.getText();
        app.Address2 = address_field_2.getText();
        app.ABV = abv_field.getText();

        Main.screenManager.closeCurrentPopOut();

        if(app.AlcoholType.equals("Wine")) {
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_WINE, "Wine Info", 1020, 487, app);
        } else{
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_5, "Page 5", 1020, 487, app);
        }
    }

}
