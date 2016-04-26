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
import commands.GetURL;
import exceptions.InvalidCommandException;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.URLPath;

import static org.junit.Assert.*;

public class GetURLTest extends GetURL {
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
    public void testGetTxtURL() throws Exception{
        GetURL getUrl = new GetURL(new URLPath("https://www.cs.cmu.edu/~spok/grimmtmp/073.txt"));
        getUrl.run(fst);
        assertEquals(true, fst.hasChild("073.txt"));
    }

    @Test
    public void testGetHtmlURL() throws Exception{
        GetURL getUrl = new GetURL(new URLPath("http://archive.ncsa.illinois.edu/primer.html"));
        getUrl.run(fst);
        assertEquals(true, fst.hasChild("primer.html"));
    }

    @Test(expected = InvalidCommandException.class)
    public void testInvalidUrl() throws Exception {
        GetURL getUrl = new GetURL(new URLPath("http://time.com/4269247/star-exploding-video-gif/?xid=time_socialflow_facebook"));
        getUrl.run(fst);
    }


    @Test
    public void testRun() throws Exception {

    }
}