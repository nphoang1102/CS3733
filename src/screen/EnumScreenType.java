package screen;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public enum EnumScreenType {
    TOP_BAR("TopBar.fxml"),
    LOG_IN("LoginScreen.fxml"),
    CREATE_ACCOUNT("CreateAccount.fxml"),
    COLA_SEARCH("ColaSearchScreen.fxml"),
    COLA_SEARCH_RESULT("ColaSearchResult.fxml"),
    MANUFACTURER_SCREEN("ManufacturerInbox.fxml"),
    //    MANUFACTURER_VIEW_FORMS(""),
    MANUFACTURER_ADD_FORM("ManufacturerLabel.fxml"),
    MANUFACTURER_EDIT("EditableApplication.fxml"),
    AGENT_APP_SCREEN("AgentAppScreen.fxml"),
    AGENT_INBOX("AgentInbox.fxml")
    ;

    private Parent scene;

    EnumScreenType(String file){
        try {
            this.scene = FXMLLoader.load(this.getClass().getResource("/screen/fxml/" + file));
        } catch (IOException e) {
            LogManager.println("FXML file not found for:" + this.toString() + ":" + Main.PATH + Main.LOG + "/" + this.getFXMLFile(), EnumWarningType.WARNING);
        } catch (Exception e) {
            LogManager.println(e.toString(), EnumWarningType.ERROR);
            LogManager.printStackTrace(e.getStackTrace());
        }
    }

    public Parent getFXMLFile() {
        return this.scene;
    }
}

