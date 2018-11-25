package me.gommeantilegit.equilinox.mod.kit.tasks.project;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.FileUtil;
import me.gommeantilegit.equilinox.mod.kit.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class MoveResourcesTask extends Task<Void> {

    public MoveResourcesTask() {
        super("MoveResourcesTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Moving resources...");

        {
            File file = new File("./src/main/java/res");
            File dest = new File("./src/main/resources/res");
            ArrayList<File> toCopy = FileUtil.listFiles(file);
            logger.log("Moving res folder...");
            for (File f : toCopy) {
                if (f.isFile()) {
                    Path path = file.toPath().relativize(f.toPath());
                    File newFile = new File(dest, path.toString());
                    copyFile(f, newFile);
                    if (!f.delete())
                        throw new IOException("Failed to delete " + f.getPath() + ".");
                }
            }
        }
        logger.log("Finished moving res folder");
        {
            logger.log("Copying all other non class files...");
            File sourceDir = new File("./src/main/java");
            File resourcesDir = new File("./src/main/resources");
            ArrayList<File> toCopy = FileUtil.listFiles(sourceDir);
            for (File file : toCopy) {
                if (!file.getName().endsWith(".java")) {
                    Path relativePath = sourceDir.toPath().relativize(file.toPath());
                    File newFile = new File(resourcesDir, relativePath.toString());
                    copyFile(file, newFile);
                    if (!file.delete())
                        throw new IOException("Failed to delete " + file.getPath() + ".");
                }
            }
        }
        logger.log("Moving resources finished.");
        return null;
    }

    private void copyFile(File oldFile, File newFile) throws IOException {
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(newFile);
            FileInputStream in = new FileInputStream(oldFile);
            IOUtil.copy(in, out);
            in.close();
            out.close();
        }
    }

}
