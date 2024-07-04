/** 
* <p>
* Modified code from author Hristo Stoyanov: https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
* <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
* 4.0</a>
* </p>
* @author Elia Hayashishita
*/
package cosc202.andie;
import java.awt.event.KeyEvent;
import java.util.Locale;

public class Control{
    public static final int COMMAND;  
    static {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.contains("mac")) || (OS.contains("darwin"))) {
            COMMAND = KeyEvent.META_DOWN_MASK;
        } else if (OS.contains("win")) {
            COMMAND = KeyEvent.CTRL_DOWN_MASK;
        } else if (OS.contains("nux")) {
            COMMAND = KeyEvent.CTRL_DOWN_MASK;
        } else {
            COMMAND = KeyEvent.CTRL_DOWN_MASK;
        }
    }
    public Control(){}
}
