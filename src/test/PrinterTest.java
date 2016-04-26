package test;

import Printer.Printer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PrinterTest extends Printer {
  Printer p;

  @Before
  public void setUp() throws Exception {
    p = new Printer("#/", " ", " ");
  }

  @After
  public void tearDown() throws Exception {
    p = null;
  }

  @Test
  public void setPromptLine() throws Exception {
    p.setPromptLine("/a/#/");
    p.printPrompt();
//        assertEquals("/a/#/ ", outContent.toString());
  }

  @Test
  public void setOutput() throws Exception {

  }

  @Test
  public void setError() throws Exception {

  }

  @Test
  public void printOutput() {

  }

  @Test
  public void printPrompt() {

  }

  @Test
  public void printErr() {

  }

  @Test
  public void printStartUpMsg() {

  }
}
