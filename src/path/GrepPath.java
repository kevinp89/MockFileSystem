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

import commands.Grep;

import java.util.Iterator;
import java.util.TreeMap;

public class GrepPath implements Path, Iterable<ConcretePath>{

  private String UIpath;
  private boolean recursive;
  private String regEx;
  private TreeMap<Integer, ConcretePath> path;

  private boolean mutiplePaths = false;

  public GrepPath(){}

  public GrepPath(String args){
      this.UIpath = args;
      populate();
  }


  /**
   * Set recursive true if -R or -r is present, populate regEx and path
   * accordingly.
   */
  private void populate(){
    //initialize Map
    this.path = new TreeMap<Integer, ConcretePath>();
    //split the input
    String[] cmd = UIpath.split(" ");
    //check for -R or -r.
    if(cmd[0].equals("-R") || cmd[0].equals("-r")){
        this.recursive = true;
        this.regEx = cmd[1];
        this.populatePaths(cmd);
    }
    //populate if recursive not present.
    else{
      this.regEx = cmd[0];
      this.populatePaths(cmd);
    }
  }

  /**
   * Populate paths by splitting pathString at " ".
   * @param pathString - a string version of user inputted path.
   */
  private void populatePaths(String[] pathString){

    if(pathString.length > 2){
      this.mutiplePaths = true;
    }

    if(pathString[0].equals("-R") || pathString[0].equals("-r")){
      for(int i=2;i<pathString.length;i++){
        ConcretePath p = new ConcretePath(pathString[i]);
        if(this.isRecursive()){p.setRecursive(true);}
        if(pathString.length > 2){
            p.setMultiplePath(true);
        }
        path.put(i, p);
      }
    }
    else{
      for(int i=1;i<pathString.length;i++){
        ConcretePath p = new ConcretePath(pathString[i]);
        if(this.isRecursive()){p.setRecursive(true);}
        if(pathString.length > 2){
            p.setMultiplePath(true);
        }
        path.put(i, p);
      }
    }
  }

  /**
   * Return the User inputted Path.
   * @return - The user Inputted Path.
   */
  public String getUIpath() {
    return UIpath;
  }

  /**
   * Return true if Recursive option present.
   * @return - true if recursive option present.
   */
  public boolean isRecursive() {
    return recursive;
  }

  /**
   * Return the regular expression inputted by user.
   * @return - regular expression inputted.
   */
  public String getRegEx() {
    return regEx;
  }

  /**
   * Return the Path from this class.
   * @return - path from this class.
   */
  public TreeMap<Integer, ConcretePath> getPath() {
    return path;
  }

  @Override public Iterator iterator() {
    return new MultiplePathIterator(path);
  }

  @Override public boolean nullPath() {
    return UIpath.equals("");
  }

  @Override public boolean rootDirExists() {
    ConcretePath p = path.get(0);
    return p.rootDirExists();
  }

  public boolean rootOnly(){
    ConcretePath p = path.get(0);
    return p.rootOnly();
  }

  @Override public String getUIPath() {
    return UIpath;
  }

  public boolean isMutiplePaths() {
    return mutiplePaths;
  }

  public static void main(String[] args) {
    GrepPath path = new GrepPath("-R /a/v /e/b/s /s/a/d");
    System.out.println(path.getPath());
    System.out.println(path.isMutiplePaths());
  }
}

