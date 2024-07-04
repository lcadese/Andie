package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to resize an image by a scale factor.
 * </p>
 * 
 * @author Elizabeth Stewart
 * @version 1.0
 */
public class ResizeFilter implements ImageOperation, java.io.Serializable {

    private double percentage;

    /**
     * <p>
     * ResizeFilter constructor when user provides a percentage value
     * </p>
     * 
     * @param percentage The percentage value by which the image should be resized
     */
    public ResizeFilter(double percentage) {
        this.percentage = percentage / 100.0;
    }

    /**
     * <p>
     * Apply resizing operation to image.
     * </p>
     * 
     * <p>
     * This method creates a copy of the input image and applies the resizing operation to the copy.
     * The original input image is not modified.
     * </p>
     * 
     * @param input The image which will be resized.
     * @return The image which has now been resized.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Determine the image type
        int type = input.getType();
        if (type == 0) {
            type = BufferedImage.TYPE_INT_ARGB;
        }

        // Create a copy of the input image
        BufferedImage copy = new BufferedImage(input.getWidth(), input.getHeight(), type);
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();

        // Calculate the new width and height based on the percentage
        int newWidth = (int) (copy.getWidth() * this.percentage);
        int newHeight = (int) (copy.getHeight() * this.percentage);

        // Create a new BufferedImage with the calculated width, height, and type
        BufferedImage resized = new BufferedImage(newWidth, newHeight, type);

        // Create a Graphics2D for the resized image
        Graphics2D g2 = resized.createGraphics();

        // Set the rendering hint to improve image quality
        if (this.percentage > 1.0) {
            // Scaling up
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        } else {
            // Scaling down
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }

        // Create an AffineTransform object to scale the image
        AffineTransform transform = AffineTransform.getScaleInstance(this.percentage, this.percentage);
        g2.drawRenderedImage(copy, transform);

        // Dispose of the Graphics2D objects
        g2.dispose();

        // Return the resized image
        return resized;
    }
}