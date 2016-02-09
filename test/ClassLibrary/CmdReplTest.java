/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

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
public class CmdReplTest {
    
    public CmdReplTest() {
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
     * Test of startRepl method, of class CmdRepl.
     */
    @Test
    public void testStartRepl() throws Exception {
        System.out.println("startRepl");
        String prompt = "";
        CmdRepl instance = null;
        instance.startRepl(prompt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadConcordance method, of class CmdRepl.
     */
    @Test
    public void testLoadConcordance() {
        System.out.println("loadConcordance");
        String conPath = "";
        CmdRepl instance = null;
        instance.loadConcordance(conPath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchCon method, of class CmdRepl.
     */
    @Test
    public void testSearchCon() {
        System.out.println("searchCon");
        String q = "";
        CmdRepl instance = null;
        String expResult = "";
        String result = instance.searchCon(q);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findArg method, of class CmdRepl.
     */
    @Test
    public void testFindArg() {
        System.out.println("findArg");
        String arg = "";
        CmdRepl instance = null;
        int expResult = 0;
        int result = instance.findArg(arg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
