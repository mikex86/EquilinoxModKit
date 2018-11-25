package me.gommeantilegit.equilinox.mod.kit.patchingapi;

import com.sun.istack.internal.NotNull;

public class PatchBuilder {

    private int startLine, endLine;
    private String content = "";

    @NotNull
    public void appendLine(String line) {
        this.content += line + "\n";
    }

    @NotNull
    public PatchBuilder setEndLine(int endLine) {
        this.endLine = endLine;
        return this;
    }

    @NotNull
    public PatchBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    @NotNull
    public PatchBuilder setStartLine(int startLine) {
        this.startLine = startLine;
        return this;
    }

    @NotNull
    public Patch createInstance() {
        return new Patch(this.startLine, this.endLine, this.content);
    }
}
