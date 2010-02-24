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
 * Gui.java
 *
 * Created on Jan 15, 2010, 3:39:37 PM
 */
package org.purl.net.wonderland.ui;

import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.CoGuiWrapper;
import org.purl.net.wonderland.nlp.resources.MorphAdornerWrapper;

/**
 *
 * @author Iulian
 */
public class Gui extends javax.swing.JFrame {

    static String baseTitle = "Wonderland";

    /** Creates new form Gui */
    public Gui() {
        initComponents();
        processMessageButton.setEnabled(false);
        CoGuiWrapper.instance().setWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                CoGuiWrapper.instance().hideGui();
                setVisible(true);
            }
        });
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
        try {
            messageProcessor = new org.purl.net.wonderland.engine.MessageProcessor();
        } catch (java.lang.Exception e1) {
            e1.printStackTrace();
        }
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
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        textFileChooser.setDialogTitle("Select input file");
        textFileChooser.setFileFilter(textFileFilter);

        kbFileChooser.setDialogTitle("Select CG XML file");
        kbFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        kbFileChooser.setFileFilter(kbFileFilter);
        kbFileChooser.setFileHidingEnabled(false);

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

        editMenu.setText("Edit");

        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

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
            if (messageProcessor.getLastFile() == null) {
                saveKbAsMenuItemActionPerformed(null);
            }
            if (messageProcessor.getLastFile() != null) {
                File kbFile = messageProcessor.getLastFile();
                messageProcessor.saveKb(kbFile);
                CoGuiWrapper.instance().showGui(kbFile);
                setVisible(false);
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
            message = messageProcessor.processMessage(message);
            noteProgramResponse(message);
        }
    }//GEN-LAST:event_processMessageButtonActionPerformed

    private void openKbMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openKbMenuItemActionPerformed
        int returnVal = kbFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = kbFileChooser.getSelectedFile();
            try {
                messageProcessor.openKb(file);
                setTitle(baseTitle + " - " + file.getName());
                historyTextArea.setText("");
                StringBuffer report = new StringBuffer();
                report.append("'");
                report.append(file.getName());
                report.append("' contains ");
                int msgCount = messageProcessor.getKb().getSentenceFactCount();
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
            if (!Utils.cogxml.equals(Utils.getExtension(file))) {
                file = new File(file.getAbsolutePath() + "." + Utils.cogxml);
            }
            try {
                messageProcessor.saveKb(file);
                setTitle(baseTitle + " - " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_saveKbAsMenuItemActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setTitle(baseTitle);
        noteProgramResponse(messageProcessor.getPersonality().getWelcomeMessage());
    }//GEN-LAST:event_formWindowOpened

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
        history.append(messageProcessor.getPersonality().getName() + ": ");
        history.append(message);
        history.append("\n\n");
        historyTextArea.setText(history.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        } catch (Throwable t) {
            System.err.println("Problem occurs when loading Default Look & Feel");
            return;
        }

        final Gui gui = new Gui();


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                gui.setVisible(true);
            }
        });
        Thread libLoader = new Thread(new Runnable() {

            public void run() {
                MorphAdornerWrapper.init();
                gui.processMessageButton.setEnabled(true);
            }
        });
        libLoader.run();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
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
    private org.purl.net.wonderland.engine.MessageProcessor messageProcessor;
    private javax.swing.JPanel messagesPanel;
    private javax.swing.JMenuItem openKbMenuItem;
    private javax.swing.JMenuItem openTextFileMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JButton processMessageButton;
    private javax.swing.JMenuItem saveKbAsMenuItem;
    private javax.swing.JButton showCGButton;
    private javax.swing.JFileChooser textFileChooser;
    private org.purl.net.wonderland.ui.TextFileFilter textFileFilter;
    // End of variables declaration//GEN-END:variables
}
