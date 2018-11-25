package me.gommeantilegit.equilinox.mod.kit.task.execution.executor;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler.AsyncTaskScheduler;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler.async.AsyncTask;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl.TaskResultNotPresent;
import me.gommeantilegit.equilinox.mod.kit.task.tasktypes.TaskGroup;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskExecutor<T> {

    /**
     * Boolean state whether the executor should automatically print the stack-traces of caught exceptions
     */
    private final boolean printExceptions;

    /**
     * Boolean state whether the executor should stop execution if an exception is thrown
     */
    private final boolean terminateOnException;

    /**
     * Task to be executed
     */
    @NotNull
    private final Task<T> task;

    /**
     * Boolean state whether {@link #runExecutor()} has run through
     */
    private boolean ran = false;

    /**
     * ResultPair of task {@link #task}
     */
    @NotNull
    private final TaskExecutionResultPair<T> resultPair;

    /**
     * All Runnables that were executed asynchronous and there state if the have been executed combined in an object
     */
    @NotNull
    private final ArrayList<AsyncTask> asyncTasks = new ArrayList<>();

    /**
     * @param task                 sets {@link #task}
     * @param printExceptions      sets {@link #printExceptions}
     * @param terminateOnException sets {@link #terminateOnException}
     */
    public TaskExecutor(@NotNull Task<T> task, boolean printExceptions, boolean terminateOnException) {
        this.task = task;
        this.printExceptions = printExceptions;
        this.resultPair = new TaskExecutionResultPair<>(task);
        this.terminateOnException = terminateOnException;
    }

    public void runExecutor() {
        runTask(resultPair);
        this.ran = true;
    }

    private void runTask(@NotNull TaskExecutionResultPair<?> task) {
        if (task.getParent() instanceof TaskGroup) {
            runGroupTask(task);
        } else {
            task.runTask(this);

            if (printExceptions) {
                TaskResult<?> taskResult = task.getResult();

                if (taskResult instanceof TaskResultNotPresent) {
                    Exception exception = ((TaskResultNotPresent) taskResult).getException();
                    exception.printStackTrace();
                }
            }
            if (terminateOnException) {
                TaskResult<?> taskResult = task.getResult();
                if (taskResult instanceof TaskResultNotPresent) {
                    throw new RuntimeException(((TaskResultNotPresent<?>) taskResult).getException());
                }
            }
        }
    }

    public void terminate() {
        this.asyncTasks.forEach(it -> {
            if (!it.isExecuted()) {
                it.shutdownExecutor();
            }
        });
    }

    private void runGroupTask(@NotNull TaskExecutionResultPair<?> pair) {
        if (!(pair.getParent() instanceof TaskGroup)) {
            throw new IllegalStateException("TaskExecutionResultPair's parent task is not instanceof TaskGroup!");
        }
        TaskGroup<?> task = (TaskGroup<?>) pair.getParent();
        if (task.allowsAsyncParalyzing()) {
            Iterator<TaskExecutionResultPair<?>> taskIterator = task.taskIterator();
            taskIterator.forEachRemaining(it -> {
                AsyncTaskScheduler scheduler = task.getScheduler();
                this.asyncTasks.add(scheduler.runAsync(() -> runTask(it)));
            });
        } else {
            Iterator<TaskExecutionResultPair<?>> taskIterator = task.taskIterator();
            taskIterator.forEachRemaining(this::runTask);
        }
    }

    public void waitForFinish() {
        while (notFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Task<T> getTask() {
        return task;
    }

    public TaskExecutionResultPair<T> getResultPair() {
        return resultPair;
    }

    public boolean notFinished() {
        return !(ran && asyncTasks.stream().allMatch(AsyncTask::isExecuted));
    }

    public ArrayList<AsyncTask> getAsyncTasks() {
        return asyncTasks;
    }
}
