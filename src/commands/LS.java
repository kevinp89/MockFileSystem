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
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import file.File;

import java.util.Arrays;
import java.util.Iterator;

/**
 * LS is a subclass of Command
 */
public class LS extends Command {

  protected static String manual = "command ls \n" +
          "argument:  directory or / or .. or /directory eg ls or ls " +
          "/src\n" +
          "Important notes:\n" +
          "*/ single slash represents root directory/n\n" +
          "*a directory path from the root can be added to" +
          " / \"/Home/User/DontPanic\n tp shpw its contents" +
          "* .. lists contents of parent directory\n" +

          "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \"\\\\ " +
          "\n \n";

  /**
   * Default constructor for :S
   */
  public LS() {
  }

  /**
   * Constructor for LS followed by a name of a directory
   */
  public LS(String dirName) {
    directory = dirName;
  }

  public LS(Path p) {
    super(p);
  }

  /**
   * Run method checks for whether the LS command was called right
   */
  public String[] run(FileSystemTree fst) throws Exception {

    try {
      if (PATH instanceof ConcreteMultiplePath) {
        //downcast
        ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
        String output = "";
        for (ConcretePath p : path) {
          Iterator<String> pathIter = p.iterator();
          output += this.assignTree(pathIter, fst, p) + "\n";
        }
        return new String[]{getCurrentDirName(), output.substring(0,
                output.length() - 1)};
      } else {
        ConcretePath path = (ConcretePath) PATH;
        String output = this.assignTree(path.iterator(), fst, path);
        return new String[]{getCurrentDirName(), output};
      }
    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }

    }//end of run();


  /**
   * Assign the root tree or current tree based on path, and return the
   * value returned by ls.
   *
   * @param iterator - ConcretePath iterator.
   * @param fst      - a FileSystemTree.
   * @param path     - a ConcretePath.
   * @return - the value returned by ls.
   * @throws FileDoesNotExistException
   */
  private String assignTree(Iterator<String> iterator, FileSystemTree fst,
                            ConcretePath path) throws FileDoesNotExistException {
    try {
      //the path provided is a root path.
      if (path.rootDirExists()) {
        if (iterator.hasNext()) {
          iterator.next();
          return this.ls(iterator, fst, path);
        } else {
          return this.ls(iterator, fst, path);
        }
      }
      //if path provided is a current directory path.
      else {
        return this.ls(iterator, getCurrentDirectory(), path);
      }
    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }

  }

  /**
   * Return the content of the last dirName in the iter. Directory must be in
   * fst.
   *
   * @param iter - Iterator for ConcretePath.
   * @param fst  - A FileSystemTree.
   * @return - the content of the iterator path.
   * @throws FileDoesNotExistException
   */
  private String ls(Iterator<String> iter, FileSystemTree fst,
                    ConcretePath path) throws
          FileDoesNotExistException {

    try {

      if (!iter.hasNext()) {

        String output = "";
        //check if path is recursive.
        if (path.isRecursive()) {
          output = this.recursiveListOut(fst);
          return output;
        }
        output = this.listOut(fst);
        return output;
      } else {
        String file = iter.next();
        if (file.equals("..")) {
          fst = fst.getParent();
        } else if (file.equals(".")) {
        } else {
          fst = fst.getSubFile(file);
        }
        return this.ls(iter, fst, path);
      }
    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }
  }

  /**
   * Recursively go through the fst getting the content of eas fst node if
   * the node is instance of Directory.
   *
   * @param fst - A FileSystemTree Node.
   * @return - content of fst and its subdirectories.
   * @throws FileDoesNotExistException
   */
  private String recursiveListOut(FileSystemTree fst) throws FileDoesNotExistException {
    // get array of children/subdirs
    FileSystemTree[] fstDirs = fst.getSubDirNodes();
    try {

      String output = "";
      for (FileSystemTree subDir : fstDirs) {
        File file = subDir.getFile();
        if (file instanceof Directory) {
          output += subDir.getPath() + ":\n" + listOut(subDir) + "\n"
                  + "\n" + this.recursiveListOut(subDir);
        }
      }
      return output.substring(0, output.length());

    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage());
    }
  }


  /**
   * Lists out the children of the name provided i.e. contents of the directory
   *
   * @param name - a Filesystemtree object that indicates the current
   *             directory whose contents to display with LS
   * @return String
   */
  private String listOut(FileSystemTree name) {
    String[] childList = name.getSubDirectories();
    Arrays.sort(childList);
    String output = "";
    for (String child : childList) {
      output += child;
      output += " ";
    }

    output = (output.equals("")) ? output : output.substring(0, output.length()
            - 1);

    //check if output needs to have the filename before it.
    if (PATH instanceof ConcreteMultiplePath) {
      output = name.getFilename() + output;
      return output;
    }
    return output;
  }

}
