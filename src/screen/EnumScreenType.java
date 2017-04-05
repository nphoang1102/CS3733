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
    LOG_IN("LoginScreen.fxml", new LoginScreenManager()),
    CREATE_ACCOUNT("CreateAccount.fxml", new CreateAccountManager()),
    COLA_SEARCH("ColaSearchScreen.fxml", new ColaSearchScreenManager()),
    COLA_SEARCH_RESULT("ColaSearchResult.fxml", new ColaSearchResultManager()),
    MANUFACTURER_SCREEN("ManufacturerInbox.fxml", new ManufacturerInboxManager()),
    //    MANUFACTURER_VIEW_FORMS(""),
    MANUFACTURER_ADD_FORM("ManufacturerLabel.fxml", new NewApplicationManager()),
//    MANUFACTURER_EDIT("ManufacturerInbox.fxml", new EditableApplicationManager()),
    AGENT_INBOX("AgentInbox.fxml", new AgentInboxManager()),
    AGENT_APP_SCREEN("AgentAppScreen.fxml", new AgentAppScreenManager()),
    ;

    private Parent scene;
    private Screen screen;

    EnumScreenType(String file, Screen screen) {
        try {
            this.scene = FXMLLoader.load(this.getClass().getResource("/screen/fxml/" + file));
            this.screen = screen;
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

    public Screen getScreen() {
        return this.screen;
    }
}
