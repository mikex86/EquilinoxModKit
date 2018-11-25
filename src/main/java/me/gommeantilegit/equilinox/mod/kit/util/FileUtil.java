package me.gommeantilegit.equilinox.mod.kit.util;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtil {

    public static ArrayList<File> listFiles(@NotNull File file) {
        if (!file.isDirectory())
            throw new IllegalStateException("File must be a directory in order to list files!");
        File[] files = file.listFiles();
        ArrayList<File> toReturn = new ArrayList<>();
        assert files != null;
        for (File f : files) {
            if (f.isDirectory())
                toReturn.addAll(listFiles(f));
            else
                toReturn.add(f);
        }
        return toReturn;
    }

    public static void deleteDirectory(File file) throws IOException {
        File[] allContents = file.listFiles();
        if (allContents != null) {
            for (File f : allContents) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }
}
