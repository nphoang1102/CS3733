package screen;

import database.DataSet;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;

/**
 * Created by ${Jack} on 4/2/2017.
 */
public class AgentAppScreenManager extends Screen{


    //all the Labels on the screen
    @FXML
    Label repId, brewNo, productSrc, productType, brandName, applicantName, applicantAdd, tradeName, alternateAdd, phoneNum, emailAdd, appDate, appName;

    //all the Buttons on the screen
    @FXML
    Button acceptButton, rejectButton;

    private String data;

    public AgentAppScreenManager() {
        super(EnumScreenType.AGENT_APP_SCREEN);
        //initialize();
    }

    /*
    @FXML
    public void initialize(){
        DataSet tempData = DatabaseManager.getApplicationNo("data");
        repId.setText(tempData.getValueForKey("AgentId"));
        brewNo.setText(tempData.getValueForKey("PermitNo"));
        productSrc.setText(tempData.getValueForKey("Source"));
        productType.setText(tempData.getValueForKey("AlcoholType"));
        brandName.setText(tempData.getValueForKey("Brand"));
        applicantName.setText(tempData.getValueForKey(""));
        applicantAdd.setText(tempData.getValueForKey("Address"));
        tradeName.setText(tempData.getValueForKey(""));
        alternateAdd.setText(tempData.getValueForKey("Address2"));
        phoneNum.setText(tempData.getValueForKey("PhoneNo"));
        emailAdd.setText(tempData.getValueForKey(""));
        appDate.setText(tempData.getValueForKey(""));
        appName.setText(tempData.getValueForKey(""));


    }
    */


    public void setData (String aData){
        data= aData;
    }



    /*
        This method is when called when the accept application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void acceptApp(MouseEvent mouseEvent) {
        //put application into public search database
        //remove dataset from agent inbox
    }


    /*
        This method is when called when the reject application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void rejectApp(MouseEvent mouseEvent) {
        //put application into rejected database
        //remove data set from agent inbox
    }

}
