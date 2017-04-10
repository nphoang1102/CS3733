package misc;

import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import screen.EnumScreenType;
import screen.Screen;

/**
 * Created by Hoang Nguyen on 4/7/2017.
 */
public class ResultPopupManager extends Screen {
    /* Class constructor */
    public ResultPopupManager() {
        super(EnumScreenType.COLA_RESULT_POPUP);
    }

    @FXML
    private Label dis_brandName, dis_ID, dis_permit, dis_serial, dis_date, dis_fanName, dis_origin, dis_class, dis_type;

    @Override
    public void onScreenFocused(DataSet result){
        dis_ID.setText("TTB ID: " + result.getValueForKey("TTBID"));
        dis_permit.setText("Permit No: " + result.getValueForKey("PermitNo"));
        dis_serial.setText("Serial No: " + result.getValueForKey("SerialNo"));
        dis_date.setText("Permit completed date: " +result.getValueForKey("CompletedDate"));
        dis_fanName.setText("Fanciful name: " +result.getValueForKey("FancifulName"));
        dis_brandName.setText(result.getValueForKey("BrandName"));
        dis_origin.setText("Origin: " +result.getValueForKey("Origin"));
        dis_class.setText("Class: " +result.getValueForKey("Class"));
        dis_type.setText("Type: " +result.getValueForKey("Type"));
    }
}
