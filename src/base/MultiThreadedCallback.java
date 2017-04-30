package base;

import database.DataSet;

import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * Created by Bailey Sostek on 4/30/17.
 */
public class MultiThreadedCallback {
    public Thread thread;
    public LinkedList <DataSet> data;

    public MultiThreadedCallback(Callable method){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    data = (LinkedList<DataSet>) method.call();
                    System.out.println("Data result Size:"+data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
