package me.gommeantilegit.equilinox.mod.kit.tasks.cleaning;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.FileUtil;

import java.io.File;

public class CleanUpTask extends Task<Void> {

    public CleanUpTask() {
        super("CleanUpTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Cleaning up caches...");
        FileUtil.deleteDirectory(new File("fernflower_cache"));
        FileUtil.deleteDirectory(new File("cfr_cache"));
        logger.log("Cleaning caches done.");
        return null;
    }

}
