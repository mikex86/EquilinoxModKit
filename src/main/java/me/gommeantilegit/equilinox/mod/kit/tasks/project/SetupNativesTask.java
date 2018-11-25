package me.gommeantilegit.equilinox.mod.kit.tasks.project;

import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.util.IOUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class SetupNativesTask extends Task<Void> {

    public SetupNativesTask() {
        super("SetupNativesTask");
    }

    @Override
    public Void executeTask(TaskExecutor<?> executor) throws IOException, URISyntaxException {
        logger.log("Deleting natives...");
        String[] natives = new String[]{"jinput-dx8.dll",
                "jinput-dx8_64.dll",
                "jinput-raw.dll",
                "jinput-raw_64.dll",
                "lwjgl.dll",
                "lwjgl64.dll",
                "OpenAL32.dll",
                "OpenAL64.dll"
        };
        for (String nativeFileName : natives) {
            File file = new File("src/main/resources/" + nativeFileName);
            if (!file.exists())
                throw new IllegalStateException("Native " + nativeFileName + " does not not exist [" + file.getAbsolutePath() + "]");
            file.delete();
            logger.log("Deleted native " + file.getName());
        }
        logger.log("Deleted natives");
        logger.log("Copying natives");
        String[] nativeFiles = new String[]{

                "project-files/natives/linux/liblwjgl.so",
                "project-files/natives/linux/liblwjgl64.so",
                "project-files/natives/linux/libopenal.so",
                "project-files/natives/linux/libopenal64.so",

                "project-files/natives/windows/lwjgl.dll",
                "project-files/natives/windows/lwjgl64.dll",
                "project-files/natives/windows/OpenAL32.dll",
                "project-files/natives/windows/OpenAL64.dll",

                "project-files/natives/macos/liblwjgl.dylib",
                "project-files/natives/macos/openal.dylib",

        };
        for (String nativeFile : nativeFiles) {
            InputStream read = getClass().getClassLoader().getResourceAsStream(nativeFile);
            FileOutputStream write = new FileOutputStream("workingDirectory/" + nativeFile.substring(nativeFile.lastIndexOf("/")));
            IOUtil.copy(read, write);
        }

        logger.log("Copying natives done.");
        return null;
    }

}
