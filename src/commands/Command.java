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

import Stack.FileStack;
import Tree.FileSystemTree;
import exceptions.FileDoesNotExistException;
import file.*;
import path.ConcretePath;
import path.Path;


public abstract class Command {

    //A string that keeps track of the path.

    //Keeps track of the ENTIRE tree.
    //protected static FileSystemTree tree;

    //Keeps track of the CURRENT directory.
//    protected static FileSystemTree currentDir = tree;

    //A string of the current directory.
  protected String manual = "";
  protected static String currentDirName = "/#";

  protected static FileSystemTree currentDirectory = FileSystemTree
    .createNewFileSystemInstance(new Directory("/"));

  protected static FileStack fileStack = new FileStack();
  protected String directory;

  //TODO: make a getter for PATH.
  protected Path PATH;

  public Command(){
  }

  public Command(Path p){this.PATH = p;}

  /**
   * Return a list of string length of two, first index is the string of
   * current directory, second index is the output returned by the command.
   * @return - a list of string. length of two.
   * @throws FileDoesNotExistException
   */
  public abstract String[] run(FileSystemTree fst)
    throws Exception;
  
  /**
   * Return the current directory.
   * @return - the current directory.
   */

  public FileSystemTree getCurrentDir(){
    return this.currentDirectory;
  }

  /**
   * Set currentDirName to given FileSystemTree node name.
   */
  protected void setCurrentDirName(){
    if(!currentDirectory.getFilename().equals("/")){
        currentDirName = "/" + currentDirectory.getFilename() + "/#";
    }else{
        currentDirName = "/#";
    }
  }

  /**
   * Return currentDirName value.
   * @return currentDirName - Value of currentDirName.
   */
  public String getCurrentDirName(){
    return currentDirName;
  }

  /**
   * Return the currentDirectory.
   * @return currentDirectory - FileSystemTree representation of the current
   *                            node.
   */
  public FileSystemTree getCurrentDirectory(){return currentDirectory;}

  /**
   * Setter of the currentDirectory.
   * @param fst - Node of FileSystemTree
   */
  protected void setCurrentDirectory(FileSystemTree fst){
    currentDirectory = fst;
  }

  /**
   * DO NOT USE THIS COMMAND, UNLESS USED FOR TESTING.
   * sets the current directory to null.
   */
  protected static void setCurrentDirectoryNone(){
    currentDirectory = null;
  }

  /**
   * DO NOT USE THIS COMMAND, UNLESS USED FOR TESTING.
   * Set current directory to a given node
   * @param node - a FileSystemTree Node.
     */
  public static void setCurrDirectory(FileSystemTree node){
    currentDirectory = node;
  }

  /**
   *  Return the FileSystemNode by using path array to go inside the tree,
   *  will raise an error otherwise.
   *
   * @param path - String of pathway to follow
   * @param FST - A FileSystemTree Node.
   * @return Travels to designated pathway if it exists
   * @throws FileDoesNotExistException
   */
  protected FileSystemTree goToDir(String[] path, FileSystemTree FST)
    throws FileDoesNotExistException{


    FileSystemTree temp = FST;

    try {

      for (String dir : path) {
        Directory tempDir = new Directory(dir);
        if(dir.equals("")){
          throw new FileDoesNotExistException("Invalid Argument");
        }

        if(dir.equals(".")){
          continue;
        }

        if (dir.equals("..")) {
          temp = temp.getParent();
        }
        else if (temp.getFile() instanceof Directory && dir.split("\\.")
          .length==1 && temp.hasChild(tempDir)) {
          temp = temp.getChild(tempDir);
        }

        else if(!temp.hasChild(dir)){
            throw new FileDoesNotExistException("File " + tempDir.getName() + ""
                + " does not exist");}
      }
      return temp;

    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException();
    }
  }

}
