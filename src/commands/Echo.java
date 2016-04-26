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
import file.File;
import file.RWFile;
import file.Directory;
import path.ConcreteEchoPath;
import path.ConcretePath;
import path.Path;

import java.util.Arrays;
import java.util.Iterator;



public class Echo extends Command {
  protected static String manual = "command ls\n" +
      "argument: a String, > or >> and outfile\n" +
      "Imoprtant notes:\n" +
      "* single > represents echo type 1\n" +
      "* if only echo String provided Shell will print out the string\n" +
      "* if you have > followed by a path, if file doesn't exist it will " +
      "be created, if it exists then the text inside it will be " +
      "replaced with provided string\n" +
      "* with >> will behavior the same way, except " +
      "it would append the string to the text file if it already exists\n" +
      "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n ";

  // Stores entire command from user input
  private String command;
  // Stores string from user input
  private String str;
  // Stores path from user input
  private String path;

  /**
   * Default Constructor.
   **/
  public Echo(){}

  /**
   * Echo Constructor.
   * @param command String representation of command inputted by user.
   **/
  public Echo(String command) {
    // Stores entire command from user input
    this.command = command;
    this.str = "";
  }

  /**
   * Echo Constructor.
   * @param p Path representation of command inputted by user.
   **/
  public Echo(Path p) {
    super(p);
    this.command = PATH.getUIPath();
  }

  /**
   * Run the command echo. If two arrows were provided, calls echoType2.
   * If one arrow was provided, calls echoType1. If file was not specified,
   * then prints the string onto the console.
   * Else, throw an error.
   *
   * @return - String[promptline, consoleOutput]
   * @throws FileDoesNotExistException
   */
  @Override
  public String[] run(FileSystemTree fst) throws FileDoesNotExistException {
    ConcreteEchoPath path = (ConcreteEchoPath) PATH;
    Iterator<String> iterator = path.getPath().iterator();
    try {
    // If path does not contain any arrows, return the command
    if(!path.containsOneArr() && !path.containsTwoArr()){
      return new String[] {getCurrentDirName(), path.getText()};
    }
    else{
      if(path.rootDirExists()){
        if(iterator.hasNext()){
          iterator.next();
          this.echo(iterator, fst, path);
          return new String[] {getCurrentDirName(), ""};
        }
        else{throw new FileDoesNotExistException("Directory / already exists");}
      }
      else{
        this.echo(iterator, getCurrentDirectory(), path);
        return new String[] {getCurrentDirName(), ""};
      }
    }

    } catch (FileDoesNotExistException e) {
      throw new FileDoesNotExistException(e.getMessage()); }

  }

  // ============================= Helpers ===========================

  /**
   * Go through iterator path and get the file to be written into.
   * @param iterator - ConcretePath iterator
   * @param fst - A FileSystemTree node
   * @param path - A ConcretePath.
   * @throws FileDoesNotExistException
     */
  private void echo(Iterator<String> iterator, FileSystemTree fst,
      ConcreteEchoPath path) throws FileDoesNotExistException{
    String file = iterator.next();

    if(!iterator.hasNext() && file.equals("/") || file.equals(".")){
      throw new FileDoesNotExistException("Directory " + file + " already "
          + "exist");
    }
    else if (file.equals("")){
      throw new FileDoesNotExistException("Directory or file not provided");
    }
    else if (!iterator.hasNext() && path.containsTwoArr()){
      fileAppend(file, fst, path.getText());
    }
    else if (!iterator.hasNext() && path.containsOneArr()){
      fileReplace(file, fst, path.getText());
    }
    else{
      if(file.equals("..")){
        fst = fst.getParent();
      }
      else if (file.equals(".")){}
      else{
        fst = fst.getSubFile(file);
      }
      this.echo(iterator, fst, path);
    }
  }

  /**
   * Helper method for echoType2.
   * Throws an error if given checker is a directory. Else if the given file
   * already exists, replaces file's contents with the string this.str .
   * Else, create new file with the string this.str .
   *
   * @param filename - name of the File.
   * @param fst - a FileSystemTree.
   * @param text - text to be replaced
   * @throws FileDoesNotExistException
   */
  private void fileReplace(String filename, FileSystemTree fst, String text)
      throws FileDoesNotExistException {
    // Throws an error if checker given is a Directory, not an RWFile / File

    if (fst.hasChild(filename)) {
      // Set a pointer pointing to the file user wanted to replace
      File replacement = fst.getChild(filename);
      // Replace the file with the string str.
      replacement.replaceText(text);
    } else {
      RWFile file = new RWFile(filename);
      file.add(text);
      fst.addFile(file);
    }
  }

  /**
   * Helper method for echoType1.
   * Throws an error if given checker is a directory. Else if the given file
   * already exists, appends the string this.str into the given file.
   * Else, create new file with the string this.str .
   *
   * @param filename - name of the File
   * @param fst - a fileSystemTree.
   * @param text - text to be appeneded.
   * @throws FileDoesNotExistException
   */
  private void fileAppend(String filename, FileSystemTree fst, String text)
      throws FileDoesNotExistException {
    // Throws an error if checker given is a Directory, not an RWFile / File

    if (fst.hasChild(filename)) {
      // Set a pointer pointing to the file user wanted to replace
      File replacement = fst.getChild(filename);
      // Replace the file with the string str.
      replacement.add("\n" + text);
    } else {
      // Add string into tempFile, then add file into temp FileSystemTree.
      RWFile file = new RWFile(filename);
      file.add(text);
      fst.addFile(file);
    }

  }


}



