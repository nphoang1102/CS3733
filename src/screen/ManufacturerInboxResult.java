package screen;

/**
 * Created by ${mrfortmeyer} on 4/5/2017.
 */
public class ManufacturerInboxResult {

    private String ApplicationNo;
    private String Brand;
    private String Status;
    private String Date;

    public ManufacturerInboxResult(String s1, String s2, String s3, String s4 ){
        ApplicationNo = s1;
        Brand = s2;
        Status = s3;
        Date = s4;
    }

    public String getApplicationNo() {return this.ApplicationNo;}
    public String getBrand() {return this.Brand;}
    public String getStatus() {return this.Status;}
    public String getDate() { return this.Date;}

    public void setApplicationNo(String s){this.ApplicationNo = s;}
    public void setBrand(String s){this.Brand = s;}
    public void setStatus(String s){this.Status = s;}
    public void setDate(String s){this.Date = s;}
}
