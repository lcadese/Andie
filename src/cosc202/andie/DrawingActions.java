package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class DrawingActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    protected ResourceBundle bundle;
    protected static Color currentColor = Color.red; // Default color

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public DrawingActions() {
        actions = new ArrayList<Action>();

        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions.add(new DrawRectangleAction(bundle.getString("rectangle"), null, bundle.getString("rectangledesc"), Integer.valueOf(KeyEvent.VK_8), Integer.valueOf(Control.COMMAND)));
        actions.add(new DrawEllipsesAction(bundle.getString("ellipses"), null, bundle.getString("ellipsesdesc"), Integer.valueOf(KeyEvent.VK_9), Integer.valueOf(Control.COMMAND)));
        actions.add(new DrawLineAction(bundle.getString("line"), null, bundle.getString("linedesc"), Integer.valueOf(KeyEvent.VK_0), Integer.valueOf(Control.COMMAND)));
        actions.add(new ColorAction(bundle.getString("color"), null, null, Integer.valueOf(KeyEvent.VK_C), Integer.valueOf(Control.COMMAND+KeyEvent.SHIFT_DOWN_MASK)));
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("drawing"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Callback for when the DrawEllipses action is triggered.
     * </p>
     * 
     * @see DrawShape
     */
    public class DrawRectangleAction extends ImageAction {

        /**
         * <p>
         * Creates a new DrawRectangleAction.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DrawRectangleAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the DrawRectangleAction is triggerd.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawRectangleAction is triggered.
         * It draws an image on the image pannel when mouse is pressed.
         * when released it draws onto the buffered image and updates.
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
                DrawSelection drawSelection = new DrawSelection(target, "rectangle", currentColor);
                target.addMouseListener(drawSelection);
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see DrawShape
     * @see DrawSelection
     */
    public class DrawEllipsesAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DrawEllipsesAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the DrawEllipses action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
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
                DrawSelection drawSeclection = new DrawSelection(target, "ellipses", currentColor);
                target.addMouseListener(drawSeclection);
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see DrawShape
     * @see DrawSelection
     */
    public class DrawLineAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DrawLineAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback for when the DrawEllipses action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
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
                DrawSelection drawSelection = new DrawSelection(target, "line", currentColor);
                target.addMouseListener(drawSelection);
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("noImage"), bundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public class ColorAction extends ImageAction {

        ColorAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        public void actionPerformed(ActionEvent e) {
            Color initialcolor = currentColor;
            Color color = JColorChooser.showDialog(null, "Select a Color", initialcolor);
            if (color != null) {
                currentColor = color;
            }
        }
    }
}
