package database;

/**
 * Created by Evan Goldstein on 4/4/17.
 */

public enum EnumTableType {
    ALCOHOL         ("ALCOHOL"),
    MANUFACTURER    ("MANUFACTURER"),
    APPLICATIONS    ("APPLICATIONS");

    protected String table;

    EnumTableType(String table){
        this.table = table;
    }

    public String getPrefix(){
        return this.table;
    }
}
