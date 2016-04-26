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
// *********************************************************package test;

import file.RWFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RWFileTest extends RWFile {

  private RWFile rw;

  @Before
  public void setUp() throws Exception {
   rw = new RWFile("a");


  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  /**
   * testing the getFileName method and comparing the result
   */
  public void testGetFileName() throws Exception {
  String expected = "a.txt"; //tostring method tested here
  String actual = rw.getFileName();
    assertEquals(expected, actual);
  }

  @Test
  /**
   * testing getText with expected result
   */
  public void testAdd() throws Exception {
    rw.add("kk");
    String expected = "kk";
    String actual = rw.getText(); //getText method tested here
    assertEquals(expected, actual);

  }

  @Test
  /**
   * testing replaceText to see if the text received is as expected
   */
  public void testReplaceText() throws Exception {
    rw.replaceText("bb");
    String expected = "bb";
    String actual = rw.getText();
    assertEquals(expected, actual);

  }

  @Test
  /**
   * testing if RW works when testing the content of file
   */
  public void testEquals() throws Exception {
    RWFile k;
     k = new RWFile("a");
    Boolean b = k.equals(rw);

    assertEquals(b, true);




  }
}