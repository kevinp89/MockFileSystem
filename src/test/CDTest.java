// **********************************************************
// Assignment2:
// Student1: Vikki Wong
// CDF user_name: c3wongvi
// UT Student #: 1000639473
// Author: Vikki Wong
//
// Student2:Pierina Camarena
// CDF user_name: c5camare
// UT Student #: 1000155761
// Author: Pierina Camarena
//
// Student3: Shahin Imtiaz
// CDF user_name: c5imtiaz
// UT Student #:1001680987
// Author: Shahin Imtiaz
//
// Student4: Kevin
// CDF user_name: c4patelk
// UT Student #: 1001331267
// Author: Kevin
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package test;

import Tree.FileSystemTree;
import commands.CD;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;

import static org.junit.Assert.*;


public class CDTest extends CD {
    FileSystemTree fst;
    @Before
    public void setUp() throws Exception {
        fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    }

    @After
    public void tearDown() throws Exception {
        fst = null;
    }

    @Test
    public void testRunSimple() throws Exception {
        fst.addFile(new Directory("a"));
        CD cd = new CD(new ConcretePath("a"));
        String[] actual = cd.run(fst);
        String[] expected = new String[]{"/a/#", ""};
        assertEquals(expected, actual);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRunSimpleException() throws Exception {
        CD cd = new CD(new ConcretePath("c"));
        String[] actual = cd.run(fst);
    }

    @Test
    public void testRunSimplePath() throws Exception{
        CD cd = new CD(new ConcretePath("/a/"));
        String[] actual = cd.run(fst);
        String[] expected = new String[]{"/a/#", ""};
        assertEquals(expected, actual);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRunSimplePathErr() throws Exception{
        CD cd = new CD(new ConcretePath("/a/e"));
        cd.run(fst);
    }

    @Test
    public void testRunComplexPath() throws Exception{
        fst.addFile(new Directory("a"));
        fst.getChild(new Directory("a")).addFile(new Directory("b"));

        CD cd = new CD(new ConcretePath("/a/b"));
        String[] actual = cd.run(fst);
        String[] expected = new String[]{"/b/#", ""};
        assertEquals(expected, actual);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRunComplexPathErr() throws Exception{
        CD cd = new CD(new ConcretePath("/a/b/c"));
        cd.run(fst);
    }

    @Test
    public void testRunComplexDot() throws Exception{
        CD cd = new CD(new ConcretePath("./a"));
        String[] actual = cd.run(fst);
        String[] expected = new String[]{"/a/#", ""};
        assertEquals(expected, actual);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRunComplexDoterr() throws Exception{
        CD cd = new CD(new ConcretePath("./a"));
        String[] actual = cd.run(fst);
    }

    @Test
    public void testRunComplexAll()throws Exception{
        FileSystemTree c = fst.getChild(new Directory("a"));
        c.addFile(new Directory("c"));

        CD cd = new CD(new ConcretePath("./b/../b/../c/../../"));
        String[] actual = cd.run(fst);
        String[] expected = new String[]{"/#", ""};
        assertEquals(expected, actual);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRunComplexAllErr() throws Exception{
        CD cd = new CD(new ConcretePath("././../b/../../.."));
        cd.run(fst);
    }
}
