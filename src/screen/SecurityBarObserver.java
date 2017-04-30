package screen;


import base.LogManager;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.LinkedList;

import static oracle.jrockit.jfr.events.Bits.intValue;

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

            //deduct points for bad passwords
            for(String s: badPasswords){
                if((s.length()!= 0)&& (s.contains(curPassword))){
                    securityLevel -= .15;
                }
            }
            securityLevel += curPassword.length() / 25.0;


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
            updateColor(securityLevel);
            //update old password
            oldPassword = curPassword;
        }

    }

    public String zeroPadHex(String input){
        if(input.length() == 1){
            input = "0"+input;
        }
        return input;
    }

    public void updateColor(Double input){
        //convert security value to a whole number
        input = input*100;
        String color;
        //map values 0 to 510 (with rounding)
        int mapped = intValue((input*5.1)+.5);
        String redVal;
        String greenVal;
        String blueVal = "00";
        //less than 255, keep red constant, vary green
        //over 255, vary red, keep green constant
        if(mapped<=255){
            //increase green value
            redVal = Integer.toHexString(255);
            greenVal = Integer.toHexString(mapped);
            greenVal = zeroPadHex(greenVal);
        }else{
            //start decreasing red value
            redVal = Integer.toHexString(510-mapped);
            greenVal = Integer.toHexString(255);
            redVal = zeroPadHex(redVal);
        }
        color =  redVal+greenVal+blueVal;
        progressBar.setStyle("-fx-accent: #"+color+"; -fx-focus-color: #34a88b;");
    }
}
