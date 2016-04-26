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
//

package Stack;

import java.util.ArrayList;

import Tree.FileSystemTree;
import exceptions.EmptyFileStackException;

public class FileStack {

    private static ArrayList<String> stackArray;


    public FileStack(){

        stackArray = new ArrayList<String>();
    }

    /**
     * Adds files to the FileStack
     * @param filePath - a File
     */
    public void push (String filePath){

        stackArray.add(filePath);
    }

    public int size(){

        return stackArray.size();
    }

    /**
     * Removes/pops files from the FileStack in a FIFO order
     * @return - the popped file
     * @throws EmptyFileStackException
     */
    public String pop() throws EmptyFileStackException{

        if (!(stackArray.isEmpty())){

            String fileToBeReturned = stackArray.get(stackArray.size
                () - 1);

            stackArray.remove(fileToBeReturned);

            return fileToBeReturned;
        }
        throw new EmptyFileStackException();

    }


    }
