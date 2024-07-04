package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * <p>
 * ImageOperation to apply an Emboss filter.
 * </p>
 * 
 * <p>
 * An emboss filter creates a 3D effect in a certain direction.
 * </p>
 * 
 * <p> 
 * Adapted from code provided by Steven Mills.
 * </p>
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable {

    protected final float[] d0 = {0, 0, 0, 1, 0, -1, 0, 0, 0};
    protected final float[] d45 = {1, 0, 0, 0, 0, 0, 0, 0, -1};
    protected final float[] d90 = {0, 1, 0, 0, 0, 0, 0, -1, 0};
    protected final float[] d135 = {0, 0, 1, 0, 0, 0, -1, 0, 0};
    protected final float[] d180 = {0, 0, 0, -1, 0, 1, 0, 0, 0};
    protected final float[] d225 = {-1, 0, 0, 0, 0, 0, 0, 0, 1};
    protected final float[] d270 = {0, -1, 0, 0, 0, 0, 0, 1, 0};
    protected final float[] d315 = {0, 0, -1, 0, 0, 0, 1, 0, 0};
    protected int option;

    /**
     * <p>
     * Construct an Emboss filter.
     * </p>
     * 
     * @param option is the option provided by the user in degrees.
     */
    public EmbossFilter(int option){
        this.option = option;
    }

    /**
     * <p>
     * Apply an Emboss filter to an image.
     * </p>
     * 
     * @param input The image to apply the Emboss filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input){
        float[] values = new float[9];
        switch(option){
            case 0:
                values = d0;
                break;
            case 45:
                values = d45;
                break;
            case 90:
                values = d90;
                break;
            case 135:
                values = d135;
                break;
            case 180:
                values = d180;
                break;
            case 225:
                values = d225;
                break;
            case 270:
                values = d270;
                break;
            case 315:
                values = d315;
                break;
        }

        Kernel kernel = new Kernel(3, 3, values);
        Convolution convOp = new Convolution(kernel, true);
        BufferedImage output = convOp.filter(input);

        return output;
    }
}