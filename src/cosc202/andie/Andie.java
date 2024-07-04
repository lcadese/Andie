package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggerd via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     *      // LanguageActions This is yet to be finished
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {

        // Attempts to set the look and feel; in case of exception, adds the dark mode
        // into preferences and loads that.
        Preferences appearancePrefs = Preferences.userNodeForPackage(Andie.class);
        SynthLookAndFeel laf = new SynthLookAndFeel();
        try {
            laf.load(Andie.class.getResourceAsStream(appearancePrefs.get("appearance", "appearance_files" + File.separator + "dark.xml")),Andie.class);
            UIManager.setLookAndFeel(laf);
        } catch (IllegalArgumentException IAex) {
            appearancePrefs.put("appearance", "appearance_files/dark.xml");
        }

        // Loads the look and feel from file
        laf.load(Andie.class.getResourceAsStream(appearancePrefs.get("appearance", "appearance_files" + File.separator + "dark.xml")),Andie.class);
        UIManager.setLookAndFeel(laf);

        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // Sets Language Preference to English
        Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        Locale.setDefault(new Locale(prefs.get("language", "en")));

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Filters that affect the Image size and orientation
        ImageActions imageActions = new ImageActions();
        menuBar.add(imageActions.createMenu());

        DrawingActions drawingActions = new DrawingActions();
        menuBar.add(drawingActions.createMenu());
        EditableImage editableImage = new EditableImage();

        // Call ToolbarActions class to make tool bar
        ToolbarActions toolbarActions = new ToolbarActions(drawingActions);
        JPanel toolbar = toolbarActions.createToolbar();
        frame.add(toolbar, BorderLayout.LINE_START);

        // Macros to repeat filter combinations
        MacroActions macroActions = new MacroActions(editableImage);
        menuBar.add(macroActions.createMenu());

        // Actions that affect the theme/appearance of ANDIE
        AppearanceActions appearanceActions = new AppearanceActions();
        menuBar.add(appearanceActions.createMenu());

        // Language menu, selecting a new language will close the app when reloaded
        // the new language will be displayed
        LanguageActions languageActions = new LanguageActions();
        menuBar.add(languageActions.createMenu());

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
