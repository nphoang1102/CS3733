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
        System.out.println("Printing all fields");
        for(PDField field : fields) {
            try {
                processField(field, "|--", field.getPartialName());
            } catch (IOException e) {
                LogManager.println(e.getMessage());
            }
        }
        //CREATE SUBMIT BUTTON
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
            System.out.println(sLevel + sParent);

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
            System.out.println(outputString);
            fillApplication(partialName,fieldValue);
        }
    }

    private void fillApplication(String partialName, String fieldValue) {
        /*
        if(pdfTerminalField.startsWith("|--Check Box22.Check Box22 =")) {
            pdfApp.AlcoholType = pdfTerminalField.replace("|--Check Box22.Check Box22 =","").trim();
        }else if(pdfTerminalField.startsWith("|--Check Box109.Check Box109 = Yes")) {
            pdfApp.ApplicationType = "Certificate of Label Approval";
        }else if(pdfTerminalField.startsWith("|--Check Box34.Check Box34 =")) {
            pdfApp.Locality = pdfTerminalField.replace("|--Check Box34.Check Box34 =", "").
                    replace(",  type=org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox", "").trim();
        }else if(pdfTerminalField.startsWith("|  |--9.  FORMULA =")) {
            pdfApp.Formula = pdfTerminalField.replace("|  |--9.  FORMULA =","").
                    replace(",  type=org.apache.pdfbox.pdmodel.interactive.form.PDTextField", "").trim();
        }else if (pdfTerminalField.startsWith("|  |--11.  WINE APPELLATION (If on label) =")) {
            pdfApp.WineAppelation = pdfTerminalField.replace("|  |--11.  WINE APPELLATION (If on label) =", "").
                    replace(",  type=org.apache.pdfbox.pdmodel.interactive.form.PDTextField","").trim();
        }else if(pdfTerminalField.startsWith("|  |--12.  PHONE NUMBER =")) {
            pdfApp.PhoneNo = pdfTerminalField.replace("|  |--12.  PHONE NUMBER =", "").
                    replace(",  type=org.apache.pdfbox.pdmodel.interactive.form.PDTextField" ,"").trim();
        }
        */
        if(partialName.contains("9.  FORMULA")) {
            pdfApp.Formula = fieldValue;
        }else if(partialName.contains("11.  WINE APPELLATION")) {
            pdfApp.WineAppelation = fieldValue;
        }else if(false) {
            return;
        }
    }
}
