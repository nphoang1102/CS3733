package screen;

import base.EnumTableType;
import base.LogManager;
import base.Main;
import database.*;
import database.images.ProxyImage;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * Created by Bailey Sostek on 4/7/17.
 */
public class TopBarManager extends Screen{

    /* FXML objects */
    @FXML private Label username, userType, imageLetter;
    @FXML private ChoiceBox searchTerm;
    @FXML private Button logIn, action, backButton, search;
    @FXML private ImageView userIcon;
    @FXML private AnchorPane screenPane;
    @FXML private TextField searchBar;

    public TopBarManager() {
        super(EnumScreenType.TOP_BAR);
    }

    private String lastFoucs = "initialUserName";
    private ArrayList<String> suggestField = new ArrayList<String>();

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
        if(!lastFoucs.equals(Main.getUsername())) {
            action.setVisible(true);
            if(Main.getUserType().equals(EnumUserType.AGENT.getTextualName())){
                action.setText("Inbox");
            }
            if(Main.getUserType().equals(EnumUserType.MANUFACTURER.getTextualName())){
                action.setText("Home");
            }
            if (!Main.getUserType().isEmpty()) {
                logIn.setText("Log Out");
                //check if user has a custom image defined
                ProxyImage userImage = new ProxyImage(Main.getUsername() + ".jpg");
                if (!userImage.exists()) {
                    System.out.println(userImage);
                    //This method sets the image to the users type first letter.
                    imageLetter.setText(Main.getUserType().substring(0, 1));
                    if (imageLetter.getText().toUpperCase().equals("W")) {
                        imageLetter.setLayoutX(userIcon.getX() + 19);
                    } else {
                        imageLetter.setLayoutX(userIcon.getX() + 23);
                    }
                    BufferedImage bufferedImage;
                    try {
                        bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot.png"));
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        userIcon.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LogManager.println("User:" + Main.getUsername() + " has a custom icon.");
                    userImage.displayImage(userIcon);
                }
            } else {
                action.setVisible(false);
                imageLetter.setText("");
                logIn.setText("Log In");
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot_empty_user.png"));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    userIcon.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        lastFoucs = Main.getUsername();
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
        if(Main.getUserType().equals(EnumUserType.AGENT.getTextualName())){
            Main.screenManager.setScreen(EnumScreenType.AGENT_INBOX);
        }
        if(Main.getUserType().equals(EnumUserType.MANUFACTURER.getTextualName())){
            Main.screenManager.setScreen(EnumScreenType.MANUFACTURER_SCREEN);
        }
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
        data.addField("isAdvance", "false");
        String toPrint = "User searches for " + searchBar.getText() + " under type " + searchTerm.getValue();
        LogManager.println(toPrint);
        this.searchBar.clear();
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT, data);
        this.initSuggestiveSearch();
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

    public void updateUserIcon(){
        if (!Main.getUserType().isEmpty()) {
            //check if user has a custom image defined
            ProxyImage userImage = new ProxyImage(Main.getUsername() + ".jpg");
            if (!userImage.exists()) {
                System.out.println(userImage);
                //This method sets the image to the users type first letter.
                imageLetter.setText(Main.getUserType().substring(0, 1));
                if (imageLetter.getText().toUpperCase().equals("W")) {
                    imageLetter.setLayoutX(userIcon.getX() + 19);
                } else {
                    imageLetter.setLayoutX(userIcon.getX() + 23);
                }
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot.png"));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    userIcon.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imageLetter.setText("");
                LogManager.println("User:" + Main.getUsername() + " has a custom icon.");
                userImage.displayImage(userIcon);
            }
        } else {
            imageLetter.setText("");
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(new File(Main.PATH + "/res/dot_empty_user.png"));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                userIcon.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DataSet generateTopBarData(){
        BasicDataSet data = new BasicDataSet();
        data.addField("username", Main.getUsername());
        data.addField("userType", Main.getUserType());
        data.addField("searchField", searchBar.getText());
        data.addField("searchTerm", searchTerm.getValue().toString());
        return data;
    }

    public void initSuggestiveSearch() {
        this.suggestField.clear();
        LinkedList<DataSet> databaseResult = DatabaseManager.queryDatabase(EnumTableType.ALCOHOL, "BrandName", "");
        for (DataSet tempSet : databaseResult) {
            Alcohol data = (Alcohol) tempSet;
            if (!this.suggestField.contains(data.BrandName)) {
                this.suggestField.add(data.BrandName);
            }
        }
        TextFields.bindAutoCompletion(
                this.searchBar,
                this.suggestField
        );
    }
}
