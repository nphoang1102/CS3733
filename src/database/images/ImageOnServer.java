package database.images;


import base.EnumWarningType;
import base.LogManager;
import base.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by Bailey Sostek on 4/11/17.
 */
public class ImageOnServer implements ImageInterface{

    private String URL="";
    private Image image = null;


    protected ImageOnServer(String url){
        this.URL = url;
        pullFromServer(url);
    }

    private void pullFromServer(String url){
        BufferedImage bufferedImage;
        try {
            FTPClient client = new FTPClient();
            client.connect("72.93.244.26");
            client.login("cadbo", "seafoamgreen");
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            bufferedImage = ImageIO.read(client.retrieveFileStream("/home/cadbo/TTB/"+url));
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            client.disconnect();
        } catch (Exception e) {
            LogManager.println("Image not found on FTP server:"+"/home/cadbo/TTB/"+url, EnumWarningType.ERROR);
        }

    }

    @Override
    public void displayImage(ImageView view) {
        if(image!=null){
            view.setImage(image);
        }
    }

    @Override
    public boolean exists() {
        if(image!=null){
            return true;
        }else{
            return false;
        }
    }
}
