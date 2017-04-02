package screen;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Created by Hoang Nguyen on 4/2/2017.
 */
public class ColaSearchScreenManager {
    /* Building the control objects */
    @FXML
    Pane colaSearchPanel;
    @FXML
    TextField entryField;
    @FXML
    Ellipse searchButton;
    @FXML
    Text searchLabel;
    @FXML
    Polygon backButton;
    @FXML
    CheckBox beer, wine;

    /* Class attributes */
    private String keywords;
    private String type;

    /* Class methods */


}
