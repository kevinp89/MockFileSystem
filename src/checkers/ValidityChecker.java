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
package checkers;
import exceptions.InvalidCommandException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tree.FileSystemTree;

//TODO: remove redundant code and make helpers.

public class ValidityChecker {

  private ArrayList<String> cmdList;
  private String userInput;
  private String invChars = "~!@#$%^&*()_+`-=|[]{};':,<>?";
  private String alpha =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public ValidityChecker(){}

  public ValidityChecker(String userInput, String[] cmdList){
    this.cmdList = new ArrayList<String>(Arrays.asList(cmdList));
    this.userInput = userInput;
    this.removeWhiteSpace();
  }

  /**
   * Returns the cleared up version of command list if the command is valid,
   * throws error otherwise.
   * @return - cleared version of command if it is valid.
   * @throws InvalidCommandException
   */
  public String[] checkValid() throws InvalidCommandException {
    if (ArrowsChecker()){
      if (cmdList.size() == 0 || userInput.length() == 0) {
        return new String[]{""};
      } else if (cmdList.get(0).equals("cd")) {
        if (cdChecker()) {
          return cmdList.toArray(new String[cmdList.size()]);
        }
      } else if (cmdList.get(0).equals("mkdir")) {
        try {
          if (mkdirChecker()) {

            String build = this.buildString();

            return new String[]{"mkdir", build.substring(0, build.length()
                    - 1)};
          }
        } catch (InvalidCommandException e) {
          throw new InvalidCommandException(e.getMessage());
        }

      } else if (cmdList.get(0).equals("cat")) {
        if (catChecker()) {

          String build = this.buildString();

          return new String[]{"cat", build.substring(0, build.length()
                  - 1)};
        }

      } else if(cmdList.get(0).contains("!")){
          if (runPreviousCommandsChecker()){
              return cmdList.toArray(new String[cmdList.size()]);
          }
      }
      else if (cmdList.get(0).equals("ls")) {
        if (lsChecker()) {
          if(cmdList.size() >= 2){
            String build = this.buildString();
          return new String[]{"ls", build.substring(0, build.length()-1)};
          }
          else{
            return new String[]{"ls", ""};
          }
        }
      } else if (cmdList.get(0).equals("pushd")) {
        if (pushdChecker()) {
          return cmdList.toArray(new String[cmdList.size()]);
        }
      } else if (cmdList.get(0).equals("history")) {
        if (historyChecker()) {
          return cmdList.toArray(new String[cmdList.size()]);
        }
      } else if (cmdList.get(0).equals("echo")) {
        if (echoTypeChecker()) {
          String build = this.buildString();

          return new String[]{"echo", build};
        }
      } else if (cmdList.get(0).equals("pwd")) {
        if (pwdChecker()) {
          String build = this.buildString();
          return new String[]{"pwd", build.substring(0, build.length()-1)};
        }
      } else if (cmdList.get(0).equals("popd")) {
        if (popdChecker()) {
          return cmdList.toArray(new String[cmdList.size()]);
        }
      } else if (cmdList.get(0).equals("man")) {
        if (manChecker()) {
          String build = this.buildString();
          return new String[]{"man", build.substring(0, build.length() - 1)};
        }
      } else if(cmdList.get(0).equals("get")) {
          if (getChecker()) {
              return cmdList.toArray(new String[cmdList.size()]);
          }
      }
      else if(cmdList.get(0).equals("grep")){
        if(grepChecker()){
          String build = this.buildString();

          return new String[]{"grep", build.substring(0, build.length()-1)};
        }
      }

      else if(cmdList.get(0).equals("mv")){
        if(mvChecker()){
          String build = this.buildString();
          return new String[]{"mv", build.substring(0, build.length()-1)};
        }
      }
      else if(cmdList.get(0).equals("cp")){
        if(cpChecker()){
          String build = this.buildString();
          return new String[]{"cp", build.substring(0, build.length()-1)};
        }
      }
    }
    throw new InvalidCommandException();

    }


  //========================= Helper Methods ==============================
  // type in your helpers here, make sure you write a docstring.

  /**
   * Build a string from cmd list from index 1 to end.
   * @return - the build string.
     */
  private String buildString(){
    List<String> temp = cmdList.subList(1, cmdList.size());

    // build up the string
    String build = "";
    for (String s : temp) {
      build += s + " ";
    }//end of loop
    return build;
  }

  /**
   * Checks if user input has only one occurrence of ">>" or ">" .
   * @return bool
   */
  private boolean ArrowsChecker() {
    if(this.userInput.contains(">")){
      String temp = this.userInput + " ";
      int OneArr = temp.split(">").length;
      int TwoArr = temp.split(">>").length;
      // if user input has only one ">>"
      if ((TwoArr == 2) && (OneArr == 3)) { return true;}
      // if user input has only one ">"
      else if ((OneArr == 2)) { return true; }
      return false;}
    return true;
  }

  /**
   * Remove all the white spaces inside the cmdList.
   */
  private void removeWhiteSpace(){
    if(!this.userInput.contains("echo")){
      cmdList.removeAll(Arrays.asList("", null));
    }
    else{
//      userInput.trim();
      String cleanUI = this.userInput.trim();
      cmdList = new ArrayList<String>(){{add("echo");}};
      cmdList.add(cleanUI.substring(5, cleanUI.length()));
    }
  }


  private boolean runPreviousCommandsChecker() throws InvalidCommandException {
      if (cmdList.get(0).length() > 1) {
          if (cmdList.get(0).split(" ").length == 1) {
              String number = cmdList.get(0);
              if(number.matches("!\\d+")){
                  return true;
              }
          }else{
              throw new InvalidCommandException("Invalid input");
          }
      }else {
          throw new InvalidCommandException("Invalid input");
      }return false;
  }

