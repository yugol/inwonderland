/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.data;

import java.util.List;

/**
 *
 * @author Iulian
 */
public interface Harvester {

    public List<Article> harvest(String topic);

    public String getId();

    public String getName();

    public boolean isActive();

    public void setActive(boolean active);
}
