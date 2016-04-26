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

public class Directory extends File{

	//empty Constructor.
	public Directory(){}
	//Construct Directory with a name.
	public Directory(String name){
		super(name);}

	/**
	 * Return true if other is a Directory and and other name equals this
	 * Directory name
	 * @param other - An Ojbect.
	 * @return - true if other is a Directory and other name equals this
	 * Directory name.
     */
	@Override public boolean equals(Object other) {
			if(other instanceof Directory){
					Directory dir = (Directory) other;
					return this.name.equals(dir.name);
			}
			return false;
	}

	// Methods do not do anything.
	@Override public void add(String text) {}
	@Override public void replaceText(String text) {}

	/**
	 * Return the name of the Directory.
	 * @return - The name of the Directory.
     */
	public String getDirName() {
			return name;
	}

	/**
	 * Return a string representation of the Directory.
	 * @return - a String representation of the Directory.
	 */
	public String toString(){
			return name;
	}

}

