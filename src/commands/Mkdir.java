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
// Student4: Kevin Patel
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
import file.Directory;
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import java.util.Iterator;

public class Mkdir extends Command {
  protected static String manual = "command mkdir\n" +
          "argument: String name of directory you want to create\n" +
          "Important notes:\n" +
          "* makes a new directory of the name provided as argument\n" +
          "* if directory already exists, it prints file already exists\n" +
          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n";

  //default Empty constructor.
  public Mkdir() {
  }

  //constructor with dir/path.
  public Mkdir(String dir) {
    directory = dir;
  }

  public Mkdir(Path p){
    super(p);
  }


  /**
   * Creates a directory in the FST wherever the path is defined.
   *
   * @return - String with path line with the current directory
   * @throws FileDoesNotExistException
   */
  @Override
  public String[] run(FileSystemTree fst)
          throws FileDoesNotExistException {
    try{
      //check if PATH is a multiple path.
      if(PATH instanceof ConcreteMultiplePath){
        //downcast.
        ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
        // loop over path.
        for(ConcretePath p : path){
          Iterator<String> pathIterator = p.iterator();
          //assign tree.
          this.assignTree(pathIterator, fst, p);
        }//end of forloop.
        //after creating every dir.
        return new String[]{getCurrentDirName(), ""};
      }
      else{
        ConcretePath path = (ConcretePath) PATH;
        Iterator<String> pathIterator = path.iterator();
        //assign the tree.
        this.assignTree(pathIterator, fst, path);
        return new String[]{getCurrentDirName(), ""};
      }
    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
  }


  /**
   * Assign a file system tree based on if path has root directory or not.
   * @param iterator - Iterator for ConcretePath.
   * @param fst - A FileSystemTree.
   * @param path - A ConcretePath.
   * @throws FileDoesNotExistException
     */
  private void assignTree(Iterator<String> iterator, FileSystemTree fst,
      ConcretePath path) throws FileDoesNotExistException{
    try{
    if(path.rootDirExists()){
      iterator.next();
      if(iterator.hasNext()){
        //skipping the first "/"

        this.makeDir(iterator, fst);
      }
      else{throw new FileDoesNotExistException("Directory / already exists");}
    }
    else{
      this.makeDir(iterator, getCurrentDirectory());
    }}
    catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }

  }

  /**
   * Make a directory inside fst, based on the path provided.
   *
   */
  private void makeDir(Iterator<String> iterator, FileSystemTree fst) throws
          FileDoesNotExistException {

    String file = iterator.next();
    //check if "/" or "." is entered.

    if(!iterator.hasNext() && file.equals("/") || file.equals(".")){
      throw new FileDoesNotExistException("Directory " + file + " already "
          + "exists");
    }
    //if no filename is provided.
    else if (file.equals("")){
      throw new FileDoesNotExistException("Directory not specified");
    }

    //If directory already present.
    else if(!iterator.hasNext() && fst.hasChild(file)){
      throw new FileDoesNotExistException("Directory: " + file + " already "
          + "exists");
    }
    //******create the file*******
    else if(!iterator.hasNext() && !file.contains("..") && !fst.hasChild(file))
    {
        fst.addFile(file);
    }
    //recurse and go through the path.
    else{
      //go up if ".."
      if(file.equals("..")){
        fst = fst.getParent();
      }
      // if . then do nothing.
      else if(file.equals(".")){}
      //go down otherwise.
      else{
        fst = fst.getSubFile(file);
      }
      //recurse.
      this.makeDir(iterator, fst);
    }
    }

  /**
   * Add dir[0] to the fst, by check if the path is correct, throw an
   * exception if path is invalid or dir exists in fst.
   *
   * @param path - String array of a path.
   * @param dir  - contains only one element, directory name.
   * @param fst  - A FileSystemTree.
   * @throws FileDoesNotExistException
   */
  private void addDir(String[] path, String[] dir, FileSystemTree fst) throws
          FileDoesNotExistException {

    if (path.length == 1) {
      fst.addFile(new Directory(path[0]));
    } else {

      try {
        FileSystemTree temp = (this.directory.startsWith("/")) ?
                goToDir(path, fst) : goToDir(path, getCurrentDirectory());

        Directory toBeMade = new Directory(dir[0]);

        if (temp.hasChild(toBeMade)) {
          throw new FileDoesNotExistException("File already exists");
        } else {
          temp.addFile(toBeMade);

        }
      } catch (FileDoesNotExistException e) {
        throw new FileDoesNotExistException(e.getMessage());
      }

    }
  }
}


