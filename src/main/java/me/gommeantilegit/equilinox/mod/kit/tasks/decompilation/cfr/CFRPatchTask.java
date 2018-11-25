package me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.cfr;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.FileUtil;
import org.benf.cfr.reader.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CFRPatchTask extends Task<ArrayList<File>> {

    public CFRPatchTask() {
        super("CFRPatchTask");
    }

    @Override
    public ArrayList<File> executeTask(TaskExecutor<?> executor) throws Exception {
        logger.log("Applying CFR patches...");
        File javaSourceDir = new File("src/main/java");
        ArrayList<File> files = FileUtil.listFiles(javaSourceDir);
        ArrayList<File> failedFiles = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".java")) {
                String firstLine = new BufferedReader(new FileReader(file)).readLine();
                if (!firstLine.startsWith("package")) {
                    logger.log("Fernflower failed to decompile " + file.getName() + ". Now trying again using CFR...");
                    Main.main(new String[]{file.getAbsolutePath(), "--outputdir", "./cfr_cache/" + file.getParentFile().toPath().relativize(new File("./").toPath()).toString()});
                    logger.log("CFR Decompilation finished.");
                    failedFiles.add(file);
                }
            }
        }
        logger.log("Finished applying CFR patches");
        return failedFiles;
    }

}
