/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.awt.EventQueue;
import javax.swing.UIManager;
import vitaminc.ui.MainWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Main {

    public static void handleException(Throwable ex) {
        reportException(ex);
        System.exit(1);
    }

    public static void reportException(Throwable ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace(System.err);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        try {
            // Set System L&F
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            handleException(ex);
        }

        final MainWindow mainWindow = new MainWindow();
        final Controller controller = new Controller(mainWindow);

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                controller.showMainWindow();
            }
        });

    }
}
