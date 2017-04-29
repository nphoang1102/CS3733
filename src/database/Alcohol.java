package database;

import base.EnumTableType;
import base.StringUtilities;

/**
 * Created by Daniel Yun on 4/8/2017.
 */
public class Alcohol extends DataSet{
    public String TTBID;
    public String PermitNo;
    public String SerialNo;
    public String CompletedDate;
    public String FancifulName;
    public String BrandName;
    public String Origin;
    public String Class;
    public String Type;
    public String AlcoholContent;
    public String VintageYear;
    public String PH;
    public String ApplicationNo;

    public void sanitize(){
        TTBID = StringUtilities.sanitize(TTBID);
        PermitNo = StringUtilities.sanitize(PermitNo);
        SerialNo = StringUtilities.sanitize(SerialNo);
        CompletedDate = StringUtilities.sanitize(CompletedDate);
        FancifulName = StringUtilities.sanitize(FancifulName);
        BrandName = StringUtilities.sanitize(BrandName);
        Origin = StringUtilities.sanitize(Origin);
        Class = StringUtilities.sanitize(Class);
        Type = StringUtilities.sanitize(Type);
        AlcoholContent = StringUtilities.sanitize(AlcoholContent);
        VintageYear = StringUtilities.sanitize(VintageYear);
        PH = StringUtilities.sanitize(PH);
        ApplicationNo = StringUtilities.sanitize(ApplicationNo);
    }
}


