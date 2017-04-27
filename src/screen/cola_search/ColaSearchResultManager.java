package screen.cola_search;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.Alcohol;
import database.BasicDataSet;
import database.DataSet;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import screen.EnumScreenType;
import screen.Screen;
import screen.cola_search.*;

import java.util.LinkedList;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaSearchResultManager extends Screen {
    /* Class attributes */
    private DataSet mapOrigin = new BasicDataSet();
    private String[] adStrings = new String[9];
    private String keywords = "";
    private String searchType = "";
    private boolean isAdvance = false;
    private LinkedList<DataSet> databaseResult = new LinkedList();
    private ObservableList<ColaResult> resultTable = FXCollections.observableArrayList();
    private DataSet tempSet = new BasicDataSet();

    /* Class constructor */
    public ColaSearchResultManager() {
        super(EnumScreenType.COLA_SEARCH_RESULT);
    }

    /* FXML objects */
    @FXML
    private TableView<ColaResult> searchResult;
    @FXML
    private TableColumn<ColaResult, String> coLid, coLsource, coLalcoholType, coLname;
    @FXML
    private Button saveToCsv, advanceSearch;
    @FXML
    private Pane colaSearchPanel;

    /* Class methods */
    @Override
    public void onScreenFocused(DataSet data){
        /* Check for advance or general search */
        if (data.getValueForKey("isAdvance").equals("false")) {
            this.keywords = data.getValueForKey("Keywords")+ "";
            this.searchType = data.getValueForKey("AlcoholType")+ "";
        }
        else {
            this.isAdvance = true;
            this.adStrings = (String[]) data.getValueForKey("advance");
        }

        /* Get the TableView stuff and result setup */
        this.initializeTable();
        this.databaseQuery();

        /* Configuration for the mouse click event */
        this.initializeMouseEvent();
    }

    /* Setup properties for the columns in tableview */
    public void initializeTable() {
        this.coLid.setCellValueFactory(new PropertyValueFactory("id"));
        this.coLsource.setCellValueFactory(new PropertyValueFactory("source"));
        this.coLalcoholType.setCellValueFactory(new PropertyValueFactory("type"));
        this.coLname.setCellValueFactory(new PropertyValueFactory("name"));
    }

    /* Initialize the mouse click event on table rows */
    public void initializeMouseEvent() {
        searchResult.setRowFactory( tv -> {
            TableRow<ColaResult> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 2) && (! row.isEmpty())) {
                    ColaResult rowData = row.getItem();
                    this.initializePopup(rowData);
                }
            });
            return row;
        });
    }

    /* Setup popup window here */
    public void initializePopup(ColaResult rowData) {
        DataSet data = new BasicDataSet();
        String title = "Additional information for " + rowData.getName() + " - " + rowData.getFname();
        data.addField("TTBID", rowData.getId());
        data.addField("PermitNo", rowData.getPermit());
        data.addField("SerialNo", rowData.getSerial());
        data.addField("CompletedDate", rowData.getDate());
        data.addField("FancifulName", rowData.getFname());
        data.addField("BrandName", rowData.getName());
        data.addField("Origin", rowData.getSource());
        data.addField("Class", rowData.getAclass());
        data.addField("Type", rowData.getType());
        data.addField("AlcoholContent", rowData.getAlCon());
        data.addField("VintageYear", rowData.getYear());
        data.addField("PH", rowData.getPh());
        Main.screenManager.popoutScreen(EnumScreenType.COLA_RESULT_POPUP, title, 800, 556, data);
    }

    /* Send the search keywords to the database and display reply from database */
    public void databaseQuery() {
        if (this.isAdvance) {
            this.databaseResult = DatabaseManager.advancedSearch(this.adStrings[0] ,
                    this.adStrings[1],
                    this.adStrings[2],
                    this.adStrings[3],
//                    this.adStrings[4],
//                    this.adStrings[5],
//                    this.adStrings[6],
                    this.adStrings[7],
                    this.adStrings[8]);
        }
        else {
            this.databaseResult = DatabaseManager.queryDatabase(EnumTableType.ALCOHOL, "BrandName" , this.keywords);
        }
        this.resultTable.clear();
        this.setMapOrigin();
        for (DataSet tempSet: this.databaseResult) {
            Alcohol data = (Alcohol) tempSet;
            String mapSource = "";
            if (this.mapOrigin.getValueForKey(data.Origin) == null) mapSource = data.Origin;
            else mapSource = this.mapOrigin.getValueForKey(data.Origin)+ "";
            this.resultTable.add(new ColaResult(data.TTBID,
                    data.PermitNo,
                    data.SerialNo,
                    data.CompletedDate,
                    data.FancifulName,
                    data.BrandName,
                    mapSource,
                    data.Class,
                    data.Type,
                    data.AlcoholContent,
                    data.VintageYear,
                    data.PH));
        }
        this.searchResult.setEditable(false);
        this.searchResult.getItems().setAll(resultTable);
        this.isAdvance = false;
    }

    /* Print search result into a CSV file on button click */
    public void toCSV() {
        IDataDownload downloadCSV = new toCSV();
        downloadCSV.downloadData(this.resultTable, ",");
        DataSet message = new BasicDataSet();
        message.addField("Message", "Search result saved to /searchResult.csv");
        Main.screenManager.popoutScreen(EnumScreenType.NOTIFICATION_SCREEN, "Search result saved successfully", 400, 150, message);
    }

    /* Print search result into a tab-delimited text file */
    public void toTab() {
        IDataDownload downloadTab = new toTSV();
        downloadTab.downloadData(this.resultTable, "\t");
        DataSet message = new BasicDataSet();
        message.addField("Message", "Search result saved to /searchResult-tab.tsv");
        Main.screenManager.popoutScreen(EnumScreenType.NOTIFICATION_SCREEN, "Search result saved successfully", 400, 150, message);
    }

    /* Print search result into a character-delimited text file */
    public void toChar() {
//        IDataDownload downloadChar = new toChSV();
//        downloadChar.downloadData(this.resultTable);
        DataSet data = new BasicDataSet();
        data.addField("ResultTable",this.resultTable);
        Main.screenManager.popoutScreen(EnumScreenType.COLA_CHARACTER_SELECTION, "Character configuration", 450, 250, data);
    }

    /* Navigate to advance search screen on mouse click */
    public void toAdvanceSearch() {
        LogManager.println("Navigate to advance search screen from cola-search result screen");
        Main.screenManager.setScreen(EnumScreenType.COLA_ADVANCE_SEARCH);
    }

    /* Initialize the origin mapping for end-user */
    private void setMapOrigin() {
        this.mapOrigin.addField("00", "American");
        this.mapOrigin.addField("01", "California");
        this.mapOrigin.addField("02", "New York");
        this.mapOrigin.addField("03", "New Jersey");
        this.mapOrigin.addField("04", "Illinois");
        this.mapOrigin.addField("05", "Virginia");
        this.mapOrigin.addField("06", "Michigan");
        this.mapOrigin.addField("07", "Washington");
        this.mapOrigin.addField("08", "Georgia");
        this.mapOrigin.addField("09", "Ohio");
        this.mapOrigin.addField("10", "Alabama");
        this.mapOrigin.addField("11", "Arizona");
        this.mapOrigin.addField("12", "Arkansas");
        this.mapOrigin.addField("13", "Colorado");
        this.mapOrigin.addField("14", "Connecticut");
        this.mapOrigin.addField("15", "Delaware");
        this.mapOrigin.addField("16", "Florida");
        this.mapOrigin.addField("17", "Hawaii");
        this.mapOrigin.addField("18", "Idaho");
        this.mapOrigin.addField("19", "Indiana");
        this.mapOrigin.addField("20", "Iowa");
        this.mapOrigin.addField("21", "Kansas");
        this.mapOrigin.addField("22", "Kentucky");
        this.mapOrigin.addField("23", "Louisiana");
        this.mapOrigin.addField("24", "Maine");
        this.mapOrigin.addField("25", "Maryland");
        this.mapOrigin.addField("26", "Massachusetts");
        this.mapOrigin.addField("27", "Minnesota");
        this.mapOrigin.addField("28", "Mississippi");
        this.mapOrigin.addField("29", "Missouri");
        this.mapOrigin.addField("30", "Montana");
        this.mapOrigin.addField("31", "Nebraska");
        this.mapOrigin.addField("32", "Nevada");
        this.mapOrigin.addField("33", "New Hampshire");
        this.mapOrigin.addField("34", "New Mexico");
        this.mapOrigin.addField("35", "North Carolina");
        this.mapOrigin.addField("36", "North Dakota");
        this.mapOrigin.addField("37", "Oklahoma");
        this.mapOrigin.addField("38", "Oregon");
        this.mapOrigin.addField("39", "Pennsylvania");
        this.mapOrigin.addField("3A", "Monaco");
        this.mapOrigin.addField("40", "Rhode Island");
        this.mapOrigin.addField("41", "South Carolina");
        this.mapOrigin.addField("42", "South Dakota");
        this.mapOrigin.addField("43", "Tennessee");
        this.mapOrigin.addField("44", "Texas");
        this.mapOrigin.addField("45", "Utah");
        this.mapOrigin.addField("46", "Vermont");
        this.mapOrigin.addField("47", "West Virginia");
        this.mapOrigin.addField("48", "Wisconsin");
        this.mapOrigin.addField("49", "Wyoming");
        this.mapOrigin.addField("4A", "Puerto Rico");
        this.mapOrigin.addField("4B", "Virgin Islands, US");
        this.mapOrigin.addField("4C", "Bermuda");
        this.mapOrigin.addField("4D", "Dominican Republic");
        this.mapOrigin.addField("4E", "Alaska");
        this.mapOrigin.addField("4F", "Nicaragua");
        this.mapOrigin.addField("4G", "Bahamas");
        this.mapOrigin.addField("4H", "Barbados");
        this.mapOrigin.addField("4I", "Curacao");
        this.mapOrigin.addField("4J", "Kenya");
        this.mapOrigin.addField("4K", "District of Columbia");
        this.mapOrigin.addField("4L", "Kosovo");
        this.mapOrigin.addField("4M", "Eritrea");
        this.mapOrigin.addField("4N", "Cambodia");
        this.mapOrigin.addField("4P", "Aruba");
        this.mapOrigin.addField("4R", "Tanzania");
        this.mapOrigin.addField("4S", "Uganda");
        this.mapOrigin.addField("4U", "Great Britain");
        this.mapOrigin.addField("4V", "Tahiti");
        this.mapOrigin.addField("4W", "Fiji");
        this.mapOrigin.addField("4X", "Republic of Cabo Verde");
        this.mapOrigin.addField("4Y", "Antigua and Barbuda");
        this.mapOrigin.addField("4Z", "Tibet");
        this.mapOrigin.addField("50", "Italy");
        this.mapOrigin.addField("51", "France");
        this.mapOrigin.addField("52", "Spain");
        this.mapOrigin.addField("53", "Germany");
        this.mapOrigin.addField("54", "Portugal");
        this.mapOrigin.addField("55", "Denmark");
        this.mapOrigin.addField("56", "Israel");
        this.mapOrigin.addField("57", "Greece");
        this.mapOrigin.addField("58", "Yugoslavia");
        this.mapOrigin.addField("59", "Japan");
        this.mapOrigin.addField("5A", "El Salvador");
        this.mapOrigin.addField("5B", "Sweden");
        this.mapOrigin.addField("5C", "Norway");
        this.mapOrigin.addField("5D", "South Korea");
        this.mapOrigin.addField("5E", "Ireland");
        this.mapOrigin.addField("5F", "Lebanon");
        this.mapOrigin.addField("5G", "Thailand");
        this.mapOrigin.addField("5H", "Iceland");
        this.mapOrigin.addField("5I", "India");
        this.mapOrigin.addField("5J", "Jamaica");
        this.mapOrigin.addField("5K", "Scotland");
        this.mapOrigin.addField("5L", "Honduras");
        this.mapOrigin.addField("5M", "Guatemala");
        this.mapOrigin.addField("5N", "Haiti");
        this.mapOrigin.addField("5O", "Slovak Republic");
        this.mapOrigin.addField("5P", "Guyana");
        this.mapOrigin.addField("5Q", "Ethiopia");
        this.mapOrigin.addField("5R", "Singapore");
        this.mapOrigin.addField("5S", "Philippines");
        this.mapOrigin.addField("5T", "Grenada");
        this.mapOrigin.addField("5U", "Zimbabwe");
        this.mapOrigin.addField("5V", "Nigeria");
        this.mapOrigin.addField("5W", "Ivory Coast");
        this.mapOrigin.addField("5Y", "Czech Republic");
        this.mapOrigin.addField("5Z", "Vietnam");
        this.mapOrigin.addField("60", "Albania");
        this.mapOrigin.addField("61", "Algeria");
        this.mapOrigin.addField("62", "Argentina");
        this.mapOrigin.addField("63", "Australia");
        this.mapOrigin.addField("64", "Austria");
        this.mapOrigin.addField("65", "Belgium");
        this.mapOrigin.addField("66", "Bolivia");
        this.mapOrigin.addField("67", "Brazil");
        this.mapOrigin.addField("68", "Bulgaria");
        this.mapOrigin.addField("69", "Canada");
        this.mapOrigin.addField("6A", "Armenia");
        this.mapOrigin.addField("6B", "Azerbaijan");
        this.mapOrigin.addField("6C", "Belarus");
        this.mapOrigin.addField("6D", "Estonia");
        this.mapOrigin.addField("6E", "Georgia");
        this.mapOrigin.addField("6F", "Kazakhstan");
        this.mapOrigin.addField("6G", "Kyrgyzstan");
        this.mapOrigin.addField("6H", "Latvia");
        this.mapOrigin.addField("6I", "Lithuania");
        this.mapOrigin.addField("6J", "Moldova");
        this.mapOrigin.addField("6K", "The Russian Federation");
        this.mapOrigin.addField("6L", "Tajikistan");
        this.mapOrigin.addField("6M", "Turkmenistan");
        this.mapOrigin.addField("6N", "Ukraine");
        this.mapOrigin.addField("6O", "Uzbekistan");
        this.mapOrigin.addField("6P", "England");
        this.mapOrigin.addField("6Q", "Wales");
        this.mapOrigin.addField("6R", "The Channel Islands");
        this.mapOrigin.addField("6S", "The Isles of Wight & Man");
        this.mapOrigin.addField("6T", "Scilly Islands");
        this.mapOrigin.addField("6U", "Hebrides");
        this.mapOrigin.addField("6V", "Orkney & Shetland Island");
        this.mapOrigin.addField("6W", "Northern Ireland");
        this.mapOrigin.addField("6X", "Slovenia");
        this.mapOrigin.addField("6Y", "Croatia");
        this.mapOrigin.addField("6Z", "Costa Rica");
        this.mapOrigin.addField("70", "Chile");
        this.mapOrigin.addField("71", "Colombia");
        this.mapOrigin.addField("72", "Cyprus");
        this.mapOrigin.addField("73", "Czechoslovakia");
        this.mapOrigin.addField("74", "Egypt");
        this.mapOrigin.addField("75", "Hungary");
        this.mapOrigin.addField("77", "Jordan");
        this.mapOrigin.addField("78", "Liberia");
        this.mapOrigin.addField("79", "Luxembourg");
        this.mapOrigin.addField("7A", "Ecuador");
        this.mapOrigin.addField("7B", "Indonesia");
        this.mapOrigin.addField("7C", "Republic of San Marino");
        this.mapOrigin.addField("7D", "Belize");
        this.mapOrigin.addField("7E", "Panama");
        this.mapOrigin.addField("7F", "Martinique");
        this.mapOrigin.addField("7G", "Macedonia");
        this.mapOrigin.addField("7H", "Ghana");
        this.mapOrigin.addField("7J", "Somoa");
        this.mapOrigin.addField("7K", "Trinidad/Tobago");
        this.mapOrigin.addField("7L", "Cameroon");
        this.mapOrigin.addField("7M", "Neth Antilles Saint Martin");
        this.mapOrigin.addField("7P", "Serbia and Montenegro");
        this.mapOrigin.addField("7Q", "Anguilla (BWI)");
        this.mapOrigin.addField("7R", "Guadeloupe");
        this.mapOrigin.addField("7T", "West Indies");
        this.mapOrigin.addField("7U", "Sri Lanka");
        this.mapOrigin.addField("7V", "Nepal");
        this.mapOrigin.addField("7W", "Saint Lucia");
        this.mapOrigin.addField("7X", "Reunion");
        this.mapOrigin.addField("7Y", "Mongolia");
        this.mapOrigin.addField("7Z", "Myanmar");
        this.mapOrigin.addField("80", "Malta");
        this.mapOrigin.addField("81", "Mexico");
        this.mapOrigin.addField("82", "Morocco");
        this.mapOrigin.addField("83", "Netherlands");
        this.mapOrigin.addField("84", "New Zealand");
        this.mapOrigin.addField("85", "Peru");
        this.mapOrigin.addField("86", "Romania");
        this.mapOrigin.addField("87", "Switzerland");
        this.mapOrigin.addField("89", "Tunisia");
        this.mapOrigin.addField("8A", "Taiwan");
        this.mapOrigin.addField("8B", "St.Vincent and the Grenadines");
        this.mapOrigin.addField("8C", "Laos");
        this.mapOrigin.addField("8D", "Namibia");
        this.mapOrigin.addField("8E", "Bosnia-Herzegovina");
        this.mapOrigin.addField("8F", "Paraquay");
        this.mapOrigin.addField("8G", "Zambia");
        this.mapOrigin.addField("8H", "Mauritius");
        this.mapOrigin.addField("8I", "Sint Marteen");
        this.mapOrigin.addField("8J", "West Bank");
        this.mapOrigin.addField("8K", "Benin");
        this.mapOrigin.addField("8L", "Liechtenstein");
        this.mapOrigin.addField("90", "Turkey");
        this.mapOrigin.addField("91", "Union of South Africa");
        this.mapOrigin.addField("92", "United Kingdom");
        this.mapOrigin.addField("93", "Uruguay");
        this.mapOrigin.addField("94", "U.S.S.R");
        this.mapOrigin.addField("95", "Venezuela");
        this.mapOrigin.addField("96", "China");
        this.mapOrigin.addField("97", "Finland");
        this.mapOrigin.addField("98", "EEC/EU");
        this.mapOrigin.addField("99", "Poland");
        this.mapOrigin.addField("9A", "Cayman Islands");
        this.mapOrigin.addField("9B", "Cabo Verde");
        this.mapOrigin.addField("9C", "Democratic Republic of the Congo");
        this.mapOrigin.addField("9D", "Senegal");
        this.mapOrigin.addField("9E", "Republic of Madagascar");
        this.mapOrigin.addField("9F", "Republic of Suriname");
        this.mapOrigin.addField("9G", "Gabon");
        this.mapOrigin.addField("9H", "San Marino");
        this.mapOrigin.addField("9I", "Bhutan");
        this.mapOrigin.addField("HK", "Hong Kong");
        this.mapOrigin.addField("MC", "Multiple Countries");
    }
}
