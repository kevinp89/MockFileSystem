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
import checkers.ValidityChecker;
import commands.Command;
import commands.Echo;
import commands.Redirect;
import file.Directory;
import file.File;
import file.RWFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcreteEchoPath;

import static org.junit.Assert.*;

public class RedirectTest extends Redirect {

  private FileSystemTree FST;

  @Before public void setUp() throws Exception {
    // Stores entire command from user input
    FST = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    Command.setCurrDirectory(FST);
  }

  @After public void tearDown() throws Exception {
    FST = FST.setFileSystemReferenceNull();
    Command.setCurrentDirectoryNone();
  }

  @Test
  public void testRun() throws Exception {
    String[] output = new String[]{"/#", "/"};
    Redirect reDir = new Redirect("> outfile.txt", output);
    FileSystemTree newFST = FST;
    String[] actual = reDir.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, actual);

    RWFile outFile = new RWFile("outfile.txt");
    outFile.add("/");
    RWFile expectedFile = (RWFile) FST.getChild("outfile.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void test2Arrows() throws Exception {
    String[] output = new String[]{"/#", "/"};
    Redirect reDir = new Redirect(">> outfile.txt", output);
    FileSystemTree newFST = FST;
    String[] actual = reDir.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, actual);

    RWFile outFile = new RWFile("outfile.txt");
    outFile.add("/");
    RWFile expectedFile = (RWFile) FST.getChild("outfile.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testRewrite() throws Exception {
    String[] output = new String[]{"/#", "old output"};
    Redirect reDir = new Redirect(">> outfile.txt", output);
    FileSystemTree newFST = FST;
    String[] actual = reDir.run(newFST);

    String[] newOutput = new String[]{"/#", "new output"};
    Redirect reDir1 = new Redirect("> outfile.txt", newOutput);
    String[] actual1 = reDir1.run(newFST);

    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, actual1);

    RWFile outFile = new RWFile("outfile.txt");
    outFile.add("new output");
    RWFile expectedFile = (RWFile) FST.getChild("outfile.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }
}
