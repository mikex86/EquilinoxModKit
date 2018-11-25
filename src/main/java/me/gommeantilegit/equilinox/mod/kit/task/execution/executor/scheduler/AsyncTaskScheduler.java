package me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler;

import com.sun.istack.internal.Nullable;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.scheduler.async.AsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskScheduler {

    private final ExecutorService executor = Executors.newFixedThreadPool(1000, Thread::new);

    public AsyncTask runAsync(@Nullable Runnable runnable) {
        AsyncTask asyncTask = new AsyncTask(executor);
        executor.submit(() -> {
            runnable.run();
            asyncTask.setExecuted(true);
        });
        return asyncTask;
    }

}
