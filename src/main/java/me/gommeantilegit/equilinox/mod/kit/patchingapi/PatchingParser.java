package me.gommeantilegit.equilinox.mod.kit.patchingapi;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class PatchingParser {

    /**
     * String that {@link #patches} is parsed of
     */
    @NotNull
    private final String diffContent;

    /**
     * ArrayList storing all patches to be applied
     */
    @NotNull
    private final ArrayList<Patch> patches = new ArrayList<>();

    public PatchingParser(@NotNull String diffContent) {
        this.diffContent = diffContent;
        parsePatches();
    }

    private void parsePatches() {
        String[] lines = diffContent.split("\n");
        PatchBuilder currentPatch = null;
        for (String line : lines) {
            line = line.replace("\r", "");
            if (line.startsWith("#")) {
                String command = line.substring(1);
                if (command.contains(":")) {
                    String[] args = command.split(":");
                    if (args.length == 2) {
                        try {
                            int startLine = Integer.parseInt(args[0]);
                            int endLine = Integer.parseInt(args[1]);
                            if (currentPatch == null) {
                                currentPatch = new PatchBuilder().setStartLine(startLine).setEndLine(endLine);
                            } else {
                                throw new RuntimeException("Failed to parse diff file! Last patch was not terminated at line: \"" + line + "\"");
                            }
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Failed to parse diff file! Cannot parse line numbers in line: \"" + line + "\". NumberFormatException caught!");
                        }
                    } else {
                        throw new RuntimeException("Failed to parse diff file! Syntax error in line: \"" + line + "\"");
                    }
                } else if (command.equalsIgnoreCase("patchEnd")) {
                    if (currentPatch == null)
                        throw new RuntimeException("Failed to parse diff file! Current Patch has already been terminated. No need to do that again at line: \"" + line + "\"");
                    else {
                        Patch patch = currentPatch.createInstance();
                        patches.add(patch);
                        currentPatch = null;
                    }
                } else {
                    throw new RuntimeException("Failed to parse diff content! Unknown instruction: " + line);
                }
            } else {
                if (currentPatch != null)
                    currentPatch.appendLine(line);
            }
        }
    }

    public ArrayList<Patch> getPatches() {
        return patches;
    }
}