  /**
   * Return true if cp command is valid, false otherwise. Checks for invalid
   * arguments, do not exceed this limit of 2.
   * @return - true if mv command is valid
   */
  private boolean cpChecker(){ return cmdList.size() == 3; }

  /**
   * Return true if mv command is valid, false otherwise. Checks if the
   * arguments do not exceed the limit of 2.
   * @return - true if mv command is valid.
     */
  private boolean mvChecker(){
    return cmdList.size() == 3;
  }

  /**
   * Return true if cd command is valid. False otherwise. Checks for invalid
   * arguments, number of arguments, and invalid characters.
   * @return - true if cd command is valid, false otherwise.
   */
  private boolean cdChecker(){
    if (cmdList.size() == 1){
      return true;
    }

    else if(cmdList.size() == 2){
      return true;
    }

    return false;
  }

  /**
   * Return true if mkdir command is valid. False otherwise. Checks for
   * invalid
   * arguments, number of arguments, and invalid characters.
   * @return - true if mkdir command is valid, false otherwise.
   */
  private boolean mkdirChecker() throws InvalidCommandException{
    if(cmdList.size() >= 2){
      // TODO:Cannot make a directory named "." or ".."
      for(String s: cmdList){
        if(patternMatch(s)){
          throw new InvalidCommandException("Invalid character: " + s);
        }
      }
      return true;
      }
    return false;
    }

    /**
     * Return true if get command is valid. False otherwise.
     * Checks for invalid arguments, number of arguments, and invalid characters/
     * @return true is get command is valid, false otherwise
     * @throws InvalidCommandException
     */
  private boolean getChecker() throws InvalidCommandException{
      if(cmdList.size() == 2){
          return true;
      }
      return false;
  }

  /**
   * Return true if ls command is valid. False otherwise. Checks for invalid
   * arguments, number of arguments, and invalid characters.
   * @return - true if ls command is valid, false otherwise.
   **/
  private boolean lsChecker() throws InvalidCommandException{
    if(cmdList.size() == 1){
      return true;
    }
    else if (cmdList.size() >= 2) {
      //check if the file exists in the tree
      for(String cmd : cmdList){
        if (patternMatch(cmd)){
          throw new InvalidCommandException("Invalid input");
        }
      }
      return true;
    }
    return false;
  }


  /**
   * Return true if cat command is valid. False otherwise. Checks
   * for invalid arguments, number of arguments, and invalid characters.
   * @return - true is cat command is valid
   */

  private boolean catChecker() throws InvalidCommandException {
      if(cmdList.size() >= 2){ //checks if the size if 2
//          if(!(cmdList.get(1).contains(invChars))){ //checks for invalid char
//              return true;
//          }
          for (String p: cmdList){
              if(patternMatch(p)){
                  throw new InvalidCommandException("Invalid character " + p);
              }
          }
          return true;
      }
      return false;
  }


  /**
   * Return true if pushd command is valid. False otherwise. Checks for
   * invalid arguments, number of arguments, and invalid characters.
   * @return - true if pushd command is valid.
   */
  private boolean pushdChecker(){
    if(cmdList.size() == 2){ //checks if the size if 2
      if(!(cmdList.get(1).contains(invChars))){ //checks for invalid char
        return true;
      }
    }
    return false;
  }

  /**
   * Return true if history command is valid. False otherwise. Checks for
   * invalid arguments, number of arguments, and invalid characters.
   * @return - true if history command is valid, false otherwise.
   */
  private boolean historyChecker(){
    if(cmdList.size()==2){
      if(!(cmdList.get(1).contains(invChars)) && !(alpha.contains(cmdList
          .get(1)))){
        return true;
      }
    }
    return true;
  }

  /**
   * Return true if echo command is valid. False otherwise. Checks for invalid
   * arguments, number of arguments.
   * @return true if echo command is valid, false otherwise.
   */
  private boolean echoTypeChecker(){
    // check if there is a '>' in between a string and the path
    if((cmdList.contains(">")) | (cmdList.contains(">>"))) {
      return true;
    // else check if it only contains a string
    } else if(cmdList.size() >= 1) {
        return (!cmdList.contains(">") | !cmdList.contains(">>"));}
    // return false if all cases failed
    return false;
  }

  /**
   * Return true if echo command is valid. False otherwise. Checks for invalid
   * arguments, number of arguments.
   * @return true if echo command is valid, false otherwise.
   */
  /**
   *
   * @return
   */
  private boolean pwdChecker() {
    return cmdList.size() == 1 || cmdList.size() == 3;
  }

  /**
   * Return true if popd command is valid, false otherwise. Checks for any
   * arguments.
   * @return - true if popd command is valid, false otherwise.
   */
  private boolean popdChecker(){
    return cmdList.size() == 1;
  }


  /**
   *  Return true if popd command is valid, false otherwise. Checks for any
    *arguments.
   * return - true if manual command is valid
   */
  private boolean manChecker() { return cmdList.size() >= 2;}

  /**
   * Return true if grep command is valid, false otherwise. Checks if number
   * of arguments are correct.
   * @return - true if grep command is valid.
     */
  private boolean grepChecker(){
    return cmdList.size() >=3;
  }


  private boolean patternMatch(String input){
    Pattern p = Pattern.compile("[~#@*+%{}<\\\\[\\\\]|\\\"\\\\_^]");
    Matcher m = p.matcher(input);
    return m.find();
  }

}


