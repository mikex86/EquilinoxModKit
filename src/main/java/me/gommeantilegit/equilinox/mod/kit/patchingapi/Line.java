package me.gommeantilegit.equilinox.mod.kit.patchingapi;

public class Line {

    private final int lineNumber;

    private boolean disabled;

    private String content;


    public Line(int lineNumber, String content) {
        this.lineNumber = lineNumber;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public Line setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public Line setContent(String content) {
        this.content = content;
        return this;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
