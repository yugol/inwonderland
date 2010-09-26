/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.ui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import vitaminc.Main;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Resources {

    public static Image mainWindowIcon;
    public static Image programWindowIcon;
    public static Image consoleWindowIcon;
    public static Image drawingWindowIcon;

    static {
        try {
            mainWindowIcon = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("res/vitaminc.png"));
            programWindowIcon = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("res/program.png"));
            consoleWindowIcon = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("res/console.png"));
            drawingWindowIcon = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("res/drawing.png"));
        } catch (IOException ex) {
            Main.handleException(ex);
        }
    }
}
