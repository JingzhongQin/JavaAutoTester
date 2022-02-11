package dt002g.com.java.test;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Paths;

public class RepositoryDownloader {

    String url; // Github repo link
    final String CLONE_DIRECTORY_PATH = "/temp/";

    public RepositoryDownloader(String url) {
        this.url = url;
    }

    public void cloneRepo() {
        System.out.println("Cloning "+url);

        try {
            Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(Paths.get(CLONE_DIRECTORY_PATH).toFile())
                    .call();
            System.out.println("Completed Cloning");
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
        }
    }
}
