package dt002g.com.java.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestRunner {

    TestRunner() {}

    public void runTests(String projectType){
        final String RUN_MAVEN_TEST_BATCH = "runMavenTests.bat";
        final String RUN_GRADLE_TEST_BATCH = "runGradleTests.bat";

        String batchFileToRun;
        if(projectType.equals(ProjectIdentifier.GRADLE)) {
            batchFileToRun = RUN_GRADLE_TEST_BATCH;
        }else if(projectType.equals(ProjectIdentifier.MAVEN)){
            batchFileToRun = RUN_MAVEN_TEST_BATCH;
        }else{
            System.out.println("Error, the project should be a Maven or Gradle project");
            return;
        }
        Process p = null;

        try {
            p = Runtime.getRuntime().exec(batchFileToRun);
        } catch (IOException e) {
            System.err.println("Error on exec(" + batchFileToRun + ")");
            e.printStackTrace();
        }

        copyOutput(p.getInputStream(), System.out);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void copyOutput(InputStream in, OutputStream out){
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
