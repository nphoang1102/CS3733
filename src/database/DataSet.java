package database;

import base.EnumTableType;
import base.LogManager;

import java.util.HashMap;

/**
 * Created by Evan Goldstein on 4/1/17.
 */
public abstract class DataSet{
    private HashMap<String, Object> dataSet = new HashMap<String, Object>();

    public DataSet(){

    }

    public void addField(String key, Object obj){
        dataSet.put(key,  obj);
    }

    public boolean hasKey(String key){
        return dataSet.containsKey(key);
    }

    public Object getValueForKey(String key){
        return dataSet.get(key);
    }
    
    public void printContents(){
        for(int i = 0; i < dataSet.keySet().size(); i++){
            LogManager.println(dataSet.keySet().toArray()[i]+"="+dataSet.values().toArray()[i]);
        }
    }
}