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

public class PathIterator implements PathIterate, Iterator<String>{
  // path treeMap.
  private TreeMap<Integer, String> path;
  private Iterator<Integer> iter;

  public PathIterator(){}
  public PathIterator(TreeMap<Integer, String> path){
    this.path = path;
    this.iter = this.path.navigableKeySet().iterator();
  }

  @Override public boolean hasNext() {
    return iter.hasNext();
  }

  @Override public String next() {
      return path.get(iter.next());
  }

  @Override public void remove() {
  }
  public boolean equals(Object o){
    return o instanceof PathIterator && this.path.equals(((PathIterator)o)
        .path);
  }
}
