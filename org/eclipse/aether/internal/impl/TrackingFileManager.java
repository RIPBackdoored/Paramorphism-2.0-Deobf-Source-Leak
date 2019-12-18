package org.eclipse.aether.internal.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TrackingFileManager {
   private static final Logger LOGGER = LoggerFactory.getLogger(TrackingFileManager.class);

   TrackingFileManager() {
      super();
   }

   public Properties read(File var1) {
      Properties var6;
      synchronized(this.getLock(var1)) {
         FileLock var3 = null;
         FileInputStream var4 = null;
         boolean var12 = false;

         label105: {
            Properties var5;
            label106: {
               try {
                  var12 = true;
                  if (var1.exists()) {
                     var4 = new FileInputStream(var1);
                     var3 = this.lock(var4.getChannel(), Math.max(1L, var1.length()), true);
                     var5 = new Properties();
                     var5.load(var4);
                     var6 = var5;
                     var12 = false;
                     break label105;
                  }

                  var5 = null;
                  var12 = false;
                  break label106;
               } catch (IOException var13) {
                  LOGGER.warn((String)"Failed to read tracking file {}", (Object)var1, (Object)var13);
                  var12 = false;
               } finally {
                  if (var12) {
                     this.release(var3, var1);
                     this.close(var4, var1);
                  }
               }

               this.release(var3, var1);
               this.close(var4, var1);
               return null;
            }

            this.release(var3, var1);
            this.close(var4, var1);
            return var5;
         }

         this.release(var3, var1);
         this.close(var4, var1);
      }

      return var6;
   }

   public Properties update(File var1, Map var2) {
      Properties var3 = new Properties();
      synchronized(this.getLock(var1)) {
         File var5 = var1.getParentFile();
         if (!var5.mkdirs() && !var5.exists()) {
            LOGGER.warn((String)"Failed to create parent directories for tracking file {}", (Object)var1);
            Properties var10000 = var3;
            return var10000;
         }

         RandomAccessFile var6 = null;
         FileLock var7 = null;
         boolean var15 = false;

         label134: {
            try {
               var15 = true;
               var6 = new RandomAccessFile(var1, "rw");
               var7 = this.lock(var6.getChannel(), Math.max(1L, var6.length()), false);
               if (var1.canRead()) {
                  byte[] var8 = new byte[(int)var6.length()];
                  var6.readFully(var8);
                  ByteArrayInputStream var9 = new ByteArrayInputStream(var8);
                  var3.load(var9);
               }

               Iterator var19 = var2.entrySet().iterator();

               while(var19.hasNext()) {
                  Entry var21 = (Entry)var19.next();
                  if (var21.getValue() == null) {
                     var3.remove(var21.getKey());
                  } else {
                     var3.setProperty((String)var21.getKey(), (String)var21.getValue());
                  }
               }

               ByteArrayOutputStream var20 = new ByteArrayOutputStream(2048);
               LOGGER.debug((String)"Writing tracking file {}", (Object)var1);
               var3.store(var20, "NOTE: This is a Maven Resolver internal implementation file, its format can be changed without prior notice.");
               var6.seek(0L);
               var6.write(var20.toByteArray());
               var6.setLength(var6.getFilePointer());
               var15 = false;
               break label134;
            } catch (IOException var16) {
               LOGGER.warn((String)"Failed to write tracking file {}", (Object)var1, (Object)var16);
               var15 = false;
            } finally {
               if (var15) {
                  this.release(var7, var1);
                  this.close(var6, var1);
               }
            }

            this.release(var7, var1);
            this.close(var6, var1);
            return var3;
         }

         this.release(var7, var1);
         this.close(var6, var1);
      }

      return var3;
   }

   private void release(FileLock var1, File var2) {
      if (var1 != null) {
         try {
            var1.release();
         } catch (IOException var4) {
            LOGGER.warn((String)"Error releasing lock for tracking file {}", (Object)var2, (Object)var4);
         }
      }

   }

   private void close(Closeable var1, File var2) {
      if (var1 != null) {
         try {
            var1.close();
         } catch (IOException var4) {
            LOGGER.warn((String)"Error closing tracking file {}", (Object)var2, (Object)var4);
         }
      }

   }

   private Object getLock(File var1) {
      String var10000;
      try {
         var10000 = var1.getCanonicalPath().intern();
      } catch (IOException var3) {
         LOGGER.warn((String)"Failed to canonicalize path {}: {}", (Object)var1, (Object)var3.getMessage());
         return var1.getAbsolutePath().intern();
      }

      return var10000;
   }

   private FileLock lock(FileChannel var1, long var2, boolean var4) throws IOException {
      FileLock var5 = null;
      int var6 = 8;

      while(var6 >= 0) {
         try {
            var5 = var1.lock(0L, var2, var4);
            break;
         } catch (OverlappingFileLockException var10) {
            if (var6 <= 0) {
               throw new IOException(var10);
            }

            try {
               Thread.sleep(50L);
            } catch (InterruptedException var9) {
               Thread.currentThread().interrupt();
            }

            --var6;
         }
      }

      if (var5 == null) {
         throw new IOException("Could not lock file");
      } else {
         return var5;
      }
   }
}
