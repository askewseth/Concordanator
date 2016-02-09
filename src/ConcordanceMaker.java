
import ClassLibrary.Bookshelf;
import ClassLibrary.Concordance;
import java.util.Scanner;

public class ConcordanceMaker{
    public static void main(String[] args) {
        Bookshelf myBooks = new Bookshelf();
        System.out.println("The number of books are: " + myBooks.getNumberOfBooks());
        String[] bookTitle = myBooks.getAllBookTitles();
        for (String bookTitle1 : bookTitle) {
            System.out.println(bookTitle1);
        }
        Scanner search = new Scanner(System.in);
        System.out.print("Please enter a search term: ");
        String searchTerm = search.nextLine();
        String[] bookTitleBySearch = myBooks.getBookTitlesByKeyword(searchTerm);
        for (String bookTitleBySearch1 : bookTitleBySearch) {
            System.out.println(bookTitleBySearch1);
        }
        System.out.print("Please enter a book name: ");
        searchTerm = search.nextLine();
        String[] bookCreds = myBooks.pullBook(searchTerm);
        Concordance concord = new Concordance(bookCreds[0], bookCreds[1], bookCreds[2]);
        System.out.println(concord.makeConcordance());
    }
}