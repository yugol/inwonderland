/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import vitaminc.ui.DrawingWindow;
import vitaminc.ui.MainWindow;
import vitaminc.ui.ConsoleWindow;
import vitaminc.ui.ProgramWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ControllerWindows {

    private static final double programWindowHorizontalFraction = 0.4;
    private static final double programWindowVericalFraction = 0.6;
    private final MainWindow mainWindow;
    private final ProgramWindow programWindow;
    private final ConsoleWindow consoleWindow;
    private final DrawingWindow drawingWindow;
    private Point usableLocation;
    private Dimension usableSize;
    private Insets usableInsets;

    ControllerWindows(MainWindow mainWindow, ProgramWindow programWindow, ConsoleWindow consoleWindow, DrawingWindow drawingWindow) {
        this.mainWindow = mainWindow;
        this.programWindow = programWindow;
        this.consoleWindow = consoleWindow;
        this.drawingWindow = drawingWindow;
        forgetUsableArea();
    }

    void setProgramWindowVisible(boolean b) {
        programWindow.setVisible(b);
        mainWindow.programButton.setSelected(b);
        mainWindow.programMenuItem.setText((b ? ("Hide") : ("Show")) + " Program Window");
    }

    void setConsoleWindowVisible(boolean b) {
        consoleWindow.setVisible(b);
        mainWindow.consoleButton.setSelected(b);
        mainWindow.consoleMenuItem.setText((b ? ("Hide") : ("Show")) + " Console Window");
    }

    void setDrawingWindowVisible(boolean b) {
        drawingWindow.setVisible(b);
        mainWindow.drawingButton.setSelected(b);
        mainWindow.drawingMenuItem.setText((b ? ("Hide") : ("Show")) + " Drawing Window");
    }

    private void findUsableArea() {
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(mainWindow.getExtendedState() | MainWindow.MAXIMIZED_BOTH);
        usableInsets = mainWindow.getInsets();
        usableLocation = mainWindow.getLocation();
        usableLocation = new Point(usableLocation.x + usableInsets.left, usableLocation.y + usableInsets.bottom);
        usableSize = mainWindow.getSize();
        usableSize = new Dimension(usableSize.width - (usableInsets.left + usableInsets.right), usableSize.height - (usableInsets.bottom * 2));
    }

    private void forgetUsableArea() {
        usableLocation = null;
        usableSize = null;
        usableInsets = null;
    }

    void placeMainWindowForTheFirstTime() {
        // mainWindow.setVisible(true);
        findUsableArea();
        placeMainWindow();
        forgetUsableArea();
    }

    void resetWindowsPositions() {
        if (!mainWindow.isVisible()) {
            mainWindow.setVisible(true);
        }
        if (!programWindow.isVisible()) {
            setProgramWindowVisible(true);
        }
        if (!consoleWindow.isVisible()) {
            setConsoleWindowVisible(true);
        }
        if (!drawingWindow.isVisible()) {
            setDrawingWindowVisible(true);
        }
        arrangeVisibleWindows();
    }

    void arrangeVisibleWindows() {
        findUsableArea();

        placeMainWindow();
        int x = usableLocation.x;
        int y = (int) (usableLocation.y + mainWindow.getMinimumSize().getHeight() + 1);
        int width = usableSize.width;
        int height = usableSize.height - (y - usableLocation.y);
        placeProgramWindow(x, y, width, height);
        placeConsoleWindow(x, y, width, height);
        placeDrawingWindow(x, y, width, height);

        forgetUsableArea();
    }

    private void placeMainWindow() {
        mainWindow.setLocation(usableLocation);
        mainWindow.setSize(usableSize.width, mainWindow.getMinimumSize().height);
    }

    private void placeProgramWindow(int x, int y, int width, int height) {
        if (consoleWindow.isVisible()) {
            height = (int) (height * programWindowVericalFraction);
        }
        if (drawingWindow.isVisible()) {
            width = (int) (width * programWindowHorizontalFraction);
        }
        programWindow.setLocation(x, y);
        programWindow.setSize(width, height);
    }

    private void placeConsoleWindow(int x, int y, int width, int height) {
        if (programWindow.isVisible()) {
            y += programWindow.getHeight() + 1;
            width = programWindow.getWidth();
            height = usableSize.height - (y - usableLocation.y);
        } else {
            if (drawingWindow.isVisible()) {
                width = (int) (usableSize.width * programWindowHorizontalFraction);
            }
        }
        consoleWindow.setLocation(x, y);
        consoleWindow.setSize(width, height);
    }

    private void placeDrawingWindow(int x, int y, int width, int height) {
        if (programWindow.isVisible()) {
            x += (programWindow.getWidth() + 1);
            width -= (programWindow.getWidth() + 1);
        } else if (consoleWindow.isVisible()) {
            x += (consoleWindow.getWidth() + 1);
            width -= (consoleWindow.getWidth() - 1);
        }
        drawingWindow.setLocation(x, y);
        drawingWindow.setSize(width, height);
    }
}
