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

public class ConcreteMultiplePath implements Path, Iterable<ConcretePath>  {

    //tracker for user Input
    private String UI;
    //tacker for multiple paths.
    private TreeMap<Integer, ConcretePath> multiplePaths;
    //recursive tracker
    private boolean recursive = false;
    //redirection tracker.
    private boolean redirection = false;
    //default constructor
    public ConcreteMultiplePath(){}
    //constructor with user inputted arguments.
    public ConcreteMultiplePath(String args){
      UI = args;
      multiplePaths = new TreeMap<Integer, ConcretePath>();
      this.populateMultiplePath();
    }

    /**
     * Populate multiple paths with key as an integer starting from 0, and
     * value as a Concrete path of each path entered by the User.
     */
    private void populateMultiplePath(){
        if(UI.split(" ").length > 1){
            String[] paths = UI.split(" ");

            if(paths[0].equals("-R") || paths[0].equals("-r")){
                this.recursive = true;
                for(int i=1; i<paths.length;i++){
                    ConcretePath p = new ConcretePath(paths[i]);
                    multiplePaths.put(i, p);
                }
            }

            for(int i=0;i<paths.length;i++){
                ConcretePath p = new ConcretePath(paths[i]);
                multiplePaths.put(i, p);
            }
        }
      }



    /**
     * Return true if user inputted path has -R as an option.
     * @return - true if path is recursive.
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * Return the User input.
     * @return - the user input.
     */
    public String getUI() {
        return UI;
    }

    /**
     * Return a MultiplePathIterator for multiplePaths.
     * @return - a MultiplePathIterator
     */
    @Override public Iterator<ConcretePath> iterator() {
        return new MultiplePathIterator(multiplePaths);
    }

    /**
     * Return true if user has entered no path:
     *     for ex: "cd"
     * @return - true if UI is an empty string.
     */
    @Override public boolean nullPath() {
        return UI.equals("");
    }

    /**
     * Return true if the path provided starts at root "/".
     * @return - true if path provided starts at root.
     */
    @Override public boolean rootDirExists() {
        return UI.startsWith("/");
    }

    /**
     * Return user input path.
     * @return - return UI
     */
    @Override public String getUIPath() {
        return UI;
    }

    /**
     * Return the current multiplePaths.
     * @return - multiplePaths.
     */
    public TreeMap<Integer, ConcretePath> getMultiplePaths(){
        return multiplePaths;
    }

    /**
     * return true if redirection is true.
     * @return - true if redirection is true.
     */
    public boolean getRedirection(){
      return redirection;
    }
}
