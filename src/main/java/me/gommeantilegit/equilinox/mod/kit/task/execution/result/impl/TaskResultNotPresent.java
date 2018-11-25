package me.gommeantilegit.equilinox.mod.kit.task.execution.result.impl;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.exception.ValueNotPresentException;

public class TaskResultNotPresent<T> implements TaskResult<T> {

    /**
     * Parent execution pair
     */
    @NotNull
    private final TaskExecutionResultPair<T> executionPair;

    /**
     * The Exception thrown during execution of {@link #executionPair}'s task.
     */
    @NotNull
    private final Exception exception;

    /**
     * @param executionPair sets {@link #executionPair}
     * @param exception     sets {@link #exception}
     */
    public TaskResultNotPresent(@NotNull TaskExecutionResultPair<T> executionPair, @NotNull Exception exception) {
        this.executionPair = executionPair;
        this.exception = exception;
    }

    /**
     * @return always false
     */
    @Override
    public boolean isPresent() {
        return false;
    }

    /**
     * Always throws a {@link ValueNotPresentException)} with parameters {@link #executionPair} and {@link #exception}
     */
    @Override
    @Nullable
    public T getValue() throws ValueNotPresentException {
        throw new ValueNotPresentException(executionPair, exception);
    }

    @NotNull
    public Exception getException() {
        return exception;
    }

    @NotNull
    public TaskExecutionResultPair<T> getExecutionPair() {
        return executionPair;
    }
}
