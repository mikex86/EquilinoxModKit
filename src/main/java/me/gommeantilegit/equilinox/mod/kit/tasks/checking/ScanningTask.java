package me.gommeantilegit.equilinox.mod.kit.tasks.checking;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ScanningTask extends Task<Void> {

    private static final String VERSION_STRING = "Version 1.0.0";

    public ScanningTask() {
        super("ScanningTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        File equilinoxJar = new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Equilinox\\EquilinoxWindows.jar");
        if (equilinoxJar.exists()) {
            URLClassLoader loader = new URLClassLoader(new URL[]{equilinoxJar.toURI().toURL()});
            String version = (String) loader.loadClass("main.MainApp").getDeclaredField("VERSION_STRING").get(null);
            if (!version.equals(VERSION_STRING)) {
                System.err.println("Unsupported Equilinox Version: This Equilinox Mod Kit does not support version: " + version + ". (Only supports " + VERSION_STRING + ")");
                executor.terminate();
                return null;
            }
        } else {
            System.err.println("Cannot find Equilinox jar! You must first purchase Equilinox on Steam!");
            executor.terminate();
            return null;
        }
        logger.log("Check passed. Version compatible. Starting Equilinox Mod Kit for " + VERSION_STRING + ".");
        return null;
    }
}
