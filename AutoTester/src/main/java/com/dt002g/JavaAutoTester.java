package com.dt002g;

import java.nio.file.Path;

//Run the Program: download repository from GitHub, run test cases if the repository has some test cases, display the
//result for the user.
public class JavaAutoTester {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Invalid url, please re-enter the url");
            return;
        }

        //Remove repository if it exists
        DirectoryCleaner directoryCleaner = new DirectoryCleaner();
        directoryCleaner.removeRepository();

        //URL
        String remoteURL = args[0];

        //Clone repository
        RepositoryDownloader repositoryDownloader = new RepositoryDownloader(remoteURL);

        //Clone the repository successfully
        if(repositoryDownloader.cloneRepo()){
            directoryCleaner.projectHasDownloaded();

            //Check if the project is a Maven or Gradle project
            ProjectIdentifier projectIdentifier = new ProjectIdentifier();
            String projectType = projectIdentifier.getProjectType();
            if(projectType.equals(ProjectIdentifier.NONE)){

                //Remove the repository
                directoryCleaner.removeRepository();
                System.out.println("Error, invalid project type. The project should be a Maven or Gradle projects");
                return;
            }

            //Try to find test cases
            TestCaseFinder testCaseFinder = new TestCaseFinder();
            Path rootPath = Path.of(testCaseFinder.CLONE_DIRECTORY_PATH);
            testCaseFinder.findTestFile(rootPath);

            //Project contains some test cases
            if(testCaseFinder.testCaseFound()){

                //Run test cases and output the result
                TestRunner testRunner = new TestRunner();
                testRunner.runTests(projectType);
            }else{
                System.out.println("Project doesn't have any test cases");
            }

            //Remove the repository
            directoryCleaner.removeRepository();
        }
    }
}
