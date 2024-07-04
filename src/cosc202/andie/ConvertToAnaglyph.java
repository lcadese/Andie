package cosc202.andie;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to convert an image to have a anaglphy 3D filter.
 * </p>
 * 
 * @author Elizabeth Stewart
 * @version 1.0
 */
public class ConvertToAnaglyph implements ImageOperation, java.io.Serializable {

    private int RED_CYAN_OFFSET;
    private int THRESHOLD;

    /**
     * <p>
     * Create a new ConvertToAnaglyph operation.
     * </p>
     * 
     * @param offset int amount coloured layers shift either side of the image
     * @param threshold int pixel darkness (anything lower will be included in the coloured layers)
     */
    public ConvertToAnaglyph(int offset, int threshold) {
        this.RED_CYAN_OFFSET = offset;
        this.THRESHOLD = threshold;
    }

    /**
     * <p>
     * Apply anaglyph conversion to an image.
     * </p>
     *
     * @param input The image to be converted to anaglyph
     * @return The resulting anaglyph image.
     */
    public BufferedImage apply(BufferedImage input) {
        float ALPHA = 0.3f;
    
        int width = input.getWidth();
        int height = input.getHeight();
    
        // Create red and cyan channel images
        BufferedImage redChannel = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage cyanChannel = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
        // Iterate over pixels in the input image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color inputColor = new Color(input.getRGB(x, y));
                int inputGray = (inputColor.getRed() + inputColor.getGreen() + inputColor.getBlue()) / 3;
    
                // Check if the input pixel is darker than the threshold
                if (inputGray < THRESHOLD) {
                    int redX = x - RED_CYAN_OFFSET;
                    int cyanX = x + RED_CYAN_OFFSET;
    
                    // Check if the red and cyan channels are within the image bounds
                    if (redX >= 0 && redX < width && cyanX >= 0 && cyanX < width) {
                        // Create a new color for the red channel image
                        Color redColor = new Color(Math.min(255, inputColor.getRed() + 100), 0, 0);
                        redChannel.setRGB(redX, y, redColor.getRGB());
    
                        // Create a new color with only the green and blue channels for the cyan channel image
                        Color cyanColor = new Color(0, inputColor.getGreen(), inputColor.getBlue());
                        cyanChannel.setRGB(cyanX, y, cyanColor.getRGB());
                    }
                }
            }
        }
    
        // Create a graphics object for the new input image
        Graphics2D g2d = input.createGraphics();
    
        // Draw the input image onto the input image
        g2d.drawImage(input, 0, 0, null);
    
        // Set the alpha composite mode to blend the red channel with the input image
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
        g2d.drawImage(redChannel, 0, 0, null);
    
        // Set the alpha composite mode to blend the cyan channel with the input image
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
        g2d.drawImage(cyanChannel, 0, 0, null);
    
        // Dispose of the graphics object
        g2d.dispose();
    
        return input;
    }
}