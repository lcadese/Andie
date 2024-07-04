package cosc202.andie;

import java.awt.Color;
import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * ImageOperation to flip an image horizontally.
 * 
 * Adapted from code provided by Steven Mills
 */
public class DrawLine implements ImageOperation, java.io.Serializable {

    private Point start;
    private Point end;
    private Color color;

    public DrawLine(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    /**
     * Flip the image horizontally.
     * 
     * @param input The image to be converted to be flipped.
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        g2d.setColor(color);
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.dispose();
        return input;
    }
}