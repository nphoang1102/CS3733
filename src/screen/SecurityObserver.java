package screen;

import javafx.scene.control.ProgressBar;

/**
 * Created by ${Victor} on 4/27/2017.
 */
public abstract class SecurityObserver {
    protected Subject subject;
    protected ProgressBar progressBar;
    /**
     * Called when the attached subject's state (or value) changes)
     */
    public abstract void update();
}
