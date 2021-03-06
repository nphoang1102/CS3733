package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by ${mrfortmeyer} on 4/18/2017.
 */

public class ApplicationPage2Manager extends Screen {
    public ApplicationPage2Manager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private Button submit_button;

    @FXML
    private Button cancel_button;

    @FXML
    private Label app_type_label;

    @FXML
    private ComboBox<String> product_source_box;

    @FXML
    private ComboBox<String> product_type_box;

    @FXML
    private TextField brand_name_field;

    @FXML
    private TextField fanciful_field;

    @FXML
    private TextField formula_field;

    @FXML
    private ComboBox<String> app_type_box;

    @FXML
    private TextField app_type_field;

    private Application app;
    private Boolean allFilled;

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.app = (Application) dataSet;

        ObservableList<String> product_source = FXCollections.observableArrayList(
                "Domestic",
                "Imported");
        ObservableList<String> product_type = FXCollections.observableArrayList(
                "Beer",
                "Wine",
                "Distilled Spirits");
        ObservableList<String> application_type = FXCollections.observableArrayList(
                "Certificate of Label Approval",
                "Certificate of Exemption From Label Approval",
                "Distinctive Liqour Bottle Approval",
                "Resubmission After Rejection");
        product_type_box.setItems(product_type);
        product_source_box.setItems(product_source);
        app_type_box.setItems(application_type);
        app_type_field.setVisible(false);
        app_type_label.setVisible(false);
        app_type_label.setMaxWidth(Double.MAX_VALUE);

        brand_name_field.setText(app.Brand);
        fanciful_field.setText(app.FancifulName);
        formula_field.setText(app.Formula);
        product_type_box.setValue(app.AlcoholType);
        product_source_box.setValue(app.Locality);
        app_type_box.setValue(app.ApplicationType);
    }

    @FXML
    void goBack() {
        app.Brand = brand_name_field.getText();
        app.FancifulName = fanciful_field.getText();
        app.Formula = formula_field.getText();
        app.AlcoholType = product_type_box.getValue();
        app.Locality = product_source_box.getValue();
        app.ApplicationType = app_type_box.getValue();

        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_1, "Page 1", 1020, 487, app);
    }

    @FXML
    public void onTypeSelected() {
        switch (app_type_box.getValue()) {
            case "Certificate of Label Approval":
                app_type_field.setVisible(false);
                app_type_label.setVisible(false);
                break;
            case "Certificate of Exemption From Label Approval":
                app_type_label.setText("For Sale Only in: ");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
            case "Distinctive Liqour Bottle Approval":
                app_type_label.setText("Total Bottle Capacity Before Closure:");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
            case "Resubmission After Rejection":
                app_type_label.setText("TTB ID: ");
                app_type_field.setVisible(true);
                app_type_label.setVisible(true);
                break;
        }
    }

    @FXML
    void submit() {
        allFilled = true;
        app.Brand = brand_name_field.getText();
        app.FancifulName = fanciful_field.getText();
        app.Formula = formula_field.getText();
        app.AlcoholType = product_type_box.getValue();
        app.Locality = product_source_box.getValue();
        app.ApplicationType = app_type_box.getValue();

        if (app.Brand == null || app.Brand.equals("")) {
            allFilled = false;
            brand_name_field.setStyle("-fx-border-color: #ff0800;");
        } else{
            brand_name_field.setStyle(null);
        }

        if (app.Formula == null || app.Formula.equals("")) {
            allFilled = false;
            formula_field.setStyle("-fx-border-color: #ff0800;");
        } else{
            formula_field.setStyle(null);
        }

        if (app.AlcoholType == null) {
            allFilled = false;
            product_type_box.setStyle("-fx-border-color: #ff0800;");
            LogManager.println("Alcohol type can't be null!");
        } else{
            product_type_box.setStyle(null);
        }

        if (app.Locality == null) {
            allFilled = false;
            product_source_box.setStyle("-fx-border-color: #ff0800;");
        } else{
            product_source_box.setStyle(null);
        }

        if (app.ApplicationType == null) {
            allFilled = false;
            app_type_box.setStyle("-fx-border-color: #ff0800;");
        } else{
            app_type_box.setStyle(null);
        }

        if (allFilled) {
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_3, "Page 3", 1020, 487, app);
        }
    }
}