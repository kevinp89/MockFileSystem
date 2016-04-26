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

package Tree;
import exceptions.FileDoesNotExistException;
import file.File;
import file.*;

import java.lang.reflect.Array;
import java.util.*;


public class FileSystemTree {

  private static FileSystemTree fileSystemReference = null;
  private ArrayList<FileSystemTree> children = new ArrayList<FileSystemTree>();
  private FileSystemTree parent;
  private File filename;

  /**
   * Constructor given a file name and no parent
   * @param filename - a file to name the newly created FileSystemTree
   */
  private FileSystemTree(File filename){
    this.filename = filename;
    this.parent = null;
  }

  /**
   * Creates a new FileSystemTree Node Instance.
   * @param fileName File instance
     * @return A new FileSystem Node.
     */
  public static FileSystemTree createNewFileSystemInstance(File fileName){
    if(fileSystemReference == null){
      fileSystemReference = new FileSystemTree(fileName);
    }
    return fileSystemReference;
  }

  /**
   * NOTE: ONLY TO BE USED WHEN WRITING TESTS.
   * Sets fileSystemReferent to null, erasing the entire fst.
   */
  public FileSystemTree setFileSystemReferenceNull(){
    fileSystemReference = null;
    return fileSystemReference;
  }

  /** Add File to the tree.
   *
   * @param file - A File.
   */
  public void addFile(File file){
    FileSystemTree newFile = new FileSystemTree(file);
    newFile.parent = this;
    children.add(newFile);
  }

  /**
   * Add A FileSystemTree Node to tree.
   * @param file - A FileSystemTree Node.
     */
  public void addFile(FileSystemTree file){
    file.parent = this;
    children.add(file);
  }

  /**
   * Convert fileName into a directory or a rwfile and add to the tree.
   * @param fileName - name of a file.
     */
  public void addFile(String fileName){
    if(fileName.contains("\\.")){
      RWFile rw = new RWFile(fileName);
      FileSystemTree newFile = new FileSystemTree(rw);
      newFile.parent = this;
      children.add(newFile);
    }
    else{
      Directory dir = new Directory(fileName);
      FileSystemTree newFile = new FileSystemTree(dir);
      newFile.parent = this;
      children.add(newFile);
    }
  }

  /**
   * Remove this node from fst.
   * @throws FileDoesNotExistException
     */
  public void deleteCurrentNode()throws FileDoesNotExistException{
    try{
      FileSystemTree parent = this.getParent();
      int index = parent.children.indexOf(this);
      parent.children.remove(index);

    }catch (FileDoesNotExistException e){
      throw new FileDoesNotExistException("Cannot remove root Node.");
    }
  }

  /**
   * Delete the File from the children.
   * @param file - anem of the File.
     */
  public void deleteChild(FileSystemTree file){
    this.children.remove(file);
  }

  /**
   * Return the height of this tree.
   * @return - the height of the tree.
   */
  private int getHeight(){
    if (this.children.size() == 0){
      return 1;
    }
    else{
      ArrayList<Integer> height = new ArrayList<Integer>();
      for(FileSystemTree node : children){
        height.add(node.getHeight());
      }
      Collections.sort(height);
      return height.get(height.size() - 1) + 1;
    }
  }

  /**
   * Return true if this tree contains the File, false otherwise.
   * @param filename - A File.
   * @return - true if File in the tree, false otherwise.
   */
  public boolean contains(File filename){
    if(this.filename.equals(filename)){
      return true;
    }
    else{
      ArrayList<Boolean> boolLst = new ArrayList<Boolean>();

      for(FileSystemTree node : children){
        boolLst.add(node.contains(filename));
      }
      return boolLst.contains(true);
    }
  }

  /**
   * Return the parent of the current node.
   * @return - parent node of the tree.
   */
  public FileSystemTree getParent() throws FileDoesNotExistException{

    if(!(this.parent == null)){
      return this.parent;
    }
    else{
      throw new FileDoesNotExistException();
    }
  }

  /**
   * Return the node of the Directory if it exists in the current node's
   * children.
   *
   * @param dirName - A Directory.
   * @return - the FileSystemTree node of the Directory in the children of
   * current tree.
   * @throws FileDoesNotExistException
   */
  public FileSystemTree getChild(File dirName) throws
    FileDoesNotExistException{

    for(FileSystemTree node : this.children){
      if (node.filename.equals(dirName)){
          return node;
      }
    }
    throw new FileDoesNotExistException();
  }

