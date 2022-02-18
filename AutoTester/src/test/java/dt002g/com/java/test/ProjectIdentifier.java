package dt002g.com.java.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

//This class is used to identify the type of project: Gradle project or Maven project
public class ProjectIdentifier {
    final static String GRADLE = "Gradle";
    final static String MAVEN = "Maven";
    final static String NONE = "NONE";
    final String POM_FILE = "pom.xml";
    final String GRADLE_BUILD_FILE = "build.gradle";
    final String CLONE_DIRECTORY_PATH = "TempRepository";

    private enum projectType{
        NONE,
        GRADLE,
        MAVEN
    }
    private projectType type;

    ProjectIdentifier(){
        this.type = projectType.NONE;
    }

    public void checkProjectType(){
        Path path = Path.of(CLONE_DIRECTORY_PATH);
        if(Files.isDirectory(path)){
            for(File subDirectory : Objects.requireNonNull(path.toFile().listFiles())){
                if(!subDirectory.isDirectory()){
                    if(subDirectory.getName().equals(POM_FILE)){
                        this.type = projectType.MAVEN;
                        return;
                    }else if(subDirectory.getName().equals(GRADLE_BUILD_FILE)){
                        this.type = projectType.GRADLE;
                        return;
                    }
                }
            }
        }
        this.type = projectType.NONE;
    }

    public String getProjectType(){
        if(this.type.equals(projectType.GRADLE)){
            return GRADLE;
        }else if(this.type.equals(projectType.MAVEN)){
            return MAVEN;
        }
        return NONE;
    }
}
