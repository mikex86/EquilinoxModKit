package me.gommeantilegit.equilinox.mod.kit;

import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.tasks.EquilinoxProjectCreationTask;

public class Main {

    public static void main(String[] args) {
        EquilinoxProjectCreationTask creationTask = new EquilinoxProjectCreationTask();
        TaskExecutor<Void> executor = new TaskExecutor<>(creationTask, false, true);
        try {
            executor.runExecutor();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(e.hashCode());
        }
    }

}
