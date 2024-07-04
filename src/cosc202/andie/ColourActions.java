package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    protected static ImagePanel target;
    protected ResourceBundle bundle;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();

        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions.add(new ConvertToGreyAction(bundle.getString("greyscale"), null, bundle.getString("greyscaledesc"),
                Integer.valueOf(KeyEvent.VK_G), Integer.valueOf(Control.COMMAND)));
        actions.add(new ContrastBrightnessAction(bundle.getString("contbright"), null,
                bundle.getString("contbrightdesc"), Integer.valueOf(KeyEvent.VK_B), Integer.valueOf(Control.COMMAND)));
        actions.add(new ConvertToAnaglyphAction(bundle.getString("anaglyph"), null, bundle.getString("anaglyph"),
                Integer.valueOf(KeyEvent.VK_3), Integer.valueOf(Control.COMMAND)));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu colourMenu = new JMenu(bundle.getString("colour"));

        for (Action action : actions) {
            colourMenu.add(new JMenuItem(action));
        }

        return colourMenu;
    }

    public static void setTarget(ImagePanel newTarget) {
        target = newTarget;
    }

    public static ImagePanel getTarget() {
        return target;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            } catch (NullPointerException NPex) {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * This class represents an action to adjust the contrast and brightness of an
     * image.
     * The action displays sliders for the user to set the desired contrast and
     * brightness
     * levels it then applies the changes to the image.
     */
    public class ContrastBrightnessAction extends ImageAction {

        /**
         * <p>
         * Constructs a new ContrastBrightnessAction with the specified name, icon,
         * description,
         * and mnemonic.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ContrastBrightnessAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);

        }

        /**
         * This method is called when the ContrastBrightnessAction is triggered.
         * It displays a dialog with sliders for adjusting contrast and brightness,
         * and then applies the changes to the target image when the user confirms.
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Creates sliders
            JSlider contrastSlider = new JSlider(-100, 100);
            JSlider brightnessSlider = new JSlider(-100, 100);

            // Creates labels for sliders
            JLabel contrastLabel = new JLabel(bundle.getString("contrast") + ": 0%");
            JLabel brightnessLabel = new JLabel(bundle.getString("brightness") + ": 0%");

            // Updates labels when contrast slider is changed
            contrastSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    contrastLabel.setText(bundle.getString("contrast") + ":" + contrastSlider.getValue() + "%");
                }
            });
            // Updates labels when brightness slider is changed
            brightnessSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    brightnessLabel.setText(bundle.getString("brightness") + ":" + brightnessSlider.getValue() + "%");
                }
            });

            // If need to make changes to JOptionPane edit this
            int result = JOptionPane.showOptionDialog(null,
                    new Object[] { contrastLabel, contrastSlider, brightnessLabel, brightnessSlider },
                    bundle.getString("adj"), JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            // If user presses OK then apply the values to the Brightness/Contrast
            // adjustement
            if (result == JOptionPane.OK_OPTION) {
                double contrast = (double) contrastSlider.getValue();
                double brightness = (double) brightnessSlider.getValue();
                // Error catch
                try {
                    target.getImage().apply(new BrightnessContrastAdjustment(contrast, brightness));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (NullPointerException NPex) {
                    JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    /**
     * <p>
     * Action to convert an image to anaglyph.
     * </p>
     * 
     * @see ConvertToAnaglyph
     */
    public class ConvertToAnaglyphAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-anaglyph action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToAnaglyphAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the convert-to-anaglyph action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToAnaglyph is triggered.
         * It displays a dialog with sliders for adjusting offset and threshold
         * These variables are then used to convert the image to anaglyph when user confirms.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Creates sliders
            JSlider offsetSlider = new JSlider(10, 20, 12);
            JSlider thresholdSlider = new JSlider(60, 256, 180);

            // Creates labels for sliders
            JLabel offsetLabel = new JLabel(bundle.getString("offset") + ": 12");
            JLabel thresholdLabel = new JLabel(bundle.getString("threshold") + ": 180");

            // Updates labels when offset slider is changed
            offsetSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    offsetLabel.setText(bundle.getString("offset") + ": " + offsetSlider.getValue());
                }
            });
            // Updates labels when threshold slider is changed
            thresholdSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    thresholdLabel.setText(bundle.getString("threshold") + ": " + thresholdSlider.getValue());
                }
            });

            // If need to make changes to JOptionPane edit this
            int result = JOptionPane.showOptionDialog(null,
                    new Object[] { offsetLabel, offsetSlider, thresholdLabel, thresholdSlider },
                    bundle.getString("adj"), JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            // If user presses OK then apply the values
            // adjustement
            if (result == JOptionPane.OK_OPTION) {
                int offset = offsetSlider.getValue();
                int threshold = thresholdSlider.getValue();
                // Error catch
                try {
                    target.getImage().apply(new ConvertToAnaglyph(offset, threshold));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (NullPointerException NPex) {
                    JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}
