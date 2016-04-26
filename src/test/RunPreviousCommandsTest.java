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
package test;

import Tree.FileSystemTree;
import commands.Command;
import commands.RunPreviousCommands;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcretePath;



public class RunPreviousCommandsTest extends RunPreviousCommands{

    FileSystemTree fst;

    @Before
    public void setUp() throws Exception {
        fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
        Command.setCurrDirectory(fst);
    }

    @After
    public void tearDown() throws Exception {
        fst = fst.setFileSystemReferenceNull();
        Command.setCurrentDirectoryNone();
    }
    @Test
    public void testPreviousCommandsSimple() throws Exception{
        RunPreviousCommands rpc = new RunPreviousCommands(new ConcretePath("!2"));
        rpc.run(fst);
    }

}