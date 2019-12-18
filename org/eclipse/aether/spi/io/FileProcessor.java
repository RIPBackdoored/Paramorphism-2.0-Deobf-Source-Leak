package org.eclipse.aether.spi.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileProcessor {
   boolean mkdirs(File var1);

   void write(File var1, String var2) throws IOException;

   void write(File var1, InputStream var2) throws IOException;

   void move(File var1, File var2) throws IOException;

   void copy(File var1, File var2) throws IOException;

   long copy(File var1, File var2, FileProcessor$ProgressListener var3) throws IOException;
}
