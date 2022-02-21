package com.dt002g;

import java.nio.file.Path;

public class JavaAutoTester {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Invalid url, please re-enter the url");
            return;
        }
        String remoteURL = args[0];
        RepositoryDownloader repositoryDownloader = new RepositoryDownloader(remoteURL);
        if(repositoryDownloader.cloneRepo()){
            TestCaseFinder testCaseFinder = new TestCaseFinder();
            Path rootPath = Path.of(testCaseFinder.CLONE_DIRECTORY_PATH);
            int[] containTests = new int[1];
            testCaseFinder.findTestFile(rootPath, containTests);

            //Project has some test cases
            if(containTests[0] > 0){
                ProjectIdentifier projectIdentifier = new ProjectIdentifier();
                String projectType = projectIdentifier.getProjectType();
                TestRunner testRunner = new TestRunner();
                testRunner.runTests(projectType);
            }else{
                System.out.println("Project doesn't have any test cases");
            }
            DirectoryCleaner directoryCleaner = new DirectoryCleaner();
            directoryCleaner.removeRepository();
            System.out.println("Done!");
        }
    }
}
