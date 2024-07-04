package Tests;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImageFlipHorizontal;
import cosc202.andie.ImageFlipVertical;

public class ImageFlipTest {
    
    /**
     * Test checking that the dimensions of the image do not change when flipped horizontally.
     */
    @Test
    void horizontalSizeTest(){
        int width = 5, height = 7;
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        ImageFlipHorizontal hFlip = new ImageFlipHorizontal();
        BufferedImage output = hFlip.apply(bf);

        Assertions.assertEquals(output.getWidth(), width);
        Assertions.assertEquals(output.getHeight(), height);
    }

    /**
     * Test checking that the dimensions of the image do not change when flipped vertically.
     */
    @Test
    void verticalSizeTest(){
        int width = 5, height = 7;
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        ImageFlipVertical vFlip = new ImageFlipVertical();
        BufferedImage output = vFlip.apply(bf);

        Assertions.assertEquals(output.getWidth(), width);
        Assertions.assertEquals(output.getHeight(), height);
    }

    /**
     * Test checking that the left and right edges of the image have been flipped correctly in a horizontal flip.
     */
    @Test
    void horizontalEdgeTest(){
        // Width and height values
        int width = 5, height = 7;

        // Create new image of width 5 and height 7
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Add colours to the image
        int[] argb = {-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-9399426,-10056582,-11039878, -8513349, -2935282,-8156565,-6070976,-1399426,-11056582,-11139878,-11633200,-13619914,-13616452};
        bf.setRGB(0, 0, width, height, argb, 0, width);

        // Get the left and right edges of the image and put them in arrays
        int[] left = new int[height], right = new int[height];   
        bf.getRGB(0, 0, 1, height, left, 0, 1);
        bf.getRGB(width-1, 0, 1, height, right, 0, 1);

        // Apply the filter
        ImageFlipHorizontal hFlip = new ImageFlipHorizontal();
        hFlip.apply(bf);
        
        // Check that the current (flipped) right edge is equal to the original left, and the current (flipped) left edge is equal to the original right. 
        Assertions.assertTrue(Arrays.equals(bf.getRGB(0, 0, 1, height, new int[height], 0, 1), right));
        Assertions.assertTrue(Arrays.equals(bf.getRGB(width-1, 0, 1, height, new int[height], 0, 1), left));
    }

    /**
     * Test checking that the top and bottom edges of the image have been flipped correctly in a vertical flip.
     */
    @Test
    void verticalEdgeTest(){
        // Width and height values
        int width = 5, height = 7;

        // Create new image of width 5 and height 7
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Add colours to the image
        int[] argb = {-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-9399426,-10056582,-11039878, -8513349, -2935282,-8156565,-6070976,-1399426,-11056582,-11139878,-11633200,-13619914,-13616452};
        bf.setRGB(0, 0, width, height, argb, 0, width);

        // Get the top and bottom edges of the image and put them in arrays
        int[] top = new int[width], bottom = new int[width];        

        bf.getRGB(0, 0, width, 1, top, 0, width);
        bf.getRGB(0, height-1, width, 1, bottom, 0, width);

        // Apply the filter
        ImageFlipVertical vFlip = new ImageFlipVertical();
        vFlip.apply(bf);
        
        // Check that the current (flipped) bottom edge is equal to the original top, and the current (flipped) top edge is equal to the original bottom. 
        Assertions.assertTrue(Arrays.equals(bf.getRGB(0, 0, width, 1, new int[width], 0, width), bottom));
        Assertions.assertTrue(Arrays.equals(bf.getRGB(0, height-1, width, 1, new int[width], 0, width), top));
    }
}
