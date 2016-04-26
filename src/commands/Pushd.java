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
import path.ConcretePath;
import path.Path;

public class Pushd extends Command {
  protected static String manual = "command pushd\n" +
    "arguments: working directory to make the current" +
    " directory (while  adding current directory to the stack)\n" +
    "important notes:\n" +
    "* the directories are stored in a stack " +
    "that uses first in last out principle\n" +
    "* popd command can be used to get directories from the stack\n" +
    "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\""
    +"\n \n";

  public Pushd(){}
  public Pushd(String dirName){
    directory = dirName;
  }
  public Pushd(Path p){super(p);}
  /**
   * push the current directory into stack, and change the current
   * directory to directory.
   * @return - String array of length 2. {promptLine, output}
   * @throws Exception
   */
  @Override public String[] run(FileSystemTree fst) throws
    Exception {

    fileStack.push(getCurrentDirectory().getPath());
    try {
      ConcretePath path = (ConcretePath) PATH;
      // change directory with cd.
      CD changeDir = new CD(path);
      return changeDir.run(fst);
    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException();
    }

  }

}
