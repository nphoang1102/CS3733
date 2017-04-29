package database;

import base.EnumTableType;

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
        TTBID = DatabaseManager.sanitize(TTBID);
        PermitNo = DatabaseManager.sanitize(PermitNo);
        SerialNo = DatabaseManager.sanitize(SerialNo);
        CompletedDate = DatabaseManager.sanitize(CompletedDate);
        FancifulName = DatabaseManager.sanitize(FancifulName);
        BrandName = DatabaseManager.sanitize(BrandName);
        Origin = DatabaseManager.sanitize(Origin);
        Class = DatabaseManager.sanitize(Class);
        Type = DatabaseManager.sanitize(Type);
        AlcoholContent = DatabaseManager.sanitize(AlcoholContent);
        VintageYear = DatabaseManager.sanitize(VintageYear);
        PH = DatabaseManager.sanitize(PH);
        ApplicationNo = DatabaseManager.sanitize(ApplicationNo);
    }
}


