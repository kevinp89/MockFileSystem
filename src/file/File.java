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


public abstract class File {

  //keeps track of the name.
  protected String name;

  //default constructor.
  public File(){}
  //Constructor with a name.
  public File(String name){
    this.name = name;
  }

  /**
   * Return the name of the current File.
   * @return - Name of the current File.
     */
  public String getName(){
        return this.name;
    }

  /**
   * Return true if other is an instance of File sub classes and other equals
   * this
   * File. False Otherwise
   * @param other - An Object.
   * @return - true if other is an instance of File sub classes and other
   * equals this
   * File. False Otherwise
     */
  public abstract boolean equals(Object other);

  /**
   * Return true if given String equals File name.
   * @param fileName - String of a name.
   * @return - true if given name equals File name, false otherwise.
     */
  public boolean fileNameEquals(String fileName){
    return this.name.equals(fileName);
  }

  /**
   * Add text to the RW File.
   * @param text - text to be added to RW File.
     */
  public abstract void add(String text);

  /**
   * Replace text inside RW File.
   * @param text - text to be replaced inside RW File.
     */
  public abstract void replaceText(String text);
}
