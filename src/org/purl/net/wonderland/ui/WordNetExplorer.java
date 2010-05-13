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
 * WordNetExplorer.java
 *
 * Created on Mar 26, 2010, 9:32:53 AM
 */
package org.purl.net.wonderland.ui;

import org.purl.net.wonderland.nlp.resources.SynsetFormatter;
import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.CoGuiWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WordNetExplorer extends javax.swing.JFrame {

    public static final WordNetExplorer instance = new WordNetExplorer();
    private static final String sensesNodeName = "Senses";
    // private File kbFile = new File(/*System.getProperty("TMP"),*/"_WordNetExplorer_temp_.cogxml");
    private SynsetFormatter userData = null;

    /** Creates new form WordNetExplorer */
    public WordNetExplorer() {
        initComponents();
        setSize(800, 600);
        fillResponse(null);

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

        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        sensesSplitPane = new javax.swing.JSplitPane();
        sensesScrollPane = new javax.swing.JScrollPane();
        sensesTree = new javax.swing.JTree();
        explanationTabs = new javax.swing.JTabbedPane();
        sensePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        senseTextPane = new javax.swing.JTextPane();
        searchButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setTitle("WordNet Explorer");

        searchLabel.setText("Search item:");

        searchTextField.setPreferredSize(new java.awt.Dimension(300, 20));
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyTyped(evt);
            }
        });

        sensesSplitPane.setDividerLocation(200);

        sensesTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sensesTreeMousePressed(evt);
            }
        });
        sensesTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                sensesTreeValueChanged(evt);
            }
        });
        sensesScrollPane.setViewportView(sensesTree);

        sensesSplitPane.setLeftComponent(sensesScrollPane);

        senseTextPane.setContentType("text/html");
        senseTextPane.setEditable(false);
        jScrollPane1.setViewportView(senseTextPane);

        javax.swing.GroupLayout sensePanelLayout = new javax.swing.GroupLayout(sensePanel);
        sensePanel.setLayout(sensePanelLayout);
        sensePanelLayout.setHorizontalGroup(
            sensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sensePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );
        sensePanelLayout.setVerticalGroup(
            sensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sensePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        explanationTabs.addTab("Sense", sensePanel);

        sensesSplitPane.setRightComponent(explanationTabs);

        searchButton.setText(">");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As ...");
        fileMenu.add(saveAsMenuItem);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sensesSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLabel)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sensesSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void sensesTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sensesTreeMousePressed
