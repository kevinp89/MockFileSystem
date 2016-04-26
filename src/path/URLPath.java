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


import exceptions.InvalidCommandException;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

public class URLPath implements Path {

  private String URLPath;

  boolean txtFile = false;
  boolean htmlFile = false;

  public URLPath() {}

  //Initializing URL path
  public URLPath(String urlPath) throws InvalidCommandException {
    this.URLPath = urlPath;
    this.checkURL();
  }

  public void checkURL() throws InvalidCommandException {
    if(URLPath.endsWith(".txt")){
        txtFile = true;
    }
    else if(URLPath.endsWith(".html")){
        htmlFile = true;
    }
    else{
        throw new InvalidCommandException("It is not a valid URL");
    }
  }

  @Override
  public Iterator iterator() {
      return null;
  }

  @Override
  public boolean nullPath() {
      return false;
  }

  @Override
  public boolean rootDirExists() {
      return false;
  }

  public String getURLPath() {
      return URLPath;
  }

  public boolean isTxtFile() {
      return txtFile;
  }

  public boolean isHtmlFile() {
      return htmlFile;
  }

  @Override
  public String getUIPath() {
      return null;
  }

  public String getURLName(){

    return URLPath.substring(URLPath.lastIndexOf("/") + 1, URLPath.length());


  }

}
