package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserAgent extends User{
    String ID;
    public UserAgent(){}
    public UserAgent(String name, String username, String email){
        this.userType = EnumUserType.AGENT;
        this.username = username;
        this.email = email;
        this.name = name;
    }
}
