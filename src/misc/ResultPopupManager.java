package misc;

import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private Label dis_brandName, dis_ID, dis_permit, dis_serial, dis_date, dis_fanName, dis_origin, dis_class, dis_type, dis_year, dis_alCon, dis_ph;
    @FXML
    private Pane window;

    @Override
    public void onScreenFocused(DataSet result){
        dis_ID.setText("TTB ID: " + result.getValueForKey("TTBID"));
        dis_permit.setText("Permit No: " + result.getValueForKey("PermitNo"));
        dis_serial.setText("Serial No: " + result.getValueForKey("SerialNo"));
        dis_date.setText("Permit completed date: " +result.getValueForKey("CompletedDate"));
        dis_fanName.setText("Fanciful name: " +result.getValueForKey("FancifulName"));
        dis_brandName.setText(result.getValueForKey("BrandName"));
        dis_brandName.layoutXProperty().bind(window.widthProperty().subtract(dis_brandName.widthProperty()).divide(2));
        dis_origin.setText("Origin: " +result.getValueForKey("Origin"));
        dis_class.setText("Class: " +result.getValueForKey("Class"));
        dis_type.setText("Type: " +result.getValueForKey("Type"));
        dis_alCon.setText("Alcohol content: "+ result.getValueForKey("AlcoholContent"));
        dis_year.setText("Vintage year: " + result.getValueForKey("VintageYear"));
        dis_ph.setText("pH level: " + result.getValueForKey("PH"));
    }
}
