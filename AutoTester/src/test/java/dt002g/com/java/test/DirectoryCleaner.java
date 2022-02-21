package dt002g.com.java.test;

import java.io.File;
import java.nio.file.Path;

public class DirectoryCleaner {
    private final File root = new File("TempRepository");

    DirectoryCleaner(){}

    public void removeRepository(){
        deleteSubDirectories(root);
        if(!root.delete()){
            System.out.println("Fail to delete file: " + root.getAbsolutePath() + ": " + root.getName());
        }
    }

    private void deleteSubDirectories(File dir){

        File[] files = dir.listFiles();
        if(files != null){
            for(File file : files){
                if(file.isDirectory()){
                    deleteSubDirectories(file);
                }
                if(!file.delete()){
                    System.out.println("Fail to delete file: " + root.getAbsolutePath() + ": " + root.getName());
                }
            }
        }

    }
}
