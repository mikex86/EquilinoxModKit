package me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.fernflower;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;

import java.io.File;
import java.io.IOException;

public class FernflowerDecompilationTask extends Task<Void> {

    public FernflowerDecompilationTask() {
        super("FernflowerDecompilationTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws IOException {
        if(new File("fernflower_cache/EquilinoxWindows.jar").exists())
        {
            logger.log("Skipping decompilation as jar is still in fernflower_cache...");
            return null;
        }
        logger.log("Starting decompilation");
        String jarPath = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Equilinox\\EquilinoxWindows.jar";
        if(!new File("fernflower_cache").mkdirs())
            throw new IOException("Failed to create fernflower_cache folder!");
        ConsoleDecompiler.main(new String[]{jarPath, "fernflower_cache"});
        logger.log("Decompilation finished!");
        return null;
    }
}
