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
    private Label dis_brandName, dis_ID, dis_permit, dis_serial, dis_date, dis_fanName, dis_origin, dis_class, dis_type, dis_year, dis_alCon, dis_ph;

    @Override
    public void onScreenFocused(DataSet result){
        if (!result.getValueForKey("TTBID").equals("")) dis_ID.setText("TTB ID: " + result.getValueForKey("TTBID"));
        if (!result.getValueForKey("PermitNo").equals("")) dis_permit.setText("Permit No: " + result.getValueForKey("PermitNo"));
        if (!result.getValueForKey("SerialNo").equals("")) dis_serial.setText("Serial No: " + result.getValueForKey("SerialNo"));
        if (!result.getValueForKey("CompletedDate").equals("")) dis_date.setText("Permit completed date: " +result.getValueForKey("CompletedDate"));
        if (!result.getValueForKey("FancifulName").equals("")) dis_fanName.setText("Fanciful name: " +result.getValueForKey("FancifulName"));
        dis_brandName.setText(result.getValueForKey("BrandName"));
        if (!result.getValueForKey("Origin").equals("")) dis_origin.setText("Origin: " +result.getValueForKey("Origin"));
        if (!result.getValueForKey("Class").equals("")) dis_class.setText("Class: " +result.getValueForKey("Class"));
        if (!result.getValueForKey("Type").equals("")) dis_type.setText("Type: " +result.getValueForKey("Type"));
        if (!result.getValueForKey("AlcoholContent").equals("")) dis_alCon.setText("Alcohol content: "+ result.getValueForKey("AlcoholContent"));
        if (!result.getValueForKey("VintageYear").equals("")) dis_year.setText("Vintage year: " + result.getValueForKey("VintageYear"));
        if (!result.getValueForKey("VintageYear").equals("")) dis_ph.setText("pH level: " + result.getValueForKey("PH"));
    }
}
