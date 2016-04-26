package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;
import path.PathIterator;

import java.util.TreeMap;

import static org.junit.Assert.*;


public class ConcretePathTest extends ConcretePath {
    ConcretePath path1;
    ConcretePath path2;
    ConcretePath path3;
    ConcretePath path4;

    @Before public void setUp() throws Exception {
        path1 = new ConcretePath("");
        path2 = new ConcretePath("a/b/c/d");
        path3 = new ConcretePath("/a/b/c/d");
        path4 = new ConcretePath("dir1 dir2 dir3");
    }

    @After public void tearDown() throws Exception {
        path1 = null;
        path2 = null;
        path3 = null;
        path4 = null;
    }

    @Test
    public void  testInitialization1(){
        TreeMap<Integer, String> expected = new TreeMap<Integer, String>();
        assertEquals(expected, path1.getPath());
    }

    @Test
    public void testInitialization2(){
        TreeMap<Integer, String> expected = new TreeMap<Integer, String>();
        expected.put(0, "a");
        expected.put(1, "b");
        expected.put(2, "c");
        expected.put(3, "d");
        assertEquals(expected, path2.getPath());
    }

    @Test
    public void testInitialization3(){
        TreeMap<Integer, String> expected = new TreeMap<Integer, String>();
        expected.put(0, "/");
        expected.put(1, "a");
        expected.put(2, "b");
        expected.put(3, "c");
        expected.put(4, "d");
        assertEquals(expected, path3.getPath());
    }

    @Test
    public void testInitialization4(){
        TreeMap<Integer, String> expected = new TreeMap<Integer, String>();
        expected.put(0, "dir1");
        expected.put(1, "dir2");
        expected.put(2, "dir3");
        assertEquals(expected, path4.getPath());
    }

    @Test
    public void testNullPath1(){
        boolean expected = true;
        assertEquals(expected, path1.nullPath());
    }

    @Test
    public void testNotNullpath(){
        boolean expected = false;
        assertEquals(expected, path2.nullPath());
    }

    @Test
    public void testRootDirExists1(){
        boolean expected = false;
        assertEquals(expected, path1.rootDirExists());
    }

    @Test
    public void testRootDirExsits2(){
        boolean expected = false;
        assertEquals(expected, path2.rootDirExists());
    }

    @Test
    public void testRootDirExsits3(){
        boolean expexted = true;
        assertEquals(expexted, path3.rootDirExists());
    }

    @Test
    public void testGetUIPath1(){
        String expexted = "";
        assertEquals(expexted, path1.getUIPath());
    }

    @Test
    public void testGetUIPath2(){
        String expected = "a/b/c/d";
        assertEquals(expected, path2.getUIPath());
    }

    @Test
    public void testGetUIPath3(){
        String expected = "/a/b/c/d";
        assertEquals(expected, path3.getUIPath());
    }

    @Test
    public void testGetUIPath4(){
        String expected = "dir1 dir2 dir3";
        assertEquals(expected, path4.getUIPath());
    }

    @Test
    public void testIteratorPath1(){
        TreeMap<Integer, String> pathI = new TreeMap<Integer, String>();
        PathIterator expected = new PathIterator(pathI);
        assertEquals(expected, path1.iterator());
    }

    @Test
    public void testIteratorPath2(){
        TreeMap<Integer, String> pathI = new TreeMap<Integer, String>();
        pathI.put(0, "a");
        pathI.put(1, "b");
        pathI.put(2, "c");
        pathI.put(3, "d");
        PathIterator expected = new PathIterator(pathI);
        assertEquals(expected, path2.iterator());
    }

    @Test
    public void testIteratorPath3(){
        TreeMap<Integer, String> pathI = new TreeMap<Integer, String>();
        pathI.put(0, "/");
        pathI.put(1, "a");
        pathI.put(2, "b");
        pathI.put(3, "c");
        pathI.put(4, "d");
        PathIterator expected = new PathIterator(pathI);
        assertEquals(expected, path3.iterator());
    }

    @Test
    public void testIteratorPath4(){
        TreeMap<Integer, String> pathI = new TreeMap<Integer, String>();
        pathI.put(0, "dir1");
        pathI.put(1, "dir2");
        pathI.put(2, "dir3");
        PathIterator expected = new PathIterator(pathI);
        assertEquals(expected, path4.iterator());

    }
}
