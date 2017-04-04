package screen;

/**
 * Created by ${Victor} on 4/2/2017.
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
}
