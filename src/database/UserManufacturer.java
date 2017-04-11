package database;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserManufacturer extends User {
    public String Address ="";
    public String Address2="";
    public String Username="";
    public String Password="";
    public String Company="";
    public String Name="";
    public String RepID="";
    public String PlantRegistry="";
    public String PhoneNo="";

    public UserManufacturer(String username){
        this.username = username;
    }
    public UserManufacturer(){}
}
