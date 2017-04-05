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

    private DataSet data;

    public AgentAppScreenManager() {
        super(EnumScreenType.AGENT_APP_SCREEN);
       // initialize();
    }


    @FXML
    public void initialize(){
        repId.setText(data.getValueForKey("AgentId"));
        brewNo.setText(data.getValueForKey("PermitNo"));
        productSrc.setText(data.getValueForKey("Source"));
        productType.setText(data.getValueForKey("AlcoholType"));
        brandName.setText(data.getValueForKey("Brand"));
        applicantName.setText(data.getValueForKey(""));
        applicantAdd.setText(data.getValueForKey("Address"));
        tradeName.setText(data.getValueForKey(""));
        alternateAdd.setText(data.getValueForKey("Address2"));
        phoneNum.setText(data.getValueForKey("PhoneNo"));
        emailAdd.setText(data.getValueForKey(""));
        appDate.setText(data.getValueForKey(""));
        appName.setText(data.getValueForKey(""));


    }



    public void setData (DataSet aData){
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
