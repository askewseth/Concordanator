/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  Tony made a change.
 */
package ClassLibrary;

/**
 *
 * @author Cory Sabol
 * 
 * This class depends on the Concordance class. 
 */
public class CmdInterface {
    
    private String[] args;
    
    // This class is going to need the String args[] from main
    public void handleArgs(String[] args) {
        this.args = args;
        
        /*
        Define the arguments that can be passed 
        */
    }
    
    /***
     * 
     * @param conPath The path or the name of the concordance to load.
     */
    public void loadConcordance(String conPath) {
        
    }
    
    /***
     * 
     * @param q
     * @return 
     * 
     * Should return the string representaion of the results of the search.
     */
    public String searchCon(String q) {
        
    }
    
    /***
     * 
     * @param arg
     * @return integer position of the argument
     */
    public int findArg(String arg) {
        
    }
}

