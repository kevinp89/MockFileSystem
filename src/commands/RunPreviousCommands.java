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
package commands;



import Runner.Runner;
import Tree.FileSystemTree;
import checkers.ValidityChecker;
import exceptions.InvalidCommandException;
import path.ConcretePath;
import path.Path;

import java.util.ArrayList;
import java.util.Scanner;

//Initializing Constructor
public class RunPreviousCommands extends Command {

    //default constructor
    public RunPreviousCommands(){}
    //constructor with a Path.
    public RunPreviousCommands(Path p){super(p);{}}

    /**
     * Run ![number] command
     * @param fst - a fst root node.
     * @return - the output for the command.
     * @throws Exception
     */
    @Override
    public String[] run(FileSystemTree fst) throws Exception {

        try{
            if(PATH instanceof ConcretePath){
                ConcretePath path = (ConcretePath) PATH;

                String numberedPath = path.getUIPath();
                String intPath = numberedPath.substring(1);
                int pathNumber = Integer.parseInt(intPath);
                History history = new History();
                ArrayList<String> historyCommands = history.getHistoryNoNumbers();
                int historyNumber = history.getHistorySize();

                if(pathNumber <= historyNumber && pathNumber > 0){
                    String commandsOutput = "";
                    String promptLine = getCurrentDirName();
                    ArrayList<String> numberCommandsHistory = history.getHistoryNoNumbersSet(pathNumber);
                    for(String command: numberCommandsHistory){
                        String[] userInputSplit = command.split(" ");
                        ValidityChecker vld = new ValidityChecker(command, userInputSplit);
                        String[] clearCmd = vld.checkValid();

                        Runner run = new Runner();
                        String[] output = run.act(clearCmd, fst);
                        commandsOutput += output[1] + "\n";
                        promptLine = output[0];

                    } return new String[]{promptLine, commandsOutput};

                }
                else{
                    throw new InvalidCommandException("History size is less than " + pathNumber);
                }
            }throw new InvalidCommandException("Invalid command");


        } catch (InvalidCommandException e){
            throw new InvalidCommandException("History command invalid!");
        }

    }
}
