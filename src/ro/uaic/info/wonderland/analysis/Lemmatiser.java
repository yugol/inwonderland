/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

/**
 *
 * @author Iulian
 */
class Lemmatiser {

    String getLemma(WTagging tagging) {
        return tagging.form.toLowerCase();
    }
}
