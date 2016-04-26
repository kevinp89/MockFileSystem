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
package commands;

import Tree.FileSystemTree;
import exceptions.FileDoesNotExistException;
import path.ConcreteEchoPath;
import path.ConcretePath;
import path.Path;

import java.util.Iterator;

public class Redirect extends Command{

  protected static String manual = "";

  // tracker for output
  private String[] output;
  //tracker for path
  private String path;

  // default constructor
  public Redirect(){}

  //construcot with path string p and output string array.
  public Redirect(String p, String[] output){
    path = p;
    this.output = output;
  }

  /**
   * Run the redirection.
   * @param fst - root fst node.
   * @return - the output of redirection.
   * @throws Exception
     */
  @Override
  public String[] run(FileSystemTree fst)throws Exception{
    try {
      ConcreteEchoPath echoPath = new ConcreteEchoPath(output[1] + path);
      Echo echo = new Echo(echoPath);

      return echo.run(fst);
    }
    catch(FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage()); }
    }

  }



