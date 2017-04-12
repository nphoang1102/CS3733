package screen;

import database.Application;

/**
 * Created by ${mrfortmeyer} on 4/5/2017.
 */
public class ManufacturerInboxResult {

    private String ApplicationNo;
    private String Brand;
    private String ApplicationStatus;
    private String DateOfSubmission;

    public ManufacturerInboxResult(String s1, String s2, String s3, String s4 ){
        ApplicationNo = s1;
        Brand = s2;
        ApplicationStatus = s3;
        DateOfSubmission = s4;
    }

    public String getApplicationNo() {return this.ApplicationNo;}
    public String getBrand() {return this.Brand;}
    public String getStatus() {return this.ApplicationStatus;}
    public String getDate() { return this.DateOfSubmission;}

    public void setApplicationNo(String s){this.ApplicationNo = s;}
    public void setBrand(String s){this.Brand = s;}
    public void setStatus(String s){this.ApplicationStatus = s;}
    public void setDate(String s){this.DateOfSubmission = s;}
}
