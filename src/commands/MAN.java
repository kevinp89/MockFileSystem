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
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import java.util.Hashtable;

/**
 * Manual is a subclass of Command
 */
public class MAN extends Command{
  // String of the manual for the man command
  protected static String manual = "command man\n" +
    "arguments:command name(s) [separated by spaces] \t\n" +
    "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\" + \"\\\\ \n \n";
  // A hashtable with all commands as key and their string manuals as values
  private Hashtable<String, String> ManualMap = new Hashtable<String, String>
          (){{
    put("cd", CD.manual);
    put("ls", LS.manual);
    put("echo", Echo.manual);
    put("history", History.manual);
    put("mkdir", Mkdir.manual);
    put("pushd", Pushd.manual);
    put("popd", Popd.manual) ;
    put("pwd", Pwd.manual);
    put("man", MAN.manual);
    put("cat", Cat.manual);

  }};

  //constructor without arguments
  public MAN(){}

  // Constructor of Man class
  public MAN(String command) {
    directory = command;
  }

  public MAN(Path p){
    super(p);
  }
  /**
   *
   * @param fst - filesystemtree node
   * @return String[] with prompt line and string of the commands' manuals.
   * @throws Exception
   */
  public String[] run(FileSystemTree fst) throws Exception {

    if(PATH instanceof ConcreteMultiplePath){
      ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
      String output = "";
      for(ConcretePath p : path){
        output += this.getManual(p);
      }
      return new String[]{getCurrentDirName(), output.substring(0, output
          .length())};
    }
    else{
      ConcretePath path = (ConcretePath) PATH;
      return new String[]{getCurrentDirName(), this.getManual(path)};
    }

  }

  /**
   * Return the Manual for the command.
   * @param command - a ConcretePath storing the command.
   * @return - the Manual for command.
     */
  private String getManual(ConcretePath command){
    return ManualMap.get(command.iterator().next());
  }
}
