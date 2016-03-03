
import ClassLibrary.CmdRepl;
import GUI.ConcordanatorWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Concordanator{
    public static void main(String[] args) {
        
        try {
            if (args.length != 0 && args[0].equals("gui")) {
                // Launch the gui from here
                ConcordanatorWindow window = new ConcordanatorWindow();
                window.setVisible(true);
            } else if (args.length == 1 || args.length == 0) {
				CmdRepl repl = new CmdRepl();
                repl.startRepl("");
            } else {
                // Print out usage to term
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(Concordanator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
