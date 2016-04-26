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
import commands.Command;
import commands.Mkdir;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;

import static org.junit.Assert.*;

public class MkdirTest extends Mkdir {
    private FileSystemTree fst;

    @Before
    public void setUp() throws Exception {
        System.out.println("in setup");
        fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
        Command.setCurrDirectory(fst);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("in tear");
        fst = fst.setFileSystemReferenceNull();
        Command.setCurrentDirectoryNone();
    }

    @Test
    public void testSimple()throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("a"));
        FileSystemTree newFst = fst;
        mk.run(newFst);
        assertEquals(true, mk.getCurrentDirectory().hasChild("a"));
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testSimpleErr() throws FileDoesNotExistException{
        Mkdir mk = new Mkdir(new ConcretePath("/"));
        mk.run(fst);
    }

    @Test
    public void testRootPath()throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/a"));
        mk.run(fst);
        assertEquals(true, fst.hasChild("a"));
    }

    @Test(expected = FileDoesNotExistException.class)
    public void testRootPathErr() throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/b/f"));
        mk.run(fst);
    }

    @Test
    public void testComplexPath1()throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/a"));
        mk.run(fst);

        //testing for complex path
        Mkdir mk2 = new Mkdir(new ConcretePath("/a/../e"));
        mk2.run(fst);

        assertEquals(true, fst.hasChild("e"));
    }

    @Test (expected = FileDoesNotExistException.class)
    public void testComplexPathErr()throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/a"));
        mk.run(fst);

        Mkdir mk2 = new Mkdir(new ConcretePath("/a/../../e"));
        mk2.run(fst);
    }

    @Test
    public void testComplexPath2() throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/a"));
        mk.run(fst);

        Mkdir mk2 = new Mkdir(new ConcretePath("/a/b"));
        mk2.run(fst);

        assertEquals(true, fst.getSubFile("a").hasChild("b"));
    }

    @Test (expected = FileDoesNotExistException.class)
    public void testComplexPath2Err() throws Exception{
        Mkdir mk = new Mkdir(new ConcretePath("/a"));
        mk.run(fst);

        Mkdir mk2 = new Mkdir(new ConcretePath("/a/b/c"));
        mk2.run(fst);
    }
}

