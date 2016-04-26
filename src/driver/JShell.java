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

package driver;


import Printer.Printer;
import Runner.Runner;
import checkers.ValidityChecker;
import java.util.Scanner;

import commands.History;
import file.Directory;
import Tree.FileSystemTree;

/**
 *   JShell Class that runs the terminal
 *
 */


public class JShell {

private static Scanner userInput = new Scanner(System.in);

	private FileSystemTree FST;

	public JShell(){
		FST = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
		this.runShell();
	}

	public static void main(String[] args) {

			JShell shell = new JShell();

//				JShell shell2 = new JShell();
//				shell2.runShell();
			//TODO: fix ls spacing thing.
	}

	/**
	 * Run JShell.
	 */
	private void runShell(){
		Printer print = new Printer("/#", " ", " ");

			print.printStartUpMsg();
			print.printPrompt(); //promptLine
			String user = userInput.nextLine();

			while(true){
				if(user.equals("exit")){
						break;
				}
				else{
					try {
						String[] userInputSplit = user.split(" ");
						History hs = new History(userInputSplit);

						//check validity
						ValidityChecker checker = new ValidityChecker(user, userInputSplit);
						String[] clearCmd = checker.checkValid();

						//run the command
						Runner run = new Runner();
						String[] output = run.act(clearCmd, this.FST);

						// set the print output and promptLine
						print.setPromptLine(output[0]);
						print.setOutput(output[1]);

						print.printOutput();
						user = userInput.nextLine();
					}
					//catch the exception from Validity checker & runner, and
					// print them.
					catch (Exception e){
						print.setError(e.getMessage());
						print.printErr();
						user = userInput.nextLine();
					}
				}
			}
	}

}
