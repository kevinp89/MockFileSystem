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
import commands.Pushd;
import exceptions.FileDoesNotExistException;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;

import static org.junit.Assert.*;

public class PushdTest extends Pushd {
  FileSystemTree fst;

  @Before
  /**
   * Setting up a new FileSystemTree to test on
   */
  public void setUp() throws Exception {
    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
  }

  @After

  public void tearDown() throws Exception {
    fst = null;
  }

  @Test
  /**
   *
   * Simple test for Pushd with single file
   */
  public void testRunSimple() throws Exception {
    fst.addFile(new Directory("a"));
    Pushd p = new Pushd(new ConcretePath("a"));
    String[] actual = p.run(fst);
    String[] expected = new String[]{"/a/#", ""};
    assertEquals(actual, expected);
  }

  /**
   * Testing a simple error for single push
   * @throws Exception
   */
  @Test(expected = FileDoesNotExistException.class)
  public void testRunSimpleErr() throws Exception {
    Pushd p = new Pushd(new ConcretePath("b"));
    p.run(fst);
  }

  @Test
  /**
   * Testing pushd with a path
   */
  public void testRunComplex1() throws Exception {
    fst.getChild(new Directory("a")).addFile(new Directory("c"));
    Pushd p = new Pushd(new ConcretePath("/a/c"));
    String[] actual = p.run(fst);
    String[] expected = new String[]{"/c/#", ""};
    assertEquals(actual, expected);
  }

  @Test(expected = FileDoesNotExistException.class)
  /**
   * Testing pushd for errors
   */
  public void testRunComplex1Err() throws Exception {
    Pushd p = new Pushd(new ConcretePath("/../c"));
    p.run(fst);
  }
}
