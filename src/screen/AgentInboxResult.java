package screen;

import javafx.scene.control.Label;

/**
 * Created by ${Jack} on 4/4/2017.
 */
public class AgentInboxResult {

     private String ManufacturerName;
     private String BrandName;
     private String ApplicationNo;

     public AgentInboxResult(String aManufacturerName, String aName, String ApplicatoinNo ){
        ManufacturerName = aManufacturerName;
        BrandName = aName;
        this.ApplicationNo = ApplicatoinNo;
     }


    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        ApplicationNo = applicationNo;
    }
}
