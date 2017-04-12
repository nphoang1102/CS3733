package base;

import database.DatabaseManager;
import database.User;
import database.UserAgent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import screen.ScreenManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main extends Application{

    /*
        This is where all of the overall Entity Controllers go
            ScreenManager
            DatabaseManager
     */
    public static ScreenManager screenManager;
    private LogManager logManager;
    public static DatabaseManager databaseManager;
    private static User user;


    public static final int WIDTH = 1280;
    public static final int HEIGHT = 784;
    public static final String NAME = "COLA Database Search Tool";


    private static Class reference = Main.class;

    //Paths used for this application
    //relative path to the application
    public static String PATH = "";
    public static final String LOG = "/log";


    @Override
    public void start(Stage primaryStage) throws Exception {
        LogManager.println("Application Started.");
        initialize(primaryStage);
        primaryStage.setTitle(NAME);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /*
        This is the initialization call, where all Managers are initialized
     */
    private void initialize(Stage primaryStage){
        //get the relative path
        PATH = StringUtilities.getRelativePath(reference);
        //Initialize all Managers
        logManager = new LogManager();
        screenManager = new ScreenManager(primaryStage);
        databaseManager = new DatabaseManager();
        //databaseManager.CreateTables();
        //databaseManager.entry();
        /*
            Check to see if local directories for Saves and Log files exist,
            if any directories are not found, create them.
        */
        File logFolder = new File(PATH+LOG);
        logManager.print("Looking for log Folder:"+PATH+LOG);

        if(!logFolder.exists()){
            boolean result = false;
            logManager.println("");
            logManager.print("Cannot find log folder ... Creating:"+PATH+LOG+" ... ");
            try{
                logFolder.mkdir();
                result = true;
            }
            catch(SecurityException se){
                logManager.println("Failure...");
                logManager.println("File Permissions do not allow the directory:"+PATH+LOG+" to be created.");
            }
            if(result) {
                logManager.println("Success...");
            }
        }else{
            logManager.println(" ... Found");
        }
    }


    @Override
    public void stop() throws Exception {
        logManager.println("Shutting down.");
        logManager.shutdown();
    }

    /*
        This is the main call to start the application.
        @param args System arguments passed into the application through the command line.
     */
    public static void main(String[] args) {

        Main main = new Main();
        main.launch(args);
    }

    public static String getUsername(){
        if(user != null){
            return user.getUsername();
        }
        return "";
    }

    public static String getUserType(){
        if(user != null){
            return user.getType().getTextualName();
        }
        return "";
    }

    public static void logOutUser(){
        user = null;
    }

    public static void setUser (User u){
//        user.setType(u.getType());
//        user.setEmail(u.getEmail());
//        user.setUsername(u.getUsername());
        user = u;
    }
}
