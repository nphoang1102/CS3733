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
    private DataSet mapOrigin = new BasicDataSet();

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
        this.setMapOrigin();
        this.warning.setVisible(false);
        this.checkOr.setSelected(false);
        this.checkAnd.setSelected(false);
    }

    /* Initialize the choice boxes */
    public void searchByInitialize() {
        ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "AlcoholType", "AlcoholContent", "VintageYear", "PH");
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "AlcoholType", "AlcoholContent", "VintageYear", "PH");
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "AlcoholType", "AlcoholContent", "VintageYear", "PH");
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
            ObservableList<String> searchByList = FXCollections.observableArrayList("TTBID", "PermitNo", "SerialNo", "CompletedDate", "FancifulName", "BrandName", "Origin", "Class", "AlcoholType", "AlcoholContent", "VintageYear", "PH");
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
            if (this.searchEntries[0].equals("Origin")) this.searchEntries[1] = this.mapOrigin.getValueForKey(this.field1.getText().toLowerCase()) + "";
            else this.searchEntries[1] = this.field1.getText();
            this.searchEntries[2] = this.drop2.getValue() + "";
            if (this.searchEntries[2].equals("Origin")) this.searchEntries[3] = this.mapOrigin.getValueForKey(this.field2.getText().toLowerCase()) + "";
            else this.searchEntries[3] = this.field2.getText();
            this.searchEntries[4] = this.drop3.getValue() + "";
            if (this.searchEntries[4].equals("Origin")) this.searchEntries[5] = this.mapOrigin.getValueForKey(this.field3.getText().toLowerCase()) + "";
            else this.searchEntries[5] = this.field3.getText();
            this.searchEntries[6] = this.drop4.getValue() + "";
            if (this.searchEntries[6].equals("Origin")) this.searchEntries[7] = this.mapOrigin.getValueForKey(this.field4.getText().toLowerCase()) + "";
            else this.searchEntries[7] = this.field4.getText();
            this.searchEntries[8] = this.searchType + "";
            DataSet searchFields = new BasicDataSet();
            searchFields.addField("advance", this.searchEntries);
            searchFields.addField("isAdvance", "true");
            LogManager.print("Under AdvanceColaSearchManager.java: the user is searching for " + this.searchEntries[1] + " under " + this.searchEntries[0]
                    + ", " + this.searchEntries[3] + " under " + this.searchEntries[2]
                    + ", " + this.searchEntries[5] + " under " + this.searchEntries[4]
                    + ", " + this.searchEntries[7] + " under " + this.searchEntries[6]
                    + ", with the search logic of " + this.searchEntries[8]);
            this.searchType = "";
            this.searchFields = 1;
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

    /* Initialize the origin mapping for end-user */
    private void setMapOrigin() {
        this.mapOrigin.addField("american", "0" );
        this.mapOrigin.addField("california", "1");
        this.mapOrigin.addField("new york", "2");
        this.mapOrigin.addField("new jersey","3");
        this.mapOrigin.addField("illinois", "4");
        this.mapOrigin.addField("virginia", "5");
        this.mapOrigin.addField("michigan", "6");
        this.mapOrigin.addField("washington", "7");
        this.mapOrigin.addField("georgia", "8");
        this.mapOrigin.addField("ohio", "9");
        this.mapOrigin.addField("alabama", "10");
        this.mapOrigin.addField("arizona", "11");
        this.mapOrigin.addField("arkansas", "12");
        this.mapOrigin.addField("colorado", "13");
        this.mapOrigin.addField("connecticut", "14");
        this.mapOrigin.addField("delaware", "15");
        this.mapOrigin.addField("florida", "16");
        this.mapOrigin.addField("hawaii", "17");
        this.mapOrigin.addField("idaho", "18");
        this.mapOrigin.addField("indiana", "19");
        this.mapOrigin.addField("iowa", "20");
        this.mapOrigin.addField("kansas", "21");
        this.mapOrigin.addField("kentucky", "22");
        this.mapOrigin.addField("louisiana", "23");
        this.mapOrigin.addField("maine", "24");
        this.mapOrigin.addField("maryland", "25");
        this.mapOrigin.addField("massachusetts", "26");
        this.mapOrigin.addField("minnesota", "27");
        this.mapOrigin.addField("mississippi", "28");
        this.mapOrigin.addField("missouri", "29");
        this.mapOrigin.addField("montana", "30");
        this.mapOrigin.addField("nebraska", "31");
        this.mapOrigin.addField("nevada", "32");
        this.mapOrigin.addField("new hampshire", "33");
        this.mapOrigin.addField("new mexico", "34");
        this.mapOrigin.addField("north carolina", "35");
        this.mapOrigin.addField("north dakota", "36");
        this.mapOrigin.addField("oklahoma", "37");
        this.mapOrigin.addField("oregon", "38");
        this.mapOrigin.addField("pennsylvania", "39");
        this.mapOrigin.addField("monaco", "3A");
        this.mapOrigin.addField("rhode island", "40");
        this.mapOrigin.addField("south carolina", "41");
        this.mapOrigin.addField("south dakota", "42");
        this.mapOrigin.addField("tennessee", "43");
        this.mapOrigin.addField("texas", "44");
        this.mapOrigin.addField("utah", "45");
        this.mapOrigin.addField("vermont", "46");
        this.mapOrigin.addField("west virginia", "47");
        this.mapOrigin.addField("wisconsin", "48");
        this.mapOrigin.addField("wyoming", "49");
        this.mapOrigin.addField("puerto rico", "4A");
        this.mapOrigin.addField("virgin islands", "4B");
        this.mapOrigin.addField("bermuda", "4C");
        this.mapOrigin.addField("dominican republic", "4D");
        this.mapOrigin.addField("alaska", "4E");
        this.mapOrigin.addField("nicaragua", "4F");
        this.mapOrigin.addField("bahamas", "4G");
        this.mapOrigin.addField("barbados", "4H");
        this.mapOrigin.addField("curacao", "4I");
        this.mapOrigin.addField("kenya", "4J");
        this.mapOrigin.addField("district of columbia", "4K");
        this.mapOrigin.addField("kosovo", "4L");
        this.mapOrigin.addField("eritrea", "4M");
        this.mapOrigin.addField("cambodia", "4N");
        this.mapOrigin.addField("aruba", "4P");
        this.mapOrigin.addField("tanzania", "4R");
        this.mapOrigin.addField("uganda", "4S");
        this.mapOrigin.addField("great britain", "4U");
        this.mapOrigin.addField("tahiti", "4V");
        this.mapOrigin.addField("fiji", "4W");
        this.mapOrigin.addField("republic of cabo verde", "4X");
        this.mapOrigin.addField("antigua and barbuda", "4Y");
        this.mapOrigin.addField("tibet", "4Z");
        this.mapOrigin.addField("italy", "50");
        this.mapOrigin.addField("france", "51");
        this.mapOrigin.addField("spain", "52");
        this.mapOrigin.addField("germany", "53");
        this.mapOrigin.addField("portugal", "54");
        this.mapOrigin.addField("denmark", "55");
        this.mapOrigin.addField("israel", "56");
        this.mapOrigin.addField("greece", "57");
        this.mapOrigin.addField("yugoslavia", "58");
        this.mapOrigin.addField("japan", "59");
        this.mapOrigin.addField("el salvador", "5A");
        this.mapOrigin.addField("sweden", "5B");
        this.mapOrigin.addField("norway", "5C");
        this.mapOrigin.addField("south korea", "5D");
        this.mapOrigin.addField("ireland", "5E");
        this.mapOrigin.addField("lebanon", "5F");
        this.mapOrigin.addField("thailand", "5G");
        this.mapOrigin.addField("iceland", "5H");
        this.mapOrigin.addField("india", "5I");
        this.mapOrigin.addField("jamaica", "5J");
        this.mapOrigin.addField("scotland", "5K");
        this.mapOrigin.addField("honduras", "5L");
        this.mapOrigin.addField("guatemala", "5M");
        this.mapOrigin.addField("haiti", "5N");
        this.mapOrigin.addField("slovak republic", "5O");
        this.mapOrigin.addField("guyana", "5P");
        this.mapOrigin.addField("ethiopia", "5Q");
        this.mapOrigin.addField("singapore", "5R");
        this.mapOrigin.addField("philippines", "5S");
        this.mapOrigin.addField("grenada", "5T");
        this.mapOrigin.addField("zimbabwe", "5U");
        this.mapOrigin.addField("nigeria", "5V");
        this.mapOrigin.addField("ivory coast", "5W");
        this.mapOrigin.addField("czech republic", "5Y");
        this.mapOrigin.addField("vietnam", "5Z");
        this.mapOrigin.addField("albania", "60");
        this.mapOrigin.addField("algeria", "61");
        this.mapOrigin.addField("argentina", "62");
        this.mapOrigin.addField("australia", "63");
        this.mapOrigin.addField("austria", "64");
        this.mapOrigin.addField("belgium", "65");
        this.mapOrigin.addField("bolivia", "66");
        this.mapOrigin.addField("brazil", "67");
        this.mapOrigin.addField("bulgaria", "68");
        this.mapOrigin.addField("canada", "69");
        this.mapOrigin.addField("armenia", "6A");
        this.mapOrigin.addField("azerbaijan", "6B");
        this.mapOrigin.addField("belarus", "6C");
        this.mapOrigin.addField("estonia", "6D");
        this.mapOrigin.addField("georgia", "6E");
        this.mapOrigin.addField("kazakhstan", "6F");
        this.mapOrigin.addField("kyrgyzstan", "6G");
        this.mapOrigin.addField("latvia", "6H");
        this.mapOrigin.addField("lithuania", "6I");
        this.mapOrigin.addField("moldova", "6J");
        this.mapOrigin.addField("the russian federation", "6K");
        this.mapOrigin.addField("tajikistan", "6L");
        this.mapOrigin.addField("turkmenistan", "6M");
        this.mapOrigin.addField("ukraine", "6N");
        this.mapOrigin.addField("uzbekistan", "6O");
        this.mapOrigin.addField("england", "6P");
        this.mapOrigin.addField("wales", "6Q");
        this.mapOrigin.addField("the channel islands", "6R");
        this.mapOrigin.addField("the isles of wight & man", "6S");
        this.mapOrigin.addField("scilly islands", "6T");
        this.mapOrigin.addField("hebrides", "6U");
        this.mapOrigin.addField("orkney & shetland island", "6V");
        this.mapOrigin.addField("northern ireland", "6W");
        this.mapOrigin.addField("slovenia", "6X");
        this.mapOrigin.addField("croatia", "6Y");
        this.mapOrigin.addField("costa rica", "6Z");
        this.mapOrigin.addField("chile", "70");
        this.mapOrigin.addField("colombia", "71");
        this.mapOrigin.addField("cyprus", "72");
        this.mapOrigin.addField("czechoslovakia", "73");
        this.mapOrigin.addField("egypt", "74");
        this.mapOrigin.addField("hungary", "75");
        this.mapOrigin.addField("jordan", "77");
        this.mapOrigin.addField("liberia", "78");
        this.mapOrigin.addField("luxembourg", "79");
        this.mapOrigin.addField("ecuador", "7A");
        this.mapOrigin.addField("indonesia", "7B");
        this.mapOrigin.addField("republic of san marino", "7C");
        this.mapOrigin.addField("belize", "7D");
        this.mapOrigin.addField("panama", "7E");
        this.mapOrigin.addField("martinique", "7F");
        this.mapOrigin.addField("macedonia", "7G");
        this.mapOrigin.addField("ghana", "7H");
        this.mapOrigin.addField("somoa", "7J");
        this.mapOrigin.addField("trinidad/tobago", "7K");
        this.mapOrigin.addField("cameroon", "7L");
        this.mapOrigin.addField("neth antilles saint martin", "7M");
        this.mapOrigin.addField("serbia and montenegro", "7P");
        this.mapOrigin.addField("anguilla (BWI)", "7Q");
        this.mapOrigin.addField("guadeloupe", "7R");
        this.mapOrigin.addField("west indies", "7T");
        this.mapOrigin.addField("sri lanka", "7U");
        this.mapOrigin.addField("nepal", "7V");
        this.mapOrigin.addField("saint lucia", "7W");
        this.mapOrigin.addField("reunion", "7X");
        this.mapOrigin.addField("mongolia", "7Y");
        this.mapOrigin.addField("myanmar", "7Z");
        this.mapOrigin.addField("malta", "80");
        this.mapOrigin.addField("mexico", "81");
        this.mapOrigin.addField("morocco", "82");
        this.mapOrigin.addField("netherlands", "83");
        this.mapOrigin.addField("new zealand", "84");
        this.mapOrigin.addField("peru", "85");
        this.mapOrigin.addField("romania", "86");
        this.mapOrigin.addField("switzerland", "87");
        this.mapOrigin.addField("tunisia", "89");
        this.mapOrigin.addField("taiwan", "8A");
        this.mapOrigin.addField("st.vincent and the grenadines", "8B");
        this.mapOrigin.addField("laos", "8C");
        this.mapOrigin.addField("namibia", "8D");
        this.mapOrigin.addField("bosnia-herzegovina", "8E");
        this.mapOrigin.addField("paraquay", "8F");
        this.mapOrigin.addField("zambia", "8G");
        this.mapOrigin.addField("mauritius", "8H");
        this.mapOrigin.addField("sint marteen", "8I");
        this.mapOrigin.addField("west bank", "8J");
        this.mapOrigin.addField("benin", "8K");
        this.mapOrigin.addField("liechtenstein", "8L");
        this.mapOrigin.addField("turkey", "90");
        this.mapOrigin.addField("union of south africa", "91");
        this.mapOrigin.addField("united kingdom", "92");
        this.mapOrigin.addField("uruguay", "93");
        this.mapOrigin.addField("u.s.s.r", "94");
        this.mapOrigin.addField("venezuela", "95");
        this.mapOrigin.addField("china", "96");
        this.mapOrigin.addField("finland", "97");
        this.mapOrigin.addField("eec/eu", "98");
        this.mapOrigin.addField("poland", "99");
        this.mapOrigin.addField("cayman islands", "9A");
        this.mapOrigin.addField("cabo verde", "9B");
        this.mapOrigin.addField("democratic republic of the congo", "9C");
        this.mapOrigin.addField("senegal", "9D");
        this.mapOrigin.addField("republic of madagascar", "9E");
        this.mapOrigin.addField("republic of suriname", "9F");
        this.mapOrigin.addField("gabon", "9G");
        this.mapOrigin.addField("san marino", "9H");
        this.mapOrigin.addField("bhutan", "9I");
        this.mapOrigin.addField("hong kong", "HK");
        this.mapOrigin.addField("multiple countries", "MC");
    }
}
