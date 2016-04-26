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


package file;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Read Write Files are text files
 */
public class RWFile extends File{

  private ArrayList<String> text;

  public RWFile(){}

  public RWFile(String name){
    super(name);
//    addExtension();
    this.text = new ArrayList<String>();
  }

  /**
   * Gets the name of the file
   * @return name - A string.
   */
  public String getFileName(){
      return name;
  }

  /**
   * Takes in a string and puts it into the file
   * @param text - a string
   */
  public void add(String text){
    if(text.length() >= 1){
      if(text.substring(text.length()-1).equals("\n")){
          this.text.add(text);}
      else{
        this.text.add(text);}
      }
  }

  /**
   * Clears the ArrayList text and replaces the content with the String text
   * @param text - A string
   */
  public void replaceText(String text) {
    this.text.clear();
      if(text.substring(text.length()-1).equals("\n")){
          this.text.add(text);
      }
      else{
          this.text.add(text);
      }
  }

  public String getText(){
    String textToPrint = "";
    for(String s : text){
      textToPrint += s;
    }
    return textToPrint;
  }

  /**
   * Returns the string representation of the filename.
   * @return name - A string of the name of the file.
   */
  public String toString(){ return name; }

  /** TODO: might not need this
   * Adds the extension .txt to files that do not have the extension.
   */
  private void addExtension(){
    String temp = name;
        //+ ".txt";
    if(name.contains(".")){
      temp = name.split("\\.")[0];
      temp += ".txt";
    }
    name = temp;
    }

  /**
   * Checks if rwfile already exists
   * @param object - the Object name to search for
   * @return boolean
   */
  public boolean equals(Object object) {
    if (object instanceof RWFile) {
      RWFile obj = (RWFile) object;
      return (this.name.equals(obj.name));
    }
    return false;
  }
}
