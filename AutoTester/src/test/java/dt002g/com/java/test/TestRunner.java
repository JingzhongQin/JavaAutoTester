package dt002g.com.java.test;

import java.io.*;

public class TestRunner {
    final String RUN_MAVEN_TEST_BATCH = "runMavenTests.bat";
    final String RUN_GRADLE_TEST_BATCH = "runGradleTests.bat";
    final String GRADLE_BUILD_FILE_PATH = "./TempRepository/build.gradle";
    final String TEST_LOGGING = "test { testLogging { events \"PASSED\", \"SKIPPED\", \"FAILED\"}}";

    TestRunner() {}

    public void runTests(String projectType){

        String batchFileToRun;
        if(projectType.equals(ProjectIdentifier.GRADLE)) {
            batchFileToRun = RUN_GRADLE_TEST_BATCH;
            addTestLogging();
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

        assert p != null;
        copyOutput(p.getInputStream(), System.out);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        copyOutput(p.getErrorStream(), System.out);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void copyOutput(InputStream in, OutputStream out){
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

    private void addTestLogging(){
        File buildGradleFile = new File(GRADLE_BUILD_FILE_PATH);
        try {
            FileWriter fw = new FileWriter(buildGradleFile, true);
            fw.append(TEST_LOGGING);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
