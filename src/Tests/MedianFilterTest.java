package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.MedianFilter;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFilterTest {
    
    /**
     * Test checking that the convertToSingleColour method converts an array of argb integers into a specified colour correctly (for all colours).
     */
    @Test
    void convertToSingleColourTest(){
        
        // Random argb colour value
        int[] argb = {-5682652};
        int[] aExpected = new int[argb.length], rExpected = new int[argb.length], gExpected = new int[argb.length], bExpected = new int[argb.length], aActual = new int[argb.length], rActual = new int[argb.length], gActual = new int[argb.length], bActual = new int[argb.length];
        
        for(int i = 0; i < argb.length; i++){
            aExpected[i] = (argb[i] & 0xFF000000) >> 24;
            rExpected[i] = (argb[i] & 0x00FF0000) >> 16;
            gExpected[i] = (argb[i] & 0x0000FF00) >> 8;
            bExpected[i] = (argb[i] & 0x000000FF);
        }
        
        MedianFilter mf = new MedianFilter();
        
        aActual = mf.convertToSingleColour(argb, 'a');
        rActual = mf.convertToSingleColour(argb, 'r');
        gActual = mf.convertToSingleColour(argb, 'g');
        bActual = mf.convertToSingleColour(argb, 'b');

        Assertions.assertTrue(Arrays.equals(aExpected, aActual));
        Assertions.assertTrue(Arrays.equals(rExpected, rActual));
        Assertions.assertTrue(Arrays.equals(gExpected, gActual));
        Assertions.assertTrue(Arrays.equals(bExpected, bActual));
        
    }

    /**
     * Test checking that the window has the correct values.
     */
    @Test
    void windowTest(){
        BufferedImage bf = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        int width = 5, height = 5;
        int[] argb = {-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-8543349, -8935282,-8936565,-9070976,-9399426,-10056582,-11039878,-11633300,-13609914,-13611452,-5682652,-9399426,-10056582,-11039878};
        int[] test = new int[argb.length];
        bf.setRGB(0, 0, width, height, argb, 0, width);
        bf.getRGB(0, 0, width, height, test, 0, width);

        Assertions.assertTrue(Arrays.equals(argb, test));
    }

}