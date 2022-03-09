package com.dt002g;

import java.io.File;

//Download a project from GitHub using URL provided by the user
public class DirectoryCleaner {

    //Root directory
    private final File root = new File("TempRepository");

    private boolean projectHasDownloaded = false;

    DirectoryCleaner(){}

    //Remove directory TempRepository and all files, subdirectories in it
    public void removeRepository(){

        //Delete subdirectories and files in TempRepository
        deleteSubDirectories(root);

        //Remove TempRepository
        //The error message won't be displayed for the user if the program haven't downloaded the repository yet.
        if(!root.delete() && projectHasDownloaded){
            System.out.println("Fail to delete file: " + root.getAbsolutePath() + ": " + root.getName());
        }
    }

    //Set variable beforeDownload to false
    public void projectHasDownloaded(){
        this.projectHasDownloaded = true;
    }

    //Remove subdirectories in directory
    private void deleteSubDirectories(File dir){

        File[] files = dir.listFiles();
        if(files != null){

            //Files and subdirectories in the directory
            for(File file : files){

                //Delete subdirectories recursively
                if(file.isDirectory()){
                    deleteSubDirectories(file);
                }

                //Remove files
                if(!file.delete()){
                    System.out.println("Fail to delete file: " + root.getAbsolutePath() + ": " + root.getName());
                }
            }
        }
    }
}
