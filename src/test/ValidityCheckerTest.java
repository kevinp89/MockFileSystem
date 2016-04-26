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

import checkers.ValidityChecker;
import exceptions.InvalidCommandException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class ValidityCheckerTest extends ValidityChecker {

  private String[] cmdList;
  private String userInput;
  private ValidityChecker validity;

  @Before
  public void setUp() throws Exception {
    userInput = "";

    cmdList = new String[]{};
  }

  @After
  public void tearDown() throws Exception {

  }

  /**
   * A test that checks if the output is as expected to an input
   */
  @Test(expected = InvalidCommandException.class)
  public void testInvalidCat() throws Exception {
    userInput = "ct hi.txt";
    cmdList = new String[]{"ct", "hi.txt"};
    ValidityChecker output = new ValidityChecker(userInput, cmdList);
    String[] actual = output.checkValid();
    String[] expected = {"cat", "hi.txt"};
  }

  /**
   * Tests to see if content of file appears with cat command
   *
   * @throws Exception
   */
  @Test
  public void testValidCat() throws Exception {
    userInput = "cat hi.txt";
    cmdList = new String[]{"cat", "hi.txt"};

    ValidityChecker output = new ValidityChecker(userInput, cmdList);
    String[] actual = output.checkValid();
    String[] expected = {"cat", "hi.txt"};
    assertEquals(expected, actual);
  }

  @Test
  /**
   * checks if the ls command works
   */
  public void testValidLs() throws Exception {
    userInput = "ls /a";
    cmdList = new String[]{"ls", "/a"};

    ValidityChecker output = new ValidityChecker(userInput, cmdList);
    String[] actual = output.checkValid();
    String[] expected = {"ls", "/a"};
    assertEquals(expected, actual);
  }

  @Test
  /**
   * tests echo case with possibly problematic characters
   */
  public void testValid() throws Exception {
    userInput = "echo text > outfile";
    cmdList = new String[]{"echo", "text > outfile"};

    ValidityChecker output = new ValidityChecker(userInput, cmdList);
    String[] actual = output.checkValid();
    String[] expected = {"echo", "text > outfile "};
    assertEquals(expected, actual);
  }
}
