package database.images;

import javafx.scene.image.ImageView;

/**
 * Created by Bailey Sostek on 4/11/17.
 */
public class ProxyImage implements ImageInterface {


    private String URL;

    public ProxyImage(String URL){
        this.URL = URL;
    }

    @Override
    public void displayImage(ImageView imageView) {
        ImageOnServer image = new ImageOnServer(URL);
        image.displayImage(imageView);
    }
}
