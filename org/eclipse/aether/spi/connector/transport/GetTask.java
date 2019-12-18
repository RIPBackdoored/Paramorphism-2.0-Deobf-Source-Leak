package org.eclipse.aether.spi.connector.transport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GetTask extends TransportTask {
   private File dataFile;
   private boolean resume;
   private ByteArrayOutputStream dataBytes;
   private Map checksums = Collections.emptyMap();

   public GetTask(URI var1) {
      super();
      this.setLocation(var1);
   }

   public OutputStream newOutputStream() throws IOException {
      return this.newOutputStream(false);
   }

   public OutputStream newOutputStream(boolean var1) throws IOException {
      if (this.dataFile == null) {
         if (this.dataBytes == null) {
            this.dataBytes = new ByteArrayOutputStream(1024);
         } else if (!var1) {
            this.dataBytes.reset();
         }

         return this.dataBytes;
      } else {
         return new FileOutputStream(this.dataFile, this.resume && var1);
      }
   }

   public File getDataFile() {
      return this.dataFile;
   }

   public GetTask setDataFile(File var1) {
      return this.setDataFile(var1, false);
   }

   public GetTask setDataFile(File var1, boolean var2) {
      this.dataFile = var1;
      this.resume = var2;
      return this;
   }

   public long getResumeOffset() {
      if (this.resume) {
         if (this.dataFile != null) {
            return this.dataFile.length();
         }

         if (this.dataBytes != null) {
            return (long)this.dataBytes.size();
         }
      }

      return 0L;
   }

   public byte[] getDataBytes() {
      return this.dataFile == null && this.dataBytes != null ? this.dataBytes.toByteArray() : EMPTY;
   }

   public String getDataString() {
      return this.dataFile == null && this.dataBytes != null ? new String(this.dataBytes.toByteArray(), StandardCharsets.UTF_8) : "";
   }

   public GetTask setListener(TransportListener var1) {
      super.setListener(var1);
      return this;
   }

   public Map getChecksums() {
      return this.checksums;
   }

   public GetTask setChecksum(String var1, String var2) {
      if (var1 != null) {
         if (this.checksums.isEmpty()) {
            this.checksums = new HashMap();
         }

         if (var2 != null && var2.length() > 0) {
            this.checksums.put(var1, var2);
         } else {
            this.checksums.remove(var1);
         }
      }

      return this;
   }

   public String toString() {
      return "<< " + this.getLocation();
   }

   public TransportTask setListener(TransportListener var1) {
      return this.setListener(var1);
   }
}
