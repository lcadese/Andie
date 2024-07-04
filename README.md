# Team Windex

## Project Parts
### Stage 1
- Image Export - Ray
- Multilingual Support - Ray
- Image Resize - Lizzie
- Image Rotations - Lizzie
- Median Filter - Veronika
- Image Flip - Veronika
- Sharpen Filter - Elia 
- Gaussian Blur Filter - Elia 
- Brightness Adjustment - Liam
- Contrast Adjustment - Liam
### Stage 2
- Extended Filters - Elia, Veronika
- Filters with negative results - Elia, Veronika
- Emboss and edge detection filters - Veronika
- Toolbar for common operations - Lizzie
- Keyboard shortcuts - Elia
    - Command and Control shortcuts for iOS and Windows respectively
- Mouse selection of rectangular objects - Ray
- Crop to selection - Ray
- Drawing functions (rectangle, ellipse, line) - Ray, Liam
- Macros for record and replay of operations - Liam
- Show us something (3D Anaglyph Filter - Lizzie, Application Appearance - Veronika)
    - filter which creates a stereoscopic effect by overlapping two additional layers on the image (a stereo pair of red and cyan)
    - Changing the appearance of the application (light, dark and pastel pink mode available)

### Images
- All images used in the custom Look and Feel options are in the public domain. The images were found on Openclipart; the license can be found at https://openclipart.org/faq. For reference, the artists are: https://openclipart.org/artist/dannya, https://openclipart.org/artist/sixsixfive, https://openclipart.org/artist/DaveMeyer and Veronika (arrow and slider thumb icons). 

## How Code Was tested
- Ray - Language support was tested manually by going through MenuBar options, it was discovered after merging that the code didn't work on MacOS due to differing file separators needed to acess the message bundles. Image export was tested manually with .jpg and .png file types 
- Lizzie - Resize Filter tested using JUnit tests: checks the mimiumum (50) and maximum (200) inputs create the desired output.
Rotate Filter tested using JUnit tests: checks that the input of 90 (90 degree rotation) creates the width and height of desired output.
Also tested by mimicking the user experience to see if inputs create expected outputs.
- Veronika - Tested Median, Image flip, Emboss and Sobel filters and the custom Look and Feel by mimicking the user experience, seeing if the output image looked correct and using JUnit tests. Tested extended filters and filters with negative results using JUnit tests.
- Elia - Tested as a user. Experienced black borders when applying Gaussian blur and Sharpen filter due to the nature of the convolutions. As this was a task in the second deliverable, this was fixed before the second deliverable due date (24/5/23). A problem was identified when applying the 3d anaglyph filter and then applying a filter that uses a convolution. The resulted in the image becoming blank. This was fixed (with the help of the other members) by removing the output BufferImage parameter in the ConvertToAnglyph.java file (the reason for the problem was uncertain). Another bug was later identified when a convolution using filter was applied on a png file. This also resulted in the image becoming blank. This was fixed (with the help of Veronika) by removing the output BufferImage parameter and declaring it inside the method (the reason for this problem was also unknown). It was also noted that the redoOps stack was not cleared when an operation (that is not applied using redo) was applied. This was shortly fixed.
- Liam - Tested brightness and contrast control by using multiple images of different sizes and colours as an end user would. Used JUnit tests to make sure RGB values were what were expected. One way to test macros was that I wrote print statements to tell me when specific operations were happening or changing. When testing early on I would have classes made to test specific operations such as classes to serialize and de-serialize information into an ops file to ensure that I had correct methods before adding them into the main program. Testing drawing actions was done mostly through visually seeing changes and based off those changes and editing code to get it to look correct.

## User Guide 
ANDIE (A Non-Destructive Image Editor) is a GUI that allows users to view images and apply various image operations to edit images. It is a Java-based software application. Tested on Windows and Mac.

To get started go to [**File**] -> [**Open**] to open a new image file to view/edit.

### User Experience
- To zoom in or out of the image go to [**View**]
- To change to another language go to [**Language**]
    Supported languages are:
        - English
        - *rgbbbrdrslkkl*
        - Swahili
        - Japanese
        - Maori
    Please note that once a language is selected, ANDIE will close and so you must run the program again.
- To change the appearance go to [**Appearance**]
    Themes are:
        - Dark
        - Light
        - Pastel Pink
    Please note that once a theme/appearance is selected, ANDIE will close and so you must run the program again.

### Operations
- To change the colouring of the image (greyscale, brightness, contrast adjustmenst, 3d anaglyph) go to [**Colour**]
- To change the orientation and size of the image (resize, rotation and flipping) go to [**Image**]
- To apply a filter to change the look of the image (blurring, sharpening, edge detection, emboss) go to [**Filter**]
- To draw in any place; lines of any length and direction or ellipses or rectangles of any size go to [**Drawing**]. You can also choose the color of these objects by going [**Drawing**]->[**Color**] beforehand.
- You can undo any of these actions with [**Edit**] -> [**Undo**]. If you would like to revert your undo operation go to [**Edit**] -> [**Redo**].
- To create a macro (a recorded sequence of image processing operations), first go to [**Macro**] then begin by clicking [**Start Recording**]. When you have added all the desirable operations to the image either click [**Save Recording**] to save it to a file or [**Stop Recording**] to stop and discard the current recording. Once you have saved a macro you can apply it to any image using [**Load Recording**]

Once you are happy with your image you can [**Save**], [**Save_As**] or [**Export**] in [**File**] and then [**File**] -> [**Exit**].


## Additional Build Instructions 

Median filter takes a long time to apply on large images and/or with a large radius.

 
