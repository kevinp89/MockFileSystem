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
import commands.Cat;
import commands.Pwd;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.RWFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcreteMultiplePath;
import path.ConcretePath;

import static org.junit.Assert.*;


public class CatTest extends Cat {

  private FileSystemTree fst;
  private FileSystemTree currentDir;

  @Before
  /**
   * setting up a file with unexpected content
   */
  public void setUp() throws Exception {
    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    RWFile file = new RWFile("outfile.txt");
    file.add("string");
    fst.addFile(file);
    RWFile longFile = new RWFile("longFile.txt");
    longFile.add("Long string, with a very long string; colons are included.");
    fst.addFile(longFile);
    RWFile weirdFile = new RWFile("weirdFile.txt");
    weirdFile.add("!@#$%^&*()");
    fst.addFile(weirdFile);
  }

  @After
  public void tearDown() {
    fst = null;
  }

  @Test
  /**
   * Testing a single file with cat
   */
  public void testOneFile() throws Exception {
    Cat catFile = new Cat(new ConcretePath("outfile.txt"));
    String[] output = catFile.run(fst);
    String[] expected = new String[]{"/#", "string\n"};
    assertEquals(expected, output);
  }

  @Test
  /**
   * Testing cat with a file of longer more complex content (including colons)
   */
  public void testOneLongFile() throws Exception {
    Cat catFile = new Cat(new ConcretePath("longFile.txt"));
    String[] output = catFile.run(fst);
    String[] expected = new String[]{"/#", "Long string, with a very long "
            + "string; colons are included.\n"};
    assertEquals(expected, output);
  }

  @Test
  /**
   * Testing cat with 2 txt files with differing content
   */
  public void test2Files() throws Exception {
    Cat catFile = new Cat(new ConcreteMultiplePath("longFile.txt outfile.txt"));
    String[] output = catFile.run(fst);
    String[] expected = new String[]{"/#", "Long string, with a very long "
            + "string; colons are included.\nstring\n"};
    assertEquals(expected, output);
  }

  @Test
  /**
   * Testing Cat with 3 files of differing content
   */
  public void test3Files() throws Exception {
    Cat catFile = new Cat(new ConcreteMultiplePath("longFile.txt outfile.txt "
            + "weirdFile.txt"));
    String[] output = catFile.run(fst);
    String[] expected = new String[]{"/#", "Long string, with a very long "
            + "string; colons are included.\nstring\n" + "!@#$%^&*()\n"};
    assertEquals(expected, output);
  }
}
