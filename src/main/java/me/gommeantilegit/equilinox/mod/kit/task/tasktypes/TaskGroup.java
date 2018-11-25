package me.gommeantilegit.equilinox.mod.kit.task.tasktypes;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler.AsyncTaskScheduler;

import java.util.Arrays;
import java.util.Iterator;

public class TaskGroup<T> extends Task<T> {

    /**
     * Boolean state, whether the executor can execute the subordinate tasks simultaneously on separate threads.
     * Note: This does not recursively affect all sub tasks of this groups tasks. This only affects direct children of this group.
     */
    private final boolean allowAsyncParalyzing;

    @Nullable
    private final AsyncTaskScheduler scheduler;

    /**
     * Subordinate tasks
     */
    @NotNull
    protected final TaskExecutionResultPair<?>[] subTasks;


    /**
     * @param name                 sets {@link #name}
     * @param allowAsyncParalyzing sets {@link #allowAsyncParalyzing}
     * @param subTasks             sets {@link #subTasks}
     */
    public TaskGroup(@NotNull String name, boolean allowAsyncParalyzing, @NotNull TaskExecutionResultPair<?>[] subTasks) {
        super(name);
        this.subTasks = subTasks;
        this.allowAsyncParalyzing = allowAsyncParalyzing;
        if (allowAsyncParalyzing)
            scheduler = new AsyncTaskScheduler();
        else
            scheduler = null;
    }

    /**
     * @return null because this task only executes subordinate tasks without any computation
     * @param executor
     */
    @Nullable
    @Override
    public T executeTask(TaskExecutor<?> executor) throws Exception {
        return null;
    }

    @NotNull
    public Iterator<TaskExecutionResultPair<?>> taskIterator() {
        return Arrays.stream(subTasks).iterator();
    }

    /**
     * @return the state of {@link #allowAsyncParalyzing}
     */
    public boolean allowsAsyncParalyzing() {
        return allowAsyncParalyzing;
    }

    public AsyncTaskScheduler getScheduler() {
        return scheduler;
    }
}
