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
    public void testGet_number_lines_0args() throws Exception {
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
        HashMap<String, Concord.Word> expResult = null;
        HashMap<String, Concord.Word> result = instance.get_concord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_concord_nocommon method, of class Concord.
     */
    @Test
    public void testGet_concord_nocommon() {
        System.out.println("get_concord_nocommon");
        Concord instance = null;
        HashMap<String, Concord.Word> expResult = null;
        HashMap<String, Concord.Word> result = instance.get_concord_nocommon();
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

    /**
     * Test of get_list_lines method, of class Concord.
     */
    @Test
    public void testGet_list_lines() {
        System.out.println("get_list_lines");
        String target_word = "";
        Concord instance = null;
        ArrayList<Integer> expResult = null;
        ArrayList<Integer> result = instance.get_list_lines(target_word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_number_lines method, of class Concord.
     */
    @Test
    public void testGet_number_lines_String() {
        System.out.println("get_number_lines");
        String target_word = "";
        Concord instance = null;
        int expResult = 0;
        int result = instance.get_number_lines(target_word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_number_occurances method, of class Concord.
     */
    @Test
    public void testGet_number_occurances() {
        System.out.println("get_number_occurances");
        String target_word = "";
        Concord instance = null;
        int expResult = 0;
        int result = instance.get_number_occurances(target_word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_all_apperances method, of class Concord.
     */
    @Test
    public void testGet_all_apperances() {
        System.out.println("get_all_apperances");
        Concord instance = null;
        HashMap<String, Integer> expResult = null;
        HashMap<String, Integer> result = instance.get_all_apperances();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_apperance_rank method, of class Concord.
     */
    @Test
    public void testGet_apperance_rank() {
        System.out.println("get_apperance_rank");
        String target_word = "";
        Concord instance = null;
        int expResult = 0;
        int result = instance.get_apperance_rank(target_word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_apperance_ranks method, of class Concord.
     */
    @Test
    public void testGet_apperance_ranks() {
        System.out.println("get_apperance_ranks");
        Concord instance = null;
        HashMap<String, Integer> expResult = null;
        HashMap<String, Integer> result = instance.get_apperance_ranks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_common_words method, of class Concord.
     */
    @Test
    public void testGet_common_words() throws Exception {
        System.out.println("get_common_words");
        Concord instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.get_common_words();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
