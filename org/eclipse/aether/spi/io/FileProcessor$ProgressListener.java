package org.eclipse.aether.spi.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface FileProcessor$ProgressListener {
   void progressed(ByteBuffer var1) throws IOException;
}
