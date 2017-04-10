package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserAgent extends User{
    String ID;
    boolean superAgent;
    public UserAgent(){}
    public UserAgent(String name, String username, String email, String ID, boolean isSuper){
        this.superAgent = isSuper;
        this.ID = ID;
        this.userType = EnumUserType.AGENT;
        this.username = username;
        this.email = email;
        this.name = name;
    }
}
