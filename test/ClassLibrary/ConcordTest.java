/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zeroxff
 */
public class ConcordTest {
    
    public ConcordTest() {
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
     * Test of get_number_lines method, of class Concord.
     */
    @Test
    public void testGet_number_lines() throws Exception {
        System.out.println("get_number_lines");
        Concord instance = null;
        int expResult = 0;
        int result = instance.get_number_lines();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_file_lines method, of class Concord.
     */
    @Test
    public void testGet_file_lines() throws Exception {
        System.out.println("get_file_lines");
        Concord instance = null;
        String[] expResult = null;
        String[] result = instance.get_file_lines();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_concord method, of class Concord.
     */
    @Test
    public void testGet_concord() {
        System.out.println("get_concord");
        Concord instance = null;
        HashMap expResult = null;
        HashMap result = instance.get_concord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_file_words method, of class Concord.
     */
    @Test
    public void testGet_file_words() {
        System.out.println("get_file_words");
        Concord instance = null;
        ArrayList[] expResult = null;
        ArrayList[] result = instance.get_file_words();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_unique_words method, of class Concord.
     */
    @Test
    public void testGet_unique_words() {
        System.out.println("get_unique_words");
        Concord instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.get_unique_words();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
