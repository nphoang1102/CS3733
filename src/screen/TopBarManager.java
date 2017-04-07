package screen;

import base.LogManager;
import base.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Bailey Sostek on 4/7/17.
 */
public class TopBarManager extends Screen{
    @FXML
    private Button pullNewBatch;

    @FXML
    Label username;

    @FXML
    Label userType;

    @FXML
    Label imageLetter;

    @FXML
    ChoiceBox searchTerm;

    @FXML
    Button logIn;

    public TopBarManager() {
        super(EnumScreenType.TOP_BAR);
    }

    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Both");
        searchTerm.setItems(typeList);
        searchTerm.setValue(null);
    }

    @Override
    public void onScreenFocused() {
        username.setText(Main.getUsername());
        userType.setText(Main.getUserType());
        if(!Main.getUserType().isEmpty()) {
            imageLetter.setText(Main.getUserType().substring(0, 1));
        }
    }

    @FXML
    public void logIn(){
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
    }
}
