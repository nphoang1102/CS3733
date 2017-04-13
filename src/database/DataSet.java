package database;

import base.EnumTableType;

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
        if(getValueForKey(key)!=null){
            return true;
        }else{
            return false;
        }
    }

    public String getValueForKey(String key){
        return dataSet.get(key);
    }
}