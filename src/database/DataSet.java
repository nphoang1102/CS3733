package database;

import base.EnumTableType;
import base.LogManager;

import java.util.HashMap;

/**
 * Created by Evan Goldstein on 4/1/17.
 */
public abstract class DataSet{
    private HashMap<String, String> dataSet = new HashMap<String, String>();

    public DataSet(){

    }

    public void addField(String key, String obj){
        dataSet.put(key,  obj);
    }

    public boolean hasKey(String key){
        return dataSet.containsKey(key);
    }

    public String getValueForKey(String key){
        return dataSet.get(key);
    }
    
    public void printContents(){
        for(int i = 0; i < dataSet.keySet().size(); i++){
            LogManager.println(dataSet.keySet().toArray()[i]+"="+dataSet.values().toArray()[i]);
        }
    }
}