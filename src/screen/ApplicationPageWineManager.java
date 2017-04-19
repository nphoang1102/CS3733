package screen;

import base.EnumTableType;
import base.Main;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import database.UserManufacturer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by ${mrfortmeyer} on 4/18/2017.
 */

public class ApplicationPageWineManager extends Screen{
    public ApplicationPageWineManager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private TextField appellation_field;

    @FXML
    private TextField grapes_field;

    @FXML
    private Label ph_label;

    @FXML
    private Label vintage_label;

    @FXML
    private TextField ph_field;

    @FXML
    private TextField vintage_field;

    @FXML
    private Label app_type_label;

    @FXML
    private Button submit_button1;

    @FXML
    private Button cancel_button1;

    private Application app;
    private Boolean allFilled;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.app = (Application) dataSet;

        appellation_field.setText(app.WineAppelation);
        vintage_field.setText(app.VintageDate);
        ph_field.setText(app.PH);
        grapes_field.setText(app.Grapes);
    }

    @FXML
    void goBack() {
        app.WineAppelation = appellation_field.getText();
        app.VintageDate = vintage_field.getText();
        app.PH = ph_field.getText();
        app.Grapes = grapes_field.getText();

        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_3, "Page 3", 1020, 487, app);
    }

    @FXML
    void submit() {
        allFilled = true;
        app.WineAppelation = appellation_field.getText();
        app.VintageDate = vintage_field.getText();
        app.PH = ph_field.getText();
        app.Grapes = grapes_field.getText();

        if(app.WineAppelation == null || app.WineAppelation.equals("")){
            allFilled = false;
            appellation_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.VintageDate == null || app.VintageDate.equals("")){
            allFilled = false;
            vintage_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.PH == null || app.PH.equals("")){
            allFilled = false;
            ph_field.setStyle("-fx-border-color: #ff0800;");
        }
        if(app.Grapes == null || app.Grapes.equals("")){
            allFilled = false;
            grapes_field.setStyle("-fx-border-color: #ff0800;");
        }

        if(allFilled) {
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_5, "Page 5", 1020, 487, app);
        }
    }

}
