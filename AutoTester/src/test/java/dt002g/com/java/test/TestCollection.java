package dt002g.com.java.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCollection {
    final String CLONE_DIRECTORY_PATH = "./TempRepository";
    final String TEST_COLLECTION_PACKAGE_PATH = "./AutoTester/src/test/dt002g.com.java/testCollection";
    final List<String> keywords = Arrays.asList("@Test", "@Before", "@After", "@Ignore", "org.junit.Assert", "org.junit.TestCase",
                                                "org.junit.TestResult", "org.junit.TestSuite");
    final String[] testStr = {"test", "Test"};

    public void collectTestFiles(Path path){
        if(!Files.isDirectory(path)){

            //Test files name contains "test" or "Test"
            if(path.toFile().getName().contains(testStr[0]) || path.toFile().getName().contains(testStr[1])){
                if(containsTestCase(path)){
                    try{
                        Files.copy(path, Path.of(TEST_COLLECTION_PACKAGE_PATH));
                    }catch(IOException e){
                        System.out.println("Error, cannot copy file: " + e.toString());
                    }

                    System.out.println(path.toFile().getAbsolutePath());
                }
            }
        }else{
            for(File subDirectory : path.toFile().listFiles()){
                collectTestFiles(Path.of(subDirectory.getPath()));
            }
        }
    }

    //Check if a file contains Test tag
    public boolean containsTestCase(Path path) {
        try{
            for(String line : Files.readAllLines(path, StandardCharsets.ISO_8859_1)){
                for(String keyword : keywords){
                    if(line.contains(keyword)){
                        return true;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
       return false;
    }

    public static void main(String[] args) {
        RepositoryDownloader repositoryDownloader = new RepositoryDownloader("https://github.com/STAMP-project/test-runner.git");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        if(repositoryDownloader.cloneRepo()){
            TestCollection testCollection = new TestCollection();
            Path rootPath = Path.of(testCollection.CLONE_DIRECTORY_PATH);
            testCollection.collectTestFiles(rootPath);
        }
    }
}
