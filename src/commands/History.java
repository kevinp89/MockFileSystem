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

import java.util.ArrayList;
import java.util.logging.StreamHandler;

public class History {
  protected static String manual = "command History\n" +
    "argument: none\n" +
    "Imoprtant notes:\n" +
    "* shows history of the terminal: the last 5 commands that were " +
    "carried out \n" +
    "Invalid characters: ~!@#$%^&*()_+`-=|/[]{};':,/<>?\\\" \n \n  ";

  // Tracker for History, stores History.
  private static ArrayList<String> history = new ArrayList<String>();

  public History(){}

  /**
   * Adds the commands to the ArrayList History in the form of strings.
   * @param cmd - the commands
   */
  public History(String[] cmd){

    String s = "";

    for (String c : cmd){

        s += c + " ";
    }

      history.add(s.substring(0, s.length()-1));
  }

  /**
   * Returns all the commands provided in order from oldest to newest
   * @return - all commands in order.
   */

  public static String getHistory(){
      return getHistory(history.size());
  }

  /**
   * Return the size of the history.
   * @return - the size of the history.
     */
  public static Integer getHistorySize() {return history.size();}

  /**
   * Get the History arrayList.
   * @return - the History ArrayList.
     */
  public static ArrayList<String> getHistoryNoNumbers() {return history;}

  public static ArrayList<String> getHistoryNoNumbersSet(int n) {
    return new ArrayList<String>(history.subList(0,n));
  }


  /**
   *Returns the n latest commands in order from oldest to newest.
   * @param n - number of commands to be returned (the n newest)
   * @return - the n latest commands in order
   */
  public static String getHistory(int n){
    //show history
    String hs = "";
    int number = history.size();
    int i = history.size() - 1;
    while(n>0){
      hs= number + ". " + history.get(i) +"\n" + hs;
      n--;
      i--;
      number--;
    }

    return hs.substring(0, hs.length()-1);
  }

}



