package database;

import base.EnumTableType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class Agent extends DataSet{

    String ID;
    String Username;
    String Password;
    String Name;
    String Email;

    public Agent() {
        super(EnumTableType.AGENT);
    }
}
