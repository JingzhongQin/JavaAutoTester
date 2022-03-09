package com.dt002g;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

//Check if the repository that has been downloaded has some test cases
public class TestCaseFinder {
    final String CLONE_DIRECTORY_PATH = "TempRepository";
    final String KEY_WORD = "@Test";
    final String[] testStr = {"test", "Test"};

    //Found any test cases within the project?
    AtomicBoolean testCaseFound = new AtomicBoolean(false);

    //Check if the project has some test files
    public void findTestFile(Path path){

        //This function terminates if one test case if found
        if(testCaseFound.get()) return;

        //Files, exclusive directories
        if(!Files.isDirectory(path)){

            //Test files name contains "test" or "Test"
            if(path.toFile().getName().contains(testStr[0]) || path.toFile().getName().contains(testStr[1])){

                //Test case found
                if(containsTestCase(path)){
                     testCaseFound.set(true);
                }
            }

        //Directories
        }else{
            for(File subDirectory : Objects.requireNonNull(path.toFile().listFiles())){
                findTestFile(Path.of(subDirectory.getPath()));
            }
        }
    }

    //Check if a file contains @Test annotation
    public boolean containsTestCase(Path path) {
        try{

            //File contains @Test annotation
            for(String line : Files.readAllLines(path, StandardCharsets.ISO_8859_1)){
                    if(line.contains(KEY_WORD)){
                        return true;
                    }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    //Test case was found?
    public boolean testCaseFound(){
        return this.testCaseFound.get();
    }
}