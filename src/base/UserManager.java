package base;

/**
 * Created by ${Jack} on 4/5/2017.
 */
public class UserManager {
    private User user;

    public UserManager(){};

    public void setUserManager(User aUser){
        user = aUser;
    }

    public UserManager getUserManager(){
        return this;
    }
}
