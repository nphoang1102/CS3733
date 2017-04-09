package screen;

import javafx.scene.control.Label;

/**
 * Created by ${mrfortmeyer} on 4/5/2017.
 */
public class ManufacturerInboxResult {

    private String TTBID;
    private String BrandName;
    private String Status;
    private String Date;

    public ManufacturerInboxResult(String s1, String s2, String s3, String s4 ){
        TTBID = s1;
        BrandName = s2;
        Status = s3;
        Date = s4;
    }

    public String getTTBID() {return this.TTBID;}
    public String getBrandName() {return this.BrandName;}
    public String getStatus() {return this.Status;}
    public String getDate() { return this.Date;}

    public void setTTBID(String s){this.TTBID = s;}
    public void setBrandName(String s){this.BrandName = s;}
    public void setStatus(String s){this.Status = s;}
    public void setDate(String s){this.Date = s;}
}
