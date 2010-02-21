/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

/**
 *
 * @author Iulian
 */
public class CodeTimer {

    private long t0;
    private long t1;
    private String msg;

    public CodeTimer(String msg) {
        this.msg = msg;
        System.err.println("Starting " + msg + " ...");
        t0 = System.currentTimeMillis();
    }

    public void stop() {
        t1 = System.currentTimeMillis();
        double seconds = ((double) (t1 - t0)) / 1000;
        System.err.println("Done " + msg + " in [" + seconds + "] seconds");
    }
}
