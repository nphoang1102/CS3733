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
    private String AlcoholType;

    public Application app;

    public ManufacturerInboxResult(String s1, String s2, String s3, String s4, String s5, Application a){
        ApplicationNo = s1;
        Brand = s2;
        ApplicationStatus = s3;
        DateOfSubmission = s4;
        AlcoholType = s5;
        app = a;
    }

    public String getApplicationNo() {return this.ApplicationNo;}
    public String getBrand() {return this.Brand;}
    public String getApplicationStatus() {return this.ApplicationStatus;}
    public String getDateOfSubmission() { return this.DateOfSubmission;}
    public String getAlcoholType() { return this.AlcoholType;}

    public void setApplicationNo(String s){this.ApplicationNo = s;}
    public void setBrand(String s){this.Brand = s;}
    public void setApplicationStatus(String s){this.ApplicationStatus = s;}
    public void setDateOfSubmission(String s){this.DateOfSubmission = s;}
    public void setAlcoholType(String s){this.AlcoholType = s;}
}
