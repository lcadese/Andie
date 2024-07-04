package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * <p>
 * Actions provided by the Appearance menu.
 * </p>
 * 
 * <p>
 * The Appearance menu allows the user to select a colour theme for ANDIE.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Veronika Luthar, adapted from code by Steven Mills and Ray Meech.
 * @version 1.0
 */
public class AppearanceActions {
    
    protected ResourceBundle bundle;

    /** A list of actions for the Language menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Appearance menu actions.
     * </p>
     */
    public AppearanceActions() {
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions = new ArrayList<Action>();
        actions.add(new DarkModeAction(bundle.getString("darkMode"), bundle.getString("darkModeDesc")));
        actions.add(new LightModeAction(bundle.getString("lightMode"), bundle.getString("lightModeDesc")));
        actions.add(new PastelPinkModeAction(bundle.getString("pastelPinkMode"), bundle.getString("pastelPinkModeDesc")));
    }

    /**
     * <p>
     * Create a menu containing the list of Appearance actions.
     * </p>
     * 
     * @return The appearance menu UI element.
     */
    public JMenu createMenu() {
        JMenu appearanceMenu = new JMenu(bundle.getString("appearance"));
        for (Action action: actions) {
            appearanceMenu.add(new JMenuItem(action));
        }
        return appearanceMenu;
    }

    /**
     * <p>
     * Action to change the appearance to Dark Mode.
     * </p>
     */
    public class DarkModeAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI to Dark Mode.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public DarkModeAction(String name, String desc) {
            super(name, null);

            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
            }
        }

        /**
         * <p>
         * Callback for when the dark mode action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LightModeAction is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("appearance", "appearance_files" + File.separator + "dark.xml");
            JOptionPane.showMessageDialog(null, bundle.getString("appearanceRestart"), bundle.getString("appearance"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to change the appearance to Light mode.
     * </p>
     */
    public class LightModeAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI to the Light Mode.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         *
         */
        public LightModeAction(String name, String desc) {
            super(name, null);

            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
            }
        }

        /**
         * <p>
         * Callback for when the light mode action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LightModeAction is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("appearance", "appearance_files" + File.separator + "light.xml");
            JOptionPane.showMessageDialog(null, bundle.getString("appearanceRestart"), bundle.getString("appearance"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to change the appearance to Light mode.
     * </p>
     */
    public class PastelPinkModeAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI to the Light Mode.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         *
         */
        public PastelPinkModeAction(String name, String desc) {
            super(name, null);

            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
            }
        }

        /**
         * <p>
         * Callback for when the light mode action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LightModeAction is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("appearance", "appearance_files" + File.separator + "pastel-pink.xml");
            JOptionPane.showMessageDialog(null, bundle.getString("appearanceRestart"), bundle.getString("appearance"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
