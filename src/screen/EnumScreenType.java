package screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public enum EnumScreenType {
    LOG_IN("LoginScreen.fxml"),
    CREATE_ACCOUNT("CreateAccount.fxml"),
    COLA_SEARCH("ColaSearchScreen.fxml"),
    COLA_SEARCH_RESULT(""),
    MANUFACTURER_SCREEN(""),
    MANUFACTURER_VIEW_FORMS(""),
    MANUFACTURER_ADD_FORM(""),
    MANUFACTURER_EDIT(""),
    AGENT_INBOX("AgentInbox.fxml"),
    AGENT_APP_SCREEN("AgentAppScreen.fxml"),
    ;

    private Parent scene;
    EnumScreenType (String file){
        try {
            this.scene = FXMLLoader.load(this.getClass().getResource("/screen/"+file));
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public Parent getFXMLFile(){
        return this.scene;
    }
}
