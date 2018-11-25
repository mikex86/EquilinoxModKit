package me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.cfr;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl.TaskResultPresent;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.fernflower.FernflowerFailedClassesDeletionTask;
import me.gommeantilegit.equilinox.mod.kit.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CFRClassCopyTask extends Task<Void> {

    @NotNull
    private final TaskExecutionResultPair<ArrayList<File>> cfrPatchTask;

    public CFRClassCopyTask(@NotNull TaskExecutionResultPair<ArrayList<File>> cfrPatchTask) {
        super("CFRClassCopyTask");
        this.cfrPatchTask = cfrPatchTask;
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Copying CFR decompiled classes...");
        if (!cfrPatchTask.wasTaskExecuted()) {
            throw new RuntimeException("Cannot run " + getName() + " if " + cfrPatchTask.getParent().getName() + " was not executed!");
        } else if (!cfrPatchTask.wasExecutionSuccessful()) {
            throw new RuntimeException("Cannot run " + getName() + " if " + cfrPatchTask.getParent().getName() + " was not successfully executed!");
        }
        TaskResult<ArrayList<File>> taskResult = cfrPatchTask.getResult();
        if (taskResult.isPresent()) {
            TaskResultPresent<ArrayList<File>> present = ((TaskResultPresent<ArrayList<File>>) taskResult);
            ArrayList<File> failedFiles = present.getValue();
            for (File failedFile : failedFiles) {
                if (failedFile.exists())
                    throw new IllegalStateException("File " + failedFile + " was not yet deleted. This should never happen. File should have been deleted by " + FernflowerFailedClassesDeletionTask.class.getSimpleName() + "!");
                File cfrFile = new File("./cfr_cache/" + failedFile.getParentFile().toPath().relativize(new File("./").toPath()).toString());
                if (!cfrFile.exists())
                    throw new IllegalStateException("File " + cfrFile.getAbsolutePath() + " does not exist! File should have been created by " + CFRPatchTask.class.getSimpleName() + "!");
                FileOutputStream write = new FileOutputStream(failedFile);
                FileInputStream read = new FileInputStream(cfrFile);
                IOUtil.copy(read, write);
                logger.log("Successfully copied " + cfrFile.getAbsolutePath() + " to " + cfrFile.getAbsolutePath() + ".");
            }
        } else {
            throw new RuntimeException("Task Result of task " + cfrPatchTask.getParent().getName() + " is not present! An Exception was caught during execution of this task and no result was returned!");
        }
        logger.log("Copying finished");
        return null;
    }
}
