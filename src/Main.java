import screen.ScreenManager;

import java.io.File;

public class Main implements Runnable {

    /*
        This is where all of the overall Entity Controllers go
            ScreenManager
            DatabaseManager
     */
    public static ScreenManager screenManager;
    public static LogManager logManager;


    private static Class reference = Main.class;

    //Paths used for this application
    //relative path to the application
    public static String PATH = "";
    public static final String LOG = "/log";


//  Local Thread running on the system, hosts all Windows.
    private Thread thread;
    private static boolean running = false;




    /*
        This is the initialization call, where all Managers are initialized
     */
    private void init(){
        //get the relative path
        PATH = StringUtilities.getRelativePath(reference);

        screenManager = new ScreenManager();
        logManager = new LogManager();
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

    /*
        This is where all logical updates happen, this method is called
        at a consistent rate of 60 evenly spaced calls per second.
     */
    private void update(){

    }

    /*
        This method is called when close is requested for the application.
     */
    private void shutdown(){
        logManager.shutdown();
    }









    /*
    This function starts the thread and sets the local variable running to true
    */
    private synchronized void start(){
        if(!running){
            this.thread = new Thread(this);
            this.thread.start();
            this.running = true;
        }
    }


    /*
        @return Returns a boolean indicating if the application is running
     */
    public static boolean isRunning(){
        return running;
    }

    /*
        This method is called when the thread is started. This method
        calls the initialization method, and then sets up a fixed update
        rate of 60 updates per second
     */
    @Override
    public void run() {
        init();
        long last = System.nanoTime();
        final float updatesPerSecond = 60.0f;
        int frames = 0;
        int updates = 0;
        long age = System.currentTimeMillis();
        long extra = 0;

        while(this.running){
            long now = System.nanoTime();

            while((now-last)+extra>=(1000000000.0/updatesPerSecond)){
                if(updates<updatesPerSecond){
                    updates++;
                    update();
                }
                extra += (now-last);
                extra -=(1000000000.0/updatesPerSecond);

                last = now;
            }

            if(System.currentTimeMillis() - age > 1000){
                age = System.currentTimeMillis();
                System.out.println("Updates:"+updates+" Frames:"+frames);
                updates = 0;
                frames = 0;
            }
        }
    }


    /*
        This is the main call to start the application.
        @param args System arguments passed into the application through the command line.
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}
