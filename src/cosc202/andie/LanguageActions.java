package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

 /**
 * <p>
 * Actions provided by the Language menu.
 * </p>
 * 
 * <p>
 * The language menu provides support for 5 languages English, Maori, Mayan, Swahili, Danish
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Ray Meech
 * @version 1.0
 */
public class LanguageActions {
    
    protected ResourceBundle bundle;

    /** A list of actions for the Language menu. */
    protected ArrayList<Action> actions;
 
    // Preferences prefs = Preferences.userNodeForPackage(EditActions.class);
    // prefs.put("language", "oxr");

    /**
     * <p>
     * Create a set of Language menu actions.
     * </p>
     */
    
    public LanguageActions() {
        actions = new ArrayList<Action>();
        actions.add(new EnglishAction("English", "Changes language to English"));
        actions.add(new SwahiliAction("Swahili", "Badilisha Lugha kuwa Kiswahili"));
        actions.add(new JapaneseAction("Japanese", "日本語に言語を変更します"));
        actions.add(new MaoriAction("Māori", "Panonitia te reo ki te reo Māori"));
        actions.add(new OrcishAction("*rgbbbrdrslkkl*", "bonk"));
    }

    /**
     * <p>
     * Create a menu contianing the list of Language actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");
        
        JMenu languageMenu = new JMenu(bundle.getString("language"));

        for (Action action: actions) {
            languageMenu.add(new JMenuItem(action));
        }
        return languageMenu;
    }

    /**
     * <p>
     * Action to change Language to English.
     * </p>
     * 
     * 
     */
    public class EnglishAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI language to English.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public EnglishAction(String name, String desc) {
            super(name, null);
            // JMenuBar menuBar = this.menuBar;
            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
             }


        }

        /**
         * <p>
         * Callback for when the English action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EnlishAction is triggered.
         * It changes the all text in the app to English.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language", "en");
            JOptionPane.showMessageDialog(null, bundle.getString("restart"), bundle.getString("language"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to change Language to Swahili.
     * </p>
     * 
     * 
     */
    public class SwahiliAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI language to Swahili.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public SwahiliAction(String name, String desc) {
            super(name, null);
            // JMenuBar menuBar = this.menuBar;
            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
             }


        }

        /**
         * <p>
         * Callback for when the Swwahili action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SwahiliAction is triggered.
         * It changes the all text in the app to Swahili.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language", "swa");
            JOptionPane.showMessageDialog(null, bundle.getString("restart"), bundle.getString("language"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to change Language to Orcish.
     * </p>
     * 
     * 
     */
    public class OrcishAction extends AbstractAction {

        /**
         * <p>
         * Changes the UI language to Orcish.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public OrcishAction(String name, String desc) {
            super(name, null);
            // JMenuBar menuBar = this.menuBar;
            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
             }


        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the OrcishAction is triggered.
         * It changes all display text to Orcish.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language", "or");
            JOptionPane.showMessageDialog(null, bundle.getString("restart"), bundle.getString("language"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to change Language to English.
     * </p>
     * 
     * 
     */
    public class JapaneseAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI language to Japan.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public JapaneseAction(String name, String desc) {
            super(name, null);
            // JMenuBar menuBar = this.menuBar;
            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
                }


        }

        /**
         * <p>
         * Callback for when the Japanese action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the JapaneseAction is triggered.
         * It changes the all text in the app to Japanese.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language", "ja");
            JOptionPane.showMessageDialog(null, bundle.getString("restart"), bundle.getString("language"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
}    

    /**
     * <p>
     * Action to change Language to Maori.
     * </p>
     * 
     * 
     */
    public class MaoriAction extends AbstractAction {
        /**
         * <p>
         * Changes the UI language to Maori.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         *
         */
        public MaoriAction(String name, String desc) {
            super(name, null);
            // JMenuBar menuBar = this.menuBar;
            if (desc != null) {
                putValue(SHORT_DESCRIPTION, desc);
             }


        }

        /**
         * <p>
         * Callback for when the Maori action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MaoriAction is triggered.
         * It changes the all text in the app to Maori.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userNodeForPackage(Andie.class);
            prefs.put("language", "mi");
            JOptionPane.showMessageDialog(null, bundle.getString("restart"), bundle.getString("language"), JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
