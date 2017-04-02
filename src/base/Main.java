package base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screen.EnumScreenType;
import screen.ScreenManager;

import java.io.File;

public class Main extends Application{

    /*
        This is where all of the overall Entity Controllers go
            ScreenManager
            DatabaseManager
     */
    private ScreenManager screenManager;
    private LogManager logManager;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final String NAME = "COLA Databse Search Thing";


    private static Class reference = Main.class;

    //Paths used for this application
    //relative path to the application
    public static String PATH = "";
    public static final String LOG = "/log";


    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize(primaryStage);
        primaryStage.setTitle(NAME);
        ScreenManager.setScreen(EnumScreenType.LOG_IN);
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
}
