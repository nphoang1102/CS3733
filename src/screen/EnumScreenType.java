package screen;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
import javafx.fxml.FXML;
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
    COLA_ADVANCE_SEARCH("AdvanceColaSearch.fxml"),
    COLA_SEARCH_RESULT("ColaSearchResult.fxml"),
    MANUFACTURER_SCREEN("ManufacturerInbox.fxml"),
    //    MANUFACTURER_VIEW_FORMS(""),
    MANUFACTURER_ADD_FORM("Application.fxml"),
    MANUFACTURER_EDIT("EditableApplication.fxml"),
    AGENT_APP_SCREEN("AgentAppScreen.fxml"),
    AGENT_INBOX("AgentInbox.fxml"),
    EDIT_ACCOUNT("UserSettings.fxml"),
    COLA_RESULT_POPUP("ColaResultPopup.fxml"),
    EDIT_APPLICATION_LIST("EditMenu.fxml"),
    APPLICATION_PAGE_1("ApplicationPage1.fxml"),
    APPLICATION_PAGE_2("ApplicationPage2.fxml"),
    APPLICATION_PAGE_3("ApplicationPage3.fxml"),
    APPLICATION_PAGE_WINE("ApplicationPageWine.fxml"),
    APPLICATION_PAGE_5("ApplicationPage5.fxml"),
    STATUS_SCREEN("StatusScreen.fxml"),
    SUPER_AGENT("SuperAgentScreen.fxml"),
    AGENT_PENDING("AgentPending.fxml"),
    AGENT_VERIFY("AgentVerification.fxml"),
    SUPER_AGENT_APPLICATION("SuperAgentApplications.fxml"),
    NOTIFICATION_SCREEN("Notification.fxml"),
    COLA_CHARACTER_SELECTION("ColaCharSelector.fxml"),
    PICK_FORM("pickform.fxml"),
    LOADING("Loading.fxml"),
    ;

    private String scene;
    private Class controller;

    EnumScreenType(String file){
        this.scene = file;
    }

    public String getFXMLFile() {
        return this.scene;
    }

}

