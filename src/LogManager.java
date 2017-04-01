import java.util.LinkedList;

/**
 * Created by Bailey Sostek on 4/1/17.
 */
public class LogManager {

    private LinkedList<String> lines = new LinkedList<String>();
    private final String filePath = "logs/Log.txt";

    public LogManager(){

    }

    public void print(String s){
        System.out.print(s);
        lines.add(s);

    }

    public void println(String s){
        System.out.println(s);
        lines.add(s);
    }

    private void shutDown(){

    }
}
