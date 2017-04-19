package database.images;

import javafx.scene.image.ImageView;

/**
 * Created by Bailey Sostek on 4/11/17.
 */
public class ProxyImage implements ImageInterface {

    private String url;

    public ProxyImage(String URL){
        url = URL;
    }

    @Override
    public void displayImage(ImageView imageView){
        new ImageOnServer(url).displayImage(imageView);
    }

    @Override
    public boolean exists() {
        if(new ImageOnServer(url)!=null){
            return new ImageOnServer(url).exists();
        }
        return false;
    }
}
