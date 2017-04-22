package screen.cola_search;

import base.Main;
import database.DataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import screen.EnumScreenType;
import screen.Screen;

/**
 * Created by Hoang Nguyen on 4/20/2017.
 */
public class ColaCharSelectorManager extends Screen {
    /* Class attributes */
    private ObservableList<ColaResult> resultTable = FXCollections.observableArrayList();
    private String delimiter = "";

    /* Class constructor */
    public ColaCharSelectorManager() {
        super(EnumScreenType.COLA_CHARACTER_SELECTION);
    }

    /* FXML objects */
    @FXML private TextField deChar;
    @FXML private Button export;
    @FXML private Label warning;

    /* Initialize */
    @Override
    public void onScreenFocused(DataSet data) {
        warning.setVisible(false);
        this.resultTable = (ObservableList<ColaResult>) data.getValueForKey("ResultTable");
    }

    /* Clear the error message */
    public void onCheck() {
        warning.setVisible(false);
    }

    /* Export the desired character delimiter to a text file */
    public void onClick() {
        this.delimiter = deChar.getText();
        if (this.delimiter.equals("")) {
            warning.setVisible(true);
            return;
        }
        IDataDownload downloadChar = new toChSV();
        downloadChar.downloadData(this.resultTable, this.delimiter);
        Main.screenManager.closeCurrentPopOut();
    }
}
