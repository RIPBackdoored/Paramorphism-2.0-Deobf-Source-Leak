package org.jline.builtins;

import java.nio.file.*;
import java.io.*;

public static class FilesCompleter extends FileNameCompleter
{
    private final Path currentDir;
    
    public FilesCompleter(final File file) {
        this(file.toPath());
    }
    
    public FilesCompleter(final Path currentDir) {
        super();
        this.currentDir = currentDir;
    }
    
    @Override
    protected Path getUserDir() {
        return this.currentDir;
    }
}
