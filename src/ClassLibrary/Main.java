/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

/**
 *
 * @author zeroxff
 */
public class Main {
    public static void main(String[] args) {
        Bookshelf myBooks = new Bookshelf();
        myBooks.inventoryBooks();
        System.out.println("The number of books are: " + myBooks.getNumberOfBooks());
        String[] bookTitle = myBooks.getAllBookTitles();
        for (int i = 0;i < bookTitle.length;i++){
            System.out.println(bookTitle[i]);
        }
    }
}
