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
package Printer;


public class Printer {
    //tracker for prompt line
    String promptLine;
    // tracker for output/
    String output;
    //tracker for error
    String error;
    //default constructor
    public Printer(){}
    //constructor with prompt, output, and error.
    public Printer(String prompt, String output, String err){
        this.promptLine = prompt;
        this.output = output;
        this.error = err;
    }

    /**
     * set promptLine to newPrompt.
     * @param newPrompt - a new prompt.
     */
    public void setPromptLine(String newPrompt){
        if(!newPrompt.equals("")){
            promptLine = newPrompt;
        }
    }

    /**
     * set output to newOutput
     * @param newOutput - a new Output.
     */
    public void setOutput(String newOutput){
        this.output = newOutput;
    }

    /**
     * set error to newError
     * @param newErr - a new Error
     */
    public void setError(String newErr){
        this.error = newErr;
    }

    /**
     * printout the output and promptline after it.
     */
    public void printOutput(){
        if(!output.equals("")){
            System.out.println(output);
        }
        System.out.print(promptLine + " ");
    }

    /**
     * print only the promptline.
     */
    public void printPrompt(){
        System.out.print(promptLine + " ");
    }

    /**
     * print out the error and the prompt line after it.
     */
    public void printErr(){
        System.out.println(error);
        printPrompt();
    }

    /**
     * Print the startup message.
     */
    public void printStartUpMsg(){
        String msg = "Welcome User!\n"
            + "Here are the commands you will be using: cd, mkdir, echo, cat,"
            + " ls, pushd, popd, pwd, history, man, get, number, grep, move, redirect."
            + "To get help for any of the commands, please type in \'man "
            + "[command]. "
            + "For redirection, type: [command] > [>>] filePath + \n"
            + "To quit the program, please type in \'exit\'." + "\n";
        System.out.println(msg);
    }


}
