package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import org.junit.Test;

import cosc202.andie.RotateFilter;

public class RotateFilterTest {

    /**
     * In this test, we create a new BufferedImage object as the input image, 
     * with a width and height of 100 pixels and the TYPE_INT_RGB type. 
     * Then create a new RotateFilter object with a rotation angle of 90 degrees 
     * and apply it to the input image using the apply() method.
     * Then use the assertNotNull() method to make sure that the output image is not null, 
     * and the assertEquals() method to make sure that the output image has the correct width and height after rotation.
     */
    @Test
    public void testRotateFilter() {
        BufferedImage input = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        BufferedImage output = null;
        int degrees = 90;
        
        RotateFilter rotateFilter = new RotateFilter(degrees);
        output = rotateFilter.apply(input);
        
        assertNotNull(output);
        assertEquals(output.getWidth(), input.getHeight());
        assertEquals(output.getHeight(), input.getWidth());
    }
}
