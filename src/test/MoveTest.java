package test;

import Tree.FileSystemTree;
import commands.Command;
import commands.Mkdir;
import commands.Move;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.ConcreteMultiplePath;
import path.ConcretePath;

import static org.junit.Assert.*;


public class MoveTest extends Move {
    FileSystemTree fst;

    @Before public void setUp() throws Exception {
        fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
        Command.setCurrDirectory(fst);
        fst.addFile("a");
        fst.addFile("b");
        fst.getSubFile("a").addFile("e");
    }

    @After public void tearDown() throws Exception {
        fst = fst.setFileSystemReferenceNull();
        Command.setCurrentDirectoryNone();
    }

    @Test
    public void testMoveSimple() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/a/e /b"));
        mv.run(fst);
        assertEquals(true, fst.getSubFile("b").hasChild("e"));
        assertEquals(false, fst.getSubFile("a").hasChild("e"));
    }

    @Test (expected = FileDoesNotExistException.class)
    public void testMoveSimpleErr() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("e /b"));
        mv.run(fst);
    }

    @Test
    public void testMoveComplex1() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/a/.././a/e /b"));
        mv.run(fst);
        assertEquals(true, fst.getSubFile("b").hasChild("e"));
        assertEquals(false, fst.getSubFile("a").hasChild("e"));
    }

    @Test
    public void testComplex2() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/a/.././a/e /b/.././b"));
        mv.run(fst);
        assertEquals(true, fst.getSubFile("b").hasChild("e"));
        assertEquals(false, fst.getSubFile("a").hasChild("e"));
    }

    // multiple slashes should not raise an error.
    @Test
    public void testComplexWithMultipleSlash() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/a///.././a/e /b/.././b"));
        mv.run(fst);
        assertEquals(true, fst.getSubFile("b").hasChild("e"));
        assertEquals(false, fst.getSubFile("a").hasChild("e"));
    }

    @Test (expected = FileDoesNotExistException.class)
    public void testComplexErr() throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/a///.././a/e/k /b/../"
            + "./b"));
        mv.run(fst);
    }

    @Test (expected = FileDoesNotExistException.class)
    public void testComplexErrSourceRoot()throws Exception{
        Move mv = new Move(new ConcreteMultiplePath("/ /a"));
        mv.run(fst);
    }
}
