package base;

import database.*;
import javafx.application.Application;
import javafx.stage.Stage;
import screen.ScreenManager;

import java.io.File;

public class Main extends Application{

    /*
        This is where all of the overall Entity Controllers go
            ScreenManager
            DatabaseManager
     */
    public static ScreenManager screenManager;
    private LogManager logManager;
    public static DatabaseManager databaseManager;
    public static User user;


    public static final int WIDTH = 1280;
    public static final int HEIGHT = 784;
    public static final String NAME = "COLA Database Search Tool";

    private static DataSet config = new BasicDataSet();

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

        //Load the config file
        String[] configData = StringUtilities.loadData(PATH+"/config.txt");
        if(configData.length>0){
            for(String line : configData){
                String name = line.split("=")[0];
                String data = line.split("=")[1];
                config.addField(name, data);
            }
        }else{
            LogManager.println("Config file not found, Creating new file.", EnumWarningType.ERROR);
            StringUtilities.saveData("/config.txt", new String[]{
                    "DatabaseType=",
                    "DatabaseServer=",
                    "DatabaseName=",
                    "DatabaseUsername=",
                    "DatabasePassword=",
                    "FTPIP=",
                    "FTPUsername=",
                    "FTPPassword=",
                    "ResponceEmailAddress=",
                    "ResponceEmailPassword="
            });

        }

        //Initialize all Managers
        logManager = new LogManager();
        screenManager = new ScreenManager(primaryStage);
        databaseManager = new DatabaseManager();
//        screenManager.initializeTopBar();
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

    public static User getUser(){
        return user;
    }

    public static void setUser (User u){
        user = u;
    }

    public static Object getConfigData(String key){
        return config.getValueForKey(key);
    }

   /* public static String getUserStatus(){
        return user.st
    }*/
}
