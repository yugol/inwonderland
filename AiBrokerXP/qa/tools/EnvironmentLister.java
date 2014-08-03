package tools;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Enumeration;

public class EnvironmentLister {

    public static void describeDesktop() {
        System.out.println("\n----- DESKTOP DESCRIPTION -----\n");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Overall");
        System.out.println("Screen width: " + screenSize.getWidth());
        System.out.println("Screen height: " + screenSize.getHeight());

        final GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (final GraphicsDevice gd : gs) {
            System.out.println("\nMonitor");
            final DisplayMode dm = gd.getDisplayMode();
            System.out.println("Width: " + dm.getWidth());
            System.out.println("Hight: " + dm.getHeight());
        }

        final DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        System.out.println("\nDefault: " + dm.getWidth() + " x " + dm.getHeight());

        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(new Frame().getGraphicsConfiguration());
        final Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        System.out.println(scnMax.top + " " + winSize.getWidth());
    }

    public static void listEnvironmentVariables() {
        System.out.println("\n----- ENVIRONMENT VARIABLES -----\n");
        for (final String key : System.getenv().keySet()) {
            System.out.println(key + " -> " + System.getenv(key));
        }
    }

    public static void listSystemProperties() {
        System.out.println("\n----- SYSTEM PROPERTIES -----\n");
        final Enumeration<Object> it = System.getProperties().keys();
        while (it.hasMoreElements()) {
            final String key = (String) it.nextElement();
            System.out.println(key + " -> " + System.getProperty(key));
        }
    }

    public static void main(final String... args) {
        describeDesktop();
        listEnvironmentVariables();
        listSystemProperties();
    }

}
