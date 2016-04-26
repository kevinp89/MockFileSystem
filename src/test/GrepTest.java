package test;

import Tree.FileSystemTree;
import commands.Command;
import commands.Grep;
import commands.Mkdir;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.File;
import file.RWFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;
import path.GrepPath;
import sun.management.FileSystem;

import static org.junit.Assert.*;


public class GrepTest extends Grep {

  private FileSystemTree fst;

  @Before
  public void setUp() throws Exception {

    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    Command.setCurrDirectory(fst);
    RWFile file1 = new RWFile("file1");
    RWFile file2 = new RWFile("file2");
    RWFile file3 = new RWFile("file3");
    RWFile file4 = new RWFile("file4");

    file1.add("this IS text for file1");
    file2.add("this is text for file2");
    file3.add("this is text for file3");
    file4.add("this is inside FILE4");

    fst.addFile(file1);
    fst.addFile(file2);
    fst.addFile(file3);

    fst.addFile(new Directory("a"));
    FileSystemTree a = fst.getSubFile("a");
    a.addFile(file4);
  }

  @After
  public void tearDown() throws Exception {
    fst = fst.setFileSystemReferenceNull();
    Command.setCurrentDirectoryNone();
  }

  @Test
  /**
   * tests if running an alphabetical grep gives expected outcome
   */
  public void testGrep1() throws Exception {
    String[] expected = new String[]{"/#", "IS"};
    Grep g = new Grep(new GrepPath("[A-Z]+ file1"));
    String[] actual = g.run(fst);
    assertEquals(expected, actual);
  }

  @Test
  /**
   * Tests if running grep with numbers
   */
  public void testGrepNumber() throws Exception {
    String[] expected = new String[]{"/#", "2"};
    Grep g = new Grep(new GrepPath("[0-9] file2"));
    String[] actual = g.run(fst);
    assertEquals(expected, actual);
  }

  @Test(expected = FileDoesNotExistException.class)
  /**
   * tests if running grep with numbers and a new file
   */
  public void testGrepException() throws Exception {
    Grep g = new Grep(new GrepPath("[0-9] myFile"));
    String[] actual = g.run(fst);
  }

  @Test
  /**
   * tests path if running grep with multiple varied items to search for
   */
  public void testGrepRecursive() throws Exception {
    String[] expected = new String[]{"/#", "/a/file4: \n" + "FILE\n"};
    Grep g = new Grep(new GrepPath("-R [A-Z]+ /a"));
    String[] actual = g.run(fst);
    assertEquals(expected, actual);
  }
}
