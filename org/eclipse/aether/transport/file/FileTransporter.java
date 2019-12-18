package org.eclipse.aether.transport.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.connector.transport.AbstractTransporter;
import org.eclipse.aether.spi.connector.transport.GetTask;
import org.eclipse.aether.spi.connector.transport.PeekTask;
import org.eclipse.aether.spi.connector.transport.PutTask;
import org.eclipse.aether.spi.connector.transport.TransportTask;
import org.eclipse.aether.transfer.NoTransporterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class FileTransporter extends AbstractTransporter {
   private static final Logger LOGGER = LoggerFactory.getLogger(FileTransporter.class);
   private final File basedir;

   FileTransporter(RemoteRepository var1) throws NoTransporterException {
      super();
      if (!"file".equalsIgnoreCase(var1.getProtocol())) {
         throw new NoTransporterException(var1);
      } else {
         this.basedir = (new File(PathUtils.basedir(var1.getUrl()))).getAbsoluteFile();
      }
   }

   File getBasedir() {
      return this.basedir;
   }

   public int classify(Throwable var1) {
      return var1 instanceof ResourceNotFoundException ? 1 : 0;
   }

   protected void implPeek(PeekTask var1) throws Exception {
      this.getFile(var1, true);
   }

   protected void implGet(GetTask var1) throws Exception {
      File var2 = this.getFile(var1, true);
      this.utilGet(var1, new FileInputStream(var2), true, var2.length(), false);
   }

   protected void implPut(PutTask var1) throws Exception {
      File var2 = this.getFile(var1, false);
      var2.getParentFile().mkdirs();

      try {
         this.utilPut(var1, new FileOutputStream(var2), true);
      } catch (Exception var4) {
         if (!var2.delete() && var2.exists()) {
            LOGGER.debug((String)"Could not delete partial file {}", (Object)var2);
         }

         throw var4;
      }

   }

   private File getFile(TransportTask var1, boolean var2) throws Exception {
      String var3 = var1.getLocation().getPath();
      if (var3.contains("../")) {
         throw new IllegalArgumentException("illegal resource path: " + var3);
      } else {
         File var4 = new File(this.basedir, var3);
         if (var2 && !var4.exists()) {
            throw new ResourceNotFoundException("Could not locate " + var4);
         } else {
            return var4;
         }
      }
   }

   protected void implClose() {
   }
}
