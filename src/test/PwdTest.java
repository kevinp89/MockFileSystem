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
import commands.Pwd;
import file.Directory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;

import static org.junit.Assert.*;


public class PwdTest extends Pwd {

  private FileSystemTree FST;

  @Before
  /**
   * setting up FileSystemTree to test pwd with
   */
  public void setUp() throws Exception {
    FST = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    Directory dir = new Directory("NewDir");
    Directory dir2 = new Directory("NewDir2");
    Directory dir3 = new Directory("NewDir3");
    FST.addFile(dir);
    FST.getChild(dir).addFile(dir2);
    FST.getChild(dir).getChild(dir2).addFile(dir3);
  }


  @After
  public void tearDown() throws Exception {
    FST = null;
  }

  @Test
  /**
   * Tests pwd with just the root of the FST available to print
   */
  public void testRoot() throws Exception {
    Pwd pwdFile = new Pwd(new ConcretePath(""));
    CD cd = new CD(new ConcretePath(""));
    cd.run(FST);
    String[] output = pwdFile.run(FST);
    String[] expected = new String[]{"/#", "/"};
    assertEquals(expected, output);
  }

  @Test
  /**
   * Tests pwd with just the root of the FST available to print
   */
  public void testOneNode() throws Exception {
    Pwd pwdFile = new Pwd(new ConcretePath(""));
    CD cd = new CD(new ConcretePath("/NewDir"));
    cd.run(FST);
    String[] output2 = pwdFile.run(FST);
    // Goes back to root
    CD cd2 = new CD(new ConcretePath(""));
    cd2.run(FST);
    String[] expected = new String[]{"/NewDir/#", "/NewDir"};
    assertEquals(expected, output2);
  }

  @Test
  /**
   * Tests pwd with 2 nodes of FST
   */
  public void testFewNodesDeep() throws Exception {
    Pwd pwdFile = new Pwd(new ConcretePath(""));
    CD cd2 = new CD(new ConcretePath("/NewDir/NewDir2/NewDir3"));
    cd2.run(FST);
    String[] output2 = pwdFile.run(FST);
    // Goes back to root
    CD cd3 = new CD(new ConcretePath(""));
    cd3.run(FST);
    String[] expected = new String[]{"/NewDir3/#", "/NewDir/NewDir2/NewDir3"};
    assertEquals(expected, output2);
  }

  @Test
  /**
   * Tests pwd going through 2 nodes and going back
   */
  public void testFewNodesDeepAndGoBack() throws Exception {
    Pwd pwdFile = new Pwd(new ConcretePath(""));
    CD cd2 = new CD(new ConcretePath("/NewDir/NewDir2/NewDir3"));
    cd2.run(FST);
    CD cd4 = new CD(new ConcretePath(".."));
    cd4.run(FST);
    String[] output2 = pwdFile.run(FST);
    String[] expected = new String[]{"/NewDir2/#", "/NewDir/NewDir2"};
    assertEquals(expected, output2);
  }

  @Test
  /**
   * Tests pwd by making it go back and forth between deep and back again
   */
  public void testDeepAndGoBackFew() throws Exception {
    Pwd pwdFile = new Pwd(new ConcretePath(""));
    CD cd2 = new CD(new ConcretePath("/NewDir/NewDir2/NewDir3"));
    cd2.run(FST);
    CD cd4 = new CD(new ConcretePath(""));
    cd4.run(FST);
    cd4.run(FST);
    String[] output2 = pwdFile.run(FST);
    String[] expected = new String[]{"/#", "/"};
    assertEquals(expected, output2);
  }

}
