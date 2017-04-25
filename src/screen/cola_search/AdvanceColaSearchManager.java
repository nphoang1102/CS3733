package screen.cola_search;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.Alcohol;
import database.BasicDataSet;
import database.DataSet;
import database.DatabaseManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import screen.EnumScreenType;
import screen.Screen;
import sun.rmi.runtime.Log;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/15/2017.
 */
public class AdvanceColaSearchManager extends Screen {
    /* Class attributes */
    private String category1 = "";
    private String category2 = "";
    private String category3 = "";
    private ArrayList<String> sugBrand = new ArrayList<String>();
    private ArrayList<String> sugFan = new ArrayList<String>();
    private ArrayList<String> sugType = new ArrayList<String>();

    /* Declaring the FXML objects */
    @FXML private TextField field1, field2, field3;
    @FXML private Button clearButton, searchButton;
    @FXML private ChoiceBox drop1, drop2, drop3;
    @FXML private Pane contain;
    @FXML private Label warning;
    @FXML private CheckBox and1, or1, and2, or2;

    /* Class constructor */
    public AdvanceColaSearchManager() {
        super(EnumScreenType.COLA_ADVANCE_SEARCH);
    }

    /* What to do on screen initialize */
    @Override
    public void onScreenFocused(DataSet data) {
        this.searchFieldInitialize();
        this.searchByInitialize();
        this.warning.setVisible(false);
        this.initSuggest();
    }

    /* Initialize the suggestive search for brand name, fanciful name and type */
    public void initSuggest() {
        this.sugBrand.clear();
        this.sugFan.clear();
        this.sugType.clear();
        LinkedList<DataSet> databaseResult = DatabaseManager.queryDatabase(EnumTableType.ALCOHOL, "BrandName", "");
        for (DataSet tempSet : databaseResult) {
            Alcohol data = (Alcohol) tempSet;
            if (!this.sugBrand.contains(data.BrandName)) this.sugBrand.add(data.BrandName);
            if (!this.sugFan.contains(data.FancifulName)) this.sugFan.add(data.FancifulName);
            if (!this.sugType.contains(data.Type)) this.sugType.add(data.Type);
        }
    }

    /* Initialize the choice boxes */
    public void searchByInitialize() {
        ObservableList<String> searchByList = FXCollections.observableArrayList("","TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
        ObservableList<String> defaultSearchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
        drop1.setItems(defaultSearchByList);
        drop2.setItems(searchByList);
        drop3.setItems(searchByList);
        drop1.setValue("BrandName");
        drop2.setValue("");
        drop3.setValue("");
    }

    /* Initialize the search fields */
    public void searchFieldInitialize() {
        field1.clear();
        field2.clear();
        field2.setDisable(true);
        field3.clear();
        field3.setDisable(true);
    }

    /* Check for the choice box selection to enable/disable search fields */
    public void onCheck() {
        if (drop2.getValue().equals("")) {
            field2.setDisable(true);
            field2.clear();
        }
        else field2.setDisable(false);
        if (drop3.getValue().equals("")) {
            field3.setDisable(true);
            field3.clear();
        }
        else field3.setDisable(false);
        this.warning.setVisible(false);
    }

    /* What to do when the clear button is clicked */
    public void clearClicked() {
        searchByInitialize();
        searchFieldInitialize();
    }

    /* What to do when the search button is clicked */
    public void searchClicked() {
        if (this.isLegit()) {
            DataSet searchFields = new BasicDataSet();
            searchFields.addField("isAdvance", "true");
            searchFields.addField("searchCat1", drop1.getValue() + "");
            searchFields.addField("searchTerm1", field1.getText());
            searchFields.addField("searchCat2", drop2.getValue() + "");
            searchFields.addField("searchTerm2", field2.getText());
            searchFields.addField("searchCat3", drop3.getValue() + "");
            searchFields.addField("searchTerm3", field3.getText());
            LogManager.print("Under AdvanceColaSearchManager.java: the user is searching for " + field1.getText() + " under " + drop1.getValue()
                    + ", " + field2.getText() + " under " + drop2.getValue()
                    + ", " + field3.getText() + " under " + drop3.getValue());
            Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT, searchFields);
        }
        else {
            this.warning.setVisible(true);
        }
    }

    /* Check if entries are eligible for advance search, we will be using this in Intersect */
    /*public boolean isLegit() {
        if ((!field1.getText().equals("")) && (!drop1.getValue().equals(""))) {
            if ( ((!drop2.getValue().equals("")) && (!field2.getText().equals("")))
                    || ((!drop3.getValue().equals("")) && (!field3.getText().equals(""))) ) {
                return true;
            }

            else return true;
        }
        else {
            this.warning.setVisible(true);
            return false;
        }
    }*/

    /* Check if entries are eligible for advance search, this is for current Union */
    public boolean isLegit() {
//        Boolean value = true;
        if ( (!drop1.getValue().equals("")) && (field1.getText().equals("")) ) {
            return false;
        }
        if ( (!drop2.getValue().equals("")) && (field2.getText().equals("")) ) {
            return false;
        }
        if ( (!drop3.getValue().equals("")) && (field3.getText().equals("")) ) {
            return false;
        }
        return true;
    }

    /* Check field 1 to initialize the suggestive search */
    public void checkField1() {
        if (drop1.getValue().equals("BrandName")) TextFields.bindAutoCompletion(this.field1, this.sugBrand);
        else if (drop1.getValue().equals("FancifulName")) TextFields.bindAutoCompletion(this.field1, this.sugFan);
        else if (drop1.getValue().equals("Type")) TextFields.bindAutoCompletion(this.field1, this.sugType);
    }

    /* Check for choice box 1 */
    public void checkChoice1() {
        if (and1.selectedProperty().getValue()) {
            or1.setSelected(false);
            or1.setIndeterminate(false);
        }
        else if (or1.selectedProperty().getValue()) {
            and1.setSelected(false);
            and1.setIndeterminate(false);
        }
    }

    /* Check for choice box 2 */
    public void checkChoice2() {
        if (and2.selectedProperty().getValue()) {
            or2.setSelected(false);
            or2.setIndeterminate(false);
        }
        else if (or2.selectedProperty().getValue()) {
            and2.setSelected(false);
            and2.setIndeterminate(false);
        }
    }
}
