package screen;

import base.*;
import database.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;

import static base.Main.main;
import static base.Main.screenManager;


/**
 * Created by ${Jack} on 4/17/2017.
 */
public class SuperAgentScreenManager extends Screen {

    @FXML
    private TableView agentTable;

    @FXML
    private TableColumn<UserAgent, String> agentUsername, agentName;

    @FXML
    private ChoiceBox statusType;

    @FXML
    private Button goButton, appScreenButton;

    @FXML
    private Label agentStatus, isPending;

    private ObservableList<UserAgent> tableInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> agents = new LinkedList<>();



    public SuperAgentScreenManager(){
        super(EnumScreenType.SUPER_AGENT);

    }
    @FXML
    public void initialize() {
       statusType.setValue("active");
    }

    @Override
    public void onScreenFocused(DataSet data) {

        UserAgent thisAgent = (UserAgent) Main.getUser();

            if (data.hasKey("agentStatus")) {
                statusType.setValue(data.getValueForKey("agentStatus"));
            } else {
                statusType.setValue("active");
            }
            agentUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            agentName.setCellValueFactory(new PropertyValueFactory<>("name"));

            //contact database to fill agents by user type
            agents = DatabaseManager.queryDatabase(EnumTableType.AGENT, "Status", (String) statusType.getValue());

            //wipes the table
            tableInfo.clear();

            //fills the table
            for (DataSet tempData : agents) {
                UserAgent tempAgent = (UserAgent) tempData;

                    tableInfo.add(tempAgent);

            }

            this.agentTable.setItems(tableInfo);

            agentTable.setRowFactory(tv -> {
                TableRow<UserAgent> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        UserAgent tempResult = row.getItem();
                        System.out.println(tempResult);

                            if (tempResult.getstatus().equals("pending")) {
                                screenManager.popoutScreen(EnumScreenType.AGENT_PENDING, "Agent Application Page", 325, 250, tempResult);
                            } else {
                                screenManager.setScreen(EnumScreenType.AGENT_INBOX, tempResult);
                            }

                    }
                });
                return row;
            });
        if(thisAgent.getstatus().equals("active")) {
            isPending.setVisible(false);
        }else{
            agentTable.setVisible(false);
            statusType.setVisible(false);
            goButton.setVisible(false);
            agentStatus.setVisible(false);
            appScreenButton.setVisible(false);
        }
    }

    public void loadTable(MouseEvent mouseEvent) {

        DataSet data = new BasicDataSet();
        data.addField("agentStatus", (statusType.getValue() + ""));
        String toPrint =  " under type " + statusType.getValue();
        LogManager.println(toPrint);
        Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT, data);
    }


    public void launchAppScreen(MouseEvent mouseEvent) {
        screenManager.setScreen((EnumScreenType.SUPER_AGENT_APPLICATION));
    }


}
