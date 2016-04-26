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

import commands.*;
import exceptions.FileDoesNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import Tree.FileSystemTree;
import checkers.ValidityChecker;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.*;

import static org.junit.Assert.*;

public class ManTest extends MAN {

  FileSystemTree fst;

  String cdMan = "command cd \n" +
          "arguments: a valid directory path in the file system. For eg. cd" +
          " Assignment/src \n" +
          "cd / and cd without any arguments takes user to root directory\n" +
          "Important notes:\n" +
          "*/ single slash represents root directory\n" +
          "*directory files are separated by / \n" +
          "* .. backtracks to parent directory\n" +

          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \" " +
          " \n \n";

  String lsMan = "command ls \n" +
          "argument:  directory or / or .. or /directory eg ls or ls " +
          "/src\n" +
          "Important notes:\n" +
          "*/ single slash represents root directory/n\n" +
          "*a directory path from the root can be added to" +
          " / \"/Home/User/DontPanic\n tp shpw its contents" +
          "* .. lists contents of parent directory\n" +

          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \"\\\\ " +
          "\n \n";

  String CatMan = "command cat\n" +
          "arguments: 2 or more txt files\n" +
          "important notes: \n" +
          "* concatenates contents of 2nd text file to 1st " +
          "text file and displays them on the shell\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\";

  String EchoMan = "command ls\n" +
          "argument: a String, > or >> and outfile\n" +
          "Imoprtant notes:\n" +
          "* single > represents echo type 1\n" +
          "* if only echo String provided Shell will print out the string\n" +
          "* if you have > followed by a path, if file doesn't exist it will " +
          "be created, if it exists then the text inside it will be " +
          "replaced with provided string\n" +
          "* with >> will behavior the same way, except " +
          "it would append the string to the text file if it already exists\n" +
          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n ";

  String HistoryMan = "command History\n" +
          "argument: none\n" +
          "Imoprtant notes:\n" +
          "* shows history of the terminal: the last 5 commands that were " +
          "carried out \n" +
          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n  ";

  String ManMan = "command man\n" +
          "arguments:command name(s) [separated by spaces] \t\n" +
          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \"\\\\ \n \n";

  String MkdirMan = "command mkdir\n" +
          "argument: String name of directory you want to create\n" +
          "Imoprtant notes:\n" +
          "* makes a new directory of the name provided as argument\n" +
          "* if direcory already exists, it prints file already exists\n" +
          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n";

  String PopdMan = "command popd\n" +
          "arguments:none \t\n" +
          "important notes:\n" +
          "* takes the first directory out of the directory " +
          "stack in last in first out order and cd's into it\n" +
          "* if no directory available displays error message\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\""
          + "\n \n";

  String PushdMan = "command pushd\n" +
          "arguments: working directory to make the current" +
          " directory (while  adding current directory to the stack)\n" +
          "important notes:\n" +
          "* the directories are stored in a stack " +
          "that uses first in last out principle\n" +
          "* popd command can be used to get directories from the stack\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\""
          + "\n \n";

  String PwdMan = "command pwd\n" +
          "arguments:none \t\n" +
          "important notes:\n" +
          "* prints current working directory including whole path\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\"" +
          "\n \n";


  @After
  public void tearDown() throws Exception {


  }

  @Test
  public void testRunSimplecd() throws Exception {
    MAN mn = new MAN(new ConcretePath("cd"));
    String[] actual = mn.run(fst);
    String[] expected = new String[]{"/#", cdMan};
    assertEquals(expected, actual);
  }

  @Test
  public void testRunSimpleLs() throws Exception {
    MAN ls = new MAN(new ConcretePath("ls"));
    String[] actual = ls.run(fst);
    String[] expected = new String[]{"/#", lsMan};
    assertEquals(expected, actual);
  }

  @Test
  public void testRunSimpleCat() throws Exception {
    MAN Cat = new MAN(new ConcretePath("cat"));
    String[] actual = Cat.run(fst);
    String[] expected = new String[]{"/#", CatMan};
    assertEquals(expected, actual);
  }

  @Test
  public void testRunMultiple() throws Exception {
    MAN multiple = new MAN(new ConcreteMultiplePath("echo pushd popd"));
    String[] actual = multiple.run(fst);
    String[] expexted = new String[]{"/#", EchoMan + PushdMan + PopdMan};
    assertEquals(expexted, actual);
  }
}


