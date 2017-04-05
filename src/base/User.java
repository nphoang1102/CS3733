package base;

import screen.EnumUserType;

/**
 * Created by Bailey Sostek on 4/3/17.
 */
public class User {
    private EnumUserType userType;
    private String username;
    private String email;


    public User(EnumUserType userType, String username, String email){
        this.userType = userType;
        this.username = username;
        this.email = email;
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
