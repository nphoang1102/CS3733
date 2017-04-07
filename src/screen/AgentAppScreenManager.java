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
    }


    @FXML
    public void initialize(){

    }

    @Override
    public void onScreenFocused(){
        repId.setText(data.getValueForKey("AgentId"));
        brewNo.setText(data.getValueForKey("PermitNo"));
        productSrc.setText(data.getValueForKey("Source"));
        productType.setText(data.getValueForKey("AlcoholType"));
        brandName.setText(data.getValueForKey("Brand"));
        applicantName.setText(data.getValueForKey("ApplicantName"));
        applicantAdd.setText(data.getValueForKey("Address"));
        tradeName.setText(data.getValueForKey("DBAortrade"));
        alternateAdd.setText(data.getValueForKey("Address2"));
        phoneNum.setText(data.getValueForKey("PhoneNo"));
        emailAdd.setText(data.getValueForKey("Email"));
        appDate.setText(data.getValueForKey("DateSubmitted"));
        appName.setText(data.getValueForKey("ApplicantName"));
    }


    public void setData (DataSet aData){
        data= aData;
        initialize();
    }



    /*
        This method is when called when the accept application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void acceptApp(MouseEvent mouseEvent) {
        //put application into public search database
        //remove dataset from agent inbox
        DatabaseManager.approveApplication(data.getValueForKey("ApplicationNo"));
        //go back to agent inbox screen
        ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);
    }


    /*
        This method is when called when the reject application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void rejectApp(MouseEvent mouseEvent) {
        //put application into rejected database
        //remove data set from agent inbox
        DatabaseManager.rejectApplication(data.getValueForKey("ApplicationNo"));
        //go back to agent inbox screen
        ScreenManager.setScreen(EnumScreenType.AGENT_INBOX);
    }

}
