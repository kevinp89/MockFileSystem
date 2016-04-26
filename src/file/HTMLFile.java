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

public class HTMLFile extends File{
	
	private ArrayList<String> text;
	
	public HTMLFile(){}
	
	public HTMLFile(String name){
		super(name);
		this.text = new ArrayList<String>();
	}



    public String getName(){
        return name;
    }


    /**
     * Checks if HTMMLFile already exists
     * @param other - the Object name to search for
     * @return boolean
     */

	@Override
	public boolean equals(Object other) {
        if (other instanceof HTMLFile) {
            HTMLFile other1 = (HTMLFile) other;
            return (this.name.equals(other1.name));
        }
		return false;
	}


	@Override
	public void add(String text) {
		if(text.length() >= 1){
            if(text.substring(text.length()-1).equals("\n")){
                this.text.add(text);
            }
            else{
                this.text.add(text);
            }
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
     * Clears the ArrayList text and replaces the content with the String text
     * @param text - A string
     */

	@Override
	public void replaceText(String text) {

        this.text.clear();
        if(text.substring(text.length()-1).equals("\n")){
            this.text.add(text);
        }
        else{
            this.text.add(text);
        }

		
	}
	

}
