package me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.deletion;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.FileUtil;

import java.io.File;

public class LibraryFilesDeletionTask extends Task<Void> {

    public LibraryFilesDeletionTask() {
        super("LibraryFilesDeletionTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {

        logger.log("Deleting unnecessary library files...");
        String[] toDelete = new String[]{"com", "org/lwjgl"};
        for (String dir : toDelete) {
            File file = new File("src/main/java/" + dir);
            if (file.exists())
                if (file.isDirectory()) {
                    FileUtil.deleteDirectory(file);
                    logger.log("Delete library folder " + file.getAbsolutePath());
                } else
                    throw new IllegalStateException("File " + file.getAbsolutePath() + " is not a directory. Have files magically changed?");
        }
        logger.log("Deletion done.");
        return null;
    }
}
