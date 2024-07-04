package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian blur.
 * </p>
 * 
 * <p>
 * A Gaussian blur is a convolution using the gaussian distribution.
 * </p>
 * 
 * 
 * @see java.awt.image.ConvolveOp
 * @author Elia Hayashishita
 * @version 1.0
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian blur with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MeanFilter
     */
    GaussianBlur(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Gaussian blur with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian blur has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    GaussianBlur() {
        this(1);
    }

    /**
     * <p>
     * Apply a  Gaussian Blur to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian Blur is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gaussian Blur to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1) * (2*radius+1);
        float sigma = radius/3.0f;
        float sum = 0; 
        float [] array = new float[size];
        for(int y=-radius; y<=radius;y++){
            for(int x=-radius; x<=radius;x++){
                sum += 1/(2*Math.PI*sigma*sigma)*(float)Math.exp(-(x*x+y*y)/(2*sigma*sigma));
            }
        }
        int index = 0;
        for(int y=-radius; y<=radius;y++){
            for(int x=-radius; x<=radius;x++){
                array[index] = (float) (1/(2*Math.PI*sigma*sigma)*Math.exp(-(x*x+y*y)/(2*sigma*sigma)))/sum;
                index ++;
            }
        }

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        Convolution convOp = new Convolution(kernel, false);
        BufferedImage output = convOp.filter(input);

        return output;
    }


}
