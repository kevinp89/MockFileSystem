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
import exceptions.EmptyFileStackException;
import path.ConcretePath;
import path.Path;


public class Popd extends Command{
  protected static String manual = "command popd\n" +
    "arguments:none \t\n" +
    "important notes:\n" +
    "* takes the first directory out of the directory " +
    "stack in last in first out order and cd's into it\n" +
    "* if no directory available displays error message\n" +
    "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\\\""
    + "\n \n";


  // Default Constructor.
  public Popd(){}

  public Popd(String dir){
    directory = dir;
    }
  public Popd(Path p){super(p);}

  /**
   * Run the command popd. Changes the current directory by poping out of
   * the most recent item stacked on FileStack.
   * @param fst - The FileSystemTree
   * @return - string array of current directory and output.
   * @throws Exception
   */
  @Override public String[] run(FileSystemTree fst) throws Exception {

    try{
      String changeDir = fileStack.pop();
      ConcretePath path = new ConcretePath(changeDir);
      CD cd = new CD(path);
      return cd.run(fst);

    }catch (EmptyFileStackException e){
      throw new EmptyFileStackException("File Stack is Empty");
    }

  }
}
