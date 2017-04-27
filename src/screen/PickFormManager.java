package screen;

import base.LogManager;
import base.Main;
import database.Application;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by turnrer on 4/27/2017.
 */
public class PickFormManager extends Screen {

    @FXML
    Button continue_button, browse_button;

    @FXML
    AnchorPane top_pane;

    private Stage primaryStage = new Stage();

    public PickFormManager() {
        super(EnumScreenType.PICK_FORM);
    }

    @Override
    public void onScreenFocused(DataSet data) {

    }

    public void onContinue() {
        LogManager.println("Creating a new application via new form");
        Application app = new Application();
        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_1, "New Application", 1020, 487, app);
    }

    public void onBrowse() {
        //OPEN DOCUMENT BROWSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String filename = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
        //IF IS PDF
            //FILL OUT APPLICATION FIELDS, CHECK FOR ERROS
            //CREATE SUBMIT BUTTON
    }
}
