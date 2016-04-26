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
import file.Directory;

import file.RWFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcreteEchoPath;
import path.ConcretePath;

import static org.junit.Assert.*;


public class EchoTest extends Echo {

  private FileSystemTree FST;

  @Before
  public void setUp() throws Exception {
    FST = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    Command.setCurrDirectory(FST);
  }


  @After
  public void tearDown() throws Exception {
    FST = FST.setFileSystemReferenceNull();
    Command.setCurrentDirectoryNone();
  }

  @Test
  /**
   * Tests if the output matches the expected output for simple echo run
   */
  public void testRun() throws Exception {
    Echo echoFile = new Echo(new ConcreteEchoPath("str"));
    FileSystemTree newFST = FST;
    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", "str"};
    assertEquals(expected, output);
  }

  @Test
  /**
   * Tests a long complex output
   */
  public void testLongStr() throws Exception {
    String str = "This is a very long sentence, and red roses "
            + "are not blue";
    Echo echoFile = new Echo(new ConcreteEchoPath(str));
    FileSystemTree newFST = FST;
    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", str};
    assertEquals(expected, output);
  }


  @Test
  public void testOneFWithExtension() throws Exception {
    String cmd = "echo hi >> OneFWithExtension.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];

    Echo echoFile = new Echo(new ConcreteEchoPath(ec));
    FileSystemTree newFST = FST;
    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output);

    RWFile outFile = new RWFile("OneFWithExtension.txt");
    outFile.add("hi");
    RWFile expectedFile = (RWFile) FST.getChild("OneFWithExtension.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testOneFileWithEmptyString() throws Exception {
    String cmd = "echo wow> OneFileWithEmptyString.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));
    FileSystemTree newFST = FST;
    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output);

    RWFile outFile = new RWFile("OneFileWithEmptyString.txt");
    outFile.add("wow");
    RWFile expectedFile = (RWFile) FST.getChild("OneFileWithEmptyString.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testOverwrite() throws Exception {
    RWFile file = new RWFile("Overwrite.txt");
    file.add("old text");
    FST.addFile(file);

    String cmd = "echo new text > Overwrite.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));
    FileSystemTree newFST = FST;

    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output);

    RWFile outFile = new RWFile("Overwrite.txt");
    outFile.add("new text");

    RWFile expectedFile = (RWFile) FST.getChild("Overwrite.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testAppend() throws Exception {
    RWFile file = new RWFile("Append.txt");
    file.add("old text");
    FST.addFile(file);

    String cmd = "echo new text >> Append.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));
    FileSystemTree newFST = FST;

    String[] output = echoFile.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output);

    RWFile outFile = new RWFile("Append.txt");
    outFile.add("old text\nnew text");

    RWFile expectedFile = (RWFile) newFST.getChild("Append.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testAppendAndOverwrite() throws Exception {
    RWFile file = new RWFile("AAO.txt");
    file.add("old text");
    FileSystemTree newFST = FST;
    newFST.addFile(file);

    String cmd = "echo More text >> AAO.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));

    String cmd2 = "echo New text > AAO.txt";
    ValidityChecker vc2 = new ValidityChecker(cmd2, cmd2.split(" "));
    String ec2 = vc2.checkValid()[1];
    Echo echoFile2 = new Echo(new ConcreteEchoPath(ec2));

    echoFile.run(newFST);
    String[] output2 = echoFile2.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output2);

    RWFile outFile = new RWFile("AAO.txt");
    outFile.add("New text");

    RWFile expectedFile = (RWFile) newFST.getChild("AAO.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testLongTextAppendAndAppend() throws Exception {
    RWFile file = new RWFile("LTAAA.txt");
    file.add("Some text that won't be replaced.");
    FileSystemTree newFST = FST;
    newFST.addFile(file);

    String cmd = "echo Long sentences, I will include semicolons; >> LTAAA.txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));

    String cmd2 = "echo this is another meaningless sentence. >> LTAAA.txt";
    ValidityChecker vc2 = new ValidityChecker(cmd2, cmd2.split(" "));
    String ec2 = vc2.checkValid()[1];
    Echo echoFile2 = new Echo(new ConcreteEchoPath(ec2));

    echoFile.run(newFST);
    String[] output2 = echoFile2.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output2);

    RWFile outFile = new RWFile("LTAAA.txt");
    outFile.add("Some text that won't be replaced.\n"
            + "Long sentences, I will include semicolons;\n"
            + "this is another meaningless sentence.");

    RWFile expectedFile = (RWFile) newFST.getChild("LTAAA.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

  @Test
  public void testLongReplaceAndReplace() throws Exception {
    RWFile file = new RWFile("LRAR.txt");
    file.add("Some text.");
    FileSystemTree newFST = FST;
    newFST.addFile(file);

    String cmd = "echo Long sentences and I will include semicolons > LRAR"
            + ".txt";
    ValidityChecker vc = new ValidityChecker(cmd, cmd.split(" "));
    String ec = vc.checkValid()[1];
    Echo echoFile = new Echo(new ConcreteEchoPath(ec));

    String cmd2 = "echo this is another meaningless sentence. > LRAR.txt";
    ValidityChecker vc2 = new ValidityChecker(cmd2, cmd2.split(" "));
    String ec2 = vc2.checkValid()[1];
    Echo echoFile2 = new Echo(new ConcreteEchoPath(ec2));

    echoFile.run(newFST);
    String[] output2 = echoFile2.run(newFST);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, output2);

    RWFile outFile = new RWFile("LRAR.txt");
    outFile.add("this is another meaningless sentence.");

    RWFile expectedFile = (RWFile) newFST.getChild("LRAR.txt");

    assertEquals(outFile.getText(), expectedFile.getText());
  }

}
