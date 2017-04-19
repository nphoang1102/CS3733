package screen;

import base.Main;
import database.DataSet;
import database.UserAgent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by ${Victor} on 4/18/2017.
 */
public class AgentVerificationManager extends Screen{
    public AgentVerificationManager() {
        super(EnumScreenType.AGENT_VERIFY);
    }
    @FXML private TextField fullNameField, emailField;

    @FXML private CheckBox tickSuperAgent, tickAgent;

    @FXML private Button submitButton;

    @FXML private Text verifyError;

    String name = "";//fullNameField.getText();
    String email ="";// emailField.getText();
    String type = "";

    @FXML
    private void submit(){
        //hide any previous errors
        verifyError.setVisible(false);
        //record name and email (type is set separately)
        name = fullNameField.getText();
        email = fullNameField.getText();
        //check to see all fields are filled in
        if(!name.equals("")&&!email.equals("")&&!type.equals("")){
            //set current agent's data to the selected fields
            UserAgent tempAgent = (UserAgent) Main.getUser();
            tempAgent.setSuperAgent(type);
            tempAgent.name = name;
            tempAgent.email = email;
            //set screen to agent home page if they don't want to be a super agent
            //set screen to super agent page if they do want to be a super agent
            if(type.equals("false")){
                Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
            }else if(type.equals("true")){
                Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT);
            }
        }else if (name.equals("")||email.equals("")){
            //if a box wasn't selected
            verifyError.setVisible(true);
            verifyError.setText("Please fill out both fields");
        }else if (type.equals("")){
            //if a box was selected, but a field was left blank
            verifyError.setVisible(true);
            verifyError.setText("Please select a box");
        }
    }

    @FXML
    private void selectSuperAgent(){
        //check if the agent box is currently selected
        if(tickSuperAgent.selectedProperty().getValue()) {
            //untick others
            tickAgent.setSelected(false);
            tickAgent.setIndeterminate(false);
            type = "true";
            Main.user.setType(EnumUserType.SUPER_AGENT);
        }else{
            type = "";
        }
    }

    @FXML
    private void selectAgent(){
        //check if the agent box is currently selected
        if(tickAgent.selectedProperty().getValue()) {
            //untick others
            tickSuperAgent.setSelected(false);
            tickSuperAgent.setIndeterminate(false);
            type = "false";
            Main.user.setType(EnumUserType.AGENT);
        }else{
            type = "";
        }
    }

    @Override
    public void onScreenFocused(DataSet data) {
        verifyError.setVisible(false);
    }
}
