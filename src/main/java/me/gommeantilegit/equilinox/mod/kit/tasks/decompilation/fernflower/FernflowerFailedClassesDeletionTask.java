package me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.fernflower;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl.TaskResultPresent;

import java.io.File;
import java.util.ArrayList;

public class FernflowerFailedClassesDeletionTask extends Task<Void> {

    @NotNull
    private final TaskExecutionResultPair<ArrayList<File>> cfrPatchTask;

    public FernflowerFailedClassesDeletionTask(@NotNull TaskExecutionResultPair<ArrayList<File>> cfrPatchTask) {
        super("FernflowerFailedClassesDeletionTask");
        this.cfrPatchTask = cfrPatchTask;
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Deleting files Fernflower failed to decompile");
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
                failedFile.delete();
                logger.log("Deleted " + failedFile.getName() + ".");
            }
        } else {
            throw new RuntimeException("Task Result of task " + cfrPatchTask.getParent().getName() + " is not present! An Exception was caught during execution of this task and no result was returned!");
        }
        logger.log("Finished deleting files.");
        return null;
    }
}
