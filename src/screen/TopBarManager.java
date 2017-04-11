package screen;

import base.LogManager;
import base.Main;
import database.BasicDataSet;
import database.DataSet;
import database.UserManufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Bailey Sostek on 4/7/17.
 */
public class TopBarManager extends Screen{

    @FXML
    private Button pullNewBatch;

    @FXML
    Label username;

    @FXML
    Label userType;

    @FXML
    Label imageLetter;

    @FXML
    ChoiceBox searchTerm;

    @FXML
    Button logIn;

    @FXML
    Button action;

    @FXML
    Button backButton;

    @FXML
    ImageView userIcon;

    @FXML
    AnchorPane screenPane;

    @FXML
    TextField searchBar;

    public TopBarManager() {
        super(EnumScreenType.TOP_BAR);
    }

    @FXML
    public void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("All", "Beer", "Wine", "Other");
        searchTerm.setItems(typeList);
        searchTerm.setValue("All");
    }

    @Override
    public void onScreenFocused(DataSet data) {
        username.setText(Main.getUsername());
        userType.setText(Main.getUserType());
        LogManager.println("VICTOR wants to know if " + Main.getUsername()+" is a "+Main.getUserType());
        if(!Main.getUserType().isEmpty()) {
            //check if user has a custom image defined
//            if(DatabaseManager.getImage()) {
                //This method sets the image to the users type first letter.
                imageLetter.setText(Main.getUserType().substring(0, 1));
                if(imageLetter.getText().toUpperCase().equals("W")){
                    imageLetter.setLayoutX(userIcon.getX()+19);
                }else{
                    imageLetter.setLayoutX(userIcon.getX()+23);
                }
                logIn.setText("Log Out");
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot.png"));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    userIcon.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }else {
//                imageLetter.setText("");
//                logIn.setText("Log Out");
//                BufferedImage bufferedImage;
//                try {
//                    bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot.png"));
//                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//                    userIcon.setImage(image);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }else{
            imageLetter.setText("");
            logIn.setText("Log In");
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(new File(Main.PATH+"/res/dot_empty_user.png"));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                userIcon.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void logIn(){
        if(!Main.getUsername().isEmpty()){
            Main.logOutUser();
            onScreenFocused(new BasicDataSet());
        }
        Main.screenManager.setScreen(EnumScreenType.LOG_IN);
    }

    @FXML
    public void back(){
        Main.screenManager.back();
    }

    @FXML
    public void action(){
//        action.setVisible(false);
        Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_EDIT, "TEST",1280, 720, new BasicDataSet());
    }

    public void setScreen(Scene scene){
        screenPane.getChildren().clear();
        screenPane.getChildren().add(0,scene.getRoot());
    }

    /* Upon enter key hit within the search field, get the values and pass into the ColaSearchResultManager.java */
    public void onEnter() {
        DataSet data = new BasicDataSet();
        data.addField("Keywords", searchBar.getText());
        data.addField("AlcoholType", (searchTerm.getValue() + ""));
        String toPrint = "User searches for " + searchBar.getText() + " under type " + searchTerm.getValue();
        LogManager.println(toPrint);
        this.searchBar.clear();
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT, data);
    }

    public String getSearchTerm(){
        if(searchTerm!=null) {
            if (searchTerm.getValue() != null) {
                return searchTerm.getValue().toString();
            } else {
                return "";
            }
        }
        return "";

    }
}
