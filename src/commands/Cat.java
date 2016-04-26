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
import file.Directory;
import file.File;
import file.HTMLFile;
import file.RWFile;
import path.ConcreteEchoPath;
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import javax.swing.text.html.HTML;
import java.util.Dictionary;
import java.util.Iterator;


public class Cat extends Command {
  protected static String manual = "command cat\n" +
          "arguments: 2 or more txt files\n" +
          "important notes: \n" +
          "* concatenates contents of 2nd text file to 1st " +
          "text file and displays them on the shell\n" +
          "invalid characters:  ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\";

  public Cat() {
  }

  public Cat(String dir) {
    directory = dir;
  }

  public Cat(Path p) {
    super(p);
  }

  /**
   * Prints out the text file of the path given, wherever the path is defined
   *
   * @return - A string of the content inside the text file given
   * @throws FileDoesNotExistException
   */
  @Override
  public String[] run(FileSystemTree fst) throws FileDoesNotExistException {

    try {

      String catOutput = "";
      //check is PAth is a multiple path.
      if (PATH instanceof ConcreteMultiplePath) {
        //downcast
        ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
        //loop over path.
        for (ConcretePath p : path) {
          Iterator<String> pathIterator = p.iterator();
          // for each path in the loop print out the output
          catOutput += this.assignTree(pathIterator, fst, p) + "\n";
        }//end of for loop
        return new String[]{getCurrentDirName(), catOutput};
        //after creating every dir
      } else {
        ConcretePath path = (ConcretePath) PATH;
        Iterator<String> pathIterator = path.iterator();
        //print out the output of the file
        catOutput += this.assignTree(pathIterator, fst, path) + "\n";
        //return the output of the file
        return new String[]{getCurrentDirName(), catOutput};
      }


    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }
  }


  private String assignTree(Iterator<String> iterator, FileSystemTree fst,
                            ConcretePath path) throws FileDoesNotExistException {
    try {
      if (path.rootDirExists()) {
        if (iterator.hasNext()) {
          iterator.next();
          return this.cat(iterator, fst);
        } else {
          throw new FileDoesNotExistException("You need to enter a "
                  + "readable file");
        }
      } else {
        return this.cat(iterator, getCurrentDirectory());
      }
    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }
  }

  /**
   * Check if the path is correct and returns the text file.
   *
   * @param iterator - the path of the text file to check and return content
   * @param fst - the FileSystemTree where the file is located
   * @return - the file content in the form of a string
   * @throws FileDoesNotExistException
   */
  private String cat(Iterator<String> iterator, FileSystemTree fst) throws
          FileDoesNotExistException {

    try{

      String file = iterator.next();


      if (file.equals("..")) {
        fst = fst.getParent();
      } else if (file.equals(".")) {
      }
      //go down
      else if(!file.equals("..") && !file.equals(".") && !file.contains("\\"
          + ".")){
        fst = fst.getSubFile(file);
      }

      if (!iterator.hasNext() && file.equals("/") || file.equals(".")) {
        throw new FileDoesNotExistException("This " + file + " is a directory. ");
      }

      else if(!iterator.hasNext() && !(fst.getFile() instanceof Directory)){
        return this.catOut(fst);
      }

      else if (!iterator.hasNext() && fst.hasChild(file)) {
        File readFile = fst.getChild(file);
        //check if it is a Readable File
        if (readFile instanceof RWFile) {
          RWFile read = (RWFile) readFile;
          return read.getText();
        } else {
          throw new FileDoesNotExistException("This " + file + " is not "
              + "readable ");
        }

      } else {
        return this.cat(iterator, fst);
      }
    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException(e.getMessage());
    }
  }

  /**
   *
   * @param fst
   * @return
     */
  private String catOut(FileSystemTree fst) throws FileDoesNotExistException{
    if(fst.getFile() instanceof RWFile){
      RWFile rw = (RWFile) fst.getFile();
      return rw.getText();
    }
    else if(fst.getFile() instanceof HTMLFile){
      HTMLFile html = (HTMLFile) fst.getFile();
      return html.getText();
    }
    else {
      throw new FileDoesNotExistException("File is a directory");
    }
  }

}
