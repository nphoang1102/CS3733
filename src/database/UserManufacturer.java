package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserManufacturer extends User {
    public String Address2;
    public String Company;
    public String RepID;
    public String PlantRegistry;
    public String PhoneNo;

    public UserManufacturer(String Username){
        Address2= "";
        super.username = Username;
        super.email = "";
        Company= "";
        super.name = "";
        RepID= "";
        PlantRegistry= "";
        PhoneNo = "";
        super.userType = EnumUserType.MANUFACTURER;
    }
}