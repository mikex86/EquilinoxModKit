package me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.exception.ValueNotPresentException;

public class TaskResultPresent<T> implements TaskResult<T> {

    /**
     * Parent execution pair
     */
    @NotNull
    private final TaskExecutionResultPair<T> executionPair;

    /**
     * Value returned by the execution of {@link #executionPair}'s task.
     * Note: Nullable
     */
    @Nullable
    private final T taskExecutionValue;

    /**
     * @param executionPair      sets {@link #executionPair}
     * @param taskExecutionValue sets {@link #taskExecutionValue}
     */
    public TaskResultPresent(TaskExecutionResultPair<T> executionPair, T taskExecutionValue) {
        this.executionPair = executionPair;
        this.taskExecutionValue = taskExecutionValue;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    @Nullable
    public T getValue() {
        return this.taskExecutionValue;
    }
}
