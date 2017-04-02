package screen;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class LoginScreenManager extends Screen{

    @FXML
    private TextField unsernameField;

    @FXML
    private Ellipse loginButton;

    @FXML
    private Polygon backButton;

    @FXML
    private Text loginLabel;

    public LoginScreenManager(EnumScreenType type) {
        super(type);
    }


    @FXML
    void userLogin() {


    }


    @FXML
    void goBack() {

    }
}
