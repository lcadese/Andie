package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.awt.Graphics2D;

/**
 * <p>
 * Actions provided by the Image menu.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Ray Meech
 * @version 1.0
 */

// mouseDragged mouse montionListner
public class DrawSelection implements MouseListener {

    Timer timer = new Timer();
    TimerTask updateTask;
    int xOrigin;
    int yOrigin;
    ImagePanel imagePanel;
    DrawRect drawRect;
    String shapeString;
    Shape shape;
    ResourceBundle bundle;
    Color color;

    /**
     * <p>
     * Create a DrawSelection object.
     * </p>
     */
    public DrawSelection(ImagePanel imagePanel, String shapeString, Color color) {
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");
        this.shapeString = shapeString;
        this.imagePanel = imagePanel;
        this.color = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    /**
     * <p>
     * Actions for when mouse is pressed
     * </p>
     * 
     * <p>
     * This method selects a rectangle starting from where the left mouse button was
     * first pressed to the current
     * mouse position.
     * Seclection is static after mouse released and can be deselected using a left
     * click
     * </p>
     * 
     * @param e The event triggering this callback.
     */
    public void mousePressed(MouseEvent e) {
        Timer timer = new Timer();

        xOrigin = e.getX();
        yOrigin = e.getY();
        long delay = 16;
        drawRect = new DrawRect(xOrigin, yOrigin);
        updateTask = new UpdateTask();
        timer.scheduleAtFixedRate(updateTask, new Date(), delay);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Stops updating shape based on cursor
        updateTask.cancel();
        if (shapeString.equals("line")) {
            Point start = new Point(xOrigin, yOrigin);
            Point end = new Point(e.getX(), e.getY());
            imagePanel.getImage().apply(new DrawLine(start, end, color));
        } else {
            imagePanel.getImage().apply(new DrawShape(drawRect.getShapeToDraw(), color));
        }
        imagePanel.repaint();
        imagePanel.revalidate();
        imagePanel.removeMouseListener(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // imagePanel.removeMouseListener(this);
    }

    public Point getCords() {
        Point localPosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(localPosition, imagePanel);
        return localPosition;

    }

    /**
     * <p>
     * Class to update the DrawRect
     * <p>
     */
    class UpdateTask extends TimerTask {
        public void run() {
            drawRect.updateRectangle();

        }
    }

    private class DrawRect extends JPanel {
        Rectangle currRectangle;
        int yOrigin;
        int xOrigin;

        public DrawRect(int xOrigin, int yOrigin) {
            currRectangle = new Rectangle(xOrigin, yOrigin, 9, 9);
            this.yOrigin = yOrigin;
            this.xOrigin = xOrigin;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g2d);
            g2d.setColor(color);
            Point p = getCords();

            if (p.x - xOrigin >= 0 && p.y - yOrigin >= 0) {
                if (shapeString == "rectangle") {
                    g2d.fillRect(xOrigin, yOrigin, p.x - xOrigin, p.y - yOrigin);
                } else if (shapeString == "ellipses") {
                    g2d.fill(new Ellipse2D.Double(xOrigin, yOrigin, currRectangle.getWidth(),
                            currRectangle.getHeight()));

                }
                currRectangle = new Rectangle(xOrigin, yOrigin, p.x - xOrigin, p.y - yOrigin);
                imagePanel.repaint();
                imagePanel.revalidate();
            }

            else if (p.x - xOrigin < 0 && p.y - yOrigin < 0) {
                if (shapeString == "rectangle") {
                    g2d.fillRect(p.x, p.y, xOrigin - p.x, yOrigin - p.y);
                } else if (shapeString == "ellipses") {
                    g2d.fill(new Ellipse2D.Double(p.x, p.y, xOrigin - p.x, yOrigin - p.y));

                }
                currRectangle = new Rectangle(p.x, p.y, xOrigin - p.x, yOrigin - p.y);
                imagePanel.repaint();
                imagePanel.revalidate();
            }

            else if (p.x - xOrigin > 0 && p.y - yOrigin < 0) {
                if (shapeString == "rectangle") {
                    g2d.fillRect(xOrigin, p.y, p.x - xOrigin, yOrigin - p.y);
                } else if (shapeString == "ellipses") {
                    g2d.fill(new Ellipse2D.Double(xOrigin, p.y, p.x - xOrigin, yOrigin - p.y));

                }
                currRectangle = new Rectangle(xOrigin, p.y, p.x - xOrigin, yOrigin - p.y);
                imagePanel.repaint();
                imagePanel.revalidate();
            }

            else {
                if (shapeString == "rectangle") {
                    g2d.fillRect(p.x, yOrigin, xOrigin - p.x, p.y - yOrigin);
                } else if (shapeString == "ellipses") {
                    g2d.fill(new Ellipse2D.Double(p.x, yOrigin, xOrigin - p.x, p.y - yOrigin));

                }
                currRectangle = new Rectangle(p.x, yOrigin, xOrigin - p.x, p.y - yOrigin);
                imagePanel.repaint();
                imagePanel.revalidate();
            }
            // new line-drawing logic
            if (shapeString.equals("line")) {
                g2d.draw(new Line2D.Double(xOrigin, yOrigin, p.x, p.y));
                int minX = Math.min(xOrigin, p.x);
                int minY = Math.min(yOrigin, p.y);
                int width = Math.abs(xOrigin - p.x);
                int height = Math.abs(yOrigin - p.y);
                currRectangle = new Rectangle(minX, minY, width, height);
                imagePanel.repaint();
                imagePanel.revalidate();
            }
        }

        public void updateRectangle() {
            Rectangle oldRect = (Rectangle) currRectangle.clone(); // save the old rectangle
            paintComponent(imagePanel.getGraphics()); // update currRectangle
            oldRect.add(currRectangle); // create a union of the old and new rectangles
            imagePanel.repaint(oldRect); // repaint only the area that has changed
        }

        public Shape getShapeToDraw() {
            if (shapeString == "rectangle") {
                return currRectangle;
            } else if (shapeString == "ellipses") {
                return new Ellipse2D.Double(currRectangle.getX(), currRectangle.getY(), currRectangle.getWidth(),
                        currRectangle.getHeight());
            } else {
                Point p = getCords();
                return new Line2D.Double(xOrigin, yOrigin, p.x, p.y);
            }
        }

    }

}
