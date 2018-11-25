package me.gommeantilegit.equilinox.mod.kit.tasks.project;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.IOUtil;

import java.io.*;

public class ProjectSetupTask extends Task<Void> {

    public enum Type {
        KOTLIN_DSL(true);

        private final boolean usesGradle;

        Type(boolean usesGradle) {
            this.usesGradle = usesGradle;
        }
    }

    @NotNull
    private final Type type;

    public ProjectSetupTask(@NotNull Type type) {
        super("ProjectSetupTask");
        this.type = type;
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Setting up project...");
        File[] directories = new File[]{
                new File("./src"),
                new File("./src/main"),
                new File("./src/main/java"),
                new File("./src/main/resources"),
                new File("./workingDirectory"),
        };
        logger.log("Creating directories");
        for (File directory : directories) {
            if (!directory.exists() && !directory.mkdirs())
                throw new IOException("Creation of directory " + directory.getAbsolutePath() + " failed!");
        }
        switch (type) {
            case KOTLIN_DSL: {
                FileOutputStream write = new FileOutputStream("build.gradle.kts");
                InputStream read = getClass().getClassLoader().getResourceAsStream("project-files/gradle/kotlin-dsl/build.gradle.kts_");
                IOUtil.copy(read, write);
                logger.log("Created build.gradle.kts file");
            }
            break;
        }
        if (type.usesGradle) {
            String[] gradleFiles = new String[]{"project-files/gradle/gradlew", "project-files/gradle/gradlew.bat", "project-files/gradle/settings.gradle"};
            for (String path : gradleFiles) {
                String relPath = path.replace("project-files/gradle/", "");
                File file = new File("./" + relPath);
                if (!file.exists())
                    file.createNewFile();
                FileOutputStream write = new FileOutputStream(new File("./" + relPath));
                InputStream read = getClass().getClassLoader().getResourceAsStream(path);
                IOUtil.copy(read, write);
                logger.log("Created " + relPath + " file");
            }
        }
        logger.log("Project files set up.");
        return null;
    }
}
