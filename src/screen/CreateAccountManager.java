package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;

public class CreateAccountManager extends Screen{
    public CreateAccountManager() {
        super(EnumScreenType.CREATE_ACCOUNT);
    }

    /* FXML objects */
    @FXML
    private Button createAccount;
    @FXML
    private TextField username;
    @FXML
    private Polygon backButton;

    /* Class methods */
    @FXML
    private void goBack(){
        //go back to the login screen
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
        return;
    }

    @FXML
    private void makeAccount(){

    }
}

