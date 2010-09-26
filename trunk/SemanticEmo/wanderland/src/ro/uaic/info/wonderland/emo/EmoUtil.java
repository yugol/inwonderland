/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeSet;
import sun.awt.EmbeddedFrame;

/**
 *
 * @author Iulian
 */
public class EmoUtil {

    static Color backColor = Color.white;
    static Color foreColor = Color.black;
    static int imgSize = 451;
    static int wheelRadius = imgSize / 3;
    static int absoluteCenter = (imgSize - 1) / 2 + 1;
    static int axisRadius = wheelRadius + wheelRadius / 5;
    static double[] iFactors = new double[]{0.25, 0.38, 0.58, 0.83};
    static int intensity[] = new int[]{
        (int) (wheelRadius * iFactors[0]),
        (int) (wheelRadius * iFactors[1]),
        (int) (wheelRadius * iFactors[2]),
        (int) (wheelRadius * iFactors[3])};
    static int labelRadius = (int) (wheelRadius * 1.05);
    static Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    static Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    static float defaultBrightness = 0.98f;
    static float defaultSaturation = 1f;

    public static Point polarToCartesian(float angle, float radius) {
        Point p = new Point();
        p.x = (int) (Math.cos(angle) * radius);
        p.y = (int) (Math.sin(angle) * radius);
        return p;
    }

    public static float cartesianToAngle(int x, int y) {
        double radius = Math.sqrt(x * x + y * y);
        double angleInRadians = Math.acos(x / radius);
        if (y < 0) {
            angleInRadians = (Math.PI * 2) - angleInRadians;
        }
        return (float) angleInRadians;
    }

    public static byte[] generateGenericEmoMap(EmoOntology emoOnt) throws IOException {
        BufferedImage img = createBasicGEW();
        Graphics2D g = img.createGraphics();
        for (EmoCategory emoCat : emoOnt.getEmoCategories()) {
            for (int i : intensity) {
                drawEmotionArea(emoCat, i, g);
            }
            drawEmotionLabel(emoCat, labelRadius, g);
        }
        return imgToByteArray(img);
    }

    private static byte[] imgToByteArray(BufferedImage img) throws IOException, ImageFormatException {
        ByteArrayOutputStream imgOut = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imgOut);
        JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(img);
        jep.setQuality(1f, false);
        encoder.setJPEGEncodeParam(jep);
        encoder.encode(img);
        imgOut.close();
        return imgOut.toByteArray();
    }

    private static BufferedImage createBasicGEW() {
        BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(backColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(foreColor);

        int diam = wheelRadius * 2 + 1;
        int x = (imgSize - diam) / 2;
        g.drawOval(x, x, diam, diam);
        g.drawLine(absoluteCenter, absoluteCenter, absoluteCenter + axisRadius, absoluteCenter);
        g.drawLine(absoluteCenter, absoluteCenter, absoluteCenter, absoluteCenter + axisRadius);
        g.drawLine(absoluteCenter, absoluteCenter, absoluteCenter - axisRadius, absoluteCenter);
        g.drawLine(absoluteCenter, absoluteCenter, absoluteCenter, absoluteCenter - axisRadius);
        return img;
    }

    private static void drawEmotionArea(EmoCategory emoCat, float intensity, Graphics2D g) {
        Point pos = polarToCartesian(emoCat.getOrientation(), intensity);
        int radius = (int) (intensity / 6);
        pos.x = absoluteCenter + pos.x - radius;
        pos.y = absoluteCenter - pos.y - radius;
        Color color = new Color(Color.HSBtoRGB(emoCat.getHue(), defaultSaturation, defaultBrightness));
        g.setColor(color);
        g.fillOval(pos.x, pos.y, radius * 2, radius * 2);
    }

    private static void drawEmotionLabel(EmoCategory emoCat, int labelRadius, Graphics2D g) {
        Point pos = polarToCartesian(emoCat.getOrientation(), labelRadius);
        String label = emoCat.getName();
        placeTextAroundCenter(pos, label, g);
        g.setColor(foreColor);
        g.setFont(boldFont);
        g.drawString(label, pos.x, pos.y);
    }

    private static void placeTextAroundCenter(Point pos, String text, Graphics2D g) {
        if (pos.x < 0) {
            int advance = 0;
            for (int i = 0; i < text.length(); ++i) {
                advance += g.getFontMetrics().charWidth(text.charAt(i));
            }
            pos.x -= advance;
        }
        if (pos.y < 0) {
            int height = g.getFontMetrics().getAscent();
            pos.y -= height;
        }
        pos.x = absoluteCenter + pos.x;
        pos.y = absoluteCenter - pos.y;
    }

    public static byte[] generateEmoSearchResultMap(EmoSearchResult esr) throws IOException {
        BufferedImage img = createBasicGEW();
        Graphics2D g = img.createGraphics();
        Set<Integer> categories = new TreeSet<Integer>();
        for (int i = 0; i < esr.getEmoHits().length; ++i) {
            if (esr.getEmoHits()[i] > 0) {
                Emotion emo = esr.getEmoOnt().getEmotions()[i];
                categories.add((int) emo.getEmoCategory());
                EmoCategory emoCat = esr.getEmoOnt().getEmoCategories()[emo.getEmoCategory() - 1];
                drawEmotionArea(emoCat, intensity[emo.getNominalIntensity() - 1], g);
            }
        }
        for (int i : categories) {
            EmoCategory emoCat = esr.getEmoOnt().getEmoCategories()[i - 1];
            drawEmotionLabel(emoCat, labelRadius, g);
        }
        return imgToByteArray(img);
    }

    public static void byteArrayToImageFile(byte[] data, String fileName) throws FileNotFoundException, IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
        out.write(data);
        out.close();
    }

    public static void generateEmoImages() throws FileNotFoundException, IOException {
        int size = 10;
        int[] radius = new int[]{4, 6, 8, 10};
        EmoOntology emoOnt = new EmoOntology();
        for (Emotion emo : emoOnt.getEmotions()) {
            float hue = emoOnt.getEmoCategories()[emo.getEmoCategory() - 1].getHue();
            BufferedImage img = new BufferedImage(size * 2 + 1, size * 2 + 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setColor(backColor);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
            g.setColor(foreColor);
            Color color = new Color(Color.HSBtoRGB(hue, defaultSaturation, defaultBrightness));
            g.setColor(color);
            int rad = radius[emo.getNominalIntensity() - 1];
            g.fillOval(size - rad, size - rad, rad * 2, rad * 2);
            byte[] bytes = imgToByteArray(img);
            byteArrayToImageFile(bytes, "./imgs/" + emo.getCode() + ".jpg");
        }
    }
}
