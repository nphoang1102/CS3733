package database;

import base.EnumTableType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class Manufacturer extends DataSet {

    String Username;
    String Password;
    String Company;
    String Name;

    public Manufacturer() {
        super(EnumTableType.MANUFACTURER);
    }
}
