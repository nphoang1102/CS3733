package database;

import database.DataSet;
import screen.EnumUserType;

/**
 * Created by Bailey Sostek on 4/3/17.
 */
public abstract class User extends DataSet {
    public EnumUserType userType;
    public String username;
    public String email;
    public String name;
    public String PasswordHash;
    public User(){}
    public User(EnumUserType userType, String username, String email, String name){
        this.userType = userType;
        this.username = username;
        this.email = email;
        this.name = name;
    }

    public EnumUserType getType(){
        return this.userType;
    }

    public String getUsername(){
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }

    public void setType(EnumUserType type){
        userType = type;
    }
    public void setUsername(String name){
        username = name;
    }

    public void setEmail(String inputEmail){
        email = inputEmail;
    }


}
