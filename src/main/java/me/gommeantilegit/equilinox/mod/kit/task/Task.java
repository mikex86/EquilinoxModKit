package me.gommeantilegit.equilinox.mod.kit.task;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.logging.TaskLogger;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;

public abstract class Task<T> {

    /**
     * Task bound logger instance
     */
    public final TaskLogger logger;

    /**
     * Name of the task
     */
    @NotNull
    private final String name;

    /**
     * @param name sets {@link #name}
     */
    protected Task(@NotNull String name) {
        this.name = name;
        this.logger = new TaskLogger(this);
    }

    /**
     * Called on Task execution
     *
     * @param executor the executor executing the task
     */
    @Nullable
    public abstract T executeTask(TaskExecutor<?> executor) throws Exception;

    public String getName() {
        return name;
    }
}
