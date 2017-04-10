package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserAgent extends User{
    String ID="";
    String name="";
    String username="";
    String passwordHash = "";
    String email="";
    boolean superAgent=false;

    public UserAgent(String name, String username, String email, String ID, boolean isSuper){
        super(EnumUserType.AGENT, username, email, name);
        this.superAgent = isSuper;
        this.ID = ID;
        if(isSuper){
            this.userType = EnumUserType.SUPER_AGENT;
        }else{
            this.userType = EnumUserType.AGENT;
        }
        this.username = username;
        this.email = email;
        this.name = name;
        this.passwordHash = "";
    }
}
