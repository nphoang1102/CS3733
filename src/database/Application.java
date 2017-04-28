package database;

import base.Main;
import base.StringUtilities;
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
        ApplicationNo = StringUtilities.sanitize(ApplicationNo);
        SerialNo = StringUtilities.sanitize(SerialNo);
        ApplicationType = StringUtilities.sanitize(ApplicationType);
        ApplicationStatus = StringUtilities.sanitize(ApplicationStatus);
        ManufacturerUsername = StringUtilities.sanitize(ManufacturerUsername);
        RepName = StringUtilities.sanitize(RepName);
//        AgentUsername = StringUtilities.sanitize(AgentUsername);
//        AgentName = StringUtilities.sanitize(AgentName);
        RepID = StringUtilities.sanitize(RepID);
        PlantRegistry = StringUtilities.sanitize(PlantRegistry);
        Locality = StringUtilities.sanitize(Locality);
        Brand = StringUtilities.sanitize(Brand);
        if(FancifulName != null){FancifulName = StringUtilities.sanitize(FancifulName);}
        AlcoholType = StringUtilities.sanitize(AlcoholType);
        ABV = StringUtilities.sanitize(ABV);
        Address = StringUtilities.sanitize(Address);
        if(Address2 != null){Address2 = StringUtilities.sanitize(Address2);}
        Formula = StringUtilities.sanitize(Formula);
        if(WineAppelation != null){WineAppelation = StringUtilities.sanitize(WineAppelation);}
        if(VintageDate != null){VintageDate = StringUtilities.sanitize(VintageDate);}
        if(Grapes != null){Grapes = StringUtilities.sanitize(Grapes);}
        if(PH != null){PH = StringUtilities.sanitize(PH);}
        PhoneNo = StringUtilities.sanitize(PhoneNo);
        Email = StringUtilities.sanitize(Email);
        if(AdditionalInfo != null){AdditionalInfo = StringUtilities.sanitize(AdditionalInfo);}
//        DateOfSubmission = StringUtilities.sanitize(DateOfSubmission);
//        DateOfApproval = StringUtilities.sanitize(DateOfApproval);
//        DateOfExpiration = StringUtilities.sanitize(DateOfExpiration);
        ApprovedTTBID = StringUtilities.sanitize(ApprovedTTBID);
//        ReasonForRejection = StringUtilities.sanitize(ReasonForRejection);
    }
}
