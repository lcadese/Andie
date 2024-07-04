package cosc202.andie;

import java.awt.Color;
import java.awt.Shape;
import java.awt.image.*;
import java.awt.Graphics2D;

/**
 * ImageOperation to flip an image horizontally.
 * 
 * Adapted from code provided by Steven Mills
 */
public class DrawShape implements ImageOperation, java.io.Serializable{

    private Shape toDraw;
    private Color color;

    public DrawShape(Shape toDraw, Color color){
        this.toDraw = toDraw;
        this.color = color;
    }



    /**
     * Flip the image horizontally.
     * 
     * @param input The image to be converted to be flipped.
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
        g2d.setColor(color); 
        g2d.fill(toDraw);
        return input;
    }
}