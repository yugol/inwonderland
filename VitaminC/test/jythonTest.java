
import java.io.IOException;
import jline.ConsoleReader;
import org.python.util.InteractiveConsole;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class jythonTest {

    public static void main(String[] args) throws IOException {
        //Py.getSystemState().ps1;
        InteractiveConsole ic = new InteractiveConsole();
        ic.interact();
        ConsoleReader console = new ConsoleReader();
        String str = console.readLine(">>> ");
    }
}
