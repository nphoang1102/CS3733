package screen;

import base.Main;
import database.DataSet;
import database.DatabaseManager;
import database.User;
import database.UserAgent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by ${Jack} on 4/18/2017.
 */
public class AgentPendingManager extends Screen{

    @FXML
    Label agentName, agentUsername, agentEmail, isSuper;

    @FXML
    Button acceptButton, rejectButton;

    UserAgent thisUser;

    public AgentPendingManager() {
        super(EnumScreenType.AGENT_PENDING);
    }

    @Override
    public void onScreenFocused(DataSet data) {
        thisUser = (UserAgent) data;

        agentName.setText(thisUser.name);
        agentEmail.setText(thisUser.getEmail());
        agentUsername.setText(thisUser.getUsername());
        if(thisUser.getSuperAgent().equals("true")){
            isSuper.setText("Super Agent");
            acceptButton.setText("Make Super Agent");
        }else{
            isSuper.setText("Agent");
            acceptButton.setText("Make Agent");
        }

    }

    public void acceptAgnet(MouseEvent mouseEvent) {
        DatabaseManager.setAgentStatus(thisUser.getUsername(), "active");
        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT);    }

    public void rejectAgent(MouseEvent mouseEvent) {
        DatabaseManager.setAgentStatus(thisUser.getUsername(), "REMOVE");
        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
    }


}
