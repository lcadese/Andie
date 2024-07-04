package cosc202.andie;
import java.awt.image.*;

public class Convolution{
    private boolean negative;
    private Kernel kernel;
    private int upperBound;
    private int lowerBound;

    public Convolution(Kernel kernel, boolean negative){
        this.kernel = kernel;
        this.negative = negative;

        if(!negative){
            upperBound = 255;
            lowerBound = 0;
        }
        else{
            upperBound = 127;
            lowerBound = -128;
        }
    }

    private int[] window(BufferedImage input, int x, int y){
        int width = kernel.getWidth(); // pof
        int height = kernel.getHeight();
        int[] localWindow = new int[width*height]; // initialises local int[] to be the size of the window
        int index = 0;
        int coordinateX = 0;
        int coordinateY = 0;
        for(int windowY = (y-height/2); windowY < (y-height/2)+height; windowY++){
            for(int windowX = (x-width/2); windowX < (x-width/2)+width; windowX++){

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
     * Converts an array of argb values to an array of a single colour.
     * </p>
     * 
     * @param argb is the array containing the values from a local neighbourhood
     * @param colour is the desired colour to convert to
     * @return array of a single colour
     */
    private int[] convertToSingleColour(int[] argb, char colour){
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

    public int convolveWindow(Kernel kernel, int[] window){
        int size = kernel.getWidth()*kernel.getHeight();
        float[] convolutionMatrix = kernel.getKernelData(new float[size]);
        float convolutionSum = 0;
        for(int i=0; i<size; i++){
            convolutionSum += convolutionMatrix[i]*window[i];
        }
        if(convolutionSum>upperBound){ 
            convolutionSum = 255;
        }
        else if(convolutionSum<lowerBound){
            convolutionSum = 0;
        }
        else{
            if(negative){
                convolutionSum += 128;
            }
            
        }
        return (int)convolutionSum;
    } 

    public BufferedImage filter(BufferedImage src){
        BufferedImage output = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < src.getHeight(); y++){ // starts at radius and goes until radius-1 so it doesn't go out of bounds
            for(int x = 0; x < src.getWidth(); x++){
                
                int[] localWindow = window(src, x, y);
                

                int[] alpha = convertToSingleColour(localWindow, 'a');
                int[] red = convertToSingleColour(localWindow, 'r');
                int[] green = convertToSingleColour(localWindow, 'g');
                int[] blue = convertToSingleColour(localWindow, 'b'); 
                
                int a = convolveWindow(kernel, alpha);
                int r = convolveWindow(kernel, red);
                int g = convolveWindow(kernel, green);
                int b = convolveWindow(kernel, blue);

                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }
}
