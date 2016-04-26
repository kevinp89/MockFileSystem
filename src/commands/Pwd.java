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
import path.Path;

public class Pwd extends Command {

  protected static String manual = "command pwd\n" +
          "arguments:none \t\n" +
          "important notes:\n" +
          "* prints current working directory including whole path\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\"" +
          "\n \n";

  // String that stores the directory

  public Pwd(){}
  /**
   * Default constructor.
   */
  public Pwd(String dir){
    directory = dir;
  }
  public Pwd(Path p){super(p);}

  /**
   * The run() method for Pwd.
   * Returns the currentDirName and Path of currentDirectory, in a String[]
   * format.
   *
   * @param fst - root fst node
   * @return String[currentDirName, Path of currentDirectory]
   */
  @Override public String[] run(FileSystemTree fst) {
    return new String[]{getCurrentDirName(), getCurrentDir().getPath()};
  }

}
