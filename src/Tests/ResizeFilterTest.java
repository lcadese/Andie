package Tests;

import static org.junit.Assert.assertEquals;
import java.awt.image.BufferedImage;
import org.junit.Test;

import cosc202.andie.ResizeFilter;


public class ResizeFilterTest {

    /** 
     * This test case creates a BufferedImage object with a width and height of 100 pixels 
     * and passes it as the input to the apply() method. It then creates a ResizeFilter object with a scale factor of 50% 
     * and applies the resizing operation to the input image. 
     * Finally, it checks that the output image has the correct dimensions 
     * (which should be half the dimensions of the input image).
     */
    @Test
    public void testApply1() {
        // Create input image with known width and height
        int width = 100;
        int height = 100;
        BufferedImage input = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create ResizeFilter object with 50% scale factor
        ResizeFilter resizeFilter = new ResizeFilter(50);

        // Apply resizing operation
        BufferedImage output = resizeFilter.apply(input);

        // Check that the output image has the correct dimensions
        assertEquals(width / 2, output.getWidth());
        assertEquals(height / 2, output.getHeight());
    }

    /** 
     * This test case creates a BufferedImage object with a width and height of 100 pixels 
     * and passes it as the input to the apply() method. It then creates a ResizeFilter object with a scale factor of 200% 
     * and applies the resizing operation to the input image. 
     * Finally, it checks that the output image has the correct dimensions 
     * (which should be double the dimensions of the input image).
     */
    @Test
    public void testApply2() {
        // Create input image with known width and height
        int width = 100;
        int height = 100;
        BufferedImage input = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create ResizeFilter object with 50% scale factor
        ResizeFilter resizeFilter = new ResizeFilter(200);

        // Apply resizing operation
        BufferedImage output = resizeFilter.apply(input);

        // Check that the output image has the correct dimensions
        assertEquals(width * 2, output.getWidth());
        assertEquals(height * 2, output.getHeight());
    }
}
