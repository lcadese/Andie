package cosc202.andie;

import java.awt.image.*;

/**
 * ImageOperation to flip an image horizontally.
 * 
 * Adapted from code provided by Steven Mills
 */
public class ImageFlipHorizontal implements ImageOperation, java.io.Serializable{

    /**
     * Flip the image horizontally.
     * 
     * @param input The image to be converted to be flipped.
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input){
        for(int y = 0; y < input.getHeight(); y++){
            for(int x = 0; x < Math.floor(input.getWidth()/2); x++){ // goes until half of the image horizontally
                int left = input.getRGB(x, y); // gets the corresponding pixel from the left side of the partition
                int right = input.getRGB(input.getWidth() - x - 1, y); // gets the corresponding pixel from the right side of the partition
                input.setRGB(input.getWidth() - x - 1, y, left); // swaps the right pixel with the left pixel
                input.setRGB(x, y, right); // swaps the left pixel with the right pixel
            }
        }
        return input;
    }
}