package screen;

import base.LogManager;
import base.Main;
import database.Application;
import database.DataSet;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;

/**
 * Created by ${Jack} on 4/2/2017.
 */
public class AgentAppScreenManager extends Screen{


    //all the Labels on the screen
    @FXML
    Label repId, brewNo, productSrc, productType, brandName, applicantName, appNameAndAdd, alternateAdd, phoneNum, emailAdd, appDate, ttbId, fancyName, formula, wineVarietal, wineAppellation, appType, alcContent, pHLevel, vintageYear;

    @FXML
    TextArea rejectReason, newAgentID, sendBackReason;

    //all the Buttons on the screen
    @FXML
    Button acceptButton, rejectButton, sendBackButtong, forwardButton;

    public AgentAppScreenManager() {
        super(EnumScreenType.AGENT_APP_SCREEN);
    }


    @FXML
    public void initialize(){

    }
    DataSet dataGlobal;

    @Override
    public void onScreenFocused(DataSet data){
        this.dataGlobal=data;
        Application application = (Application)data;
        repId.setText(application.RepID);
    // brewNo exists as PlantRegistry in Data Set
        brewNo.setText(application.PlantRegistry);
        productSrc.setText(application.Locality);
        productType.setText(application.AlcoholType);
        brandName.setText(application.Brand);
        applicantName.setText(application.AgentName);
        appNameAndAdd.setText(application.Address);
        alternateAdd.setText(application.Address2);
        phoneNum.setText(application.PhoneNo);
        emailAdd.setText(application.Email);
        appDate.setText(application.DateOfSubmission);
    //What is TTBID in Dataset?
    // ttbId.setText(application.SerialNo);
        fancyName.setText(application.FancifulName);
        formula.setText(application.Formula);
    // wineVarietal exists as Grapes in Data Set
        wineVarietal.setText(application.Grapes);
        wineAppellation.setText(application.WineAppelation);
        appType.setText(application.ApplicationType);
    // alcoholContent exists as ABV in Data Set
        alcContent.setText(application.ABV);
        pHLevel.setText(application.PH);
        vintageYear.setText(application.VintageDate);
    }


    /*
        This method is when called when the accept application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void acceptApp(MouseEvent mouseEvent) {
        if(dataGlobal!=null){
            Application app = (Application) dataGlobal;
            DatabaseManager.approveApplication(app.ApplicationNo);
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }

    }


    /*
        This method is when called when the reject application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void rejectApp(MouseEvent mouseEvent) {
        if(dataGlobal!=null){
            Application app = (Application) dataGlobal;
            if(!rejectReason.getText().isEmpty()){
                app.ReasonForRejection = rejectReason.getText();
            }
            DatabaseManager.rejectApplication(app.ApplicationNo);
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }
    }

    public void forwardApp(MouseEvent mouseEvent) {
        Application app = (Application) dataGlobal;
        String agentID = newAgentID.getText();
        if((agentID.equals(null)) || (agentID.equals(""))){
            //screenManager.popoutScreen(EnumScreenType.ERROR_SCREEN, "Review Application", agentID);
            return;
        }
        try{
            Main.databaseManager.forwardApplication(app.ApplicationNo, agentID);
        }catch(Exception e){
            LogManager.println("there was an error forwarding the message");
        }
    }

    public void sendBackApp(MouseEvent mouseEvent) {

    }

}
