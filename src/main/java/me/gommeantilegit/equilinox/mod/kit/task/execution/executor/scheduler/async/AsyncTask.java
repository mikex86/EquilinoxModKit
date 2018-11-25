package me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncTask {

    /**
     * State whether
     */
    private boolean executed;

    /**
     * ThreadPoolExecutor for scheduling the task
     */
    private final ExecutorService executor;

    public AsyncTask(ExecutorService executor) {
        this.executor = executor;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public void shutdownExecutor() {
        if (!executor.isShutdown())
            executor.shutdown();
        if (!executor.isTerminated()) {
            try {
                executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
