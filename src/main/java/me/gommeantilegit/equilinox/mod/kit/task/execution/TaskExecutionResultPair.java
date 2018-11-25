package me.gommeantilegit.equilinox.mod.kit.task.execution;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl.TaskResultNotPresent;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl.TaskResultPresent;

public class TaskExecutionResultPair<T> {

    /**
     * Parent task
     */
    @NotNull
    private final Task<T> parent;

    /**
     * Value returned by the task
     */
    @Nullable
    private TaskResult<T> result;

    /**
     * The boolean state whether the task has been executed and thus if it has computed a value for {@link #result}
     */
    private boolean taskExecuted = false;

    /**
     * Boolean state whether the execution of {@link #parent} was successful
     * False if task has not yet been executed
     */
    private boolean executionSuccessful = false;

    /**
     * @param parent parent task
     */
    public TaskExecutionResultPair(@NotNull Task<T> parent) {
        this.parent = parent;
    }

    /**
     * @return the task result returned by the task
     */
    @NotNull
    public TaskResult<T> evaluateTaskResult(TaskExecutor<?> executor) {
        try {
            T value = this.parent.executeTask(executor);
            this.taskExecuted = true;
            this.executionSuccessful = true;
            //Returning a TaskResultPresent because invocation of task was successful
            return new TaskResultPresent<>(this, value);
        } catch (Exception exception) {
            this.taskExecuted = true;
            this.executionSuccessful = false;
            //Returning a TaskResultNotPresent because invocation of task resulted in an exception and no return value is present
            return new TaskResultNotPresent<>(this, exception);
        }
    }

    /**
     * Executes the task by setting {@link #result} to {@link #evaluateTaskResult(TaskExecutor)}
     */
    public void runTask(TaskExecutor<?> executor) {
        this.result = this.evaluateTaskResult(executor);
    }

    public boolean wasExecutionSuccessful() {
        return executionSuccessful;
    }

    public boolean wasTaskExecuted() {
        return taskExecuted;
    }

    public TaskResult<T> getResult() {
        return result;
    }

    public Task<T> getParent() {
        return parent;
    }
}
