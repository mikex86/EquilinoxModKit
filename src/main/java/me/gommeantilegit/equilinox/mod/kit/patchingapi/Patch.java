package me.gommeantilegit.equilinox.mod.kit.patchingapi;

public class Patch {

    private final int startLine, endLine;
    private final String content;

    public Patch(int startLine, int endLine, String content) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.content = content;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public String getContent() {
        return content;
    }
}
