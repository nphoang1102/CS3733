package misc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Hoang Nguyen on 4/7/2017.
 */
public class ResultPopupManager {
    @FXML
    private Label dis_brandName, dis_ID, dis_permit, dis_serial, dis_date, dis_fanName, dis_origin, dis_class, dis_type;

    @FXML
    void initialize() {
    }

    @FXML
    void initData(ColaResult result) {
        dis_brandName.setText(result.getName());
    }
}
