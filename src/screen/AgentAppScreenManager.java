package screen;

import base.Main;
import database.Application;
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

    private Application data;

    public AgentAppScreenManager() {
        super(EnumScreenType.AGENT_APP_SCREEN);
    }


    @FXML
    public void initialize(){

    }

    @Override
    public void onScreenFocused(DataSet data){
        Application application = (Application)data;
    }


    /*
        This method is when called when the accept application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void acceptApp(MouseEvent mouseEvent) {

    }


    /*
        This method is when called when the reject application button is pressed. This
        method places the application into the public database with all the information.
     */
    public void rejectApp(MouseEvent mouseEvent) {

    }

}
