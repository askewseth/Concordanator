/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zeroxff
 */
public class Main {
    public static void main(String[] args) {
//        Bookshelf myBooks = new Bookshelf();
//        myBooks.inventoryBooks();
//        System.out.println("The number of books are: " + myBooks.getNumberOfBooks());
//        String[] bookTitle = myBooks.getAllBookTitles();
//        for (int i = 0;i < bookTitle.length;i++){
//            System.out.println(bookTitle[i]);
//        }
        
        // Create a new repl
        CmdRepl repl = new CmdRepl(args);
        try {
            // Start the repl or gui depending on what is in the args
            // This is essentially the entry point into all the good stuff :^)
            repl.startRepl("");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
