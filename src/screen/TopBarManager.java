package screen;

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
    ChoiceBox searchTerm;

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

    }
}
