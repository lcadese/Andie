package cosc202.andie;

import java.util.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

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
 * @author Elizabeth Stewart, Veronika Luthar, Ray Meech
 * @version 1.0
 */

public class ImageActions {

    /** A list of actions for the Image menu. */
    protected ArrayList<Action> actions;
    protected ResourceBundle bundle;

    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public ImageActions() {
        actions = new ArrayList<Action>();

        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions.add(new ResizeFilterAction(bundle.getString("resize"), null, bundle.getString("resizedesc"),
                Integer.valueOf(KeyEvent.VK_EQUALS), Integer.valueOf(Control.COMMAND + KeyEvent.ALT_DOWN_MASK)));
        actions.add(new RotateFilterAction(bundle.getString("rotate"), null, bundle.getString("rotatedesc"),
                Integer.valueOf(KeyEvent.VK_RIGHT), Integer.valueOf(Control.COMMAND + KeyEvent.ALT_DOWN_MASK)));
        actions.add(
                new ImageFlipHorizontalAction(bundle.getString("flipH"), null, bundle.getString("flipHdesc"),
                        Integer.valueOf(KeyEvent.VK_RIGHT), Integer.valueOf(Control.COMMAND)));
        actions.add(new ImageFlipVerticalAction(bundle.getString("flipV"), null, bundle.getString("flipVdesc"),
                Integer.valueOf(KeyEvent.VK_UP), Integer.valueOf(Control.COMMAND)));
        actions.add(new ImageCropAction(bundle.getString("crop"), null, bundle.getString("cropdesc"), Integer.valueOf(KeyEvent.VK_S), Integer.valueOf(Control.COMMAND + KeyEvent.ALT_DOWN_MASK)));

    }

    /**
     * <p>
     * Create a menu containing the list of Image actions.
     * </p>
     * 
     * @return The Image menu UI element.
     */
    public JMenu createMenu() {
        JMenu imageMenu = new JMenu(bundle.getString("image"));

        for (Action action : actions) {
            imageMenu.add(new JMenuItem(action));
        }

        return imageMenu;
    }

    /**
     * <p>
     * Action to Resize Image with a Resize filter.
     * </p>
     * 
     * @see ResizeFilter
     */
    public class ResizeFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer mnemonic) {
            super(name, icon, desc, key, mnemonic);
        }

        /**
         * <p>
         * Callback
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeFilterAction is triggered.
         * It prompts the user, then applys an appropriately sized {@link ResizeFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the scale - ask the user.
            int scale = 100;
            int scaleMax = 200;
            int scaleMin = 25;

            // Pop-up dialog box to ask for the scale value.
            JSlider scaleSlider = new JSlider(scaleMin, scaleMax, scale);
            scaleSlider.setMajorTickSpacing(25);
            scaleSlider.setPaintTicks(true);
            scaleSlider.setPaintLabels(true);

            scaleSlider.setFont(new Font("Arial", Font.PLAIN, 10)); 

            int option = JOptionPane.showOptionDialog(null, scaleSlider, bundle.getString("persize"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                scale = scaleSlider.getValue();
            }

            // check if there is an image opened, if no image is opened catch, if image is
            // opened use ResizeFilter
            try {
                // Apply filter with given scale
                target.getImage().apply(new ResizeFilter(scale));
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
    }

    /**
     * <p>
     * Action to Rotate Image with a Rotate filter.
     * </p>
     * 
     * @see RotateFilter
     */
    public class RotateFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer mnemonic) {
            super(name, icon, desc, key, mnemonic);
        }

        /**
         * <p>
         * Callback
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateFilterAction is triggered.
         * It prompts the user, then applys an appropriately sized {@link RotateFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Determine the degrees - ask the user.
            int degrees = 0;
            int degreesMax = 270;
            int degreesMin = -270;

            // Pop-up dialog box to ask for the scale value.
            JSlider degreesSlider = new JSlider(degreesMin, degreesMax, degrees);
            degreesSlider.setMajorTickSpacing(90);
            degreesSlider.setSnapToTicks(true);
            degreesSlider.setPaintTicks(true);
            degreesSlider.setPaintLabels(true);

            degreesSlider.setFont(new Font("Arial", Font.PLAIN, 10)); 

            int option = JOptionPane.showOptionDialog(null, degreesSlider, bundle.getString("degrot"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                degrees = degreesSlider.getValue();
            }

            // check if there is an image opened, if no image is opened catch, if image is
            // opened use RotateFilter
            try {
                // Apply filter with given degrees
                target.getImage().apply(new RotateFilter((int) degrees));
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    /**
     * <p>
     * Action to flip an image horizontally.
     * </p>
     * 
     * @see ImageFlipHorizontal
     */
    public class ImageFlipHorizontalAction extends ImageAction {

        /**
         * <p>
         * Create a new horizontal image-flip action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageFlipHorizontalAction(String name, ImageIcon icon, String desc, Integer key, Integer mnemonic) {
            super(name, icon, desc, key, mnemonic);
        }

        /**
         * <p>
         * Callback for when the horizontal image-flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageFlipHorizontalAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Apply filter with given radius
                target.getImage().apply(new ImageFlipHorizontal());
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to flip an image vertically.
     * </p>
     * 
     * @see ImageFlipVertical
     */
    public class ImageFlipVerticalAction extends ImageAction {

        /**
         * <p>
         * Create a new vertical image-flip action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageFlipVerticalAction(String name, ImageIcon icon, String desc, Integer key, Integer mnemonic) {
            super(name, icon, desc, key, mnemonic);
        }

        /**
         * <p>
         * Callback for when the vertical image-flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageFlipVerticalAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new ImageFlipVertical());
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to Crop Image with ImageCrop.
     * </p>
     * 
     * @see ImageCrop
     */
    public class ImageCropAction extends ImageAction {

        static MouseListener cropSelection;

        /**
         * <p>
         * Create a new Image Crop action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageCropAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback
         * </p>
         * 
         * <p>
         * This method is called whenever the ImageCrop action is triggered.
         * It allows the user to draw a cropable rectangle on the screen and then crop
         * the image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (target.getImage().hasImage()) {
                MouseListener[] listenerList = target.getMouseListeners();
                if (listenerList.length != 0) {
                    target.removeMouseListener(listenerList[0]);
                }
                CropSelection cropSelection = new CropSelection(target);
                target.addMouseListener(cropSelection);
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
