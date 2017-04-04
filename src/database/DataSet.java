package database;

import java.util.HashMap;

/**
 * Created by Evan Goldstein on 4/1/17.
 */
public class DataSet{
    private HashMap<String, String> dataSet = new HashMap<String, String>();

    public DataSet(){

    }

    public void setValueForKey(String key, String obj){
        dataSet.put(key,  obj);
    }

    public String getValueForKey(String key){
        return dataSet.get(key);
    }
}
