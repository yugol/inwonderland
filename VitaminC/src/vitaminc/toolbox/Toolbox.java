/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.toolbox;

import java.util.HashMap;
import java.util.Map;
import vitaminc.Main;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Toolbox {

    private static Map<String, Tool> tools = new HashMap<String, Tool>();

    static {
        try {
            ToolboxBuilder.buildToolbox(tools);
        } catch (Exception ex) {
            Main.handleException(ex);
        }
    }

    public static Tool get(String name) {
        return tools.get(name);
    }
}
