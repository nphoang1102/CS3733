package database;

import javafx.scene.control.Label;

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
}
