package com.dt002g;

import java.io.*;

//Run all test cases within the repository
public class TestRunner {

    //Batch files that run test cases within a Maven or Gradle project
    final String RUN_MAVEN_TEST_BATCH = "./batchFiles/runMavenTests.bat";
    final String RUN_GRADLE_TEST_BATCH = "./batchFiles/runGradleTests.bat";
    final String GRADLE_BUILD_FILE_PATH = "./TempRepository/build.gradle";

    //Add test logging to build.gradle
    final String TEST_LOGGING = "test { testLogging { events \"PASSED\", \"SKIPPED\", \"FAILED\"}}";

    TestRunner() {}

    //Run test cases within the repository
    public void runTests(String projectType){

        //Which batch we should use to run the test cases?
        String batchFileToRun;

        //Gradle project
        if(projectType.equals(ProjectIdentifier.GRADLE)) {
            batchFileToRun = RUN_GRADLE_TEST_BATCH;
            addTestLogging();

        //Maven project
        }else if(projectType.equals(ProjectIdentifier.MAVEN)){
            batchFileToRun = RUN_MAVEN_TEST_BATCH;

        //Neither a Maven project, nor a Gradle project
        }else{
            System.out.println("Error, the project should be a Maven or Gradle project");
            return;
        }
        Process p = null;

        //Execute the batch file
        try {
            p = Runtime.getRuntime().exec(batchFileToRun);
        } catch (IOException e) {
            System.err.println("Error on exec(" + batchFileToRun + ")");
            e.printStackTrace();
        }

        assert p != null;

        //Get the result
        copyOutput(p.getInputStream(), System.out);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get error messages
        copyOutput(p.getErrorStream(), System.out);
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Copy output to System.out
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

    //Append test logging to build.gradle file
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
