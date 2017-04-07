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
    public void initData(ColaResult result) {
        dis_brandName.setText(result.getName());
        dis_ID.setText(result.getId());
        dis_permit.setText(result.getPermit());
        dis_serial.setText(result.getSerial());
        dis_date.setText(result.getDate());
        dis_fanName.setText(result.getFname());
        dis_class.setText(result.getAclass());
        dis_type.setText(result.getType());
    }
}
