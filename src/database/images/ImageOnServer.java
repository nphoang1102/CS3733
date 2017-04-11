package database.images;


import base.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by Bailey Sostek on 4/11/17.
 */
public class ImageOnServer implements ImageInterface{

    private String URL="";
    private Image image;

    protected ImageOnServer(String url){
        this.URL = url;
    }

    private void pullFromServer(String url){
        BufferedImage bufferedImage;
        try {
            FTPClient client = new FTPClient();
            FileOutputStream fos = null;

            client.connect("72.93.244.26");
            client.login("cadborosaurus", "seafoamgreen");

            bufferedImage = ImageIO.read(client.retrieveFileStream("/TTB/"+url));
            image = SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayImage(ImageView view) {
        if(image == null) {
            pullFromServer(URL);
        }
        view.setImage(image);
    }
}
