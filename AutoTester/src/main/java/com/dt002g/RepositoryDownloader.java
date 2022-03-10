package com.dt002g;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Paths;

//Download a repository from GitHub
public class RepositoryDownloader {

    String url; // GitHub repo link
    final String CLONE_DIRECTORY_PATH = "./TempRepository/"; //Where the repository should be stored

    public RepositoryDownloader(String url) {
        this.url = url;
    }

    //Download a repository using URL from GitHub and the repository will be stored in ./TemRepository/
    public boolean cloneRepo() {
        try {
            Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(Paths.get(CLONE_DIRECTORY_PATH).toFile())
                    .call()
                    .close();
            //Return true if the repository has been downloaded successfully
            return true;
        } catch (GitAPIException e) {
            System.out.println("Error, fail to clone the repository, please try again");
            e.printStackTrace();
        }

        //Fail to download the repository
        return false;
    }
}
