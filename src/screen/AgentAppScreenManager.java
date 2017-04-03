package screen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by ${Jack} on 4/2/2017.
 */
public class AgentAppScreenManager extends Screen{

    /* Constructors */
    public AgentAppScreenManager() {
        super(EnumScreenType.AGENT_APP_SCREEN);
    }

    //all the Labels on the screen
    @FXML
    Label repId, brewNo, productSrc, productType, brandName, applicantName, applicantAdd, tradeName, alternateAdd, phoneNum, emailAdd, appDate, appName;

    //all the Buttons on the screen
    @FXML
    Button acceptButton, rejectButton;

    /*
        Sets up the screen with the correct information in each of the Labels
     */
    public void setUpScreen(){
        //fill Labels
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
