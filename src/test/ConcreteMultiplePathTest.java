package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcreteMultiplePath;
import path.ConcretePath;

import java.util.TreeMap;

import static org.junit.Assert.*;
//the class works but the memory address for ConcretePath in the Tree map
// differ from expected to actual, even thought the same.
public class ConcreteMultiplePathTest extends ConcreteMultiplePath {
    ConcreteMultiplePath path1;
    ConcreteMultiplePath path2;
    ConcreteMultiplePath path3;
    @Before public void setUp() throws Exception {
        path1 = new ConcreteMultiplePath("/a /b /d");
        path2 = new ConcreteMultiplePath("a/b/c/d /a/e/d/s/");
        path3 = new ConcreteMultiplePath("/a/b/c/d /..////e/q/");
    }

    @After public void tearDown() throws Exception {
        path1 = null;
        path2 = null;
        path3 = null;
    }

    @Test
    public void testInitialization(){
        TreeMap<Integer, ConcretePath> expected = new TreeMap<Integer,
            ConcretePath>();
        expected.put(0, new ConcretePath("/a"));
        expected.put(1, new ConcretePath("/b"));
        expected.put(2, new ConcretePath("/c"));
        assertEquals(expected, path1.getMultiplePaths());
    }

}
