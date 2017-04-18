package screen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by ${Jack} on 4/18/2017.
 */
public class AgentPendingManager {

    @FXML
    Label agentName, agentUsername, agentEmail, isSuper;

    @FXML
    Button acceptButton, rejectButton;

    public AgentPendingManager(){
        //super(EnumScreenType.AGENT_PENDING);
    }

    public void acceptAgnet(MouseEvent mouseEvent) {
    }

    public void rejectAgent(MouseEvent mouseEvent) {
    }
}
