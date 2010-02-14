/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

/**
 *
 * @author Iulian
 */
public class EtoGleem extends Personality {

    @Override
    public String getWelcomeMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi!\n"
                + "Welcome to Wanderland the place where meaning has no boundary. "
                + "We are glad that you are interested in our world."
                + "I will be your facilitator. My name is ");
        sb.append(getFullName());
        sb.append(" but you can call me ");
        sb.append(getName());
        sb.append(".\nIs there anything you would preffer us to talk about?");
        return sb.toString();
    }

    @Override
    public String getFullName() {
        return "Eto Gleem";
    }

    @Override
    public String getName() {
        return "Eto";
    }

    @Override
    public String getId() {
        return "etogleem";
    }
}
