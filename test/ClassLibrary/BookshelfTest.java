/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cory Sabol
 */
public class BookshelfTest {
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    private String winPath = "src\\books";
    private String nixPath = "src//books";
    
    public BookshelfTest() {
    }
    
    public static boolean isWindows() {

    	return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

    	return (OS.indexOf("mac") >= 0);

    }

    public static boolean isNix() {

	return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNumberOfBooks method, of class Bookshelf.
     */
    @Test
    public void testGetNumberOfBooks() {
        System.out.println("getNumberOfBooks");
        Bookshelf instance = new Bookshelf();
        int expResult = 20;
        int result = instance.getNumberOfBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBookTitles method, of class Bookshelf.
     */
    @Test
    public void testGetAllBookTitles() {
        System.out.println("getAllBookTitles");
        Bookshelf instance = new Bookshelf();
        
        // Create the list of titles to be expected
        File folder = null;
        
        if (isWindows()) {
            // Adhere to windows paths
            folder = new File(winPath);
        } else if (isNix()) {
            // Adhere to *nix paths
            folder = new File(nixPath);            
        }
        
        // get the files in books dir
        File[] files = folder.listFiles();
        
        String[] expResult = new String[20];
        
        // Create the exp results
        System.out.println("Books: ");
        for (int i = 0; i < files.length; i++) {
            // Strip .txt from name
            String title = files[i].getName().substring(0,
                    files[i].getName().length() - 4);
            
            expResult[i] = title;
            System.out.println(expResult[i]);
        }
                
        String[] result = instance.getAllBookTitles();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getBookTitlesByKeyword method, of class Bookshelf.
     */
    @Test
    public void testGetBookTitlesByKeyword() {
        System.out.println("getBookTitlesByKeyword");
        String keyword = "";
        Bookshelf instance = new Bookshelf();
        String[] expResult = null;
        String[] result = instance.getBookTitlesByKeyword(keyword);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inventoryBooks method, of class Bookshelf.
     */
    @Test
    public void testInventoryBooks() {
        System.out.println("inventoryBooks");
        Bookshelf instance = new Bookshelf();
        instance.inventoryBooks();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
