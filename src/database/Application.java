package database;

import javafx.scene.control.Label;

import java.lang.reflect.Field;

/**
 * Created by Evan Goldstein on 4/6/17.
 */
public class Application extends DataSet {
    public String ApplicationNo;
    public String SerialNo;
    public String ApplicationType;
    public String ApplicationStatus;
    public String ManufacturerUsername;
    public String RepName;
    public String AgentUsername;
    public String AgentName;
    public String RepID;
    public String PlantRegistry;
    public String Locality;
    public String Brand;
    public String FancifulName;
    public String AlcoholType;
    public String ABV;
    public String Address;
    public String Address2;
    public String Formula;
    public String WineAppelation;
    public String VintageDate;
    public String Grapes;
    public String PH;
    public String PhoneNo;
    public String Email;
    public String AdditionalInfo;
    public String DateOfSubmission;
    public String DateOfApproval;
    public String DateOfExpiration;
    public String ApprovedTTBID;
    public String ReasonForRejection;

    public int revisionNo;

    public String getBrand(){
        return this.Brand;
    }

    public String getManufacturerUsername(){
        return this.ManufacturerUsername;
    }

    public String getApprovedTTBID(){ return this.ApprovedTTBID;}
    public String getAgentUsername(){ return this.AgentUsername;}
    public String getAgentName(){ return  this.AgentName;}
    public String getDateOfSubmission() { return this.DateOfSubmission;}

    public void sanitize() {
        ApplicationNo = DatabaseManager.sanitize(ApplicationNo);
        SerialNo = DatabaseManager.sanitize(SerialNo);
        ApplicationType = DatabaseManager.sanitize(ApplicationType);
        ApplicationStatus = DatabaseManager.sanitize(ApplicationStatus);
        ManufacturerUsername = DatabaseManager.sanitize(ManufacturerUsername);
        RepName = DatabaseManager.sanitize(RepName);
        AgentUsername = DatabaseManager.sanitize(AgentUsername);
        AgentName = DatabaseManager.sanitize(AgentName);
        RepID = DatabaseManager.sanitize(RepID);
        PlantRegistry = DatabaseManager.sanitize(PlantRegistry);
        Locality = DatabaseManager.sanitize(Locality);
        Brand = DatabaseManager.sanitize(Brand);
        FancifulName = DatabaseManager.sanitize(FancifulName);
        AlcoholType = DatabaseManager.sanitize(AlcoholType);
        ABV = DatabaseManager.sanitize(ABV);
        Address = DatabaseManager.sanitize(Address);
        Address2 = DatabaseManager.sanitize(Address2);
        Formula = DatabaseManager.sanitize(Formula);
        WineAppelation = DatabaseManager.sanitize(WineAppelation);
        VintageDate = DatabaseManager.sanitize(VintageDate);
        Grapes = DatabaseManager.sanitize(Grapes);
        PH = DatabaseManager.sanitize(PH);
        PhoneNo = DatabaseManager.sanitize(PhoneNo);
        Email = DatabaseManager.sanitize(Email);
        AdditionalInfo = DatabaseManager.sanitize(AdditionalInfo);
        DateOfSubmission = DatabaseManager.sanitize(DateOfSubmission);
        DateOfApproval = DatabaseManager.sanitize(DateOfApproval);
        DateOfExpiration = DatabaseManager.sanitize(DateOfExpiration);
        ApprovedTTBID = DatabaseManager.sanitize(ApprovedTTBID);
        ReasonForRejection = DatabaseManager.sanitize(ReasonForRejection);
    }
}
