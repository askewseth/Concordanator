/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  Tony made a change.
 */
package ClassLibrary;

import java.io.*;

/**
 *
 * @author Cory Sabol
 * 
 * This class depends on the Concordance class. 
 */
public class CmdRepl {
    
    private String[] args;
    private boolean exit = false;
    private String prompt = "> ";
    private BufferedReader reader = new BufferedReader();
    
    /***
     * Constuctor for the repl.
     * @param args 
     */
    public CmdRepl(String[] args) {
       this.args = args;
       
       for (String arg : args) {
           if (arg.equals("gui")) {
              // Launch gui from here.
           }
           // Otherwise do continue and do nothing
       }
    }
    
    /***
     * Start the interactive interface to of the program.
     */
    public void startRepl(String prompt) {
        this.args = args;
        
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
            reader.
            
        } while (!exit);
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

