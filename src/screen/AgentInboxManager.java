package screen;

/**
 * Created by ${Victor} on 4/2/2017.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Polygon;

public class AgentInboxManager extends Screen {
    /* Class constructor */
    public AgentInboxManager() {
        super(EnumScreenType.AGENT_INBOX);
    }

    /* Objects on UI */
    @FXML
    private Button pullNewBatch, newApplication;
    @FXML
    private Polygon backButton;
    @FXML
    private TextArea results;

    /* Class methods */
    @FXML
    void goBack() {

    }

    @FXML
    void pullNewBatch(){

    }

    @FXML
    void newApplication(){

    }
}

