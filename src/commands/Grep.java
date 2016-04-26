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


import Tree.FileSystemTree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import exceptions.FileDoesNotExistException;
import file.Directory;
import file.File;
import file.RWFile;
import path.ConcretePath;
import path.GrepPath;
import path.Path;

import java.util.Iterator;

public class Grep extends Command{

    protected static String manual = "Command grep: \n" +" Prints any lines "
        + "containing the regular expression (RegEx) pattern.\n"
        + "Arguments: \n" +
        "-R - if provided, Finds all files in the path provided, find the "
        + "RegEx Pattern\n" + "RegEx - A valid regular expression\n" + "Path: "
        + "of" + "the directory/file\n";


    private String regEx;

    public Grep(){}

    public Grep(Path path){
        super(path);
    }

    /**
     * Run the Grep Command.
     * @param fst - Root node Tree.
     * @return - output for Grep.
     * @throws Exception
     */
    @Override public String[] run(FileSystemTree fst) throws Exception {

        //downcast to Grep path.
        GrepPath path = (GrepPath) PATH;
        this.regEx = path.getRegEx();
        try {
            String output = "";
            for (ConcretePath p : path) {
                Iterator<String> iter = p.iterator();
                output += this.assignTree(iter, fst, p);
            }
            return new String[] {getCurrentDirName(), output.substring(0,
                output.length())};
        }catch (FileDoesNotExistException e){
            throw new FileDoesNotExistException(e.getMessage());
        }
    }

    /**
     * Assign the root tree or current tree based on path, and return the
     * value returned by ls.
     * @param iterator - ConcretePath iterator.
     * @param fst - a FileSystemTree.
     * @param path - a ConcretePath.
     * @return - the value returned by ls.
     * @throws FileDoesNotExistException
     */
    private String assignTree(Iterator<String> iterator, FileSystemTree fst,
        ConcretePath path) throws FileDoesNotExistException{
        try {
            //the path provided is a root path.
            if (path.rootDirExists()) {
                iterator.next(); //skip the /
                if (iterator.hasNext()) {
                    return this.grep(iterator, fst, path);
                }
                else{
                    throw new FileDoesNotExistException("Directory Not "
                        + "Provided");
                }
            }
            //if path provided is a current directory path.
            else{
                return this.grep(iterator, getCurrentDirectory(), path);
            }
        }catch (FileDoesNotExistException e){
            throw new FileDoesNotExistException(e.getMessage());
        }

    }

    /**
     * Return the string from the file inside fst (retrieved by iterator) that
     * contains the regular expression pattern.
     * @param iterator - An iterator for GrepPath.
     * @param fst - A FileSystemTree
     * @param path - A GrepPath
     * @return - A string that contains the regular expression pattern.
     * @throws FileDoesNotExistException
     */
    private String grep(Iterator<String> iterator, FileSystemTree fst,
        ConcretePath path) throws FileDoesNotExistException{

        try {
            if (!iterator.hasNext()) {

                if(path.rootOnly() && path.isRecursive()){
                    return this.getRecursiveMatch(fst, path);
                }

                throw new FileDoesNotExistException("/ is a root "
                        + "directory");
            }
            else{
                String file = iterator.next();
                //go through the path.
                if(file.equals("..")){
                    fst = fst.getParent();
                }
                else if(file.equals(".")){}
                else {
                    fst = fst.getSubFile(file);
                }
                if(!iterator.hasNext()){
                    if(path.isRecursive()){
                        // if only root directory is given/
                        if(fst.getFile() instanceof Directory){
                            return this.getRecursiveMatch(fst, path);
                        }
                        else{
                            throw new FileDoesNotExistException(file + " is "
                                + "not a directory.");
                        }
                    }

                    else{
                        if(path.hasMultiplePaths()){
                            return fst.getPath() + ":\n" + this.getMatch(fst.getFile()
                            ) + "\n \n" ;
                        }
                        return this.getMatch(fst.getFile());
                    }
                }
                //recurse
                return this.grep(iterator, fst, path);
            }
        }catch (FileDoesNotExistException e){
            throw new FileDoesNotExistException(e.getMessage());
        }
    }


    /**
     * Return a String which matches the regular expression in path in all
     * RWFiles under the current fst node, and all the children of fst if
     * children are directories.
     * @param fst - A FileSystemTree node.
     * @param path - A grep Path.
     * @return
     * @throws FileDoesNotExistException
     */
    private String getRecursiveMatch(FileSystemTree fst, ConcretePath path)
        throws FileDoesNotExistException{

        FileSystemTree[] children = fst.getSubDirNodes();

        try {
            String result = "";
            for(FileSystemTree child : children){
                File file = child.getFile();
                //if RW file, get the content
                if(file instanceof RWFile){
                    result += child.getPath() + ": \n" + this.getMatch(file)
                        + "\n";
                }
                //if directory, recurse and get the result.
                if(file instanceof Directory){
                    result += getRecursiveMatch(fst.getChild(file), path) +
                        "\n";
                }
            }
            return result;
        }catch (FileDoesNotExistException e){
            throw new FileDoesNotExistException(e.getMessage());
        }

    }

    /**
     * Return the String which matches the regular Expression from path in
     * the given file in fst.
     * @param filename - a RWfile name
     * @return - the String which matches the regular Expression from path in
     * the given file in fst.
     * @throws FileDoesNotExistException
     */
    private String getMatch(File filename)
        throws
        FileDoesNotExistException{

        if (filename instanceof RWFile) {
            RWFile f = (RWFile) filename;
            String Txt = f.getText();
            return this.patternMatch(this.regEx, Txt);
        }
        else{
            throw new FileDoesNotExistException(filename.getName() + "is a "
                + "directory.");
        }
    }

    /**
     * Return a string which matches the regEx in text.
     * @param regEx - a string version of regulat expression/
     * @param text - a string text.
     * @return - A string which matches regEx in text.
     */
    private String patternMatch(String regEx, String text){
        Pattern p  = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        String result = "";
        while(m.find()){
            result += m.group();
        }
        return result;
    }

}
