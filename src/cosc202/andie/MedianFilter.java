package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * A Median filter takes the median (middle) value of a local neighbourhood in the image, and replaces each pixel with it.
 * </p>
 * 
 * <p> 
 * Adapted from code provided by Steven Mills
 * </p>
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of the filter to apply. A radius of 1 a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    public MedianFilter(){
        this.radius = 1;
    }

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the window used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter.
     */
    public MedianFilter(int radius){
        this.radius = radius;
    }

    /** 
     * <p>
     * Converts an array of argb values to an array of a single colour.
     * </p>
     * 
     * @param argb is the array containing the values from a local neighbourhood
     * @param colour is the desired colour to convert to
     * @return array of a single colour
     */
    public int[] convertToSingleColour(int[] argb, char colour){
        int[] singleColour = new int[argb.length];
        int bitwiseVal = 0; // int value to multiply argb by
        int shiftVal = 0; // value to shift bit by

        switch(colour){
            case 'a':
                bitwiseVal = 0xFF000000;
                shiftVal = 24;
                break;
            case 'r':
                bitwiseVal = 0x00FF0000;
                shiftVal = 16;
                break;
            case 'g':
                bitwiseVal = 0x0000FF00;
                shiftVal = 8;
                break;
            case 'b':
                bitwiseVal = 0x000000FF;
                shiftVal = 0;
                break;
        } 

        for(int i = 0; i < argb.length; i++){
            singleColour[i] = (argb[i] & bitwiseVal) >> shiftVal;
        }
        return singleColour;
    }

    /**
     * <p>
     * Returns a window of radius r of a given image.
     * </p>
     */
    private int[] window(BufferedImage input, int x, int y, int radius){
        int[] localWindow = new int[(2*radius+1)*(2*radius+1)]; // initialises local int[] to be the size of the window
        int index = 0;
        int coordinateX = 0;
        int coordinateY = 0;
        for(int windowY = (y-radius); windowY < (y-radius)+2*radius+1; windowY++){
            for(int windowX = (x-radius); windowX < (x-radius)+2*radius+1; windowX++){

                // handling if Y coordinate is out of bounds
                if(windowY < 0){
                    coordinateY = 0;
                }
                else if(windowY > input.getHeight()-1){
                    coordinateY = input.getHeight()-1;
                }
                else {
                    coordinateY = windowY;
                }

                // handling if X coordinate is out of bounds
                if(windowX < 0){
                    coordinateX = 0;
                }
                else if(windowX > input.getWidth()-1){
                    coordinateX = input.getWidth()-1;
                }
                else {
                    coordinateX = windowX;
                }

                // loops through the window and adds the value of each pixel to the array
                localWindow[index] = input.getRGB(coordinateX, coordinateY); 
                index++;
            }
        }
        return localWindow;
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null); // initialise output image

        for(int y = 0; y < input.getHeight(); y++){ // starts at radius and goes until radius-1 so it doesn't go out of bounds
            for(int x = 0; x < input.getWidth(); x++){
                
                int[] localWindow = window(input, x, y, radius);

                int[] alpha = convertToSingleColour(localWindow, 'a');
                int[] red = convertToSingleColour(localWindow, 'r');
                int[] green = convertToSingleColour(localWindow, 'g');
                int[] blue = convertToSingleColour(localWindow, 'b'); 

                Arrays.sort(alpha);
                Arrays.sort(red);
                Arrays.sort(green);
                Arrays.sort(blue);

                int a = alpha[alpha.length / 2];
                int r = red[red.length / 2];
                int g = green[green.length / 2];
                int b = blue[blue.length / 2];
                
                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }
}