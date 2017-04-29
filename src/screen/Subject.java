package screen;

import java.util.ArrayList;
import java.util.List;


public class Subject {
    private List<SecurityObserver> observers = new ArrayList();
    private String password;
    private String username;

    /**
     * Accessor for the value field
     * @return The current value of the subject
     */
    public String getPassword(){
        return password;
    }
    public String getUsername() {return username;}

    /**
     * Setter for the value field
     * @param password New password to propagate to clients
     * @param username send the username to clients
     */
    public void setValue(String password, String username){
        this.password = password;
        this.username = username;
        notifyObservers();
    }

    /**
     * Adds a new observer client to the list of observers for this value
     * @param observer An instance of an observer
     */
    public void attachObserver(SecurityObserver observer){
        observers.add(observer);
    }

    /**
     * Calls the update function to tell each observer this subject's value
     * has changed so they need to update.
     */
    public void notifyObservers(){
        for(SecurityObserver obs : observers)
            obs.update();
    }
}

