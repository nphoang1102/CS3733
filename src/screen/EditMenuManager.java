package screen;

import base.LogManager;
import base.Main;
import database.Application;
import database.BasicDataSet;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;

/**
 * Created by ${mrfortmeyer} on 4/15/2017.
 */

public class EditMenuManager extends Screen{

    public EditMenuManager() {
        super(EnumScreenType.LOG_IN);
    }

    @FXML
    private CheckBox revision1;

    @FXML
    private CheckBox revision2;

    @FXML
    private CheckBox revision3;

    @FXML
    private CheckBox revision4;

    @FXML
    private CheckBox revision5;

    @FXML
    private CheckBox revision6;

    @FXML
    private CheckBox revision7;

    @FXML
    private CheckBox revision8;

    @FXML
    private CheckBox revision9;

    @FXML
    private CheckBox revision10;

    @FXML
    private CheckBox revision11;

    @FXML
    private CheckBox revision12;

    @FXML
    private Button nextButton;

    private LinkedList<CheckBox> boxList = new LinkedList<>();

    private LinkedList<CheckBox> selectedList = new LinkedList<>();

    private LinkedList<Boolean> boolList = new LinkedList<>();

    private Application data;

    public Screen getScreen(){
        return this;
    }

    @Override
    public void onScreenFocused(DataSet dataSet) {
        this.data = (Application) dataSet;
    }

    public void next(){
        boxList.clear();
        selectedList.clear();
        boolList.clear();

        boxList.add(revision1);
        boxList.add(revision2);
        boxList.add(revision3);
        boxList.add(revision4);
        boxList.add(revision5);
        boxList.add(revision6);
        boxList.add(revision7);
        boxList.add(revision8);
        boxList.add(revision9);
        boxList.add(revision10);
        boxList.add(revision11);
        boxList.add(revision12);

        for(CheckBox c : boxList){
            boolList.add(c.isSelected());
        }

        for(int i = 0 ; i < 12; i++){
            if(boolList.get(i)){
                selectedList.add(boxList.get(i));
            }
        }

        if(selectedList.size() > 1){
            LogManager.println("Nice try Lane, but you can't select multiple revisions");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Revision Selection Error");
            alert.setHeaderText("You selected multiple revisions!");
            alert.setContentText("Please Only Select One Revision");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("css/Error.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            alert.showAndWait();
        } else if(selectedList.size() == 0){
            LogManager.println("Nice try Lane, but you have to select something");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Revision Selection Error");
            alert.setHeaderText("You didn't select anything!");
            alert.setContentText("Please Select a Revision");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("css/Error.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            alert.showAndWait();
        }
        else{
            String text = selectedList.get(0).getText();
            LogManager.println(text);
            String[] splitString = text.split(". ");
            LogManager.println("Going to edit: " + splitString[0]);
            Integer revNo = Integer.parseInt(splitString[0]);
            if(!data.AlcoholType.equals("Wine") && ((revNo == 4) || (revNo == 5) || (revNo == 7))) {
                LogManager.println("That's for wine only!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Revision Selection Error");
                alert.setHeaderText("You can't select that one!");
                alert.setContentText("The Revision You Selected is Only for Wines");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("css/Error.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                alert.showAndWait();
                return;
            }

            data.revisionNo = revNo;
            Main.screenManager.closeCurrentPopOut();
            Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_EDIT, data.FancifulName, 1025, 700, data);
        }
        return;
    }
}
