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
import exceptions.InvalidCommandException;
import file.HTMLFile;
import file.RWFile;
import path.Path;
import path.URLPath;

import java.net.*;
import java.io.*;

public class GetURL extends Command{

    public GetURL() {}

    public GetURL(Path p){super(p);{}}

    /**
     * Run the GetUrl command.
     * @param fst - Root Node Tree.
     * @return - the output of GetUrl.
     * @throws Exception
     */
    @Override
    public String[] run(FileSystemTree fst) throws Exception {
        try{
            if(PATH instanceof URLPath) {
                //downcast
                URLPath path = (URLPath) PATH;
                path.checkURL();

                URL newUrl = new URL(path.getURLPath());
                URLConnection yc = newUrl.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(newUrl.openStream()));

                String inputLine;
                String urlOutput = "";
                while ((inputLine = in.readLine()) != null)
                    urlOutput += inputLine + "\n";
                in.close();

                if (path.isHtmlFile()) {
                    HTMLFile html = new HTMLFile(path.getURLName());
                    html.add(urlOutput);
                    getCurrentDirectory().addFile(html);

                } else if (path.isTxtFile()) {
                    RWFile rw = new RWFile(path.getURLName());
                    rw.add(urlOutput);
                    getCurrentDirectory().addFile(rw);
                }
            }
            return new String[]{getCurrentDirName(), ""};
                }catch (InvalidCommandException e){
            throw new InvalidCommandException(e.getMessage());
                     }
    }


    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.oracle.com/");
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
