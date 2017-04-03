package base;

/**
 * Created by Bailey Sostek on 4/3/17.
 */
public enum EnumWarningType {
    NOTE     ("[NOTE] "    ),
    WARNING  ("[WARNING] " ),
    ERROR    ("[ERROR] "   );

    protected String prefix;

    EnumWarningType(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }
}
