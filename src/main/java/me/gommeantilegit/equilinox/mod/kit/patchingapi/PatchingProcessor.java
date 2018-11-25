package me.gommeantilegit.equilinox.mod.kit.patchingapi;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PatchingProcessor {

    /**
     * Source and dif file content
     */
    @NotNull
    private final String sourceContent, diffContent;

    /**
     * Object used to parse the diff content
     */
    @NotNull
    private final PatchingParser patchingParser;

    /**
     * ArrayList of line objects representing {@link #sourceContent}
     */
    @NotNull
    private final ArrayList<Line> lines = new ArrayList<>();

    @NotNull
    private final String output;

    /**
     * @param source     Source file. Original file to be patched
     * @param difference The file containing the information to transform the source file into the final result
     */
    public PatchingProcessor(@NotNull File source, @NotNull File difference) throws IOException {
        this(
                new String(Files.readAllBytes(source.toPath())),
                new String(Files.readAllBytes(difference.toPath()))
        );
    }

    /**
     * @param sourceContent source content to be transformed
     * @param diffContent   difference content used to transform {@link #sourceContent} into the final result
     */
    public PatchingProcessor(@NotNull String sourceContent, @NotNull String diffContent) {
        this.sourceContent = sourceContent;
        this.diffContent = diffContent;
        this.patchingParser = new PatchingParser(diffContent);
        this.parseLines();
        this.output = computeOutput();
    }

    /**
     * Parses {@link #sourceContent} in it's representation {@link #lines}
     */
    private void parseLines() {
        String[] lines = this.sourceContent.split("\n");
        int lineNumber = 1;
        for (String line : lines) {
            this.lines.add(new Line(lineNumber++, line));
        }
    }

    private String computeOutput() {
        ArrayList<Patch> patches = this.patchingParser.getPatches();
        ArrayList<Line> lines = new ArrayList<>();
        lines.addAll(this.lines);
        int offset = 0;
        for (Patch patch : patches) {
            String[] patchLines = patch.getContent().split("\n");
            int index = patch.getStartLine() - 1 + offset;
            for (String patchLine : patchLines) {
                lines.add(index++, new Line(-1, patchLine));

                offset++;
            }
            int toRemove = index;
            for (int i = patch.getStartLine(); i < patch.getEndLine() + 1; i++)
                lines.set(toRemove++, null);
        }
        return parseToString(lines);
    }

    /**
     * @param lines
     * @return {@link #lines} as String form
     */
    private String parseToString(ArrayList<Line> lines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Line line : lines) {
            if (line != null)
                if (!line.isDisabled())
                    stringBuilder.append(line.getContent()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * @param start start line
     * @param end   end line
     * @return returns a list containing all lines between start and end including themselves
     */
    private ArrayList<Line> getLinesBetween(int start, int end) {
        ArrayList<Line> lines = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            Line line = getLine(i);
            lines.add(line);
        }

        return lines;
    }

    /**
     * @param lineNumber the given line number
     * @return the line at the given line numer
     */
    private Line getLine(int lineNumber) {
        if (lineNumber - 1 >= this.lines.size() || lineNumber < 1) {
            throw new RuntimeException("Patch file points to line not present in source file! (line: " + lineNumber + ")");
        }
        return this.lines.stream().filter(l -> l.getLineNumber() == lineNumber).findFirst().get();
    }

    public String getOutput() {
        return output;
    }
}
