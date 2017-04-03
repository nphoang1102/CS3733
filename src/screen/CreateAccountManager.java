package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateAccountManager extends Screen{
    public CreateAccountManager() {
        super(EnumScreenType.CREATE_ACCOUNT);
    }

    /* FXML objects */
    @FXML
    private Button createAccount;
    @FXML
    private TextField username;

    /* Class methods */
    @FXML
    private void goBack(){

    }

    @FXML
    private void makeAccount(){

    }
}

