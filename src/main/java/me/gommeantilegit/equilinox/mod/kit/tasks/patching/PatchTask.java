package me.gommeantilegit.equilinox.mod.kit.tasks.patching;

import me.gommeantilegit.equilinox.mod.kit.patchingapi.PatchingProcessor;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class PatchTask extends Task<Void> {

    public PatchTask() {
        super("PatchTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Applying patches...");
        File patchesDir = new File("patches");
        if (!patchesDir.exists()) {
            logger.log("Skipping Patching Task. Patches folder does not exist!");
            return null;
        }
        if (patchesDir.isFile()) {
            throw new IllegalStateException("Patches directory must not be a file!");
        }
        File[] patchFiles = patchesDir.listFiles();

        if (patchFiles == null)
            throw new IllegalStateException("Patch files mustn't be null!");
        for (File patchFile : patchFiles) {
            File toPatch = new File("src/main/java/" + patchFile.getName().replace("_", "/").replaceFirst("\\.patch", ""));
            String source = new String(Files.readAllBytes(toPatch.toPath()));
            String dif = new String(Files.readAllBytes(patchFile.toPath()));
            PatchingProcessor patchingProcessor = new PatchingProcessor(source, dif);
            FileWriter fileWriter = new FileWriter(toPatch);
            fileWriter.write(patchingProcessor.getOutput());
            fileWriter.close();
            logger.log("Patched: " + toPatch.getPath());
        }
        logger.log("Patching done.");
        return null;
    }
}
