package base;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by Bailey Sostek on 4/2/17.
 */
public class StringUtilities {

    /*
        This function determines where the application is hosted on the computer, and returns a string
        representing the absolute path to the main directory for the project.

        @param reference   = The class to get the absolute path to. This should be the main class of the application.
        @return            = Returns a string representing the absolute path to the class 'reference'
     */
    public static String getRelativePath(Class reference){
        String absPath = "";
        try {
            absPath = reference.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            absPath = absPath.replace("/build/classes/", "");
            String[] split = absPath.split("/");
            String out = "";
            for(int i = 0; i < split.length; i++){
                if(split[i].contains(".jar")){
                    split[i]="";
                }
                out = out+"/"+split[i];
            }
            while(out.contains("//")){
                out = out.replaceAll("//", "/");
            }
            absPath = out;
        } catch (Exception e) {
            e.printStackTrace();
            absPath = "";
        }
        return absPath;
    }

    public static void saveData(String path, String[] data){
        try {
            PrintWriter p = new PrintWriter(new File(Main.PATH+path));
            for(int i = 0; i<data.length; i++){
                p.println(data[i]);
            }
            p.close();
        } catch (Exception e) {
            LogManager.println("Error, File:"+Main.PATH+path+" cannot be saved.");
            e.printStackTrace();
        }
    }
}
