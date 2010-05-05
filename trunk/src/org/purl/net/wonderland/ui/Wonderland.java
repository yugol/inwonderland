/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
/*
 * Wonderland.java
 *
 * Created on Jan 15, 2010, 3:39:37 PM
 */
package org.purl.net.wonderland.ui;

import java.awt.event.ActionEvent;
import org.purl.net.wonderland.util.UI;
import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.CoGuiWrapper;

/**
 *
 * @author Iulian
 */
public class Wonderland extends javax.swing.JFrame {

    static String baseTitle = "Wonderland";
    private Engine engine;

    /** Creates new form Wonderland */
    public Wonderland() throws Exception {
        engine = new Engine();
        initComponents();
        processMessageButton.setEnabled(false);

        CoGuiWrapper.instance().setWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                CoGuiWrapper.instance().hideGui();
                showCGButton.setEnabled(true);
                processMessageButton.setEnabled(true);
            }
        });

        buildPersonalityMenu();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textFileChooser = new javax.swing.JFileChooser();
        kbFileChooser = new javax.swing.JFileChooser();
        textFileFilter = new org.purl.net.wonderland.ui.TextFileFilter();
        kbFileFilter = new org.purl.net.wonderland.ui.KbFileFilter();
        mainTabs = new javax.swing.JTabbedPane();
        messagesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyTextArea = new javax.swing.JTextArea();
        processMessageButton = new javax.swing.JButton();
        showCGButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openTextFileMenuItem = new javax.swing.JMenuItem();
        openKbMenuItem = new javax.swing.JMenuItem();
        saveKbAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        personalityMenu = new javax.swing.JMenu();
        toolsMenu = new javax.swing.JMenu();
        ontExpMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        textFileChooser.setDialogTitle("Select input file");
        textFileChooser.setFileFilter(textFileFilter);

        kbFileChooser.setDialogTitle("Select CG XML file");
        kbFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        kbFileChooser.setFileFilter(kbFileFilter);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("mainFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        messagesPanel.setVerifyInputWhenFocusTarget(false);

        inputTextArea.setColumns(20);
        inputTextArea.setLineWrap(true);
        inputTextArea.setRows(5);
        inputTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(inputTextArea);

        historyTextArea.setColumns(20);
        historyTextArea.setEditable(false);
        historyTextArea.setLineWrap(true);
        historyTextArea.setRows(5);
        historyTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(historyTextArea);

        processMessageButton.setText(">");
        processMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processMessageButtonActionPerformed(evt);
            }
        });

        showCGButton.setText("CG");
        showCGButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCGButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout messagesPanelLayout = new javax.swing.GroupLayout(messagesPanel);
        messagesPanel.setLayout(messagesPanelLayout);
        messagesPanelLayout.setHorizontalGroup(
            messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(processMessageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showCGButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        messagesPanelLayout.setVerticalGroup(
            messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showCGButton)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(processMessageButton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainTabs.addTab("Messages", messagesPanel);

        fileMenu.setText("File");

        openTextFileMenuItem.setText("Open Text File...");
        openTextFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openTextFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openTextFileMenuItem);

        openKbMenuItem.setText("Open Knowledge Base...");
        openKbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openKbMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openKbMenuItem);

        saveKbAsMenuItem.setText("Save Knowledge Base As ...");
        saveKbAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveKbAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveKbAsMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        personalityMenu.setText("Personality");
        menuBar.add(personalityMenu);

        toolsMenu.setText("Tools");

        ontExpMenuItem.setText("Ontology Explorer...");
        ontExpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ontExpMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(ontExpMenuItem);

        menuBar.add(toolsMenu);

        helpMenu.setText("Help");

        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void showCGButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCGButtonActionPerformed
        try {
            if (engine.getLastFile() == null) {
                saveKbAsMenuItemActionPerformed(null);
            }
            if (engine.getLastFile() != null) {
                File kbFile = engine.getLastFile();
                engine.saveKb(kbFile);
                CoGuiWrapper.instance().showGui(kbFile);
                showCGButton.setEnabled(false);
                processMessageButton.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }

    }//GEN-LAST:event_showCGButtonActionPerformed

    private void openTextFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openTextFileMenuItemActionPerformed
        int returnVal = textFileChooser.showDialog(this, "Open");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = textFileChooser.getSelectedFile();
            try {
                String message = org.purl.net.wonderland.util.IO.getFileContentAsString(file);
                inputTextArea.setText(message);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_openTextFileMenuItemActionPerformed

    private void processMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processMessageButtonActionPerformed
        String message = noteUserMessage();
        if (message != null) {
            try {
                message = engine.processMessage(message);
            } catch (Exception ex) {
                W.reportExceptionConsole(ex);
            }
            noteProgramResponse(message);
        }
    }//GEN-LAST:event_processMessageButtonActionPerformed

    private void openKbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openKbMenuItemActionPerformed
        int returnVal = kbFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = kbFileChooser.getSelectedFile();
            try {
                engine.openKb(file);
                setTitle(baseTitle + " - " + file.getName());
                historyTextArea.setText("");
                StringBuffer report = new StringBuffer();
                report.append("'");
                report.append(file.getName());
                report.append("' contains ");
                int msgCount = engine.getFactCount();
                report.append(msgCount);
                report.append((msgCount == 1) ? (" message.") : (" messages."));
                noteProgramResponse(report.toString());
                inputTextArea.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_openKbMenuItemActionPerformed

    private void saveKbAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveKbAsMenuItemActionPerformed
        int returnVal = kbFileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = kbFileChooser.getSelectedFile();
            if (!UI.cogxml.equals(UI.getExtension(file))) {
                file = new File(file.getAbsolutePath() + "." + UI.cogxml);
            }
            try {
                engine.saveKb(file);
                setTitle(baseTitle + " - " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_saveKbAsMenuItemActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setTitle(baseTitle);
        showWellcomeMessage();
    }

    private void showWellcomeMessage() {
        noteProgramResponse(engine.getPersonality().getWelcomeMessage());
    }//GEN-LAST:event_formWindowOpened

    private void ontExpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ontExpMenuItemActionPerformed
        WordNetExplorer.instance.setVisible(true);
    }//GEN-LAST:event_ontExpMenuItemActionPerformed

    private String noteUserMessage() {
        String message = inputTextArea.getText().trim();
        if (message.length() > 0) {
            StringBuffer history = new StringBuffer(historyTextArea.getText());
            history.append("You: ");
            history.append(inputTextArea.getText());
            history.append("\n\n");
            historyTextArea.setText(history.toString());
            inputTextArea.setText("");
            return message;
        }
        return null;
    }

    private void noteProgramResponse(String message) {
        StringBuffer history = new StringBuffer(historyTextArea.getText());
        history.append(engine.getPersonality().getName() + ": ");
        history.append(message);
        history.append("\n\n");
        historyTextArea.setText(history.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        W.getVerbNetDataFolder();

        try {
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        } catch (Throwable t) {
            System.err.println("Problem occurs when loading Default Look & Feel");
            return;
        }

        try {
            final Wonderland gui = new Wonderland();
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    gui.setVisible(true);
                }
            });
            Thread libLoader = new Thread(new Runnable() {

                public void run() {
                    W.init();
                    gui.processMessageButton.setEnabled(true);
                }
            });
            libLoader.run();
        } catch (Exception ex) {
            System.err.println("Error initializing Wonderland");
            W.handleException(ex);
        }


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextArea historyTextArea;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFileChooser kbFileChooser;
    private org.purl.net.wonderland.ui.KbFileFilter kbFileFilter;
    private javax.swing.JTabbedPane mainTabs;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel messagesPanel;
    private javax.swing.JMenuItem ontExpMenuItem;
    private javax.swing.JMenuItem openKbMenuItem;
    private javax.swing.JMenuItem openTextFileMenuItem;
    private javax.swing.JMenu personalityMenu;
    private javax.swing.JButton processMessageButton;
    private javax.swing.JMenuItem saveKbAsMenuItem;
    private javax.swing.JButton showCGButton;
    private javax.swing.JFileChooser textFileChooser;
    private org.purl.net.wonderland.ui.TextFileFilter textFileFilter;
    private javax.swing.JMenu toolsMenu;
    // End of variables declaration//GEN-END:variables

    private void buildPersonalityMenu() {
        try {
            String packageName = Personality.class.getCanonicalName();
            packageName = packageName.substring(0, packageName.lastIndexOf('.'));

            // Get a File object for the package
            File directory = null;
            try {
                ClassLoader cld = Thread.currentThread().getContextClassLoader();
                if (cld == null) {
                    throw new ClassNotFoundException("Can't get class loader.");
                }
                String path = packageName.replace('.', '/');
                URL resource = cld.getResource(path);
                if (resource == null) {
                    throw new ClassNotFoundException("No resource for " + path);
                }
                directory = new File(resource.getFile());
            } catch (NullPointerException x) {
                throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
            }

            ButtonGroup bgroup = new ButtonGroup();
            if (directory.exists()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                for (int i = 0; i < files.length; i++) {
                    // we are only interested in .class files
                    if (files[i].endsWith(".class")) {
                        // removes the .class extension
                        Class cls = Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6));
                        // select only subclasses of Personality
                        if (Personality.class.isAssignableFrom(cls) && !cls.isAssignableFrom(Personality.class)) {
                            addPersonalityMenuItem(cls.getCanonicalName(), bgroup);
                        }
                    }
                }
            } else {
                throw new ClassNotFoundException(packageName + " does not appear to be a valid package");
            }


        } catch (Exception ex) {
            System.err.println("Error building personality menu. Using defaults.");
            W.reportExceptionConsole(ex);
            personalityMenu.setVisible(false);
        }
    }

    private void addPersonalityMenuItem(String clsName, ButtonGroup bgroup) throws Exception {
        final Personality pers = (Personality) Class.forName(clsName).newInstance();

        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem();
        menuItem.setText(pers.getFullName());

        if (pers.getClass().getCanonicalName().equals(engine.getPersonality().getClass().getCanonicalName())) {
            menuItem.setSelected(true);
        } else {
            menuItem.setSelected(false);
        }
        bgroup.add(menuItem);

        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                engine.setPersonality(pers);
                showWellcomeMessage();
            }
        });

        personalityMenu.add(menuItem);
    }
}
