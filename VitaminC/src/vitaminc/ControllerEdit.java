/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import javax.swing.JOptionPane;
import org.gjt.sp.jedit.JEditBeanShellAction;
import org.gjt.sp.jedit.textarea.Selection;
import org.gjt.sp.jedit.textarea.StandaloneTextArea;
import vitaminc.ui.ProgramWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ControllerEdit {

    private final ProgramWindow programWindow;
    private String searchPattern = null;
    private int lastFindIndex = -1;

    public ControllerEdit(ProgramWindow programWindow) {
        this.programWindow = programWindow;
    }

    private void invoke(String action) {
        StandaloneTextArea textArea = programWindow.codeEditor;
        JEditBeanShellAction shellAction = textArea.getActionContext().getAction(action);
        shellAction.invoke(textArea);
    }

    public void undo() {
        invoke("undo");
    }

    public void redo() {
        invoke("redo");
    }

    public void cut() {
        invoke("cut");
    }

    public void copy() {
        invoke("copy");
    }

    public void paste() {
        invoke("paste");
    }

    public void find() {
        String selection = programWindow.codeEditor.getSelectedText();
        lastFindIndex = programWindow.codeEditor.getCaretPosition();
        String tmp = (String) JOptionPane.showInputDialog(
                null,
                "Please enter the search pattern",
                "Find",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                selection);
        if (tmp != null) {
            searchPattern = tmp;
            findNext();
        }
    }

    public void findNext() {
        if (searchPattern == null) {
            find();
        } else {
            String text = programWindow.codeEditor.getText();
            if (lastFindIndex < 0 || lastFindIndex >= text.length()) {
                lastFindIndex = 0;
            }
            int find = text.indexOf(searchPattern, lastFindIndex);
            if (find < 0) {
                if (lastFindIndex > 0) {
                    lastFindIndex = 0;
                    find = text.indexOf(searchPattern, lastFindIndex);
                }
            }
            if (find < 0) {
                JOptionPane.showMessageDialog(null, "The pattern\n`" + searchPattern + "'\ncould not be found.", "Find", JOptionPane.INFORMATION_MESSAGE);
            } else {
                lastFindIndex = find + 1;
                Selection s = new Selection.Range(find, find + searchPattern.length());
                programWindow.codeEditor.setCaretPosition(find);
                programWindow.codeEditor.setSelection(s);
            }
        }
    }
}
