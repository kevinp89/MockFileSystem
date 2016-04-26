package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import path.PathIterator;

import javax.xml.bind.annotation.XmlTransient;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import static org.junit.Assert.*;


public class PathIteratorTest extends PathIterator {

  PathIterator p1;
  PathIterator p2;
  PathIterator p3;
  PathIterator p4;

  @Before
  public void setUp() {
    TreeMap<Integer, String> path1 = new TreeMap<Integer, String>();
    p1 = new PathIterator(path1);
    TreeMap<Integer, String> path2 = new TreeMap<Integer, String>();
    path2.put(0, "a");
    path2.put(1, "b");
    path2.put(2, "c");
    path2.put(3, "d");
    p2 = new PathIterator(path2);
    TreeMap<Integer, String> path3 = new TreeMap<Integer, String>();
    path3.put(0, "/");
    path3.put(1, "a");
    path3.put(2, "b");
    path3.put(3, "c");
    path3.put(4, "d");
    p3 = new PathIterator(path3);
    TreeMap<Integer, String> path4 = new TreeMap<Integer, String>();
    path4.put(0, "dir1");
    path4.put(1, "dir2");
    path4.put(2, "dir3");
    p4 = new PathIterator(path4);
  }

  @After
  public void tearDown() {
    p1 = null;
    p2 = null;
    p3 = null;
    p4 = null;
  }

  @Test
  public void testHasNext1() {
    boolean expected = false;
    assertEquals(expected, p1.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testNext1() {
    Object expected = null;
    assertEquals(expected, p1.next());
  }

  @Test
  public void testHasNext2() {
    boolean expected = true;
    assertEquals(expected, p2.hasNext());
  }

  @Test
  public void testNext2() {
    String expected = "a";
    assertEquals(expected, p2.next());
  }

  @Test
  public void testNextAll2() {
    String[] expected = {"a", "b", "c", "d"};
    String[] actual = new String[4];
    int i = 0;
    while (p2.hasNext()) {
      actual[i] = p2.next();
      i++;
    }
    assertEquals(expected, actual);
  }

  @Test
  public void testHasNext3() {
    boolean expected = true;
    assertEquals(expected, p3.hasNext());
  }

  @Test
  public void testNextAll3() {
    String[] expected = {"/", "a", "b", "c", "d"};
    String[] actual = new String[5];
    int i = 0;
    while (p3.hasNext()) {
      actual[i] = p3.next();
      i++;
    }
    assertEquals(expected, actual);
  }

  @Test
  
  public void testHasNext4() {
    boolean expected = true;
    assertEquals(expected, p4.hasNext());
  }

  @Test
  /**
   * Tests for 4 paths
   */
  public void testNextAll4() {
    String[] expected = {"dir1", "dir2", "dir3"};
    String[] actual = new String[3];
    int i = 0;
    while (p4.hasNext()) {
      actual[i] = p4.next();
      i++;
    }
    assertEquals(expected, actual);
  }

}
