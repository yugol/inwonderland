/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DrawingWindow.java
 *
 */
package vitaminc.ui;

import java.awt.event.KeyEvent;
import vitaminc.Controller;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class DrawingWindow extends javax.swing.JFrame {

    public static final String DEFAULT_TITLE = "Drawing";
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /** Creates new form DrawingWindow */
    public DrawingWindow() {
        initComponents();
        setTitle(DEFAULT_TITLE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setIconImage(Resources.drawingWindowIcon);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        controller.setDrawingWindowVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        handleKeyPressed(evt);
    }//GEN-LAST:event_formKeyPressed

    public void clickAt(double x, double y) {
        System.out.println(x + " " + y);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new DrawingWindow().setVisible(true);
            }
        });
    }

    public void handleKeyPressed(KeyEvent evt) {
        controller.checkAndHandleShortcut(evt, this);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}