/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import org.python.util.InteractiveInterpreter;
import org.python.util.PythonInterpreter;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ScriptRunner extends Thread {

    private final String script;
    private final InteractiveInterpreter interpreter;
    private final ControllerInterpreter controller;
    private Throwable interruptException;
    private boolean userInterrupt;

    ScriptRunner(String script, InteractiveInterpreter interpreter, ControllerInterpreter controller) {
        super();
        this.script = script;
        this.interpreter = interpreter;
        this.controller = controller;
    }

    Throwable getInterruptException() {
        return interruptException;
    }

    boolean isUserInterrupt() {
        return userInterrupt;
    }

    void setUserInterrupt(boolean userInterrupt) {
        this.userInterrupt = userInterrupt;
    }

    @Override
    public void run() {
        userInterrupt = false;
        interruptException = null;
        try {
            interpreter.exec(script);
        } catch (Throwable t) {
            interruptException = t;
            if (userInterrupt) {
                return;
            }
        }
        controller.doneRunningScript();
    }
}