  /**
   * Returns File of fileName if it exists in the children.
   * @param fileName - String of a file name.
   * @return - a File of the fileName if it exists in the children..
   * @throws FileDoesNotExistException
   */
  public File getChild(String fileName) throws
    FileDoesNotExistException{

    for(FileSystemTree node : this.children){
      if (node.filename.fileNameEquals(fileName)){
          return node.getFile();
      }
    }
    throw new FileDoesNotExistException();
  }

  /**
   * Return the node of the fileName if it exists in thes current node's
   * children.
   * @param fileName - name of the directory
   * @return - the node of the filename.
   * @throws FileDoesNotExistException
     */
  public FileSystemTree getSubFile(String fileName) throws
      FileDoesNotExistException{

    for(FileSystemTree node : this.children){
      if (node.filename.fileNameEquals(fileName)){
        return node;
      }
    }
    throw new FileDoesNotExistException("File " + fileName + " does not exist"
        + ".");

  }

  /**
   * Return true if File exists in the children list
   * @param name - name of a File.
   * @return - true if file exists in the children.
   */
  public boolean hasChild(File name){
    for (FileSystemTree child : this.children){
        if(child.filename.equals(name) && (child.filename.getClass() == name
          .getClass())){
          return true;
      }
    }
    return false;
  }

  /**
   * Return true if File exists in the children list
   * @param filename - name of a File.
   * @return - true if file exists in the children.
   */
  public boolean hasChild(String filename){

    for (FileSystemTree child : this.children){
      if(child.filename.fileNameEquals(filename)){
        return true;
      }
    }
    return false;
  }

  /**
   * Return a string representation of the current tree in the following
   * format: root[[children], [children[children]]].
   * @return - String representation of the current tree.
   */
  public String toString(){

    if (children.isEmpty()){
      return "[" + filename.toString() + "]";
    }
    else{
      String str = "";
      str += "[" + filename.toString();
      for(FileSystemTree node : this.children){
       str += node.toString() + ", ";
      }

      return str.substring(0, str.length() - 2) + "]";
    }
  }

  /**
   * Returns a string representation of the path containing the directories
   * from root to current node.
   * Following format: root/documents/pictures, where pictures is the
   * current node.
   *
   * @return - String representation of path.
   */
  public String getPath(){
    if (this.parent == null){
      return "/";
    }
    else{
      String p = this.filename.getName();

      String path = this.parent.getPath();
      if (path.equals("/")){
        p = path + p;}
      else{
        p = path + "/" + p;
      }
      return p;
    }
  }

  /**
   * Return True if the File name provided equals the current node name.
   * @param name - name of a File.
   * @return - True if File name equals current node.
   */
  public boolean nodeEqual(File name){
      return this.filename.equals(name);
  }

  /**
   * Return the filename of the current node.
   * @return - the filename of the current node.
   */
  public String getFilename(){
    return this.filename.getName();
  }

  /**
   * Return the File.
   * @return - the File rooted at the root.
   */
  public File getFile(){
    return this.filename;
  }

  /**
  * gets a list of the subDirectories
  * @return List of Strings
  */
  public String[] getSubDirectories(){

  String[] childNames = new String[children.size()];

  for(int i = 0; i < children.size(); i++){
    childNames[i] = children.get(i).getFilename();
  }
  return childNames;

  }

  /**
   * Return an array of children in this fst.
   * @return - array of children.
     */
  public FileSystemTree[] getSubDirNodes(){

    FileSystemTree[] result = children.toArray(new FileSystemTree[children.size
        ()]);

    return result;
  }


  // TODO: Add JavaDoc
  public static void main(String[] args) {
    FileSystemTree tree = new FileSystemTree(new Directory("root"));

    tree.addFile(new Directory("documents"));
    tree.addFile(new Directory("download"));
    tree.addFile(new Directory("pictures"));

    Directory dir = new Directory("documents");

    try{

      FileSystemTree f = tree.getSubFile("documents");
      f.deleteCurrentNode();
      System.out.println(tree.toString());

      }
    catch (FileDoesNotExistException e){
      System.out.println(e.getMessage());

      }
  }


}
