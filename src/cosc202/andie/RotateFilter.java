package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to rotate an image.
 * </p>
 * 
 * <p>
 * Rotating comes down to re-ordering the values in the array. 
 * </p>
 * 
 * <p>
 * This filter is non-destructive and returns a new, rotated image.
 * </p>
 * 
 * <p>
 * Note: This implementation assumes that the input image has a width and height greater than zero.
 * </p>
 * 
 * @author Elizabeth Stewart
 * @version 1.0
 */
public class RotateFilter implements ImageOperation, java.io.Serializable {

    private int degrees;

    /**
     * <p>
     * RotateFilter constructor when user provides a degrees value
     * </p>
     * 
     * @param degrees The degress value by which the image should be rotated
     */
    public RotateFilter(int degrees) {       
        this.degrees = degrees;
    }

    /**
     * <p>
     * Applies a rotation transformation to the input image and returns the result as a new BufferedImage.
     * <p>
     * 
     * @param input the input image to apply the transformation to
     * @return the transformed image as a new BufferedImage
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();

        // Calculate the new width and height based on the rotation angle
        int newWidth = getRotatedWidth(input);
        int newHeight = getRotatedHeight(input);

        // Create a new BufferedImage with the calculated width, height, and type
        BufferedImage output = new BufferedImage(newWidth, newHeight, input.getType());

        // Rotate the image pixel by pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int newX = 0, newY = 0;
                switch (degrees) {
                    case 90:
                    case -270:
                        newX = y;
                        newY = width - x - 1;
                        break;
                    case 180:
                    case -180:
                        newX = width - x - 1;
                        newY = height - y - 1;
                        break;
                    case 270:
                    case -90:
                        newX = height - y - 1;
                        newY = x;
                        break;
                    default:
                        newX = x;
                        newY = y;
                        break;
                }
                output.setRGB(newX, newY, input.getRGB(x, y));
            }
        }

        return output;
    }

    /**
     * <p>
     * Calculates the width of the rotated image based on the degrees of rotation.
     * <p>
     * 
     * @param input the input image to calculate the width from
     * @return the width of the rotated image
    */
    private int getRotatedWidth(BufferedImage input) {
        if (degrees % 180 == 0) {
            return input.getWidth();
        } else {
            return input.getHeight();
        }
    }

    /**
     * <p>
     * Calculates the height of the rotated image based on the degrees of rotation.
     * <p>
     * 
     * @param input the input image to calculate the height from\
     * @return the height of the rotated image
    */
    private int getRotatedHeight(BufferedImage input) {
        if (degrees % 180 == 0) {
            return input.getHeight();
        } else {
            return input.getWidth();
        }
    }
}