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
import exceptions.InvalidArgumentException;
import file.File;
import file.HTMLFile;
import file.RWFile;
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import java.util.Iterator;

public class Copy extends Command {
  protected static String manual = "Command mv (move file) arg1: path of "
    + "file to be moved. arg1: path of directory where arg1 being moved: "
    + "\n"
    +" Moves arg1 to arg2. ";

  private FileSystemTree toBeRemoved;
  private FileSystemTree fstWithRemoveFile;

  public Copy(){}
  public Copy(Path p){
    super(p);
  }

  /**
   * Run the copy command and return the output.
   * @param fst - root fileSystemTree.
   * @return - the output of copy command.
   * @throws FileDoesNotExistException
   */
  @Override public String[] run(FileSystemTree fst) throws
      FileDoesNotExistException {

    try{
      // downcast
      ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
      //get iterator for path.
      Iterator<ConcretePath> iterator = path.iterator();
      //get ConcretePath for source and target
      ConcretePath source = iterator.next();
      ConcretePath target = iterator.next();
      //move the file.
      this.copyFile(source, target, fst);
      return new String[]{getCurrentDirName(), ""};

    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
    catch (InvalidArgumentException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
  }

  /**
   * Copy file from source and put into target path.
   * @param source - the source path
   * @param target - the target path
   * @param fst - a fileSystemTree node.
   * @throws FileDoesNotExistException
   * @throws InvalidArgumentException
     */
  private void copyFile(ConcretePath source, ConcretePath target,
      FileSystemTree fst) throws FileDoesNotExistException, InvalidArgumentException{

    //get the file
    FileSystemTree file;
    if(source.rootDirExists()){
      if(source.rootOnly()){
        throw new InvalidArgumentException("Root Directory, /, cannot"
            + " be moved");
      }
      else{
        Iterator<String> iter = source.iterator();
        // skip the /.
        iter.next();
        file = this.getFile(iter, fst);
        //check target's iterator
        Iterator<String> targetIterator = target.iterator();
        if(target.rootDirExists()) {
          //skip the /
          targetIterator.next();
          this.copy(file, targetIterator, fst);
        }
        else{
          this.copy(file, targetIterator, getCurrentDirectory());
        }
      }
    }
    else{
      Iterator<String> iter = source.iterator();
      file = this.getFile(iter, getCurrentDirectory());
      Iterator<String> targetIterator = target.iterator();
      if(target.rootDirExists()) {
        this.copy(file, targetIterator, fst);
      }
      else{
        this.copy(file, targetIterator, getCurrentDirectory());
      }
    }
  }

  /**
   * Return the file inside fst from the given iterator path
   * @param iterator - An Iterator for Concrete Path
   * @param fst - A FileSystemTree
   * @return - the file inside fst from the given iterator path.
   * @throws FileDoesNotExistException
   * @throws InvalidArgumentException
   */
  private FileSystemTree getFile(Iterator<String> iterator, FileSystemTree fst) throws
      FileDoesNotExistException, InvalidArgumentException{

    String file = iterator.next();
    //get the file
    if(!iterator.hasNext() && fst.hasChild(file)){
      return fst.getSubFile(file);
    }

    else if(!iterator.hasNext() && !fst.hasChild(file)){
      throw new FileDoesNotExistException("File " + file + " does not "
          + "exist");
    }

    else{
      fst = this.getFST(file, fst);
      return this.getFile(iterator, fst);
    }
  }

  /**
   * Add in the file in the fst given iterator path.
   * @param file - File to be added in fst
   * @param iterator - Iterator for ConcretePath.
   * @param fst - A FileSystemTree.
   * @throws FileDoesNotExistException
   */
  private void copy(FileSystemTree file, Iterator<String> iterator,
      FileSystemTree
      fst)throws FileDoesNotExistException, InvalidArgumentException{

    String dir = iterator.next();

    if(!iterator.hasNext()){
      if(dir.equals("/")){
        if (!fst.hasChild(file.getFile())) {
          fst.getSubFile(dir).addFile(file);
        }
        else{
          throw new FileDoesNotExistException(
              "Identical file: " + file + " in " + fst.getPath() +
                  " directory");
        }
      }

      if(fst.hasChild(dir)) {
        // go inside dir and add in file.
        FileSystemTree subdir = fst.getSubFile(dir);
        if (!subdir.hasChild(file.getFile())) {
          fst.getSubFile(dir).addFile(file);
        } else {
          throw new FileDoesNotExistException(
              "Identical file: " + file + " in " + fst.getPath() + " "
                  + "directory");
        }
      }
    }
    else if(!iterator.hasNext() && !fst.hasChild(dir)){
      throw new FileDoesNotExistException("File " + dir + " does not "
          + "exist");
    }

    else{
      fst = this.getFST(dir, fst);
      this.copy(file, iterator, fst);
    }
  }

  /**
   * Return a FileSystemTree such that if path == "..", returns fst's parent
   * if path == ".", return fst, otherwise return fst's subdirectory path.
   * @param path - operator or name of a directory.
   * @param fst - a FileSystemTree
   * @return - a FileSystemTree.
   * @throws FileDoesNotExistException
   */
  private FileSystemTree getFST(String path, FileSystemTree fst) throws
      FileDoesNotExistException{

    try {
      if (path.equals("..")) {
        return fst.getParent();
      } else if (path.equals(".")) {
        return fst;
      } else {
        FileSystemTree subDir = fst.getSubFile(path);
        if (subDir.getFile() instanceof RWFile || subDir.getFile()
            instanceof HTMLFile) {
          throw new InvalidArgumentException("File" + path + " is "
              + "not a directory.");
        } else {
          return subDir;
        }
      }
    }catch (InvalidArgumentException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
  }
}
