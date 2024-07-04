package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * This is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
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
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /**
     * The current image, the result of applying {@link ops} to {@link original}.
     */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** Tracking for when the Macros are recording */
    private static boolean isRecording;
    /** A List for the recorded opeartions */
    static List<ImageOperation> recordedOps;
    /** Tracking for if isRunningRecordedOps */
    static boolean isRunningRecordedOps = false;
    /** Resource bundle */
    protected ResourceBundle bundle;

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack
     * of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Start recording image operations.
     * </p>
     *
     * <p>
     * This method initializes the list of recorded operations and sets isRecording
     * to true.
     * </p>
     */
    public void startRecording() {
        isRecording = true;
        recordedOps = new ArrayList<>();
    }

    /**
     * <p>
     * Stop recording image operations.
     * </p>
     *
     * <p>
     * This method sets isRecording to false and clears the list of recorded
     * operations.
     * </p>
     */
    public void stopRecording() {
        isRecording = false;
        if (recordedOps != null) {
            recordedOps.clear();
        }
    }

    /**
     * <p>
     * Saves the recorded operations to a file.
     * Stops the recording and clears the recorded operations after saving.
     * 
     * @param fileName The file name to save the recorded operations to.
     * @throws IOException If an error occurs while saving.
     *                     <p>
     */
    public void saveRecordedOps(String fileName) throws IOException {
        if (recordedOps != null && !recordedOps.isEmpty()) {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(recordedOps);
            objOut.close();
            fileOut.close();
            JOptionPane.showMessageDialog(null,
                    bundle.getString("recOpratsave"), fileName, JOptionPane.INFORMATION_MESSAGE);

            // Stop recording and clear recorded operations
            stopRecording();
            recordedOps.clear();
        } else {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("recOpratnosave"), fileName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * <p>
     * Load recorded operations from a file.
     * </p>
     * 
     * <p>
     * This method deserializes a list of recorded operations from a file.
     * </p>
     * 
     * @param fileName The file to load the operations from.
     * @throws IOException            If something goes wrong during file reading.
     * @throws ClassNotFoundException If the file does not contain a correctly
     *                                serialized list of operations.
     */
    public void loadRecordedOps(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        recordedOps = (List<ImageOperation>) objIn.readObject();
        objIn.close();
        fileIn.close();
    }

    /**
     * <p>
     * Apply the recorded operations to the image.
     * </p>
     * 
     * <p>
     * This method iterates through the list of recorded operations and applies each
     * one to the current image.
     * It also creates a copy of the list to avoid any concurrent modification
     * issues.
     * </p>
     */
    public void runRecordedOps() {
        if (recordedOps != null) {
            isRunningRecordedOps = true;
            List<ImageOperation> copiedList = new ArrayList<>(recordedOps);
            for (ImageOperation op : copiedList) {
                this.apply(op);

            }
            isRunningRecordedOps = false;
        }
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knoweldge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code>
     * added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws Exception If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        ops.clear();
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);

        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved
     * as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Save an image to a speficied file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * Write the image to a new file of name 'imageFileName'
     * 
     * @param op
     */
    public void export(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;

        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(getCurrentImage(), extension, new File(imageFilename));
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        current = op.apply(current);
        redoOps.clear();
        ops.add(op);
        if (isRecording == true) {
            recordedOps.add(op);
        }
    }

    public void redoApply(ImageOperation op) {
        current = op.apply(current);
        ops.add(op);
        if (isRecording == true) {
            recordedOps.add(op);
        }
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
        ImageOperation undidRecord = ops.pop();
        redoOps.push(undidRecord);
        if (isRecording) {
            recordedOps.remove(undidRecord);
        }
        refresh();
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo() {
        redoApply(redoOps.pop());
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the
     *         {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }

}
