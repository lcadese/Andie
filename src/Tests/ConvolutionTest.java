package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.Kernel;
import cosc202.andie.Convolution;

public class ConvolutionTest {
    @Test
    void convolveWindowTestPositive(){
        int[] below = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] above = {-300, -300, -300, -300, -300, -300, -300, -300, -300};
        int[] avg = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        
        float[] kernelVals = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        boolean negative = false;
        Kernel kernel = new Kernel(3, 3, kernelVals);

        Convolution conv = new Convolution(kernel, negative);
        Assertions.assertEquals(0, conv.convolveWindow(kernel, below));
        Assertions.assertEquals(255, conv.convolveWindow(kernel, above));
        Assertions.assertEquals(9, conv.convolveWindow(kernel, avg));
    }

    @Test
    void convolveWindowTestNegative(){
        int[] below = {129, 129, 129, 129, 129, 129, 129, 129, 129};
        int[] above = {-128, -128, -128, -128, -128, -128, -128, -128, -128};
        int[] avg = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        float[] kernelVals = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        boolean negative = true;
        Kernel kernel = new Kernel(3, 3, kernelVals);

        Convolution conv = new Convolution(kernel, negative);

        Assertions.assertEquals(0, conv.convolveWindow(kernel, below));
        Assertions.assertEquals(255, conv.convolveWindow(kernel, above));
        Assertions.assertEquals(128, conv.convolveWindow(kernel, avg));
    }
}
