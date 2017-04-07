package screen;

/**
 * Created by ${Jack} on 4/2/2017.
 */
public enum EnumUserType {
    PUBLIC_USER("User"),
    MANUFACTURER("Manufacturer"),
    AGENT("Agent"),
    SUPER_AGENT("WOAH YOUR A SUPER USER YOU COOL DUDE"),
    ;

    protected String textualName;

    EnumUserType (String name){
        this.textualName = name;
    }

    public String getTextualName(){
        return this.textualName;
    }
}
