package screen;

import base.LogManager;
import base.Main;
import database.Application;
import database.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by turnrer on 4/27/2017.
 */
public class PickFormManager extends Screen {

    @FXML
    Button continue_button, browse_button;

    @FXML
    AnchorPane screen_pane;

    private Stage primaryStage = new Stage();
    private Application pdfApp = new Application();

    public PickFormManager() {
        super(EnumScreenType.PICK_FORM);
    }

    @Override
    public void onScreenFocused(DataSet data) {

    }

    public void onContinue() {
        LogManager.println("Creating a new application via new form");
        Application app = new Application();
        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.popoutScreen(EnumScreenType.APPLICATION_PAGE_1, "New Application", 1020, 487, app);
    }

    public void onBrowse() {
        //OPEN DOCUMENT BROWSER
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String filename = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
        if(!filename.endsWith(".pdf")) {
            LogManager.println("User file not pdf");
            Label error = new Label();
            error.setText("File not recognized as .pdf! select a different file!");
            error.setLayoutX(browse_button.getLayoutX() - (error.getWidth() / 2.0));
            error.setLayoutY(browse_button.getLayoutY() + 25.0);
            screen_pane.getChildren().add(error);
            return;
        }
        //Load into PDDocument
        PDDocument document;
        try {
            document = PDDocument.load(new File(filename));
        } catch (IOException e) {
            LogManager.print("Error reading pdf ");
            LogManager.println(e.getMessage());
            return;
        }
        //FIll out Application using PDDocument
        PDDocumentCatalog docCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        List<PDField> fields = acroForm.getFields();
        //System.out.println("Printing all fields");
        for(PDField field : fields) {
            try {
                processField(field, "|--", field.getPartialName());
            } catch (IOException e) {
                LogManager.println(e.getMessage());
            }
        }
        //CREATE SUBMIT BUTTON
        Main.screenManager.closeCurrentPopOut();
        Main.screenManager.popoutScreen(EnumScreenType.MANUFACTURER_ADD_FORM,"View New Application", 1280, 720,pdfApp);
    }

    // COPIED FROM APACHE PDFBOX EXAMPLES
    private void processField(PDField field, String sLevel, String sParent) throws IOException
    {
        String partialName = field.getPartialName();

        if (field instanceof PDNonTerminalField)
        {
            if (!sParent.equals(field.getPartialName()))
            {
                if (partialName != null)
                {
                    sParent = sParent + "." + partialName;
                }
            }
            //System.out.println(sLevel + sParent);

            for (PDField child : ((PDNonTerminalField)field).getChildren())
            {
                processField(child, "|  " + sLevel, sParent);
            }
        }
        else
        {
            String fieldValue = field.getValueAsString();
            StringBuilder outputString = new StringBuilder(sLevel);
            outputString.append(sParent);
            if (partialName != null)
            {
                outputString.append(".").append(partialName);
            }
            outputString.append(" = ").append(fieldValue);
            outputString.append(",  type=").append(field.getClass().getName());
            //System.out.println(outputString);
            fillApplication(partialName,fieldValue);
        }
    }

    private void fillApplication(String partialName, String fieldValue) {
        if(partialName.contains("FORMULA")) {
            pdfApp.Formula = fieldValue;
        }else if(partialName.contains("WINE APPELLATION")) {
            pdfApp.WineAppelation = fieldValue;
        }else if(partialName.contains("PHONE NUMBER")) {
            pdfApp.PhoneNo = fieldValue;
        }else if(partialName.contains("EMAIL ADDRESS")) {
            pdfApp.Email = fieldValue;
        }else if(partialName.contains("FANCIFUL NAME")) {
            pdfApp.FancifulName = fieldValue;
        }else if(partialName.contains("BRAND NAME")) {
            pdfApp.Brand = fieldValue;
        }else if(partialName.contains("GRAPE VARIETAL(S)")) {
            pdfApp.Grapes = fieldValue;
        }else if(partialName.contains("NAME AND ADDRESS OF APPLICANT AS SHOWN ON PLANT REGISTRY, BASIC")) {
            pdfApp.Address = fieldValue;
        }else if(partialName.contains("MAILING ADDRESS, IF DIFFERENT")) {
            pdfApp.Address2 = fieldValue;
        }else if(partialName.equals(" (If any)")) {
            pdfApp.RepID = fieldValue;
        }else if(partialName.equals(" (Required)") ) {
            pdfApp.PlantRegistry = fieldValue;
        }else if(partialName.contains("SERIAL NUMBER 1")) {
            pdfApp.SerialNo = fieldValue;
        }else if(partialName.contains("SERIAL NUMBER 2")) {
            pdfApp.SerialNo = pdfApp.SerialNo + fieldValue;
        }else if(partialName.contains("SERIAL NUMBER 3")) {
            pdfApp.SerialNo = pdfApp.SerialNo + fieldValue;
        }else if(partialName.contains("SERIAL NUMBER 4")) {
            pdfApp.SerialNo = pdfApp.SerialNo + fieldValue;
        }else if(partialName.contains("ONLY IF IT DOES NOT APPEAR ON THE LABELS")) {
            pdfApp.AdditionalInfo = fieldValue;
        }else if (partialName.contains("PRINT NAME OF APPLICANT OR AUTHORIZED AGENT")) {
            pdfApp.RepName = fieldValue;
        }else if (partialName.contains("Check Box109") && fieldValue.equals("yes")) {
            pdfApp.ApplicationType = "Certificate of Label Approval";
        }else if (partialName.contains("Check Box22") && fieldValue.equals("Malt")) {
            pdfApp.AlcoholType = "Beer";
        }else if (partialName.contains("Check Box22") && fieldValue.equals("Spirits")) {
            pdfApp.AlcoholType = "Distilled Spirits";
        }else if (partialName.contains("Check Box22") && fieldValue.equals("Wine")) {
            pdfApp.AlcoholType = "Wine";
        }else if(partialName.contains("Check Box34")) {
            pdfApp.Locality = fieldValue.trim();
        }
        //System.out.println("Partial Name: " + partialName + " Field Value: " + fieldValue);

    }
}
