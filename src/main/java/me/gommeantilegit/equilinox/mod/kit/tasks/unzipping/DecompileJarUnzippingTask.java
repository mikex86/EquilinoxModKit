package me.gommeantilegit.equilinox.mod.kit.tasks.unzipping;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DecompileJarUnzippingTask extends Task<Void> {

    public DecompileJarUnzippingTask() {
        super("DecompileJarUnzippingTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws IOException {
        logger.log("Starting unzipping");
        ZipFile zipFile = new ZipFile("fernflower_cache/EquilinoxWindows.jar");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        String extractPath = "src/main/java";
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                File directory = new File(extractPath, entry.getName());
                if (directory.exists())
                    if (!directory.exists() && !directory.mkdirs())
                        throw new IOException("Failed to create Directory " + directory.getAbsolutePath() + ".");
            } else {
                InputStream stream = zipFile.getInputStream(entry);
                File file = new File(extractPath, entry.getName());
                if (file.exists()) continue;
                file.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                IOUtil.copy(stream, out);
                stream.close();
                out.close();
            }
        }
        zipFile.close();
        logger.log("Unzipping finished");
        return null;
    }

}
