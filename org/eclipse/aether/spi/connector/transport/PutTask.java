package org.eclipse.aether.spi.connector.transport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public final class PutTask extends TransportTask {
   private File dataFile;
   private byte[] dataBytes;

   public PutTask(URI var1) {
      super();
      this.dataBytes = EMPTY;
      this.setLocation(var1);
   }

   public InputStream newInputStream() throws IOException {
      return (InputStream)(this.dataFile != null ? new FileInputStream(this.dataFile) : new ByteArrayInputStream(this.dataBytes));
   }

   public long getDataLength() {
      return this.dataFile != null ? this.dataFile.length() : (long)this.dataBytes.length;
   }

   public File getDataFile() {
      return this.dataFile;
   }

   public PutTask setDataFile(File var1) {
      this.dataFile = var1;
      this.dataBytes = EMPTY;
      return this;
   }

   public PutTask setDataBytes(byte[] var1) {
      this.dataBytes = var1 != null ? var1 : EMPTY;
      this.dataFile = null;
      return this;
   }

   public PutTask setDataString(String var1) {
      return this.setDataBytes(var1 != null ? var1.getBytes(StandardCharsets.UTF_8) : null);
   }

   public PutTask setListener(TransportListener var1) {
      super.setListener(var1);
      return this;
   }

   public String toString() {
      return ">> " + this.getLocation();
   }

   public TransportTask setListener(TransportListener var1) {
      return this.setListener(var1);
   }
}
