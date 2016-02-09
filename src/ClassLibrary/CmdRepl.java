/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  Tony made a change.
 */
package ClassLibrary;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cory Sabol - cssabol@uncg.edu
 * 
 * This class depends on the Concordance class. 
 */
public class CmdRepl implements Serializable {
    
    private String[] args;
    private boolean exit = false;
    private String prompt = "> ";
    private InputStreamReader reader = new InputStreamReader(System.in);
    private BufferedReader in = new BufferedReader(reader);
    private String cmdStr;
    private boolean conLoaded = false;
    
    /***
     * Constuctor for the repl.
     * @param args 
     */
    public CmdRepl() {
//       this.args = args;
//       
//       for (String arg : args) {
//           if (arg.equals("gui")) {
//              // Launch gui from here.
//           }
//           // Otherwise do continue and do nothing
//       }
        // The above code may actually be unecessary.
    }
    
    /***
     * Start the interactive interface to of the program.
     */
    public void startRepl(String prompt) throws IOException {
        
        if (!prompt.isEmpty() && prompt.length() < 5 && prompt.endsWith(" ")) {
            this.prompt = prompt;
        }
        
        /**
         * commands available:
            load <title | path>
            help
            listbooks [keyword]
            listcons [keyword]
            prompt => [title of con] >
            build <title | path> (possibly redundant)
            search <keyword>
            numoccur <keyword>
            numlines <title>
            surrwords <keyword, offset>
            phrase <phrase>
         */
        
        System.out.println("Welcome to Concordanator, the best darn concordance tool around ;^/)/n");
        // Main repl loop.
        do {
            
            System.out.print(this.prompt);
            cmdStr = in.readLine();            
            // Tokenize the command entered.
            ArrayList<String> cmd = tokenizeCmd(cmdStr, " ");
            
            System.out.println("Commands");
            for (String c : cmd) {
                System.out.println(c);
            }
            evalCmd(cmd);
            
        } while (!exit);
    }
    
    /***
     * Is this method actually necessary? Look into this more - cory
     * @param cmdStr - The command string to tokenize
     * @param delim - the delimiter to separate tokens by
     * @return ArrayList<String> of tokens
     */
    private ArrayList<String> tokenizeCmd(String cmdStr, String delim) {
        ArrayList<String> tknStrs = new ArrayList<String>();
        // Break the command str into tokens
        StringTokenizer tknz = new StringTokenizer(cmdStr);
        
        while (tknz.hasMoreElements()) {
            tknStrs.add(tknz.nextToken(delim));
        }
        
        return tknStrs;
    }
    
    private void evalCmd(ArrayList<String> cmd) {
        List<String> cmdArg = new ArrayList<String>();
        
        if (cmd.size() > 1) {
            cmdArg = cmd.subList(1, cmd.size());
        }
        System.out.println("Command Arg: " + cmdArg.toString());
        switch (cmd.get(0).toLowerCase()) {
            case "load" :
                // Find the text in here.
                break;
            case "help" :
                // handle 
                break;
            case "listbooks" :
                // handle
                break;
            case "listcons" :
                // handle
                break;
            case "exit" :
                exit = true;
            default :
                // Display help/usage
        }
        
        if (conLoaded) {
            // Allow checks for the commands that require 
        }
    }
    
    /***
     * 
     * @param conPath The path or the name of the concordance to load.
     */
    public void loadConcordance(String conPath) {
        throw new UnsupportedOperationException();
    }
    
    /***
     * 
     * @param q
     * @return 
     * 
     * Should return the string representaion of the results of the search.
     */
    public String searchCon(String q) {
        throw new UnsupportedOperationException();
    }
    
    /***
     * 
     * @param arg
     * @return integer position of the argument
     */
    public int findArg(String arg) {
        throw new UnsupportedOperationException();
    }
}

