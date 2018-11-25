package me.gommeantilegit.equilinox.mod.kit.task.execution.result;

import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.exception.ValueNotPresentException;

public interface TaskResult<T> {

    /**
     * @return true, whether the result stores a value. returns false, if execution of the parent task threw an exception
     */
    boolean isPresent();

    /**
     * @return the result of the task's execution.
     * @throws ValueNotPresentException if execution of the parent task threw an exception
     */
    @Nullable
    T getValue() throws ValueNotPresentException;

}
