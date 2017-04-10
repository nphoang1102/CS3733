package database;

import base.LogManager;
import base.Main;
import screen.EnumScreenType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserManufacturer extends User {
    String Address="";
    String Address2="";
    String Password="";
    String Username="";
    String Company="";
    String Name="";
    String BreweryPermitNumber="";
    String email="";
    String phoneNumber="";
    String representativeIdNumber="";
    String plantRegistryBasicPermitNumber="";

    public UserManufacturer(String name, String username){
        this.name = name;
        this.username = username;
    }

    public UserManufacturer() {
        LogManager.println("VICTOR PUT A SECOND CONSTRUCTER IN MANUFACTURER, HE IS SORRY, BUT IT GOT RID OF HIS ERRORS");
    }
}
