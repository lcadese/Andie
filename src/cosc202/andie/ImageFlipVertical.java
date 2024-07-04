package cosc202.andie;

import java.awt.image.*;

/**
 * ImageOperation to flip an image horizontally.
 * 
 * Adapted from code provided by Steven Mills
 */
public class ImageFlipVertical implements ImageOperation, java.io.Serializable{

    /**
     * Flip the image horizontally.
     * 
     * @param input The image to be converted to be flipped.
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input){
        for(int  x = 0; x < input.getWidth(); x++){
            for(int y = 0; y < Math.floor(input.getHeight()/2); y++){ // goes until half of the image vertically 
                int up = input.getRGB(x, y); // gets the corresponding pixel from the upper side of the partition
                int down = input.getRGB(x, input.getHeight() - y - 1); // gets the corresponding pixel from the lower side of the partition
                input.setRGB(x, input.getHeight() - y - 1, up); // swaps the lower pixel with the upper pixel
                input.setRGB(x, y, down); // swaps the upper pixel with the lower pixel
            }
        }
        return input; 
    }
}