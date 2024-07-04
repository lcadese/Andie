package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.awt.Graphics;


/**
 * <p>
 * Actions provided by the Image menu.
 * </p>
 * 
 * 
 * @author Ray Meech
 * @version 1.0
 */
public class CropSelection implements MouseListener {

    Timer timer = new Timer();
    TimerTask updateTask;
    int xOrigin;
    int yOrigin;
    ImagePanel imagePanel;
    SelectionRect selectionRect;
    Rectangle selectionArea;
    protected ResourceBundle bundle;

    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public CropSelection(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

    }

    @Override
    public void mouseClicked(MouseEvent e){
    }

        /**
         * <p>
         * Actions for when mouse is pressed
         * </p>
         * 
         * <p>
         * This method selects a rectangle starting from where the left mouse button was first pressed to the current 
         * mouse position.
         * Seclection is static after mouse released and can be deselected using a left click
         * </p>
         * 
         * @param e The event triggering this callback.
         */
    @Override
    public void mousePressed(MouseEvent e){
        Timer timer = new Timer();

        xOrigin = e.getX();
        yOrigin = e.getY();
        long delay = 16;
        selectionRect = new SelectionRect(xOrigin, yOrigin);
        updateTask = new UpdateTask();
        timer.scheduleAtFixedRate(updateTask, new Date(), delay);
        // selectionRect.updateRectangle();
    }

    @Override
        /**
         * <p>
         * Actions for when mouse is released.
         * </p>
         * 
         * <p>
         * Creates a static selection area from where the mouse press was released unless
         * selection rect has a height or width of less than 10
         * 
         * @param e The event triggering this callback.
         */
    public void mouseReleased(MouseEvent e){
        // Stops painting selection rectangles where the cursor is
        updateTask.cancel();
        if(selectionRect.currRectangle.getWidth() >10 && selectionRect.currRectangle.getHeight() > 10){
            selectionRect.selectArea();
            String[] options = {bundle.getString("apply"), bundle.getString("retry"), bundle.getString("cancel")};
            int option = JOptionPane.showOptionDialog(null, null, "Do you want to crop?",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                imagePanel.repaint();
                imagePanel.getParent().revalidate(); 
                imagePanel.removeMouseListener(this);
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                try{
                imagePanel.getImage().apply(new ImageCrop(selectionRect.currRectangle));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();  
                imagePanel.removeMouseListener(this);
            } catch (RasterFormatException exception){
                imagePanel.repaint();
                imagePanel.getParent().revalidate();  
                JOptionPane.showMessageDialog(null, bundle.getString("invalidCrop"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
            }
            else{
                imagePanel.repaint();
                imagePanel.getParent().revalidate();
            }
        }
        }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

        /**
         * <p>
         * Method to get coordinates of mouse position relative to the image panel.
         * </p>
         * 
         * @return Point localPosition
         */
    public Point getCords(){
        Point localPosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(localPosition, imagePanel);
        return localPosition;        
    }


    class UpdateTask extends TimerTask{
        public void run(){
                selectionRect.updateRectangle();

        }
    }



    private class SelectionRect extends JPanel{
        protected Rectangle currRectangle;
        int yOrigin;
        int xOrigin;
        
        public SelectionRect(int xOrigin, int yOrigin){
            currRectangle = new Rectangle(xOrigin, yOrigin, 9, 9);
            this.yOrigin = yOrigin;
            this.xOrigin = xOrigin;
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Point p = getCords();
            
           if(p.x - xOrigin>= 0 && p.y - yOrigin >= 0){
            g.drawRect(xOrigin, yOrigin, p.x - xOrigin, p.y - yOrigin);
            g.setColor(Color.white);
            g.drawRect(xOrigin-1, yOrigin-1, p.x - xOrigin + 2, p.y - yOrigin + 2);
            g.drawRect(xOrigin+1, yOrigin+1, p.x - xOrigin -2, p.y - yOrigin -2);
            currRectangle = new Rectangle(xOrigin, yOrigin, p.x - xOrigin, p.y - yOrigin);
            imagePanel.repaint();
            imagePanel.revalidate();
          }  
          else if(p.x - xOrigin < 0 && p.y - yOrigin < 0){
            g.drawRect(p.x, p.y, xOrigin - p.x, yOrigin - p.y);
            g.setColor(Color.white);
            g.drawRect(p.x-1, p.y-1, xOrigin - p.x + 2, yOrigin - p.y + 2);
            g.drawRect(p.x+1, p.y+1, xOrigin - p.x - 2, yOrigin - p.y - 2);
            currRectangle = new Rectangle(p.x, p.y, xOrigin - p.x, yOrigin - p.y);
            imagePanel.repaint();
            imagePanel.revalidate();
          }
          else if(p.x - xOrigin > 0 && p.y - yOrigin < 0){
            g.drawRect(xOrigin, p.y, p.x - xOrigin, yOrigin - p.y);
            g.setColor(Color.white);
            g.drawRect(xOrigin-1, p.y-1, p.x - xOrigin + 2, yOrigin - p.y + 2);
            g.drawRect(xOrigin + 1, p.y + 1, p.x - xOrigin - 2, yOrigin - p.y- 2);
            currRectangle = new Rectangle(xOrigin, p.y, p.x - xOrigin, yOrigin - p.y);
            imagePanel.repaint();
            imagePanel.revalidate();
          }
          else{
            g.drawRect(p.x, yOrigin, xOrigin - p.x, p.y - yOrigin);
            g.setColor(Color.white);
            g.drawRect(p.x + 1, yOrigin + 1, xOrigin - p.x - 2, p.y - yOrigin - 2);
            g.drawRect(p.x - 1, yOrigin - 1, xOrigin - p.x + 2, p.y - yOrigin + 2);
            currRectangle = new Rectangle(p.x, yOrigin, xOrigin - p.x, p.y - yOrigin);
            imagePanel.repaint();
            imagePanel.revalidate();
          }
        }

        public void updateRectangle(){
            paintComponent(imagePanel.getGraphics());

        }

        public  void selectArea(){
            Graphics g = imagePanel.getGraphics();
            super.paintComponent(g);
            g.drawRect(currRectangle.getLocation().x, currRectangle.getLocation().y, (int) currRectangle.getWidth(), (int) currRectangle.getHeight());
            g.setColor(Color.white);
            g.drawRect(currRectangle.getLocation().x - 1, currRectangle.getLocation().y - 1, (int) currRectangle.getWidth() + 2, (int) currRectangle.getHeight() + 2);
            g.drawRect(currRectangle.getLocation().x + 1, currRectangle.getLocation().y+ 1, (int) currRectangle.getWidth() - 2, (int) currRectangle.getHeight() - 2);
        }
}
    
}
