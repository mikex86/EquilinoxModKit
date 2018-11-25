package me.gommeantilegit.equilinox.mod.kit.logging;

import me.gommeantilegit.equilinox.mod.kit.task.Task;

public class TaskLogger {

    private final String prefix;

    public TaskLogger(Task task) {
        this.prefix = "[" + task.getName() + "]";
    }

    public void log(String message) {
        System.out.println(this.prefix + " " + message);
    }
}
