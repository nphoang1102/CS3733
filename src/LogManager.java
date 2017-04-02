import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class LogManager {

    private LinkedList<String> lines = new LinkedList<String>();
    private final String fileName = "Log.txt";

    public LogManager(){

    }

    /*
        @param line = The line to be added to the log file

        This function both prints the parameter line to the console, and to the log file.
     */
    public void print(String line){
        System.out.print(line);
        lines.add(line);

    }

    /*
        @param line = The line to be added to the log file

        This function both prints the parameter line to the console, and to the log file followed by a new line character.
     */
    public void println(String line){
        System.out.println(line);
        lines.add(line);
    }

    /*
        @param line = The line to be added to the log file

        This function prints the paramater line to the log file.
     */
    public void printSilent(String line){
        lines.add(line);
    }


    public void shutdown(){
        String[] out = new String[lines.size()];
        int index = 0;
        for(String line : lines){
            out[index] = line;
            index++;
        }
        StringUtilities.saveData(Main.PATH+Main.LOG+fileName, out);
    }
}
