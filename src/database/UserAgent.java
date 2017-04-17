package database;

import screen.EnumUserType;

/**
 * Created by Evan Goldstein on 4/8/17.
 */
public class UserAgent extends User{
    String ID;
    String superAgent;
    String status;

    public UserAgent(String name, String username, String email, String ID, String isSuper, String status){
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
        this.status = status;
    }

    public UserAgent(String Username){
        this.ID = Math.random()+"";
        this.superAgent = "false";
        this.email = "";
        this.name = "Bub";
        this.username = Username;
        super.userType = EnumUserType.AGENT;
        this.status = "pending";
    }
    public String getSuperAgent(){
        return this.superAgent;
    }
    public void setSuperAgent(String s){
        if(s.equalsIgnoreCase("true")){
            this.superAgent = s;
        }else if(s.equalsIgnoreCase ("false")){
            this.superAgent = s;
        }else{
            //invalid superAgent type
        }
    }
    public void setUserType(EnumUserType temp){
        userType = temp;
    }

    public String getstatus(){return this.status;}
    public void setStatus(String s) {
        if (s.equalsIgnoreCase("pending")) {
            this.status = s;
        } else if (s.equalsIgnoreCase("suspended")) {
            this.status = s;
        } else if (s.equalsIgnoreCase("approved")) {
            this.status = s;
        } else {
            //invalid agent status
        }
    }
}