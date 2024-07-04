package cosc202.andie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.util.*;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

 /**
 * <p>
 * Actions provided by the toolbar menu.
 * </p>
 * 
 * <p>
 * The Toolbar menu is very common across a wide range of applications.
 * It shows icons which acts as a short cut option to image opperations.
 * </p>
 * 
 * @author Elizabeth Stewart
 * @version 1.0
 */
public class ToolbarActions {
    
    protected ResourceBundle bundle;
    protected DrawingActions drawingActions;

    public ToolbarActions(DrawingActions drawingActions){
        this.drawingActions = drawingActions;
    }
    public JPanel createToolbar() {

        // Get the resource bundle for localized strings
        String filePath = "cosc202" + File.separator + "andie" + File.separator + "language_files" + File.separator;
        bundle = ResourceBundle.getBundle(filePath + "MessageBundle");

        // Create toolbar panel
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolbar.setPreferredSize(new Dimension(60, toolbar.getHeight()));

        // Create button panel with vertical BoxLayout and border
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(40, 425));
        buttonPanel.setBorder(new LineBorder(Color.BLACK));
        toolbar.add(buttonPanel);

        // Add button to show/hide toolbar
        JToggleButton toggleButton = new JToggleButton(bundle.getString("show"));
        toggleButton.setPreferredSize(new Dimension(50, 20));
        toggleButton.setFont(new Font("Arial", Font.PLAIN, 10));
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean visible = !buttonPanel.isVisible();
                buttonPanel.setVisible(visible);
                if (visible) {
                    toggleButton.setText(bundle.getString("hide"));
                } else {
                    toggleButton.setText(bundle.getString("show"));
                }
            }
        });
        toolbar.add(toggleButton);

        // Hide the toolbar by default
        buttonPanel.setVisible(false);

        // Add buttons with icons to the toolbar
        // Button 1 - Save
        ImageIcon saveIcon = new ImageIcon(Andie.class.getClassLoader().getResource("save.png")); // load the image to a imageIcon
        Image saveImage = saveIcon.getImage(); // transform it 
        Image newimgSave = saveImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        saveIcon = new ImageIcon(newimgSave);  // transform it back
        // create new fileactions instance to be able to create a new FileSaveAction instance
        FileActions saveTest = new FileActions();
        // create a new jbutton with the FileSaveAction, note you need to put the icon here
        JButton saveButton = new JButton(saveTest.new FileSaveAction(null, saveIcon, filePath, null, null));
        // set hover text to button
        saveButton.setToolTipText(bundle.getString("save"));
        // add button to buttonPanel
        buttonPanel.add(saveButton); 

        // Button 2 - undo
        ImageIcon undoIcon = new ImageIcon(Andie.class.getClassLoader().getResource("undo.png")); // load the image to a imageIcon
        Image undoImage = undoIcon.getImage(); // transform it 
        Image newimgUndo = undoImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        undoIcon = new ImageIcon(newimgUndo);  // transform it back
        // create new editactions instance to be able to create a new UndoAction instance
        EditActions undoTest = new EditActions();
        // create a new jbutton with the UndoAction, note you need to put the icon here
        JButton undoButton = new JButton(undoTest.new UndoAction(null, undoIcon, filePath, null, null));
        // set hover text to button
        undoButton.setToolTipText(bundle.getString("undo"));
        // add button to buttonPanel
        buttonPanel.add(undoButton); 

        // Button 3 - redo
        ImageIcon redoIcon = new ImageIcon(Andie.class.getClassLoader().getResource("redo.png")); // load the image to a imageIcon
        Image redoImage = redoIcon.getImage(); // transform it 
        Image newimgRedo = redoImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        redoIcon = new ImageIcon(newimgRedo);  // transform it back
        // create new editactions instance to be able to create a new RedoAction instance
        EditActions redoTest = new EditActions();
        // create a new jbutton with the RedoAction, note you need to put the icon here
        JButton redoButton = new JButton(redoTest.new RedoAction(null, redoIcon, filePath, null, null));
        // set hover text to button
        redoButton.setToolTipText(bundle.getString("redo"));
        // add button to buttonPanel
        buttonPanel.add(redoButton); 

        // Button 4 - zoom in
        ImageIcon zoomInIcon = new ImageIcon(Andie.class.getClassLoader().getResource("zoomin.png")); // load the image to a imageIcon
        Image zoomInImage = zoomInIcon.getImage(); // transform it 
        Image newimgZoomIn = zoomInImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        zoomInIcon = new ImageIcon(newimgZoomIn);  // transform it back
        // create new viewactions instance to be able to create a new ZoomInAction instance
        ViewActions zoomInTest = new ViewActions();
        // create a new jbutton with the ZoomInAction, note you need to put the icon here
        JButton zoomInButton = new JButton(zoomInTest.new ZoomInAction(null, zoomInIcon, filePath, null, null));
        // set hover text to button
        zoomInButton.setToolTipText(bundle.getString("zoomin"));
        // add button to buttonPanel
        buttonPanel.add(zoomInButton); 

        // Button 5 - zoom out
        ImageIcon zoomOutIcon = new ImageIcon(Andie.class.getClassLoader().getResource("zoomout.png")); // load the image to a imageIcon
        Image zoomOutImage = zoomOutIcon.getImage(); // transform it 
        Image newimgZoomOut = zoomOutImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        zoomOutIcon = new ImageIcon(newimgZoomOut);  // transform it back
        // create new viewactions instance to be able to create a new ZoomOutAction instance
        ViewActions zoomOutTest = new ViewActions();
        // create a new jbutton with the ZoomOutAction, note you need to put the icon here
        JButton zoomOutButton = new JButton(zoomOutTest.new ZoomOutAction(null, zoomOutIcon, filePath, null, null));
        // set hover text to button
        zoomOutButton.setToolTipText(bundle.getString("zoomout"));
        // add button to buttonPanel
        buttonPanel.add(zoomOutButton); 

        // Button 6 - resize
        ImageIcon resizeIcon = new ImageIcon(Andie.class.getClassLoader().getResource("resize.png")); // load the image to a imageIcon
        Image resizeImage = resizeIcon.getImage(); // transform it 
        Image newimgResize = resizeImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        resizeIcon = new ImageIcon(newimgResize);  // transform it back
        // create new imageactions instance to be able to create a new resizefilteraction instance
        ImageActions resizeTest = new ImageActions();
        // create a new jbutton with the resizefilteraction, note you need to put the icon here
        JButton resizeButton = new JButton(resizeTest.new ResizeFilterAction(null, resizeIcon, filePath, null, null));
        // set hover text to button
        resizeButton.setToolTipText(bundle.getString("resize"));
        // add button to buttonPanel
        buttonPanel.add(resizeButton); 

        // Button 7 - rotate
        ImageIcon rotateIcon = new ImageIcon(Andie.class.getClassLoader().getResource("rotate.png")); // load the image to a imageIcon
        Image rotateImage = rotateIcon.getImage(); // transform it 
        Image newimgRotate = rotateImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        rotateIcon = new ImageIcon(newimgRotate);  // transform it back
        // create new imageactions instance to be able to create a new rotatefilteraction instance
        ImageActions rotateTest = new ImageActions();
        // create a new jbutton with the rotatefilteraction, note you need to put the icon here
        JButton rotateButton = new JButton(rotateTest.new RotateFilterAction(null, rotateIcon, filePath, null, null));
        // set hover text to button
        rotateButton.setToolTipText(bundle.getString("rotate"));
        // add button to buttonPanel
        buttonPanel.add(rotateButton); 

        // Button 8 - flip horizontal 
        ImageIcon flipHIcon = new ImageIcon(Andie.class.getClassLoader().getResource("flipH.png")); // load the image to a imageIcon
        Image flipHImage = flipHIcon.getImage(); // transform it 
        Image newimgFlipH = flipHImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        flipHIcon = new ImageIcon(newimgFlipH);  // transform it back
        // create new imageactions instance to be able to create a new ImageFlipHorizontalAction instance
        ImageActions flipHTest = new ImageActions();
        // create a new jbutton with the ImageFlipHorizontalAction, note you need to put the icon here
        JButton flipHButton = new JButton(flipHTest.new ImageFlipHorizontalAction(null, flipHIcon, filePath, null, null));
        // set hover text to button
        flipHButton.setToolTipText(bundle.getString("flipHdesc"));
        // add button to buttonPanel
        buttonPanel.add(flipHButton); 

        // Button 9 - flip vertical
        ImageIcon flipVIcon = new ImageIcon(Andie.class.getClassLoader().getResource("flipV.png")); // load the image to a imageIcon
        Image flipVImage = flipVIcon.getImage(); // transform it 
        Image newimgFlipV = flipVImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        flipVIcon = new ImageIcon(newimgFlipV);  // transform it back
        // create new imageactions instance to be able to create a new ImageFlipVerticalAction instance
        ImageActions flipVTest = new ImageActions();
        // create a new jbutton with the ImageFlipVerticalAction, note you need to put the icon here
        JButton flipVButton = new JButton(flipVTest.new ImageFlipVerticalAction(null, flipVIcon, filePath, null, null));
        // set hover text to button
        flipVButton.setToolTipText(bundle.getString("flipVdesc"));
        // add button to buttonPanel
        buttonPanel.add(flipVButton); 

        // Button 10 - rectangle draw
        ImageIcon recDrawIcon = new ImageIcon(Andie.class.getClassLoader().getResource("recDraw.png")); // load the image to a imageIcon
        Image recDrawImage = recDrawIcon.getImage(); // transform it 
        Image newimgRecDraw = recDrawImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        recDrawIcon = new ImageIcon(newimgRecDraw);  // transform it back
        // create a new jbutton with the DrawRectangleAction, note you need to put the icon here
        JButton recDrawButton = new JButton(drawingActions.new DrawRectangleAction(null, recDrawIcon, filePath, null, null));
        // set hover text to button
        recDrawButton.setToolTipText(bundle.getString("rectangledesc"));
        // add button to buttonPanel
        buttonPanel.add(recDrawButton); 

        // Button 11 - ellipses draw
        ImageIcon elDrawIcon = new ImageIcon(Andie.class.getClassLoader().getResource("elDraw.png")); // load the image to a imageIcon
        Image elDrawImage = elDrawIcon.getImage(); // transform it 
        Image newimgElDraw = elDrawImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        elDrawIcon = new ImageIcon(newimgElDraw);  // transform it back
        // create a new jbutton with the rawEllipsesAction, note you need to put the icon here
        JButton elDrawButton = new JButton(drawingActions.new DrawEllipsesAction(null, elDrawIcon, filePath, null, null));
        // set hover text to button
        elDrawButton.setToolTipText(bundle.getString("ellipsesdesc"));
        // add button to buttonPanel
        buttonPanel.add(elDrawButton);

        // Button 12 - line draw
        ImageIcon lineDrawIcon = new ImageIcon(Andie.class.getClassLoader().getResource("lineDraw.png")); // load the image to a imageIcon
        Image lineDrawImage = lineDrawIcon.getImage(); // transform it 
        Image newimgLineDraw = lineDrawImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        lineDrawIcon = new ImageIcon(newimgLineDraw);  // transform it back
        // create a new jbutton with the DrawLineAction, note you need to put the icon here
        JButton lineDrawButton = new JButton(drawingActions.new DrawLineAction(null, lineDrawIcon, filePath, null, null));
        // set hover text to button
        lineDrawButton.setToolTipText(bundle.getString("linedesc"));
        // add button to buttonPanel
        buttonPanel.add(lineDrawButton);

        toolbar.add(buttonPanel);
        return toolbar;

    }
}

