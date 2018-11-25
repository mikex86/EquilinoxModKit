import me.gommeantilegit.equilinox.mod.kit.patchingapi.PatchingProcessor;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PatchingTest {

    @Test
    public void patching() {
        PatchingProcessor processor = new PatchingProcessor(read("DataUpdateTickets.java"), read("dataManagement_DataUpdateTickets.java.patch"));
        System.out.println(processor.getOutput());
    }

    private String read(String path) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
        String line;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
