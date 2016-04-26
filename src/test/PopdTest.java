package test;

import Tree.FileSystemTree;
import commands.Popd;
import commands.Pushd;
import exceptions.EmptyFileStackException;
import exceptions.FileDoesNotExistException;
import file.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PopdTest extends Popd {

  FileSystemTree fst;

  @Before
  public void setUp() throws Exception {
    fst = FileSystemTree.createNewFileSystemInstance(new Directory("/"));
  }

  @After
  public void tearDown() throws Exception {
    fst = null;
  }

  @Test
  /**
   * test popping a singular item with an empty return
   */
  public void testRunSimple() throws Exception {
    fst.addFile(new Directory("a"));
    Pushd p = new Pushd("a");
    p.run(fst);

    Popd pop = new Popd("");
    String[] actual = pop.run(fst);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, actual);
  }

  /**
   * Testing pop for a simple error
   * @throws Exception
   */
  @Test(expected = EmptyFileStackException.class)
  public void testRunSimpleErr() throws Exception {
    Popd pop = new Popd("");
    pop.run(fst);
  }

  /**
   * A complex case for pop with multiple items popped
   * @throws Exception
   */
  @Test
  public void testRunComplex() throws Exception {
    fst.addFile(new Directory("a"));
    fst.getChild(new Directory("a")).addFile(new Directory("b"));
    Pushd p = new Pushd("/a/b");
    p.run(fst);

    Popd pop = new Popd("");
    String[] actual = pop.run(fst);
    String[] expected = new String[]{"/#", ""};
    assertEquals(expected, actual);
  }

  /**
   * Testing for pop error
   * @throws Exception
   */
  @Test(expected = EmptyFileStackException.class)
  public void testRunComplexErr() throws Exception {
    Popd pop = new Popd("");
    pop.run(fst);
  }


}
