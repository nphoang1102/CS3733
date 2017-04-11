package database;

import base.EnumTableType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class Manufacturer extends DataSet {

    String Username;
    String Company;
    String Name;
    String BreweryPermitNumber;
    String email;
    String phoneNumber;
    String representativeIdNumber;
    String plantRegistryBasicPermitNumber;

    public Manufacturer() {
        //super(EnumTableType.MANUFACTURER);
    }
}
