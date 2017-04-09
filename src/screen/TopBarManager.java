package screen;

import base.LogManager;
import base.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Bailey Sostek on 4/7/17.
 */
public class TopBarManager extends Screen{
    /* Some class attributes */
    private String keywords;
    private String searchType = "Beer";

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
        ObservableList<String> typeList = FXCollections.observableArrayList("Beer", "Wine", "Other");
        searchTerm.setItems(typeList);
        searchTerm.setValue("Beer");
    }

    @Override
    public void onScreenFocused() {
        username.setText(Main.getUsername());
        userType.setText(Main.getUserType());
        if(!Main.getUserType().isEmpty()) {
            imageLetter.setText(Main.getUserType().substring(0, 1));
            logIn.setText("Log Out");
        }else{
            imageLetter.setText("");
            logIn.setText("Log In");
        }

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(Main.PATH+"/res/dot.png"));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            userIcon.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logIn(){
        if(!Main.getUsername().isEmpty()){
            Main.logOutUser();
            onScreenFocused();
        }
    }

    public void setScreen(Scene scene){
        screenPane.getChildren().clear();
        screenPane.getChildren().add(0,scene.getRoot());
    }

    public void onEnter() {
        this.keywords = searchBar.getText();
        this.searchType = searchTerm.getValue() + "";
        String toPrint = "User searches for " + this.keywords + " under type " + this.searchType;
        LogManager.println(toPrint);
        this.searchBar.clear();
        Main.screenManager.setScreen(EnumScreenType.COLA_SEARCH_RESULT);
    }
}
