package screen;

import base.Main;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Created by Hoang Nguyen on 4/20/2017.
 */
public class NotificationManager extends Screen {
    /* Class constructor */
    public NotificationManager() {
        super(EnumScreenType.NOTIFICATION_SCREEN);
    }

    /* FXML objects */
    @FXML private Label message;
    @FXML private Button closeButton;
    @FXML private Pane container;

    /* Initialize */
    @Override
    public void onScreenFocused(DataSet data) {
        this.message.setText(data.getValueForKey("Message") + "");
        this.message.layoutXProperty().bind(this.container.widthProperty().subtract(this.message.widthProperty()).divide(2));

    }

    /* Close button */
    public void hitMe() {
        Main.screenManager.closeCurrentPopOut();
        return;
    }
}
