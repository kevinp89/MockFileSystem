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
package path;

import java.util.Iterator;
import java.util.TreeMap;


public class ConcreteEchoPath implements Path, Iterable<ConcretePath>  {

  // tracker for User Input.
  private String UI;
  // Tracker for text.
  private String text;
  // path tracker.
  private TreeMap<Integer, ConcretePath> path;
  //default constructor
  public ConcreteEchoPath(){}
  //constructor with user inputted string.
  public ConcreteEchoPath(String UInput){
    UI = UInput;
    populateTextPath();
    textCleanUp();
  }

  /**
   * This method splits up path to make it work according to the input
   */
  private void populateTextPath(){
    String[] splitList = (UI.contains(">>")) ? UI.split(">>") : UI.split(">");
    text = splitList[0].trim();
    if(splitList[0].contains("\"")){
      text = text.replaceAll("\"", "");
    }
    path = new TreeMap<Integer, ConcretePath>();
    //if outfile is provided.
    if(splitList.length > 1){
      path.put(0, new ConcretePath(splitList[1]));}
    //only if no outfile is provided.
    else{
      path.put(0, new ConcretePath(splitList[0]));
    }
  }

  /**
   * Cleans text up of extra "\"'s
   */
  private void textCleanUp(){
    text = text.trim();
//    text = text.replace("\"", "");
  }

  /**
   * This method gives the text
   * @return String of text
   */
  public String getText(){ return text; }

  /**
   * This method gives the path in question
   * @return path
   */
  public ConcretePath getPath(){ return path.get(0); }

  /**
   * Verifies whether  ">" is contained
   * @return True/False
   */
  public boolean containsOneArr(){ return UI.contains(">"); }

  /**
   * Verifies whether ">>" is contained
   * @return True/False
   */
  public boolean containsTwoArr(){ return UI.contains(">>"); }

  /**
   * Return null as iterator
   * @return - null as iterator.
     */
  @Override public Iterator<ConcretePath> iterator() {
    return null;
  }

  /**
   * Return true if path is null, does not apply for this class
   * @return - true if path is null, does not apply for this class
     */
  @Override public boolean nullPath() {
    return false;
  }

  /**
   * method does not apply for this class.
   * @return - return false
     */
  @Override public boolean rootDirExists() {
    return false;
  }

  /**
   * Method does not apply for this class.
   * @return - return null.
     */
  @Override public String getUIPath() {
    return null;
  }

}

