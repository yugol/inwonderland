/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.kb;

import cogui.appli.stda.CoguiStandAloneProject;
import cogui.appli.stda.CoguiStdaActionFactory;
import cogui.appli.stda.ViewManager;
import fr.lirmm.rcr.cogui2.kernel.model.CoguiConstants;
import fr.lirmm.rcr.cogui2.ui.action.project.ExitAction;
import fr.lirmm.rcr.cogui2.ui.action.project.OpenProjectAction;
import fr.lirmm.rcr.cogui2.ui.project.ICoguiProject;
import fr.lirmm.rcr.cogui2.ui.project.ProjectManager;
import fr.lirmm.rcr.cogui2.ui.project.ViewHandler;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Iulian
 */
public final class CoGuiWrapper {

    private class DefaultWindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent evt) {
            new ExitAction(pm).actionPerformed(null);
        }
    }
    private static CoGuiWrapper inst = null;

    public static CoGuiWrapper instance() {
        if (inst == null) {
            inst = new CoGuiWrapper();
        }
        return inst;
    }
    private ICoguiProject pm;
    private DefaultWindowListener defaultWindowListener = new DefaultWindowListener();

    private CoGuiWrapper() {
        // first init of project manager
        System.out.println(CoguiConstants.COGUI_VERSION);
        // System.out.println(org.jgraph.graph.GraphConstants.);
        ViewManager viewManager = new ViewManager();
        CoguiStdaActionFactory actionManager = new CoguiStdaActionFactory(viewManager.getDescriptionLabel());
        pm = new CoguiStandAloneProject(actionManager, viewManager);
        // set current environment in ProjectManager to give access to
        // project environment: some components called back by ui features
        // must have an external reference to current project environment
        ProjectManager.instance().setCurrentEnvironment(pm);
        Component component = viewManager.getMainComponent();
        viewManager.getFrame().setJMenuBar(actionManager.createMainMenu(pm));
        viewManager.getFrame().getContentPane().add(actionManager.createMainToolBar(pm), BorderLayout.NORTH);
        viewManager.getFrame().getContentPane().add(component, BorderLayout.CENTER);
        viewManager.getFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        viewManager.getFrame().addWindowListener(defaultWindowListener);
        viewManager.getTaskView().updateView(pm);
        viewManager.getFrame().setSize(800, 600);
        viewManager.getFrame().setLocationRelativeTo(null);
        // notify for preferencesAction
        actionManager.notifyFrame(viewManager.getFrame());
        // update state of menu and toolbar items
        actionManager.updateActionState();
    }

    public void showGui(final File kbFile) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                ViewHandler viewManager = ProjectManager.instance().getCurrentEnvironment().getViewHandler();
                viewManager.getFrame().setVisible(true);
                viewManager.getProjectView().restoreFocus();
                new OpenProjectAction(pm, kbFile).actionPerformed(null);
            }
        });
    }

    public void setWindowListener(WindowListener listener) {
        ViewHandler viewManager = ProjectManager.instance().getCurrentEnvironment().getViewHandler();
        Frame frame = viewManager.getFrame();
        frame.removeWindowListener(defaultWindowListener);
        frame.addWindowListener(listener);
    }

    public void hideGui() {
        pm.closeProject();
        ViewHandler viewManager = ProjectManager.instance().getCurrentEnvironment().getViewHandler();
        Frame frame = viewManager.getFrame();
        frame.setVisible(false);
    }
}