//        TreePath path = sensesTree.getPathForLocation(evt.getX(), evt.getY());
//        try {
//            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
//            SynsetFormatter sd = (SynsetFormatter) node.getUserObject();
//            senseTextPane.setVisible(true);
//            senseTextPane.setText(sd.getSenseHTML());
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_sensesTreeMousePressed

    private void searchTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyTyped
        int keyCode = evt.getKeyChar();
        // JOptionPane.showMessageDialog(this, keyCode);
        if (keyCode == 10) {
            fillResponse(searchTextField.getText());
        }
    }//GEN-LAST:event_searchTextFieldKeyTyped

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        fillResponse(searchTextField.getText());
    }//GEN-LAST:event_searchButtonActionPerformed

    private void sensesTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_sensesTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
        displaySenses(node);
    }//GEN-LAST:event_sensesTreeValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        W.getVerbNetDataFolder();

        try {
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        } catch (Throwable t) {
            System.err.println("Problem occurs when loading Default Look & Feel");
            W.handleException(t);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WordNetExplorer().setVisible(true);
            }
        });

        WordNetWrapper.offsetKeyAlphaToSenseKey("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTabbedPane explanationTabs;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel sensePanel;
    private javax.swing.JTextPane senseTextPane;
    private javax.swing.JScrollPane sensesScrollPane;
    private javax.swing.JSplitPane sensesSplitPane;
    private javax.swing.JTree sensesTree;
    // End of variables declaration//GEN-END:variables

    private void fillResponse(String item) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(sensesNodeName, true);

        if (item != null) {
            item = item.trim();
            item = item.replaceAll("\\s+", "_");

            if (WordNetWrapper.isSenseKey(item)) {
                Synset synset = WordNetWrapper.lookup(item);
                Synset[] senses = new Synset[]{synset};
                item = synset.getWord(0).getLemma();
                POS pos = synset.getPOS();
                if (pos == POS.NOUN) {
                    addSenses(top, "Noun", item, senses);
                } else if (pos == POS.ADJECTIVE) {
                    addSenses(top, "Adjective", item, senses);
                } else if (pos == POS.ADVERB) {
                    addSenses(top, "Adverb", item, senses);
                } else if (pos == POS.VERB) {
                    addSenses(top, "Verb", item, senses);
                }
            } else {
                searchTextField.setText(item);
                addSenses(top, "Noun", item, WordNetWrapper.getSenses(item, POS.NOUN));
                addSenses(top, "Verb", item, WordNetWrapper.getSenses(item, POS.VERB));
                addSenses(top, "Adjective", item, WordNetWrapper.getSenses(item, POS.ADJECTIVE));
                addSenses(top, "Adverb", item, WordNetWrapper.getSenses(item, POS.ADVERB));
            }
        }

        TreeModel model = new DefaultTreeModel(top);
        sensesTree.setModel(model);
        expandAll(sensesTree, new TreePath(model.getRoot()), true);
        displaySenses(top);
    }

    private void displaySenses(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();

        userData = null;

        if (userObject instanceof SynsetFormatter) {

            userData = (SynsetFormatter) userObject;
            senseTextPane.setText(userData.getSenseHTML());

            // add hypernym
            Synset hypernym = userData.getWrapper().getHypernym();
            if (hypernym != null) {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(new SynsetFormatter(-1, null, hypernym));
                node.add(node2);
                expandAll(sensesTree, new TreePath(node.getPath()), true);
            }

        } else {

            if (userObject instanceof String) {
                String userString = (String) userObject;
                if (userString.equals(sensesNodeName)) {
                    StringBuilder html = new StringBuilder();
                    for (int i = 0; i < node.getChildCount(); i++) {
                        html.append(getPOSOverviewHTML((DefaultMutableTreeNode) node.getChildAt(i)));
                        html.append("<br/>");
                    }
                    senseTextPane.setText(html.toString());
                } else {
                    senseTextPane.setText(getPOSOverviewHTML(node));
                }
            }
        }

        senseTextPane.setCaretPosition(0);
    }

    private void addSenses(DefaultMutableTreeNode top, String nodeName, String item, Synset[] senses) {
        if (senses != null) {
            if (nodeName != null) {
                nodeName += " (" + senses.length + " sense" + ((senses.length > 1) ? ("s") : ("")) + ")";
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
                top.add(node);
                top = node;
            }
            for (int i = 0; i < senses.length; i++) {
                Synset synset = senses[i];
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(new SynsetFormatter(i, item, synset));
                top.add(node);
            }
        }
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    private String getPOSOverviewHTML(DefaultMutableTreeNode root) {
        StringBuilder html = new StringBuilder();
        html.append("<br/>");
        String[] chunks = ((String) root.getUserObject()).split(" ", 2);
        String pos = chunks[0].toLowerCase();
        String senseCount = chunks[1].substring(1, chunks[1].length() - 1);
        html.append("The <u>" + pos + "</u> <b>" + searchTextField.getText() + "</b> has " + senseCount + ":");
        html.append("<br/><br/>");
        html.append("<table border='0' cellpadding='0'>");
        for (int i = 0; i < root.getChildCount(); i++) {
            SynsetFormatter synsetData = (SynsetFormatter) ((DefaultMutableTreeNode) root.getChildAt(i)).getUserObject();
            html.append("<tr>");
            html.append("<td valign='top'>");
            html.append((i + 1) + ".&nbsp;");
            html.append("</td>");
            html.append("<td>");
            html.append(synsetData.getWordsHTML());
            html.append(" -- ");
            for (String item : synsetData.getWrapper().getExplanations()) {
                html.append("<br/>(");
                html.append(item);
                html.append(")");
            }
            html.append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }
}
