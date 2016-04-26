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

public class PathObjectIterator implements PathIterate {

  private TreeMap<Integer, ConcretePath> path;
  private Iterator<Integer> i;

  public PathObjectIterator(TreeMap<Integer, ConcretePath> p){
    this.path = p;
    i = p.navigableKeySet().iterator();
  }

  @Override public boolean hasNext() {
      return i.hasNext();
  }

  @Override public ConcretePath next() {
      return path.get(i.next());
  }


  public boolean equals(Object o){

    return o instanceof PathObjectIterator && this.path.equals((
            (PathObjectIterator)o).path);}
}
