package com.dt002g;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Paths;

public class RepositoryDownloader {

    String url; // GitHub repo link
    final String CLONE_DIRECTORY_PATH = "./TempRepository/";

    public RepositoryDownloader(String url) {
        this.url = url;
    }

    public boolean cloneRepo() {
        System.out.println("Cloning "+url);

        try {
            Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(Paths.get(CLONE_DIRECTORY_PATH).toFile())
                    .call()
                    .close();
            System.out.println("Completed Cloning");
            return true;
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
        }
        return false;
    }
}
