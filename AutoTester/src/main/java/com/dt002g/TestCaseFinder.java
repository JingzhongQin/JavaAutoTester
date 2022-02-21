package com.dt002g;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class TestCaseFinder {
    final String CLONE_DIRECTORY_PATH = "TempRepository";
    final String KEY_WORD = "@Test";
    final String[] testStr = {"test", "Test"};


    //Check if the project has some test files
    public void findTestFile(Path path, int[] numberOfTests){
        if(numberOfTests[0] > 0) return;
        if(!Files.isDirectory(path)){

            //Test files name contains "test" or "Test"
            if(path.toFile().getName().contains(testStr[0]) || path.toFile().getName().contains(testStr[1])){
                if(containsTestCase(path)){
                     numberOfTests[0]++;
                }
            }
        }else{
            for(File subDirectory : Objects.requireNonNull(path.toFile().listFiles())){
                findTestFile(Path.of(subDirectory.getPath()), numberOfTests);
            }
        }
    }

    //Check if a file contains Test tag
    public boolean containsTestCase(Path path) {
        try{
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
}