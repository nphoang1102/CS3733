package screen;


import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;

import static base.Main.screenManager;

/**
 * Created by ${Jack} on 4/25/2017.
 */
public class SuperAgentApplicationManager extends Screen {

    @FXML
    private TableView appTable;

    @FXML
    private TableColumn<UserAgent, String> ttbidNum ,agentUsername, agentName, dateSubmitted;

    @FXML
    private ChoiceBox appStatusBox;

    @FXML
    private Button sortButton;

    @FXML
    private Label title;

    private ObservableList<Application> tableInfo = FXCollections.observableArrayList();
    private LinkedList<DataSet> applications = new LinkedList<>();

    public SuperAgentApplicationManager(){
        super(EnumScreenType.SUPER_AGENT_APPLICATION);
    }

    @FXML
    public void initialize() {
        appStatusBox.setValue("PENDING");
    }


    @Override
    public void onScreenFocused(DataSet data) {



        if (data.hasKey("appStatus")) {
            appStatusBox.setValue(data.getValueForKey("appStatus"));
            title.setText("All " + data.getValueForKey("appStatus") + " Applications");
        } else {
            appStatusBox.setValue("PENDING");
            title.setText("All PENDING Applicatoins");
        }

        ttbidNum.setCellValueFactory(new PropertyValueFactory<>("ApprovedTTBID"));
        agentUsername.setCellValueFactory(new PropertyValueFactory<>("AgentUsername"));
        agentName.setCellValueFactory(new PropertyValueFactory<>("AgentName"));
        dateSubmitted.setCellValueFactory(new PropertyValueFactory<>("DateOfSubmission"));

        applications = DatabaseManager.queryDatabase(EnumTableType.APPLICATION, "ApplicationStatus", (String) appStatusBox.getValue());

        //wipes the table
        tableInfo.clear();

        //fills the table
        for (DataSet tempData : applications) {
            Application tempAgent = (Application) tempData;
            tableInfo.add(tempAgent);

        }

        this.appTable.setItems(tableInfo);

        appTable.setRowFactory(tv -> {
            TableRow<Application> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Application tempResult = row.getItem();

                    screenManager.popoutScreen(EnumScreenType.AGENT_APP_SCREEN, "Monitering Pending Application", tempResult);
                }
            });
            return row;
        });




    }

    public void SortApplications(MouseEvent mouseEvent) {
        String tempType = (String) appStatusBox.getValue();
/*        LinkedList<DataSet> results =  DatabaseManager.queryDatabase(EnumTableType.APPLICATION,"ApplicationStatus",tempType);
        if(results.size() > 0) {
            for (DataSet tempData : results) {
                applications.add(tempData);
            }
        }*/
        DataSet data = new BasicDataSet();
        data.addField("appStatus", (appStatusBox.getValue() + ""));
        String toPrint =  " under type " + appStatusBox.getValue();
        LogManager.println(toPrint);
        Main.screenManager.setScreen(EnumScreenType.SUPER_AGENT_APPLICATION, data);
    }
}
