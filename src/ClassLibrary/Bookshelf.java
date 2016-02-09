package ClassLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Bookshelf {

    // Private class fields
    private int numElements;
    private Book header;
    private final String WINDOWS_BOOK_DIRECTORY = "src\\books";
    private final String LINUX_BOOK_DIRECTORY = "books";
    private String OSName;

    // Class constructor
    public Bookshelf() {
        this.header = new Book();  // Creates empty node for header.
        this.numElements = 0;
        this.OSName = System.getProperty("os.name").substring(0, 3);
        this.inventoryBooks();
    }

    // Class methods
    public int getNumberOfBooks() {
        return numElements;
    }

    public String[] getAllBookTitles() {
        String temp[] = new String[this.numElements];
        int index = 0;
        Book curr = header.next;
        while (curr != header) {
            temp[index] = curr.getTitle() + " by " + curr.getAuthor();
            index++;
            curr = curr.next;
        }
        String[] temp2 = new String[index];
        System.arraycopy(temp, 0, temp2, 0, index);
        return temp2;
    }

    public String[] getBookTitlesByKeyword(String keyword) {
        String[] temp = new String[this.numElements];
        int index = 0;
        Book curr = header.next;
        while (curr != header) {
            if (curr.getTitle().toLowerCase().contains(keyword.toLowerCase()) || curr.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                temp[index] = curr.getTitle() + " by " + curr.getAuthor();
                index++;
            }
            curr = curr.next;
        }
        String[] temp2 = new String[index];
        System.arraycopy(temp, 0, temp2, 0, index);

        return temp2;
    }

    public void inventoryBooks() {
        String fileDirectory;
        if (this.OSName.equals("Win")) {
            fileDirectory = this.WINDOWS_BOOK_DIRECTORY;
        } else {
            fileDirectory = this.LINUX_BOOK_DIRECTORY;
        }
        if (new File(fileDirectory).isDirectory()) {
            File dir = new File(fileDirectory);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    // Strip the file extension and chop the file names into a String array using the keyword 'by'
                    if (child.getName().substring(child.getName().length() - 4).equals(".txt")) {    // Exclude files that do not have a .txt file extention
                        String[] pieces = this.retrieveCredentialsFromFile(child.getName());
                        this.addBook(pieces[0], pieces[1], child.getName());
                    }
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }
        }
    }
    
    public String[] pullBook(String book){
        String[] temp = new String[3];
        Book curr = header.next;
        while (curr != header) {
            if (book.contains(curr.getTitle())){
                temp[0] = curr.getTitle();
                temp[1] = curr.getAuthor();
                if (this.OSName.equals("Win")){
                    temp[3] = WINDOWS_BOOK_DIRECTORY + curr.getFileName();
                    break;
                }
                else{
                    temp[3] = LINUX_BOOK_DIRECTORY + curr.getFileName();
                    break;
                }               
            }
            
            curr = curr.next;
        }
        
        return temp;
    }

    private boolean addBook(String titl, String auth, String fn) {
        boolean success = false;
        Book curr = header.next;  // Create a pointer named curr.
        Book add = new Book(titl, auth, fn);   // Create a DNode with the data.

        // if the list is empty, then just insert the item as the first item in the list.
        if (curr == header) {
            // Just attach the newly created node to the header.
            add.next = header;
            add.prev = header;
            header.next = add;
            header.prev = add;
            numElements = 1;
            success = true;
        } // However, if the list is not empty, iterate through the list until you find the correct
        // location to add the new item.
        else {
            // Iterate through the list until the proper location to place the data has been reached.
            while (curr != header) {
                int result = add.title.compareTo(curr.title);
                if (result < 0) {
                    // Once the proper location has been found, add the new item.
                    curr.prev.next = add;
                    add.prev = curr.prev;
                    add.next = curr;
                    curr.prev = add;
                    numElements++;
                    success = true;
                    curr = header;  // once the item has been added, break the loop.
                } else if (curr.next == header) {
                    // If the proper location is at the end of the list, add it here.
                    curr.next.prev = add;
                    curr.next = add;
                    add.prev = curr;
                    add.next = header;
                    numElements++;
                    success = true;
                    curr = header;  // once the item has been added, break the loop.
                } else {
                    curr = curr.next; // Else, advance the pointer to the next location and try there.
                }
            }
        }
        return success;
    }

    private String[] retrieveCredentialsFromFile(String fn) {
        String[] temp = new String[2];
        File bookFile;
        BufferedReader fileIn;
        if (this.OSName.equals("Win")) {
            bookFile = new File(this.WINDOWS_BOOK_DIRECTORY + "\\" + fn);
        } else {
            bookFile = new File(this.LINUX_BOOK_DIRECTORY + "/" + fn);
        }
        if (bookFile.isFile()) {
            try {
                fileIn = new BufferedReader(new FileReader(bookFile));
                int parts = 0;
                while (fileIn.ready() && parts != 2) {
                    String line = fileIn.readLine();
                    if (line.length() >= 7) {
                        if (line.substring(0, 7).contains("Title:")) {
                            temp[0] = line.substring(7, line.length());
                            parts++;
                        }
                        if (line.substring(0, 8).contains("Author:")) {
                            temp[1] = line.substring(8, line.length());
                            parts++;
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Bookshelf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Bookshelf.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            // Do something if file does not exist.
        }

        return temp;
    }

    // Private inner Book class.
    private final class Book {

        // Private class fields
        private final String title, author, fileName;
        private Book next, prev;

        // Private class constructor for creating the header
        private Book() {
            this.title = null;
            this.author = null;
            this.fileName = null;
            next = prev = this;
        }

        // Private class constructor
        private Book(String titl, String auth, String fn) {
            this.title = titl;
            this.author = auth;
            this.fileName = fn;
            next = prev = this;
        }

        public String getTitle() {
            return this.title;
        }

        public String getAuthor() {
            return this.author;
        }

        public String getFileName() {
            return this.fileName;
        }
    }
}
