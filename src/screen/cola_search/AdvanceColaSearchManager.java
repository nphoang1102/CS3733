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
    private int searchFields = 1;

    /* Declaring the FXML objects */
    @FXML private TextField field1, field2, field3, field4;
    @FXML private Button clearButton, exactButton, combiButton;
    @FXML private ChoiceBox drop1, drop2, drop3, drop4;
    @FXML private Pane contain;
    @FXML private Label warning, ex1, ex2, ex3, plus1, plus2, plus3;
    @FXML private CheckBox checkOr, checkAnd;

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
        setAddField1(false);
        setAddField2(false);
        setAddField3(false);

    }

    /* Set visibility for first additional search field */
    public void setAddField1(boolean visibility) {
        this.field2.setVisible(visibility);
        this.drop2.setVisible(visibility);
        this.plus2.setVisible(visibility);
        this.ex1.setVisible(visibility);
        if (visibility) {
            this.field2.clear();
            this.plus1.setVisible(false);
        }
    }

    /* Set visibility for second additional search field */
    public void setAddField2(boolean visibility) {
        this.field3.setVisible(visibility);
        this.drop3.setVisible(visibility);
        this.plus3.setVisible(visibility);
        this.ex2.setVisible(visibility);
        if (visibility) {
            this.field3.clear();
            this.plus1.setVisible(false);
            this.plus2.setVisible(false);
            this.ex1.setVisible(false);
        }
    }

    /* Set visibility for third additional search field */
    public void setAddField3(boolean visibility) {
        this.field4.setVisible(visibility);
        this.drop4.setVisible(visibility);
        this.ex3.setVisible(visibility);
        if (visibility) {
            this.field4.clear();
            this.plus1.setVisible(false);
            this.plus2.setVisible(false);
            this.plus3.setVisible(false);
            this.ex1.setVisible(false);
            this.ex2.setVisible(false);
        }
    }

    /* What to do when the clear button is clicked */
    public void clearClicked() {
        searchByInitialize();
        searchFieldInitialize();
    }

    /* Combination search (Union) */
    public void combiSearch() {
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

    /* And search clicked */
    public void clickAnd() {
        if(this.checkAnd.selectedProperty().getValue()) {
            this.checkOr.setSelected(false);
            this.checkOr.setSelected(false);
        }
    }

    /* Or search clicked */
    public void clickOr() {
        if(this.checkOr.selectedProperty().getValue()) {
            this.checkAnd.setSelected(false);
            this.checkAnd.setSelected(false);
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


    /* Add additional fields starts here */
    public void add1() {
        this.setAddField1(true);
        this.searchFields = 2;
    }

    public void add2() {
        this.setAddField2(true);
        this.searchFields = 3;
    }

    public void add3() {
        this.setAddField3(true);
        this.searchFields = 4;
    }
    /* Add additional fields ends here */

    /* Remove additional fields starts here */
    public void rem1() {
        this.setAddField1(false);
        this.plus1.setVisible(true);
        this.searchFields = 1;
    }

    public void rem2() {
        this.setAddField2(false);
        this.plus2.setVisible(true);
        this.ex1.setVisible(true);
        this.searchFields = 2;
    }

    public void rem3() {
        this.setAddField3(false);
        this.plus3.setVisible(true);
        this.ex2.setVisible(true);
        this.searchFields = 3;
    }
    /* Remove additional fields ends here */
}
