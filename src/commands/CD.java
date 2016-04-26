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
import path.PathIterator;

import java.util.Iterator;

public class CD extends Command{

  protected static String manual = "command cd \n" +
    "arguments: a valid directory path in the file system. For eg. cd" +
    " Assignment/src \n" +
    "cd / and cd without any arguments takes user to root directory\n" +
    "Important notes:\n" +
    "*/ single slash represents root directory\n" +
    "*directory files are separated by / \n" +
    "* .. backtracks to parent directory\n" +

    "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \" " +
    " \n \n";

  // A string that stores directory
  private PathIterator i;
  private String current;

  //Default constructor.
  public CD(){}
  //Constructor with directory name/path.
  public CD(String dir){
    directory = dir;
  }

  public CD(Path p){super(p);}

  /**
   * Run the command cd. Changes the current directory to the one provided.
   * Goes up a directory if ".." is provided.
   *
   * @return - String[promptline, consoleOutput]
   * @throws FileDoesNotExistException
   */
  @Override public String[] run(FileSystemTree fst) throws
    FileDoesNotExistException {

    ConcretePath path = (ConcretePath) PATH;

    // change to root directly if no arguments given.
    if(path.nullPath() || path.rootOnly()){
      // change to root.
      this.changeToRoot(fst);
      return new String[]{getCurrentDirName(), ""};
    }
    try {
      Iterator<String> iterator = path.iterator();
      //if root directory given.
      if(path.rootDirExists()) {
        iterator.next();
        this.changeDir(iterator, fst);
        return new String[]{getCurrentDirName(), ""};}
      //pass in the current directory.
      else{
        this.changeDir(iterator, getCurrentDirectory());
        return new String[]{getCurrentDirName(), ""};
      }

    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
    }

  /**
   * Change to the directory by recursively moving through the path with the
   * given iterator.
   * @param iterator - Iterator for the ConcretePath.
   * @param fst - A FileSystemTree.
   * @throws FileDoesNotExistException
     */
  private void changeDir(Iterator<String> iterator, FileSystemTree fst) throws
      FileDoesNotExistException{

    try{
      String file = iterator.next();
      //go up a directory.
      if(file.equals("..")){
        fst = fst.getParent();
      }
      // stay at current directory/
      if(file.equals(".")){}

      //go down the path.
      else if(!file.equals("..") && !file.equals(".") && !file.contains("\\"
          + ".")){
        fst = fst.getSubFile(file);
      }

      //change to directory.
      if(!iterator.hasNext() && !file.contains("\\.")){
        setCurrentDirectory(fst);
        setCurrentDirName();
      }
      //go down directory
      else{
        //recurse
        changeDir(iterator, fst);
      }}
    catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
  }

  /**
   * Change the currentDirectory to given fst node.
   * @param fst - a FileSystemTree node which points to root node.
   */
  private void changeToRoot(FileSystemTree fst){
    setCurrentDirectory(fst);
    setCurrentDirName();
  }

}
