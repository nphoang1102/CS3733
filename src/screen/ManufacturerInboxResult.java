package screen;

import javafx.scene.control.Label;

/**
 * Created by ${mrfortmeyer} on 4/5/2017.
 */
public class ManufacturerInboxResult {

    private String TTBID;
    private Label BrandName;
    private String Status;
    private String Date;

    public ManufacturerInboxResult(String s1, Label aLabel, String s2, String s3 ){
        TTBID = s1;
        BrandName = aLabel;
        Status = s2;
        Date = s3;
    }




}
