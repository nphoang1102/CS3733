package database;

import base.EnumTableType;

/**
 * Created by Daniel Yun on 4/8/2017.
 */
public class Alcohol extends DataSet{

    String TTBID;
    String PermitNo;
    String SerialNo;
    String CompletedDate;
    String FancifulName;
    String BrandName;
    String Origin;
    String Class;
    String Type;
    String AlcoholContent;
    String VintageYear;
    String PH;

    public Alcohol() {
        super(EnumTableType.ALCOHOL);
    }
}
