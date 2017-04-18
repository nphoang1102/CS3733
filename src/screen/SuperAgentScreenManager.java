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

import static base.Main.screenManager;


/**
 * Created by ${Jack} on 4/17/2017.
 */
public class SuperAgentScreenManager extends Screen {

    @FXML
    private TableView agentTable;

    @FXML
    private TableColumn<UserAgent, String> agentUsername, agentName, numApps;

    @FXML
    private ChoiceBox statusType;

    @FXML
    private Button goButton;

    private ObservableList<UserAgent> tableInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> agents = new LinkedList<>();



    public SuperAgentScreenManager(){
        super(EnumScreenType.SUPER_AGENT);
    }

    @Override
    public void onScreenFocused(DataSet data) {
        statusType.setValue("ACTIVE");

        agentUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        agentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        numApps.setCellValueFactory(new PropertyValueFactory<>("numAppsRev"));

        //contact database to fill agents by user type
        agents =  DatabaseManager.queryDatabase(EnumTableType.AGENT, "Status", (String) statusType.getValue());

        //wipes the table
        tableInfo.clear();

        //fills the table
        for(DataSet tempData: agents){
            UserAgent tempAgent = (UserAgent) tempData;
            if(tempAgent.getSuperAgent().equals("false")) {
                tableInfo.add(tempAgent);
            }
        }

        this.agentTable.setItems(tableInfo);

        agentTable.setRowFactory( tv -> {
            TableRow<UserAgent> row = new TableRow<>();
            row.setOnMouseClicked( event-> {
                if(event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    UserAgent tempResult = row.getItem();
                    System.out.println(tempResult);
                    if(tempResult.getSuperAgent().equalsIgnoreCase("false")) {
                        if(tempResult.getstatus().equals("PENDING")){
                            screenManager.popoutScreen(EnumScreenType.AGENT_PENDING, "Agent Application Page", tempResult);
                        }else {
                            screenManager.popoutScreen(EnumScreenType.AGENT_INBOX, "View Agent Page", tempResult);
                        }
                    }
                }
            });
            return row;
        });
    }

    public void loadTable(MouseEvent mouseEvent) {
        String tempType = (String) statusType.getValue();
        LinkedList<DataSet> results =  DatabaseManager.queryDatabase(EnumTableType.AGENT,"Status",tempType);
        if(results.size() > 0) {
            for (DataSet tempData : results) {
                agents.add(tempData);
            }
        }
        screenManager.setScreen(EnumScreenType.SUPER_AGENT);
    }


}
