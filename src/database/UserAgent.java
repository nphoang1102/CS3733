package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserAgent extends User{
    String ID;
    String superAgent;

    public UserAgent(String name, String username, String email, String ID, String isSuper){
        super(EnumUserType.AGENT, username, email, name);
        this.superAgent = isSuper;
        this.ID = ID;
        if(isSuper.equals("true")){
            this.userType = EnumUserType.SUPER_AGENT;
        }else{
            this.userType = EnumUserType.AGENT;
        }
        this.username = username;
        this.email = email;
        this.name = name;
    }

    public UserAgent(String Username){
        this.ID = Math.random()+"";
        this.superAgent = "false";
        this.email = "";
        this.name = "Bub";
        this.username = Username;
        super.userType = EnumUserType.AGENT;
    }
    public String getSuperAgent(){
        return this.superAgent;
    }

    public void setUserType(EnumUserType temp){
        userType = temp;
    }

}