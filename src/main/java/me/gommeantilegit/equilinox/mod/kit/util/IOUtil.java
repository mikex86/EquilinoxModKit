package me.gommeantilegit.equilinox.mod.kit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

    /**
     * Default size used for byte buffers
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     */
    public static long copy(InputStream source, OutputStream sink)
            throws IOException
    {
        long nread = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }
}
