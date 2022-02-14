package dt002g.com.java.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestCollection {
    final String CLONE_DIRECTORY_PATH = "TempRepository";
    final String TEST_COLLECTION_PACKAGE_PATH = "javaautotester/AutoTester/src/test/java/dt002g/com/java/test/testCollection/";
    final List<String> keywords = Arrays.asList("@Test", "@Before", "@After", "@Ignore", "org.junit.Assert", "org.junit.TestCase",
            "org.junit.TestResult", "org.junit.TestSuite");
    final String[] testStr = {"test", "Test"};

    public void collectTestFiles(Path path){
        if(!Files.isDirectory(path)){

            //Test files name contains "test" or "Test"
            if(path.toFile().getName().contains(testStr[0]) || path.toFile().getName().contains(testStr[1])){
                if(containsTestCase(path)){
                    try{
                        String filePath = TEST_COLLECTION_PACKAGE_PATH + path.getFileName();
                        Files.copy(path.toAbsolutePath(), Path.of(filePath).toAbsolutePath());
                    }catch(IOException e){
                        System.out.println("Error, cannot copy file: " + e);
                    }
                }
            }
        }else{
            for(File subDirectory : Objects.requireNonNull(path.toFile().listFiles())){
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
        if(repositoryDownloader.cloneRepo()){
            TestCollection testCollection = new TestCollection();
            Path rootPath = Path.of(testCollection.CLONE_DIRECTORY_PATH);
            testCollection.collectTestFiles(rootPath);
        }
    }
}
