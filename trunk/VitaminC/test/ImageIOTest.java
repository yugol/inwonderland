/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import vitaminc.Main;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ImageIOTest {

    public ImageIOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void loadImage() throws IOException {
        Image img = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("res/VitaminC.png"));
        System.out.println(img);
    }
}
