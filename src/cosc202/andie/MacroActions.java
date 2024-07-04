package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * <p>
 * This class provides MacroActions which are used for recording,
 * stopping recording, saving and loading macros on the given EditableImage.
 * </p>
 * 
 * @author Liam Cade
 */

public class MacroActions {
    protected ResourceBundle bundle;
    protected ArrayList<Action> actions;
    private EditableImage editableImage;

    /**
     * <p>
     * The constructor for the MacroActions class.
     * </p>
     *
     */
    public MacroActions(EditableImage editableImage) {
        this.editableImage = editableImage;
        actions = new ArrayList<>();

        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        actions.add(new StartMacroRecordingAction(bundle.getString("startRec"), null, bundle.getString("startRecdesc"),
                Integer.valueOf(KeyEvent.VK_4), Integer.valueOf(Control.COMMAND)));
        actions.add(new StopMacroRecordingAction(bundle.getString("stopRec"), null, bundle.getString("stopRecdesc"),
                Integer.valueOf(KeyEvent.VK_5), Integer.valueOf(Control.COMMAND)));
        actions.add(new SaveMacroAction(bundle.getString("saveRec"), null, bundle.getString("saveRecdesc"),
                Integer.valueOf(KeyEvent.VK_6), Integer.valueOf(Control.COMMAND)));
        actions.add(new LoadMacroAction(bundle.getString("loadRec"), null, bundle.getString("loadRecdesc"),
                Integer.valueOf(KeyEvent.VK_7), Integer.valueOf(Control.COMMAND)));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The Image menu UI element.
     */
    public JMenu createMenu() {
        JMenu macroMenu = new JMenu(bundle.getString("macro"));

        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

    /**
     * <p>
     * Action to start recording a macro.
     * </p>
     *
     * @see StartMacroRecordingAction
     */
    private class StartMacroRecordingAction extends ImageAction {

        /**
         * <p>
         * Create a new StartMacroRecording action.
         * </p>
         *
         * @param name     The name of the action.
         * @param icon     An icon to represent the action.(if needed)
         * @param desc     A brief description of the action.
         * @param key      A key to use as a keyboard shortcut for the action.
         * @param modifier A modifier key to use along with the shortcut.
         */
        public StartMacroRecordingAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback
         * </p>
         *
         * <p>
         * This method is called whenever the StartMacroRecordingAction is triggered.
         * It starts the recording of operations on the EditableImage.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, bundle.getString("startRecchanges"),
                    bundle.getString("macro"), JOptionPane.INFORMATION_MESSAGE);
            editableImage.startRecording();
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to stop recording a macro.
     * </p>
     *
     * @see StopMacroRecordingAction
     */
    private class StopMacroRecordingAction extends ImageAction {
        /**
         * <p>
         * Create a new StopMacroRecording action.
         * </p>
         *
         * @param name     The name of the action.
         * @param icon     An icon to represent the action.(if needed)
         * @param desc     A brief description of the action.
         * @param key      A key to use as a keyboard shortcut for the action.
         * @param modifier A modifier key to use along with the shortcut.
         */
        public StopMacroRecordingAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback
         * </p>
         *
         * <p>
         * This method is called whenever the StopMacroRecordingAction is triggered.
         * It stops the recording of operations on the EditableImage.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("stopRecchanges"),
                    bundle.getString("macro"), JOptionPane.INFORMATION_MESSAGE);
            editableImage.stopRecording();
            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to save a macro.
     * </p>
     *
     * @see SaveMacroAction
     */
    private class SaveMacroAction extends ImageAction {
        /**
         * <p>
         * Create a new SaveMacro action.
         * </p>
         *
         * @param name     The name of the action.
         * @param icon     An icon to represent the action.(if needed)
         * @param desc     A brief description of the action.
         * @param key      A key to use as a keyboard shortcut for the action.
         * @param modifier A modifier key to use along with the shortcut.
         */
        public SaveMacroAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback
         * </p>
         *
         * <p>
         * This method is called whenever the SaveMacroAction is triggered.
         * It saves the recorded operations to a file.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, bundle.getString("typeMacroName"), bundle.getString("macro"), JOptionPane.INFORMATION_MESSAGE);
            JFileChooser fileSelect = new JFileChooser();
            int returnValue = fileSelect.showSaveDialog(target);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    String filepath = fileSelect.getSelectedFile().getCanonicalPath();
                    if (!filepath.endsWith(".ops")) {
                        filepath += ".ops";
                    }
                    editableImage.saveRecordedOps(filepath);
                } catch (IOException except) {
                    System.err.println(bundle.getString("saveRecerror") + except.getMessage());
                    except.printStackTrace();
                }

            }
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to load a macro.
     * </p>
     *
     * @see LoadMacroAction
     */
    private class LoadMacroAction extends ImageAction {
        /**
         * <p>
         * Create a new LoadMacro action.
         * </p>
         *
         * @param name     The name of the action.
         * @param icon     An icon to represent the action.(if needed)
         * @param desc     A brief description of the action.
         * @param key      A key to use as a keyboard shortcut for the action.
         * @param modifier A modifier key to use along with the shortcut.
         */
        public LoadMacroAction(String name, ImageIcon icon, String desc, Integer key, Integer modifier) {
            super(name, icon, desc, key, modifier);
        }

        /**
         * <p>
         * Callback
         * </p>
         *
         * <p>
         * This method is called whenever the LoadMacroAction is triggered.
         * It loads the recorded operations from a file and applies them to the
         * EditableImage.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String opsFilePath = fileChooser.getSelectedFile().getCanonicalPath();

                    if (!opsFilePath.endsWith(".ops")) {
                        JOptionPane.showMessageDialog(null,
                                bundle.getString("loadRecerrorload"),
                                bundle.getString("invalidFile"), JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    target.getImage().loadRecordedOps(opsFilePath);
                    target.getImage().runRecordedOps();
                } catch (IOException except) {
                    System.err.println(bundle.getString("loadRecerror"));
                    except.printStackTrace();

                } catch (ClassNotFoundException e1) {

                    e1.printStackTrace();
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }
    }
}
