package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class LogManager{

    private static LinkedList<String> lines = new LinkedList<String>();
    private static final String fileName = "Log.txt";
    private static boolean lineTerminated = true;

    public LogManager(){

    }

    /*
        @param line = The line to be added to the log file

        This function both prints the parameter line to the console, and to the log file.
     */
    public static void print(String line){
        System.out.print(line);
        if(lines.size()>0) {
            //if the last line printed was not terminated, then print this line on the last line
            if(!lineTerminated) {
                //print on the last line
                String lastLine = lines.getFirst();
                lines.removeFirst();
                lines.add(lastLine + line);
            }else{
                //if the line was terminated, then print on a new line, and indicate that this new line is not terminated
                lines.add(getDate()+EnumWarningType.NOTE.prefix+line);
            }
        }else{
            lines.add(getDate()+EnumWarningType.NOTE.prefix+line);
        }
        lineTerminated = false;
    }

    /*
        @param line = The line to be added to the log file
        @param warning = the warning level for this message

        This function both prints the parameter line to the console, and to the log file.
     */
    public static void print(String line, EnumWarningType error){
        System.out.print(line);
        if(lines.size()>0) {
            //if the last line printed was not terminated, then print this line on the last line
            if(!lineTerminated) {
                //print on the last line
                String lastLine = lines.getFirst();
                lines.removeFirst();
                lines.add(lastLine + line);
            }else{
                //if the line was terminated, then print on a new line, and indicate that this new line is not terminated
                lines.add(getDate()+error.prefix+line);
            }
        }else{
            lines.add(getDate()+error.prefix+line);
        }
        lineTerminated = false;
    }



    /*
        @param line = The line to be added to the log file

        This function both prints the parameter line to the console, and to the log file followed by a new line character.
     */
    public static void println(String line){
        System.out.println(line);
        printSilent(line);
    }

    /*
            @param line = The line to be added to the log file
            @param warning = the warning level for this message

            This function both prints the parameter line to the console, and to the log file followed by a new line character.
         */
    public static void println(String line, EnumWarningType error){
        System.out.println(line);
        printSilent(line, error);
    }



    /*
        @param line = The line to be added to the log file

        This function prints the paramater line to the log file.
     */
    public static void printSilent(String line){
        //if the last printed line was not terminated, print on that line then indicate that that line is now terminated
        if(!lineTerminated){
            //print on the last line
            if(lines.size()>0) {
                String lastLine = lines.getFirst();
                lines.removeFirst();
                lines.add(lastLine + line);
            }else{
                lines.add(getDate()+EnumWarningType.NOTE.prefix+line);
            }
        }else {
            lines.add(getDate()+EnumWarningType.NOTE.prefix+line);
        }
        lineTerminated = true;
    }

    /*
        @param line = The line to be added to the log file
        @param warning = the warning level for this message

        This function prints the paramater line to the log file.
     */
    public static void printSilent(String line, EnumWarningType error){
        //if the last printed line was not terminated, print on that line then indicate that that line is now terminated
        if(!lineTerminated){
            //print on the last line
            if(lines.size()>0) {
                String lastLine = lines.getFirst();
                lines.removeFirst();
                lines.add(lastLine + line);
            }else{
                lines.add(getDate()+error.prefix+line);
            }
        }else {
            lines.add(getDate()+error.prefix+line);
        }
        lineTerminated = true;
    }




    public void shutdown(){
        String[] out = new String[lines.size()];
        int index = 0;
        for(String line : lines){
            out[index] = line;
            index++;
        }
        StringUtilities.saveData(Main.LOG+"/"+fileName, out);
    }

    public static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
