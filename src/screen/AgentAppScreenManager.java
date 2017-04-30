package screen;

import Email.EmailManager;
import base.*;
import database.*;
import database.images.ProxyImage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by ${Jack} on 4/2/2017.
 */
public class AgentAppScreenManager extends Screen{


    //all the Labels on the screen
    @FXML
    Label repId, brewNo, productSrc, productType, brandName, applicantName, appNameAndAdd, alternateAdd, phoneNum, emailAdd, appDate, ttbId, fancyName, formula, wineVarietal, wineAppellation, appType, alcContent, pHLevel, vintageYear;

    @FXML
    TextArea rejectReason, newAgentID, sendBackReason;

    @FXML
    ImageView theLabel;
    //all the Buttons on the screen
    @FXML
    Button acceptButton, rejectButton, forwardButton, sendBackButton;

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

        String temp = Main.getUserType();
        System.out.println(temp);



        if(temp.equals("Super User")){
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
            forwardButton.setVisible(false);
            rejectReason.setVisible(false);
            newAgentID.setVisible(false);
            sendBackButton.setVisible(false);
            sendBackReason.setVisible(false);
        }

        ProxyImage image = new ProxyImage("alcohol/"+application.ApplicationNo+".png");
        image.displayImage(theLabel);

    }


    /*
        This method is when called when the accept application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void acceptApp(MouseEvent mouseEvent) {
        if(dataGlobal!=null){
            Application app = (Application) dataGlobal;
            DatabaseManager.approveApplication(app.ApplicationNo, StringUtilities.getDate(), StringUtilities.getExpirationDate());
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
            app.ApplicationStatus = "APPROVED";


            String manufacturerUsername = app.getManufacturerUsername();
            LinkedList<DataSet> manufacturerDataSet = DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", manufacturerUsername);

            if(manufacturerDataSet.size()<=0){
                LogManager.println("Manufacturer:"+manufacturerUsername+" does not exist!", EnumWarningType.WARNING);
                return;
            }

            // Recipient's email ID needs to be mentioned.
            String to = "";//change accordingly

            UserManufacturer manufacturer = null;
            if(manufacturerDataSet.getFirst()!=null){
                manufacturer = (UserManufacturer)manufacturerDataSet.getFirst();
                //Check for email
                if(!manufacturer.getEmail().isEmpty()){
                    to = manufacturer.getEmail();
                }else{
                    LogManager.println("Manufacturer:"+manufacturerUsername+" does not have an email address.", EnumWarningType.WARNING);
                    return;
                }
            }

            LinkedList<DataSet> agentDataSet = DatabaseManager.queryDatabase(EnumTableType.AGENT, "Username", Main.getUsername());
            app.AgentName = ((UserAgent)agentDataSet.getFirst()).getName();
            String email = ((UserAgent)agentDataSet.getFirst()).getEmail();

            //Send an email
            EmailManager.sendEmail(to, "Your Application has been Accepted.", new String[]{
                    "Your "+app.ApplicationType+" submitted on "+app.DateOfSubmission+" has been "+app.ApplicationStatus+"!",
                    "If you want to contact your representative, contact "+app.AgentName+" @ "+ email+"."
            });
        }
    }


    /*
        This method is when called when the reject application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void rejectApp(MouseEvent mouseEvent) {
        Application app = (Application) dataGlobal;
        String status = "REJECTED";
        String reason = "";
        if(!rejectReason.getText().isEmpty()){
            reason = rejectReason.getText();
        }



        updateDatabase(status, reason);
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
            DataSet data = new BasicDataSet();
            data.addField("Message", "There is no agent with that username");
            Main.screenManager.popoutScreen(EnumScreenType.NOTIFICATION_SCREEN, "no such user", 400, 150,data);
            return;
        }
        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
    }


    public void sendBackApp(MouseEvent mouseEvent) {
        String status = "NEEDS WORK";
        String reason = "";
        if(!sendBackReason.getText().isEmpty()){
            reason = sendBackReason.getText();
        }

        updateDatabase(status, reason);
    }

    private void updateDatabase(String status, String reason){
        if(dataGlobal!=null){
            Application app = (Application) dataGlobal;
            app.ReasonForRejection = reason;

            DatabaseManager.rejectApplication(app.ApplicationNo, reason, status);
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }

        if(dataGlobal!=null) {
            Application app = (Application) dataGlobal;
            String manufacturerUsername = app.getManufacturerUsername();
            LinkedList<DataSet> manufacturerDataSet = DatabaseManager.queryDatabase(EnumTableType.MANUFACTURER, "Username", manufacturerUsername);

            if (manufacturerDataSet.size() <= 0) {
                LogManager.println("Manufacturer:" + manufacturerUsername + " does not exist!", EnumWarningType.WARNING);
                return;
            }

            // Recipient's email ID needs to be mentioned.
            String to = "";//change accordingly

            UserManufacturer manufacturer = null;
            if (manufacturerDataSet.getFirst() != null) {
                manufacturer = (UserManufacturer) manufacturerDataSet.getFirst();
                //Check for email
                if (!manufacturer.getEmail().isEmpty()) {
                    to = manufacturer.getEmail();
                } else {
                    LogManager.println("Manufacturer:" + manufacturerUsername + " does not have an email address.", EnumWarningType.WARNING);
                    return;
                }
            }

            String tempStatus;
            if(status.equals("NEEDS WORK")){
                tempStatus = "sent back";
            }else{
                tempStatus = "Rejected";
            }
            //Send an email
            EmailManager.sendEmail(to, "Your Application has been " + tempStatus + ".", new String[]{app.ReasonForRejection});
        }

    }
}
