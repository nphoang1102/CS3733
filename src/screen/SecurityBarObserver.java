package screen;


import base.LogManager;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.LinkedList;

public class SecurityBarObserver extends SecurityObserver{

    private String username ="";
    /**
     * Constructor; Note that we attach the instance of this class to
     * the subject object.
     * @param subject Subject who drives the data
     * @param progressBar TextArea bound to this observer
     */
    public SecurityBarObserver(Subject subject, ProgressBar progressBar){
        this.subject = subject;
        this.subject.attachObserver(this);
        this.progressBar = progressBar;
    }

    String oldPassword="";

    @Override
    public void update() {
        // set the security bar to the correct value

        //show the progress bar
        progressBar.setVisible(true);
        //record current password
        String curPassword = subject.getPassword();
        this.username = subject.getUsername();
        //reset security level
        double securityLevel = 0.0;
        //check the password actually changed
        if(!curPassword.equals(oldPassword)) {

            LinkedList<String> badPasswords = new LinkedList<String>(Arrays.asList(
                    "1234567890",
                    "password",
                    "qwerty",
                    this.username,
                    "1111111",
                    "dragon",
                    "mustang",
                    "letmein",
                    "baseball",
                    "master"
            ));

            //not a bad password
            for(String s: badPasswords){
                if(s.contains(curPassword)){
                    securityLevel -= .15;
                }
            }
            securityLevel += curPassword.length() / 30.0;


            if (curPassword.matches(".*\\d+.*")) {
                //password contains a number
                //award quite a few points
                securityLevel += 0.06;
            }
            if(curPassword.matches("^.*[A-Z].*$")){
                //password contains upper case
                securityLevel +=.06;
            }
            if (curPassword.matches("^.*[^a-zA-Z0-9 ].*$")) {
                //password has a symbol
                //award a lot of points
                securityLevel += .15;
            }

            //keep level in bounds
            if(securityLevel < 0){
                securityLevel = 0;
            }else if(securityLevel > 1){
                securityLevel = 1;
            }
            progressBar.setProgress(securityLevel);
            if (securityLevel <= .25) {
                //low security
                //make bar red
                progressBar.setStyle("-fx-accent: #ff0000; -fx-focus-color: #34a88b;");
            } else if (securityLevel <= .7) {
                //yellow
                progressBar.setStyle("-fx-accent: #FFF100; -fx-focus-color: #34a88b;");
            } else if (securityLevel > .7) {
                //orange
                progressBar.setStyle("-fx-accent:  #34a88b; -fx-focus-color: #34a88b;");
            }
            //update old password
            oldPassword = curPassword;
        }

    }

    public void RedToGreen(double input){
        input *= 100;
        int color = 0x000000;


    }
}
