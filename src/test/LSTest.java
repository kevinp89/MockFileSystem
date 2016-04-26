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
import commands.Mkdir;
import exceptions.FileDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import commands.Mkdir;
import Tree.FileSystemTree;
import checkers.ValidityChecker;
import commands.LS;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;

import static org.junit.Assert.*;

public class LSTest extends LS {

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
      fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
      fst.addFile(new Directory("a"));
      fst.addFile(new Directory("kk"));
      fst.getChild(new Directory("a")).addFile(new Directory("c"));
      fst.addFile(new Directory("b"));
      LS ls = new LS(new ConcretePath("a"));
      String[] actual = ls.run(fst);
      String[] expected = new String[]{"/#", "c c"};
      assertEquals(expected, actual);
    }

    @Test
    /**
     * Tests LS run to see whether it works
     */
    public void testRunfalse() throws Exception{
      LS ls = new LS (new ConcretePath("b"));
      String[] actual = ls.run(fst);
      String[] expected = new String[]{"/#", ""};
      assertEquals(expected, actual);
    }

  /**
   * Tests LS error to see whether it was raised
   * @throws Exception
   */
  @Test(expected= FileDoesNotExistException.class)
  public void testErr() throws Exception{
    LS ls = new LS(new ConcretePath("/t"));
    ls.run(fst);
  }

  @Test
  /**
   * Testing LS by navigating it through a directory
   */
  public void testRunpath() throws Exception{
    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    fst.addFile(new Directory("a"));
    fst.addFile(new Directory("kk"));
    fst.getChild(new Directory("a")).addFile(new Directory("c"));
    fst.addFile(new Directory("b"));
    LS ls = new LS(new ConcretePath("/a"));
    String[] expected = {"/#", "c c c"};
    String[] output = ls.run(fst);

    assertEquals(expected, output);

  }
  @Test
  /**
   * Testing LS by creating complex path to navigate
   */
  public void testRunpathcomplex() throws Exception{
    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    fst.addFile(new Directory("a"));
    fst.getChild(new Directory("a")).addFile(new Directory("c"));
    Mkdir mk = new Mkdir(new ConcretePath("a/c/b"));
    mk.run(fst);
    LS ls = new LS(new ConcretePath("/a/c"));
    String[] expected = {"/#", "b"};
    String[] output = ls.run(fst);

    assertEquals(expected, output);

  }

}

