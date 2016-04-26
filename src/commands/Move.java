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
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;
import file.HTMLFile;
import file.RWFile;
import path.ConcreteMultiplePath;
import path.ConcretePath;
import path.Path;

import file.File;
import java.util.Iterator;

//Move Command.
public class Move extends Command{

    protected static String manual = "Command cp (copy file) arg1: path of "
        + "file to be copied. arg1: path of directory where arg1 being copied: "
        + "\n"
        +" Copies arg1 to arg2. ";

    private FileSystemTree toBeRemoved;
    private FileSystemTree fstWithRemoveFile;

    public Move(){}
    public Move(Path p){
        super(p);
    }

    /**
     * Run the mkdir command.
     * @param fst - Root FST Node.
     * @return - the output for mkdir.
     * @throws FileDoesNotExistException
     */
    @Override public String[] run(FileSystemTree fst) throws
        FileDoesNotExistException {

        try{
            // downcast
            ConcreteMultiplePath path = (ConcreteMultiplePath) PATH;
            //get iterator for path.
            Iterator<ConcretePath> iterator = path.iterator();
            //get ConcretePath for source and target
            ConcretePath source = iterator.next();
            ConcretePath target = iterator.next();
            //move the file.
            this.moveFile(source, target, fst);
            return new String[]{getCurrentDirName(), ""};

        }catch (FileDoesNotExistException e){
            throw new FileDoesNotExistException(e.getMessage());
        }
        catch (InvalidArgumentException e){
            throw new FileDoesNotExistException(e.getMessage());
        }
    }

    /**
     * Move file from source to target removing the source file from original
     * position.
     * @param source - source path.
     * @param target - target path.
     * @param fst - A fileSystemTree to be worked on.
     * @throws FileDoesNotExistException
     * @throws InvalidArgumentException
     */
    private void moveFile(ConcretePath source, ConcretePath target,
        FileSystemTree fst) throws FileDoesNotExistException, InvalidArgumentException{

        //get the file
        FileSystemTree file;
        if(source.rootDirExists()){
            if(source.rootOnly()){
                throw new InvalidArgumentException("Root Directory, /, cannot"
                    + " be moved");
            }
            else{
                Iterator<String> iter = source.iterator();
                // skip the /.
                iter.next();
                file = this.getFile(iter, fst);
                //check target's iterator
                Iterator<String> targetIterator = target.iterator();
                if(target.rootDirExists()) {
                    //skip the /
                    targetIterator.next();
                    this.move(file, targetIterator, fst, target);
                }
                else{
                    this.move(file, targetIterator, getCurrentDirectory(),
                        target);
                }
            }
        }
        else{
            Iterator<String> iter = source.iterator();
            file = this.getFile(iter, getCurrentDirectory());
            Iterator<String> targetIterator = target.iterator();
            if(target.rootDirExists()) {
                this.move(file, targetIterator, fst, target);
            }
            else{
                this.move(file, targetIterator, getCurrentDirectory(), target);
            }
        }
    }

    /**
     * Return the file inside fst from the given iterator path and remove the
     * file.
     * @param iterator - An Iterator for Concrete Path
     * @param fst - A FileSystemTree
     * @return - the file inside fst from the given iterator path.
     * @throws FileDoesNotExistException
     * @throws InvalidArgumentException
     */
    private FileSystemTree getFile(Iterator<String> iterator, FileSystemTree fst) throws
        FileDoesNotExistException, InvalidArgumentException{

        String file = iterator.next();
        //get the file
        if(!iterator.hasNext() && fst.hasChild(file)){
            FileSystemTree result = fst.getSubFile(file);
            //remove the child.
            this.toBeRemoved = result;
            this.fstWithRemoveFile = fst;
            return result;
        }

        else if(!iterator.hasNext() && !fst.hasChild(file)){
            throw new FileDoesNotExistException("File " + file + " does not "
                + "exist");
        }

        else{
            fst = this.getFST(file, fst);
            return this.getFile(iterator, fst);
        }
    }

    /**
     * Add in the file in the fst given iterator path.
     * @param file - File to be added in fst
     * @param iterator - Iterator for ConcretePath.
     * @param fst - A FileSystemTree.
     * @throws FileDoesNotExistException
     */
    private void move(FileSystemTree file, Iterator<String> iterator, FileSystemTree
        fst, ConcretePath path)throws FileDoesNotExistException,
        InvalidArgumentException{


        if(iterator.hasNext()){

            if(path.rootOnly()){
                this.addAndRemoveFile(fst, file);
            }
            else{
                String dir = iterator.next();
                    if(dir.equals("/")){
                        dir = iterator.next();
                    }
                    fst = this.getFST(dir, fst);
                    this.move(file, iterator, fst, path);
            }
        }
        else{

            if(!fst.hasChild(file.getFile()) && !iterator.hasNext()) {
                // go inside dir and add in file.
                this.addAndRemoveFile(fst, file);
            }
            else{
                throw new FileDoesNotExistException("Identical file exist in"
                    + " target directory");
            }
        }

    }

    /**
     * Add in the fil ein fst and remove toBeRemoved from fstWithRemoveFile.
     * @param fst - A FileSystemTree.
     * @param file - A source FileSystemTree Node.
     * @throws FileDoesNotExistException
     */
    private void addAndRemoveFile(FileSystemTree fst, FileSystemTree file)
        throws FileDoesNotExistException{

        if (!fst.hasChild(file.getFile())) {
            fst.addFile(file);
            this.removeFileNode();
        }
        else{
            throw new FileDoesNotExistException(
                "Identical file: " + file + " in " + fst.getPath() +
                    " directory");
        }
    }

    /**
     * Remove the toBeRemoved node from fstWithRemoveFile tree.
     */
    private void removeFileNode(){
        this.fstWithRemoveFile.deleteChild(this.toBeRemoved);
    }

    /**
     * Return a FileSystemTree such that if path == "..", returns fst's parent
     * if path == ".", return fst, otherwise return fst's subdirectory path.
     * @param path - operator or name of a directory.
     * @param fst - a FileSystemTree
     * @return - a FileSystemTree.
     * @throws FileDoesNotExistException
     */
    private FileSystemTree getFST(String path, FileSystemTree fst) throws
        FileDoesNotExistException{

        try {
            if (path.equals("..")) {
                return fst.getParent();
            } else if (path.equals(".")) {
                return fst;
            } else {
                FileSystemTree subDir = fst.getSubFile(path);
                if (subDir.getFile() instanceof RWFile || subDir
                    .getFile() instanceof HTMLFile) {
                    throw new InvalidArgumentException("File" + path + " is "
                        + "not a directory.");
                } else {
                    return subDir;
                }
            }
        }catch (InvalidArgumentException e){
            throw new FileDoesNotExistException(e.getMessage());
        }
    }
}
