package cosc202.andie;
import java.awt.image.*;
/**
 ** <p>
 * ImageOperation to apply a sharpen filter.
 * </p>
 * @author Elia Hayashishita
 * <p>
 * A Sharpen filter enhances the difference between the neighbouring darker and lighter pixels
 * </p>
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    SharpenFilter(){

    }
    public BufferedImage apply(BufferedImage input){
        float [] array = {0, -1/2.0f, 0, -1/2.0f, 3f, -1/2.0f, 0, -1/2.0f, 0};

        Kernel kernel = new Kernel(3, 3, array);
        Convolution convOp = new Convolution(kernel, false);
        BufferedImage output = convOp.filter(input);

        return output;
    }
}
