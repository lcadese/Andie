package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * <p>
 * ImageOperation to apply a Sobel filter.
 * </p>
 * 
 * <p>
 * A sobel filter is like an emboss filter that emphasizes lines either horizontally or vertically.
 * </p>
 * 
 * <p> 
 * Adapted from code provided by Steven Mills.
 * </p>
 */
public class SobelFilter implements ImageOperation, java.io.Serializable {

    protected final float[] H = {-0.5f, 0f, 0.5f, -1f, 0f, 1f, -0.5f, 0f, 0.5f};  
    protected final float[] V = {-0.5f, -1f, -0.5f, 0f, 0f, 0f, 0.5f, 1f, 0.5f};

    protected int option;

    /**
     * <p>
     * Construct a Sobel filter.
     * </p>
     * 
     * @param option is the option provided by the user (H for horizontal or V for vertical)
     */
    public SobelFilter(int option){
        this.option = option;
    }

    /**
     * <p>
     * Apply a Sobel filter to an image.
     * </p>
     * 
     * @param input The image to apply the Sobel filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input){
        float[] values = new float[9];
        switch(option){
            case 'H':
                values = H;
                break;
            case 'V':
                values = V;
                break;
        }

        Kernel kernel = new Kernel(3, 3, values);
        Convolution convOp = new Convolution(kernel, true);
        BufferedImage output = convOp.filter(input);

       return output;
    }
}