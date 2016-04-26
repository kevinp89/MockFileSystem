package test;

import Stack.FileStack;
import Tree.FileSystemTree;
import exceptions.EmptyFileStackException;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class FileStackTest extends FileStack {

  private FileSystemTree fst;
  private FileStack stack;

  @Before
  public void setUp() throws Exception {

    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
    stack = new FileStack();
  }


  @After
  public void tearDown() throws Exception {


  }


  @Test(expected = EmptyFileStackException.class)
  public void testEmptyStackPop() throws Exception {
    stack.pop();

  }

  @Test
  public void testPop() throws Exception {

    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.pop();
    int actual = stack.size();
    int expected = 1;
    assertEquals(expected, actual);

  }

  @Test
  public void testPopSeveral() throws Exception {

    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.pop();
    stack.pop();
    stack.pop();
    int actual = stack.size();
    int expected = 2;
    assertEquals(expected, actual);

  }


  @Test
  public void testPushSimple() throws Exception {

    stack.push(fst.getPath());
    int actual = stack.size();
    int expected = 1;
    assertEquals(expected, actual);

  }

  @Test
  public void testPushSeveral() throws Exception {

    stack.push(fst.getPath());
    stack.push(fst.getPath());
    stack.push(fst.getPath());
    int actual = stack.size();
    int expected = 3;
    assertEquals(expected, actual);

  }
}
