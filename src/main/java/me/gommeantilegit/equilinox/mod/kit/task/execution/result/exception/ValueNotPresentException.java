package me.gommeantilegit.equilinox.mod.kit.task.execution.result.exception;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;

/**
 * Thrown if {@link TaskResult#getValue()} is invoked when the execution was not successful and therefore has thrown an exception.
 */
public class ValueNotPresentException extends RuntimeException {
    //TODO:
    public ValueNotPresentException(@NotNull TaskExecutionResultPair<?> executionPair, @NotNull Exception exception) {

    }

}
