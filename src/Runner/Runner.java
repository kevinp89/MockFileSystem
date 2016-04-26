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
package Runner;

import Tree.FileSystemTree;
import commands.*;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidCommandException;
import file.Directory;
import file.File;
import path.*;
import test.ConcretePathTest;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Hashtable;

public class Runner {

  private static Hashtable<String, String> cmdClasses = new Hashtable<String,
    String>(){
    {
      put("cd", "commands.CD");
      put("mkdir", "commands.Mkdir");
      put("ls", "commands.LS");
      put("pwd", "commands.Pwd");
      put("pushd", "commands.Pushd");
      put("echo", "commands.Echo");
      put("popd", "commands.Popd");
      put("man", "commands.MAN");
      put("cat", "commands.Cat");
      put("grep", "commands.Grep");
      put("mv", "commands.Move");
      put("cp", "commands.Copy");
      put("get", "commands.GetURL");
      put("!", "commands.RunPreviousCommands");

    }
  };

  public Runner(){}

  /**
   * Return a String array of outputs, outputted by commands. First index
   * must be the promptLine, second index must be the output of the command
   * (if any). The String array must be length of 2.
   * Throw InvalidCommandException if no (or any) command runs.
   *
   * @param cmd - String array of a splitted command.
   * @return - String array of outputs.
   * @throws Exception
   */
  public String[] act(String[] cmd, FileSystemTree fst)throws Exception{
    Boolean redirBool = false;
    if(cmd[0].equals("history")){
      return this.runHistory(cmd);
    }

    else if(cmd[0].equals("")){return new String[]{"", ""};}

    String command = (cmd[0].contains("!")) ? cmdClasses.get("!") :
        cmdClasses.get(cmd[0]);

    Class<?> newClass = Class.forName(command);

    //check if the command has a second argument, if it doesn't
    // initialize the class with "".
    Command runCmd;
    String redirect = "";
      if (cmd.length == 2 || cmd[0].equals("pwd")){
        Path cp;

        if(!cmd[0].equals("echo") && cmd[1].contains(">")){
          redirBool = true;
          if(cmd[1].contains(">>")){
            String[] split = cmd[1].split(">>");
            cp = this.getPath(cmd[0], split[0]);
            redirect = ">>" + split[1];
          }
          else{
            String[] split = cmd[1].split(">");
            cp = this.getPath(cmd[0], split[0]);
            redirect = ">" + split[1];
          }
        }else{
          cp = this.getPath(cmd[0], cmd[1]);
          redirect = "";
        }

      runCmd = (Command) newClass.getConstructor(Path.class).newInstance(cp);}
      else{
        ConcretePath cp;
        if(cmd[0].contains("!")){
          cp = new ConcretePath(cmd[0]);
        }else{
          cp = new ConcretePath("");
        }
        runCmd = (Command) newClass.getConstructor(Path.class).newInstance(cp);
      }

      try{
        String[] output = runCmd.run(fst);
        if(redirBool){
          Redirect redir = new Redirect(redirect, output);
          return redir.run(fst);
        }
        return output;

      }
      catch (InvalidCommandException e){
        throw new InvalidCommandException(e.getMessage());
      }
      catch (FileDoesNotExistException e){
        throw new InvalidCommandException(e.getMessage());
      }
      catch(EmptyStackException e){
        throw new InvalidCommandException(e.getMessage());
      }
  }


  /**
   * Return either ConcreteMultiplePath if more than one path provided, or
   * return ConcretePath if single path provided.
   * @param cmd - user Argument.
   * @return - Path.
   */
  public Path getPath(String command, String cmd) throws InvalidCommandException {
    if (command.equals("echo")){
      return new ConcreteEchoPath(cmd);
    }
    else if(command.equals("grep")){
      return new GrepPath(cmd);
    }
    else if(cmd.contains(">") || cmd.contains(">>")){
      //TODO: REDIRECTION not echo anymore
      return new ConcretePath(cmd);
    }
    else if(cmd.contains("-r") || cmd.contains("-R") && cmd.split(" ")
        .length <= 2){
      return new ConcretePath(cmd);
    }
    else if(command.equals("get")){
      return new URLPath(cmd);
    }

    else if(cmd.split(" ").length > 1){
      return new ConcreteMultiplePath(cmd);
    }
    else {
      return new ConcretePath(cmd);
    }
  }


  /**
   * Return the history from the History stack. If number provided, n
   * previous history, otherwise return all history.
   * @param cmd - String Array of {command, arguments[if any]}
   * @return - promptline and history.
   */
  private String[] runHistory(String[] cmd){
    if(cmd.length == 2){
      return new String[]{"", History.getHistory(Integer.parseInt
            (cmd[1]))};
    }else{
      return new String[]{"", History.getHistory()};
    }
  }


  /**
   * MAIN METHOD FOR TESTING ONLY.
   */
  public static void main(String[] args) {
    FileSystemTree fst = FileSystemTree.createNewFileSystemInstance
        (new Directory("/"));
//        FileSystemTree currentDir = fst;


    String[] cmd = new String[]{"mkdir", "a b"};
    //String[] cmd2 = new String[]{"cat", "outfile.txt"};
//
//        String[] cmd3 = new String[]{"mkdir", "b"};
    String[] cmd2 = new String[]{"cd", "a"};
    String[] cmd3 = new String[]{"mkdir", "e" };
    String[] cmd4 = new String[]{"cd", "e"};
    String[] cmd5 = new String[]{"pushd", "/b"};
    String[] cmd55 = new String[]{"popd", ""};
    String[] cmd6 = new String[]{"ls"};
    Runner r = new Runner();
        // TODO : clean up
//        String[] cmd4 = new String[]{"cd", "b"};
//        String[] cmd5 = new String[]{"ls", "/b"};


//        String[] cmd6 = new String[]{"cd", "stacy"};
//      String[] cmd7 = new String[]{"ls", ""};
//        String[] cmd7 = new String[]{"cd", "/"};


    try{
        r.act(cmd, fst);
        r.act(cmd2, fst);
        r.act(cmd3, fst);
        r.act(cmd4, fst);
        r.act(cmd5, fst);
        r.act(cmd55, fst);
        System.out.println(Arrays.toString(r.act(cmd3, fst)));

          //  TODO: clean up
//            r.act(cmd4, fst);
//            r.act(cmd5, fst);
//            r.act(cmd55, fst);
//            System.out.println(Arrays.toString(r.act(cmd6, fst)));
//            System.out.println(Arrays.toString(r.act(cmd5, fst)));



    }
    catch (Exception e){
        System.out.println(e.getMessage());
    }

  }


}
// ["cat", "path > filepath"]
