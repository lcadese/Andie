package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    protected ResourceBundle bundle;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();

        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions.add(new MeanFilterAction(bundle.getString("meanfilter"), null, bundle.getString("meanfilterdesc"),
                Integer.valueOf(KeyEvent.VK_M), Integer.valueOf(Control.COMMAND)));
        actions.add(new SharpenFilterAction(bundle.getString("sharpen"), null, bundle.getString("sharpendesc"),
                Integer.valueOf(KeyEvent.VK_F), Integer.valueOf(Control.COMMAND)));
        actions.add(new MedianFilterAction(bundle.getString("median"), null, bundle.getString("mediandesc"),
                Integer.valueOf(KeyEvent.VK_M), Integer.valueOf(Control.COMMAND + KeyEvent.SHIFT_DOWN_MASK)));
        actions.add(new GaussianBlurAction(bundle.getString("gaussian"), null, bundle.getString("gaussiandesc"),
                Integer.valueOf(KeyEvent.VK_G), Integer.valueOf(Control.COMMAND + KeyEvent.SHIFT_DOWN_MASK)));
        actions.add(new EmbossFilterAction(bundle.getString("emboss"), null, bundle.getString("embossdesc"),
                Integer.valueOf(KeyEvent.VK_E), Integer.valueOf(Control.COMMAND)));
        actions.add(new SobelFilterAction(bundle.getString("sobel"), null, bundle.getString("sobeldesc"),
                Integer.valueOf(KeyEvent.VK_F), Integer.valueOf(Control.COMMAND + KeyEvent.SHIFT_DOWN_MASK)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("filter"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            int radiusMax = 10;
            int radiusMin = 1;

            // Pop-up dialog box to ask for the radius value.
            JSlider radiusSlider = new JSlider(radiusMin, radiusMax, radiusMin);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);

            int option = JOptionPane.showOptionDialog(null, radiusSlider, bundle.getString("filterRad"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            }

            // Create and apply the filter
            try {
                // Apply filter with given radius
                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public class GaussianBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new gaussian-blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianBlurAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * This method is called whenever the Gaussian Blur is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            int radiusMax = 10;
            int radiusMin = 1;

            // Pop up dialog box with a JSlider to ask for radius value
            JSlider radiusSlider = new JSlider(radiusMin, radiusMax, radiusMin);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);

            // Pop-up dialog box to ask for the radius value.
            int option = JOptionPane.showOptionDialog(null, radiusSlider, bundle.getString("filterRad"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            }

            // Create and apply the filter
            try {
                // Apply filter with given radius
                target.getImage().apply(new GaussianBlur(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link SharpenFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Create and apply the filter
            try {
                // Apply filter with given radius
                target.getImage().apply(new SharpenFilter());
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
     * Action to apply a median filter to an image.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius using a JSlider, then applies the
         * filter with the input radius to the image.
         * JSlider code is adapted from Liam Cade's version.
         * 
         * @see MedianFilter
         */
        public void actionPerformed(ActionEvent e) {
            // Default radius set to one to ensure code does not break
            int radius = 1, radiusMax = 5, radiusMin = 1;

            // Pop up dialog box with a JSlider to ask for radius value
            JSlider radiusSlider = new JSlider(radiusMin, radiusMax, radiusMin);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);

            // Option selected
            int option = JOptionPane.showOptionDialog(null, radiusSlider, bundle.getString("filterRad"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return from the dialog box
            if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            } else {
                return;
            }

            try {
                // Apply filter with given radius
                target.getImage().apply(new MedianFilter(radius));
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
     * Action to apply an embossing filter to an image.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new emboss filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the emboss filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * 
         * @see EmbossFilter
         */
        public void actionPerformed(ActionEvent e) {
            // Default value set to 1
            int value = 0, valueMax = 315, valueMin = 0;

            // Pop up dialog box with a JSlider to ask for radius value
            JSlider valueSlider = new JSlider(valueMin, valueMax, valueMin);
            valueSlider.setMajorTickSpacing(45);
            valueSlider.setSnapToTicks(true);
            valueSlider.setPaintTicks(true);
            valueSlider.setPaintLabels(true);

            // Option selected
            int option = JOptionPane.showOptionDialog(null, valueSlider, bundle.getString("filterAzimuth"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return from the dialog box
            if (option == JOptionPane.OK_OPTION) {
                value = valueSlider.getValue();
            } else {
                return;
            }
            try {
                target.getImage().apply(new EmbossFilter(value));
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
     * Action to apply a sobel filter to an image.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SobelFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sobel filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the sobel filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * 
         * @see SobelFilter
         */
        public void actionPerformed(ActionEvent e) {
            char direction = 'H';

            // Pop up dialog box with a JSlider to ask for orientation
            String[] options = { bundle.getString("horizontal"), bundle.getString("vertical") };
            JComboBox<String> cmb = new JComboBox<String>(options);

            // Option selected
            int option = JOptionPane.showOptionDialog(null, cmb, bundle.getString("filterDirection"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return from the dialog box
            if (option == JOptionPane.OK_OPTION) {
                if (cmb.getSelectedItem() == bundle.getString("vertical")) {
                    direction = 'V';
                }
                // value = valueSlider.getValue();
            } else {
                return;
            }
            try {
                target.getImage().apply(new SobelFilter(direction));
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
