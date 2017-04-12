package database;

import base.EnumTableType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class Manufacturer extends DataSet {

    public String Username;
    public String Company;
    public String Name;
    public String BreweryPermitNumber;
    public String email;
    public String phoneNumber;
    public String representativeIdNumber;
    public String plantRegistryBasicPermitNumber;

    public Manufacturer() {
        //super(EnumTableType.MANUFACTURER);
    }
}
