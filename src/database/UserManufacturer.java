package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserManufacturer extends User {
    public String Address2;
    public String Username;
    public String Email;
    public String Company;
    public String Name;
    public String RepID;
    public String PlantRegistry;
    public String PhoneNo;

    public UserManufacturer(String Username){
        Address2= "";
        this.Username = Username;
        Email= "";
        Company= "";
        Name= "";
        RepID= "";
        PlantRegistry= "";
        PhoneNo = "";
        super.userType = EnumUserType.MANUFACTURER;
    }
}
