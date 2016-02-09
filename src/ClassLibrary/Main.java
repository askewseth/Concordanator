/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zeroxff
 */
public class Main {
    
    public static void main(String[] args) {
//        Bookshelf myBooks = new Bookshelf();
//        System.out.println("The number of books are: " + myBooks.getNumberOfBooks());
//        String[] bookTitle = myBooks.getAllBookTitles();
//        for (String bookTitle1 : bookTitle) {
//            System.out.println(bookTitle1);
//        }
//        Scanner search = new Scanner(System.in);
//        System.out.print("Please enter a search term: ");
//        String searchTerm = search.nextLine();
//        String[] bookTitleBySearch = myBooks.getBookTitlesByKeyword(searchTerm);
//        for (String bookTitleBySearch1 : bookTitleBySearch) {
//            System.out.println(bookTitleBySearch1);
//        }
        
        // Start the repl from here
        CmdRepl repl = new CmdRepl();
        
        // Test out get all book titles
        Bookshelf shelf = new Bookshelf();
        String[] titles = shelf.getAllBookTitles();
        
        System.out.println("Titles: ====================");
        for (String s : titles) {
            System.out.println(s);
        }
        
        try {
            
            if (args.length != 0 && args[1].equals("gui")) {
                // Launch the gui from here
            } else if (args.length == 1 || args.length == 0) {
                repl.startRepl("");
            } else {
                // Print out usage to term
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
