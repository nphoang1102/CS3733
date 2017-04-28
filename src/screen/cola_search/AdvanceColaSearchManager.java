package screen.cola_search;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import com.sun.xml.internal.fastinfoset.util.StringArray;
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
    private String[] searchEntries = new String[9];
    private String searchType = "";
    private int searchFields = 1;

    /* Declaring the FXML objects */
    @FXML private TextField field1, field2, field3, field4;
    @FXML private Button searchButton, clearButton;
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
        this.checkOr.setSelected(false);
        this.checkAnd.setSelected(false);
    }

    /* Initialize the choice boxes */
    public void searchByInitialize() {
        ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
        drop1.setItems(searchByList);
        drop2.setItems(searchByList);
        drop3.setItems(searchByList);
        drop4.setItems(searchByList);
        drop1.setValue("BrandName");
        drop2.setValue("");
        drop3.setValue("");
        drop4.setValue("");
    }

    /* Initialize the search fields */
    public void searchFieldInitialize() {
        field1.clear();
        this.plus1.setVisible(true);
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
            this.drop2.setItems(searchByList);
            this.drop2.setValue("");
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
            this.drop3.setItems(searchByList);
            this.drop3.setValue("");
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "Type", "AlcoholContent", "VintageYear", "PH");
            this.drop4.setItems(searchByList);
            this.drop4.setValue("");
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
        this.onScreenFocused(new BasicDataSet());
    }

    /* Check if the entry is legit or not */
    public boolean isLegit() {
        this.warning.setText("Some entries are invalid. Please check your entries and try again.");
        if ( (!drop1.getValue().equals("")) && (field1.getText().equals("")) ) {
            this.warning.setText("Search field cannot be blank.");
            return false;
        }
        this.warning.setText("Please collapse all unused search fields or fill in everything.");
        switch (this.searchFields) {
            case 2:
                if ( (drop2.getValue().equals("")) || (field2.getText().equals("")) ) return false;
                break;
            case 3:
                if ( (drop2.getValue().equals("")) || (field2.getText().equals("")) ) return false;
                if ( (drop3.getValue().equals("")) || (field3.getText().equals("")) ) return false;
                break;
            case 4:
                if ( (drop2.getValue().equals("")) || (field2.getText().equals("")) ) return false;
                if ( (drop3.getValue().equals("")) || (field3.getText().equals("")) ) return false;
                if ( (drop4.getValue().equals("")) || (field4.getText().equals("")) ) return false;
                break;
        }
        if (this.searchType.equals("")) {
            this.warning.setText("Please choose a search logic.");
            return false;
        }
        if (this.searchType.equals("and")) {
            String[] categories = new String[4];
            categories[0] = this.drop1.getValue()+"";
            categories[1] = this.drop2.getValue()+"";
            categories[2] = this.drop3.getValue()+"";
            categories[3] = this.drop4.getValue()+"";
            for (int i = 1; i < 4; i++) {
                if ((categories[i].equals(categories[i-1])) && (!categories[i].equals(""))) {
                    this.warning.setText("Search categories cannot be the same.");
                    return false;
                }
            }
        }
        return true;
    }

    /* Combination search (Union) */
    public void clickSearch() {
        if (this.isLegit()) {
            this.searchEntries[0] = this.drop1.getValue() + "";
            this.searchEntries[1] = this.field1.getText();
            this.searchEntries[2] = this.drop2.getValue() + "";
            this.searchEntries[3] = this.field2.getText();
            this.searchEntries[4] = this.drop3.getValue() + "";
            this.searchEntries[5] = this.field3.getText();
            this.searchEntries[6] = this.drop4.getValue() + "";
            this.searchEntries[7] = this.field4.getText();
            this.searchEntries[8] = this.searchType + "";
            DataSet searchFields = new BasicDataSet();
            searchFields.addField("advance", this.searchEntries);
            searchFields.addField("isAdvance", "true");
            LogManager.print("Under AdvanceColaSearchManager.java: the user is searching for " + this.searchEntries[1] + " under " + this.searchEntries[0]
                    + ", " + this.searchEntries[3] + " under " + this.searchEntries[2]
                    + ", " + this.searchEntries[5] + " under " + this.searchEntries[4]
                    + ", " + this.searchEntries[7] + " under " + this.searchEntries[6]
                    + ", with the search logic of " + this.searchEntries[8]);
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
            this.searchType = "and";
        }
        else this.searchType = "";
    }

    /* Or search clicked */
    public void clickOr() {
        if(this.checkOr.selectedProperty().getValue()) {
            this.checkAnd.setSelected(false);
            this.searchType = "or";
        }
        else this.searchType = "";
    }

    /* Check for warning and stuffs */
    public void onCheck() {
        this.warning.setVisible(false);
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
