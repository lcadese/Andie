package Tests;

import org.junit.jupiter.api.Test;

import cosc202.andie.BrightnessContrastAdjustment;
import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrightnessContrastAdjustmentTest {

    @Test
    public void testBrightnessIncrease() {
        BufferedImage input = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Set pixel colour to (128, 128, 128)
        input.setRGB(0, 0, 0x808080);

        // 100% brightness increase
        BrightnessContrastAdjustment adjustment = new BrightnessContrastAdjustment(0, 100);
        BufferedImage output = adjustment.apply(input);

        // Expected output (255, 255, 255)
        Color expectedColor = new Color(255, 255, 255);
        Color actualColor = new Color(output.getRGB(0, 0));
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testBrightnessDecrease() {
        BufferedImage input = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Set pixel colour to (128, 128, 128)
        input.setRGB(0, 0, 0x808080);

        // 100% brightness decrease
        BrightnessContrastAdjustment adjustment = new BrightnessContrastAdjustment(0, -100);
        BufferedImage output = adjustment.apply(input);

        // Expected output (0, 0, 0)
        Color expectedColor = new Color(0, 0, 0);
        Color actualColor = new Color(output.getRGB(0, 0));
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testContrastIncrease() {
        BufferedImage input = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Set pixel colour to (128, 128, 128)
        input.setRGB(0, 0, 0x808080);

        // 100% contrast increase
        BrightnessContrastAdjustment adjustment = new BrightnessContrastAdjustment(100, 0);
        BufferedImage output = adjustment.apply(input);

        // Expected output colour (128, 128, 128)
        Color expectedColor = new Color(128, 128, 128);
        Color actualColor = new Color(output.getRGB(0, 0));
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testContrastDecrease() {
        BufferedImage input = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Set pixel colour to (128, 128, 128)
        input.setRGB(0, 0, 0x808080);

        // 100% contrast decrease
        BrightnessContrastAdjustment adjustment = new BrightnessContrastAdjustment(-100, 0);
        BufferedImage output = adjustment.apply(input);

        // Expected output (127, 127, 127)
        Color expectedColor = new Color(127, 127, 127);
        Color actualColor = new Color(output.getRGB(0, 0));
        assertEquals(expectedColor, actualColor);
    }
}
