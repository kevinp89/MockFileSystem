package path;

import java.util.Iterator;
import java.util.TreeMap;

// A class for Path
public class ConcretePath implements Path, Iterable<String>{
  //tracker for UI path
  private String UIPath;
  //tracker for path.
  private TreeMap<Integer, String> path;
  //tracker of multiplePath.
  private boolean multiplePath = false;
  private boolean recursive = false;
  public ConcretePath(){}

  public ConcretePath(String UI){
    this.UIPath = UI;
    path = new TreeMap<Integer, String>();
    this.populatePath();
  }

  //TODO: remove redundancy for multiple paths.

  /**
   * Split the UIPath and populate TreeMap with keys as an index integer
   * and value as the directory path.
   */
  private void populatePath(){
      if(UIPath.contains("/")){
              populateDirSplit();
      }else{populateNonPathSplit();}
      }

  //TODO: for "cat file >> outfile", [2] would be outfile

  public void setMultiplePath(boolean multiplePath) {
    this.multiplePath = multiplePath;
  }

  private void populateDirSplit(){

    String[] recCheck = UIPath.split(" ");
    if(recCheck[0].equals("-R") || recCheck[0].equals("-r")){
      this.recursive = true;
      String[] split = recCheck[1].split("/");
      if(recCheck[1].startsWith("/")){
       path.put(0, "/");
        for(int i=1; i<split.length; i++){
          if(!split[i].equals("")){
            path.put(i, split[i]);}
        }
      }
      else{
        for(int i=0; i<split.length; i++){
          if(!split[i].equals("")){
            path.put(i, split[i]);}
        }
      }
    }
    else{

      String[] split = UIPath.split("/");
      // root path
      if(UIPath.startsWith("/")){
        path.put(0, "/");
        for(int i=1; i<split.length; i++){
          if(!split[i].equals("")){
            path.put(i, split[i]);}
        }
      }
      //current dir path.
      else{
        for(int i=0; i<split.length; i++){
          if(!split[i].equals("")){
            path.put(i, split[i]);}
        }
      }
    }
  }

  private void populateNonPathSplit(){
    String[] split = UIPath.split(" ");
    for(int i=0;i<split.length;i++){
      if(!split[i].equals("")){
        path.put(i, split[i]);}
    }
  }

  /**
   * Return a PathIterator for Path.
   * @return - a PathIterator for Path.
   */
  public Iterator<String> iterator(){
      return new PathIterator(path);
  }

  /**
   * Return true if path is empty, in other words, if user has inputted
   * nothing as an argument for a command.
   * @return - true if path is empty.
   */
  public boolean nullPath(){
      return UIPath.equals("") && path.isEmpty();
  }

  /**
   * Return true if inputted path starts with /, meaning if it starts with
   * root dir, return true, false otherwise.
   * @return -true if UIpath starts with /, false otherwise.
   */
  public boolean rootDirExists(){
    if(UIPath.split(" ")[0].contains("-r") || UIPath.split(" ")[0].contains
        ("-R")){
      return UIPath.split(" ")[1].contains("/");
    }
    else{
    return UIPath.startsWith("/");
    }
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }

  public boolean rootOnly(){
    return path.size() == 1 && path.get(0).equals("/");
  }

  public boolean currentOnly(){
    return path.size() == 1 && path.get(0).equals(".");
  }


  public boolean isRWFile(String file){
    return file.split("\\.").length == 2;
  }

  public boolean contains(String str){
    return this.UIPath.contains(str);
  }

  public String getUIPath(){
    return UIPath;
  }



  public TreeMap getPath(){
      return path;
  }

  public boolean hasMultiplePaths(){
      return multiplePath;
  }

  public boolean isRecursive() {
    return recursive;
  }

  public static void main(String[] args) {
    ConcretePath path = new ConcretePath("/a/b/c");
    System.out.println(path.getPath());
    System.out.println(path.isRecursive());

  }
}
