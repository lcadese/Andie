package cosc202.andie;

import java.awt.Rectangle;
import java.awt.image.*;

/**
 * ImageOperation to crop the image.
 * 
 * Adapted from code provided by Steven Mills
 */
public class ImageCrop implements ImageOperation, java.io.Serializable{

    private Rectangle toCrop;

    public ImageCrop(Rectangle toCrop){
        this.toCrop = toCrop;
    }



    /**
     * Crops the BufferedImage
     * 
     * @param input The image to be converted to be cropped.
     * @return The resulting cropped image.
     */
    public BufferedImage apply(BufferedImage input){
            input = input.getSubimage(toCrop.getLocation().x, toCrop.getLocation().y, (int) toCrop.getWidth(), (int) toCrop.getHeight());
        return input;
    }
}