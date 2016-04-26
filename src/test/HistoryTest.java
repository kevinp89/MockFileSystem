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

import commands.History;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class HistoryTest extends History {

  private History hist;


  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

    hist = null;

  }

  @Test
  /**
   * Testing history after only one action was taken
   */
  public void onlyHistory() throws Exception {

    String[] cmd = new String[]{"history"};
    History hist = new History(cmd);
    String actual = hist.getHistory();
    String expected = "1. history";
    assertEquals(expected, actual);

  }

  @Test
  /**
   * Testing history for multiple commands to see whether output matches
   */
  public void testSeveralCommands() throws Exception {

    String[] cmd = new String[]{"ls"};
    String[] cmd2 = new String[]{"mkdir p"};
    String[] cmd3 = new String[]{"echo Pierina > myFile.txt"};
    String[] cmd4 = new String[]{"cat myFile.txt"};
    String[] cmd5 = new String[]{"history"};
    hist = new History(cmd);
    hist = new History(cmd2);
    hist = new History(cmd3);
    hist = new History(cmd4);
    hist = new History(cmd5);
    String actual = hist.getHistory();
    String expected = "1. ls\n" + "2. mkdir p\n" + "3. echo Pierina > myFile.txt\n" + "4. cat myFile.txt\n" +
            "5. history";
    assertEquals(expected, actual);

  }

  @Test
  /**
   * Tesing for multiple commands with history
   */
  public void testInvalidCommands() throws Exception {

    String[] cmd = new String[]{"ls"};
    String[] cmd2 = new String[]{"mkdir p"};
    String[] cmd3 = new String[]{"asdjsa"};
    String[] cmd4 = new String[]{"mdir p"};
    String[] cmd5 = new String[]{"history"};
    hist = new History(cmd);
    hist = new History(cmd2);
    hist = new History(cmd3);
    hist = new History(cmd4);
    hist = new History(cmd5);
    String actual = hist.getHistory();
    String expected = "1. ls\n" + "2. mkdir p\n" + "3. asdjsa\n" + "4. mdir p\n" +
            "5. history";
    assertEquals(expected, actual);


  }

  @Test
  /**
   * Tests if history command displays right output for a string
   * of commands
   */
  public void truncatingSeveral() throws Exception {

    String[] cmd = new String[]{"ls"};
    String[] cmd2 = new String[]{"mkdir p"};
    String[] cmd3 = new String[]{"asdjsa"};
    String[] cmd4 = new String[]{"mdir p"};
    String[] cmd5 = new String[]{"history"};
    hist = new History(cmd);
    hist = new History(cmd2);
    hist = new History(cmd3);
    hist = new History(cmd4);
    hist = new History(cmd5);
    String actual = hist.getHistory(2);
    String expected = "4. mdir p\n" +
            "5. history";
    assertEquals(expected, actual);

  }


}