package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * A class used for adjusting the contrast and brightness of an image.
 * </p>
 * 
 */
public class BrightnessContrastAdjustment implements ImageOperation, java.io.Serializable {

    private double brightnessPercentage;
    private double contrastPercentage;

    /**
     * <p>
     * Constructs a BrightnessContrastAdjustment object
     * </p>
     * 
     * @param contrast   percentage
     * @param brightness percentage
     */
    public BrightnessContrastAdjustment(double contrast, double brightness) {
        this.brightnessPercentage = brightness;
        this.contrastPercentage = contrast;
    }

    /**
     * <p>
     * Uses the brightness and contrast adjustment to the current image and returns
     * adjusted image.
     * </p>
     * 
     * @param input the image
     * @return the adjusted image
     */

    public BufferedImage apply(BufferedImage input) {
        // Implement brightness and contrast adjustment

        int width = input.getWidth();
        int height = input.getHeight();

        // Create a new image to store the result
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        float contrastAdj = (float) (1f + (contrastPercentage / 100f));
        float brightnessAdj = (float) (1f + (brightnessPercentage / 100f));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = input.getRGB(i, j);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = (rgb) & 0xFF;

                // adjust contrast and brightness
                red = (int) (contrastAdj * (red - 127.5f) + 127.5f * brightnessAdj);
                green = (int) (contrastAdj * (green - 127.5f) + 127.5f * brightnessAdj);
                blue = (int) (contrastAdj * (blue - 127.5f) + 127.5f * brightnessAdj);

                // check for new rgb values out of bounds
                red = Math.max(0, Math.min(red, 255));
                green = Math.max(0, Math.min(green, 255));
                blue = Math.max(0, Math.min(blue, 255));

                // set new rgb values
                rgb = (red << 16) | (green << 8) | blue;
                output.setRGB(i, j, rgb);
            }
        }

        return output;
    }
}
