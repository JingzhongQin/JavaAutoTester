package dt002g.com.java.test;

import java.io.*;
import java.nio.Buffer;
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

    public static void main(String[] args) {
        RepositoryDownloader repositoryDownloader = new RepositoryDownloader("https://github.com/LableOrg/java-maven-junit-helloworld.git");
        if(repositoryDownloader.cloneRepo()){
            TestCaseFinder testCaseFinder = new TestCaseFinder();
            Path rootPath = Path.of(testCaseFinder.CLONE_DIRECTORY_PATH);
            int[] containTests = new int[1];
            testCaseFinder.findTestFile(rootPath, containTests);

            //Project has some test cases
            if(containTests[0] > 0){
                ProjectIdentifier projectIdentifier = new ProjectIdentifier();
                projectIdentifier.checkProjectType();
                String type = projectIdentifier.getProjectType();
                if(type.equals(ProjectIdentifier.MAVEN)){
                    System.out.println("True");

                    Process p = null;

                    try {
                        p = Runtime.getRuntime().exec("runMavenTests.bat");
                    } catch (IOException e) {
                        System.err.println("Error on exec(runMavenTests.bat)");
                        e.printStackTrace();
                    }

                    copyOutput(p.getInputStream(), System.out);
                    try {
                        p.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("-> TestRunner");
                System.out.println();
            }

        }
    }

    static void copyOutput(InputStream in, OutputStream out){
        while (true) {
            int c = 0;
            try {
                c = in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (c == -1)
                break;
            try {
                out.write((char) c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}