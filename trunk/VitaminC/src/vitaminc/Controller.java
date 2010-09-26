/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.gjt.sp.jedit.buffer.JEditBuffer;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.util.InteractiveConsole;
import org.python.util.PythonInterpreter;
import vitaminc.ui.AboutWindow;
import vitaminc.ui.DrawingWindow;
import vitaminc.ui.FeaturedTextArea;
import vitaminc.ui.MainWindow;
import vitaminc.ui.ConsoleWindow;
import vitaminc.ui.ProgramWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Controller {

    // http://markmail.org/message/oigpxea7talu4x5o
    // http://wiki.python.org/jython/JythonMonthly/Articles/October2006/3
    // 
    private final Model model;
    private final MainWindow mainWindow;
    private final ProgramWindow programWindow;
    private final ConsoleWindow consoleWindow;
    private final DrawingWindow drawingWindow;
    private final AboutWindow aboutWindow;
    private final InteractiveConsole interpreter;
    private final ControllerFile controllerFile;
    private final ControllerEdit controllerEdit;
    private final ControllerInterpreter controllerInterpreter;
    private final ControllerWindows controllerWindows;

    public Controller(MainWindow mainWindow) {
        this.interpreter = new InteractiveConsole();

        this.mainWindow = mainWindow;
        this.programWindow = new ProgramWindow();
        this.consoleWindow = new ConsoleWindow();

        DrawingWindow jturtleWindow = null;
        try {
            Properties postProperties = new Properties();
            PythonInterpreter.initialize(null, postProperties, null);

            interpreter.exec("from jturtle import *");
            PyObject screen = interpreter.get("Screen").__call__();
            PyStringMap screenDict = (PyStringMap) screen.getDict();
            jturtleWindow = (DrawingWindow) screenDict.get(new PyString("_root")).__tojava__(DrawingWindow.class);
        } catch (Exception ex) {
            Main.handleException(ex);
        }
        this.drawingWindow = jturtleWindow;
        this.drawingWindow.setVisible(false);
        this.aboutWindow = new AboutWindow();


        this.mainWindow.setController(this);
        this.programWindow.setController(this);
        this.consoleWindow.setController(this);
        this.drawingWindow.setController(this);

        this.model = new Model(programWindow.codeEditor);

        this.controllerFile = new ControllerFile(model, mainWindow, interpreter, programWindow, this);
        this.controllerEdit = new ControllerEdit(programWindow);
        this.controllerInterpreter = new ControllerInterpreter(mainWindow, programWindow, consoleWindow, drawingWindow, interpreter);
        this.controllerWindows = new ControllerWindows(mainWindow, programWindow, consoleWindow, drawingWindow);
    }

    void showMainWindow() {
        int sz = (int) mainWindow.getPreferredSize().getHeight();
        mainWindow.setMinimumSize(new Dimension(1024, sz));
        mainWindow.initializeToolButtons();
        controllerWindows.placeMainWindowForTheFirstTime();
    }

    public void initialize() {
        drawingWindow.setTitle(DrawingWindow.DEFAULT_TITLE);
        controllerWindows.resetWindowsPositions();
        controllerInterpreter.setRunEnabled(true);
        controllerInterpreter.initializeConsole();

        programWindow.requestFocus();

        File autoload = new File("autoload.py");
        if (autoload.exists()) {
            model.setProgramFile(autoload);
            controllerFile.readProgramFromFile();
        }
    }

    public void about() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = aboutWindow.getSize().width;
        int h = aboutWindow.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        aboutWindow.setLocation(x, y);
        aboutWindow.setVisible(true);
    }

    public void exitApplication() {
        if (controllerFile.trySaveProgram() == JOptionPane.CANCEL_OPTION) {
            return;
        }

        System.out.println("EXIT APPLICATION");

        if (aboutWindow != null) {
            aboutWindow.dispose();
        }
        if (drawingWindow != null) {
            drawingWindow.dispose();
        }
        if (programWindow != null) {
            programWindow.dispose();
        }
        if (consoleWindow != null) {
            consoleWindow.dispose();
        }
        if (mainWindow != null) {
            mainWindow.dispose();
        }
        System.exit(0);
    }

    public void markDirty(boolean b) {
        if (b) {
            model.setDirty(true);
            programWindow.setTitle(ProgramWindow.DEFAULT_TITLE + " *");
        } else {
            model.setDirty(false);
            programWindow.setTitle(ProgramWindow.DEFAULT_TITLE);
        }
    }

    public void checkAndHandleShortcut(KeyEvent e, Object sender) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F1:
                Util.openWebHelp();
                break;
            case KeyEvent.VK_F2:
                toggleProgramWindowVisible();
                break;
            case KeyEvent.VK_F3:
                toggleConsoleWindowVisible();
                break;
            case KeyEvent.VK_F4:
                toggleDrawingWindowVisible();
                break;
            case KeyEvent.VK_F5:
                runProgram();
                break;
            case KeyEvent.VK_F7:
                controllerEdit.findNext();
                break;
            case KeyEvent.VK_F:
                if (e.isControlDown() && !(sender instanceof ConsoleWindow)) {
                    controllerEdit.find();
                }
                break;
            case KeyEvent.VK_Z:
                if (e.isControlDown() && !(sender instanceof ConsoleWindow)) {
                    controllerEdit.undo();
                }
                break;
            case KeyEvent.VK_Y:
                if (e.isControlDown() && !(sender instanceof ConsoleWindow)) {
                    controllerEdit.redo();
                }
                break;
            case KeyEvent.VK_N:
                if (e.isControlDown()) {
                    controllerFile.newProgram();
                }
                break;
            case KeyEvent.VK_R:
                if (e.isControlDown()) {
                    controllerFile.readProgram();
                }
                break;
            case KeyEvent.VK_W:
                if (e.isControlDown()) {
                    controllerFile.writeProgram();
                }
                break;
        }
    }

    public void resetWindowsPositions() {
        controllerWindows.resetWindowsPositions();
        programWindow.requestFocus();
    }

    public void toggleProgramWindowVisible() {
        controllerWindows.setProgramWindowVisible(!programWindow.isVisible());
    }

    public void toggleConsoleWindowVisible() {
        controllerWindows.setConsoleWindowVisible(!consoleWindow.isVisible());
    }

    public void toggleDrawingWindowVisible() {
        controllerWindows.setDrawingWindowVisible(!drawingWindow.isVisible());
    }

    public void iconify() {
        programWindow.setExtendedState(JFrame.ICONIFIED);
        consoleWindow.setExtendedState(JFrame.ICONIFIED);
        drawingWindow.setExtendedState(JFrame.ICONIFIED);
    }

    public void deiconify() {
        programWindow.setExtendedState(JFrame.NORMAL);
        consoleWindow.setExtendedState(JFrame.NORMAL);
        drawingWindow.setExtendedState(JFrame.NORMAL);
    }

    public void runProgram() {
        controllerInterpreter.startRunningScript();
    }

    public void stopProgram() {
        controllerInterpreter.stopRunningScript();
    }

    public void newProgram() {
        controllerFile.newProgram();
    }

    public void writeProgram() {
        controllerFile.writeProgram();
    }

    public void writeProgramAs() {
        controllerFile.saveAsProgram();
    }

    public void readProgram() {
        controllerFile.readProgram();
    }

    public void setProgramWindowVisible(boolean selected) {
        controllerWindows.setProgramWindowVisible(selected);
    }

    public void setConsoleWindowVisible(boolean selected) {
        controllerWindows.setConsoleWindowVisible(selected);
    }

    public void setDrawingWindowVisible(boolean b) {
        controllerWindows.setDrawingWindowVisible(b);
    }

    public void arrangeVisibleWindows() {
        controllerWindows.arrangeVisibleWindows();
    }

    public void undo() {
        controllerEdit.undo();
    }

    public void redo() {
        controllerEdit.redo();
    }

    public void cut() {
        controllerEdit.cut();
    }

    public void copy() {
        controllerEdit.copy();
    }

    public void paste() {
        controllerEdit.paste();
    }

    public void find() {
        controllerEdit.find();
    }

    public void findNext() {
        controllerEdit.findNext();
    }

    public void addSnippet(String snippet) {
        FeaturedTextArea textArea = programWindow.codeEditor;
        JEditBuffer buffer = textArea.getBuffer();
        int carretPos = textArea.getCaretPosition();
        buffer.insertIndented(carretPos, snippet);
        programWindow.requestFocus();
    }

    private void focustoggleProgramWindowVisible() {
        Window wnd = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        if (programWindow == wnd) {
            setProgramWindowVisible(false);
        } else {
            int state = programWindow.getExtendedState();
            if ((state & ProgramWindow.ICONIFIED) != 0) {
                programWindow.setExtendedState(ProgramWindow.NORMAL);
            } else {
                if (programWindow.isVisible()) {
                    programWindow.requestFocus();
                } else {
                    setProgramWindowVisible(true);
                }
            }
        }
    }

    private void focustoggleConsoleWindowVisible() {
        Window wnd = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        if (consoleWindow == wnd) {
            setConsoleWindowVisible(false);
        } else {
            if (consoleWindow.isVisible()) {
                consoleWindow.requestFocus();
            } else {
                setConsoleWindowVisible(true);
            }
        }
    }

    private void focustoggleDrawingWindowVisible() {
        Window wnd = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        if (drawingWindow == wnd) {
            setDrawingWindowVisible(false);
        } else {
            if (drawingWindow.isVisible()) {
                drawingWindow.requestFocus();
            } else {
                setDrawingWindowVisible(true);
            }
        }
    }
}

