package org.jline.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;

public class NonBlockingReaderImpl extends NonBlockingReader {
   public static final int READ_EXPIRED = -2;
   private Reader in;
   private int ch = -2;
   private String name;
   private boolean threadIsReading = false;
   private IOException exception = null;
   private long threadDelay = 60000L;
   private Thread thread;
   static final boolean $assertionsDisabled = !NonBlockingReaderImpl.class.desiredAssertionStatus();

   public NonBlockingReaderImpl(String var1, Reader var2) {
      super();
      this.in = var2;
      this.name = var1;
   }

   private synchronized void startReadingThreadIfNeeded() {
      if (this.thread == null) {
         this.thread = new Thread(this::run);
         this.thread.setName(this.name + " non blocking reader thread");
         this.thread.setDaemon(true);
         this.thread.start();
      }

   }

   public synchronized void shutdown() {
      if (this.thread != null) {
         this.notify();
      }

   }

   public void close() throws IOException {
      this.in.close();
      this.shutdown();
   }

   public synchronized boolean ready() throws IOException {
      return this.ch >= 0 || this.in.ready();
   }

   protected synchronized int read(long var1, boolean var3) throws IOException {
      if (this.exception != null) {
         if (!$assertionsDisabled && this.ch != -2) {
            throw new AssertionError();
         } else {
            IOException var10 = this.exception;
            if (!var3) {
               this.exception = null;
            }

            throw var10;
         }
      } else {
         if (this.ch >= -1) {
            if (!$assertionsDisabled && this.exception != null) {
               throw new AssertionError();
            }
         } else if (!var3 && var1 <= 0L && !this.threadIsReading) {
            this.ch = this.in.read();
         } else {
            if (!this.threadIsReading) {
               this.threadIsReading = true;
               this.startReadingThreadIfNeeded();
               this.notifyAll();
            }

            boolean var4 = var1 <= 0L;

            while(var4 || var1 > 0L) {
               long var5 = System.currentTimeMillis();

               try {
                  if (Thread.interrupted()) {
                     throw new InterruptedException();
                  }

                  this.wait(var1);
               } catch (InterruptedException var8) {
                  this.exception = (IOException)(new InterruptedIOException()).initCause(var8);
               }

               if (this.exception != null) {
                  if (!$assertionsDisabled && this.ch != -2) {
                     throw new AssertionError();
                  }

                  IOException var7 = this.exception;
                  if (!var3) {
                     this.exception = null;
                  }

                  throw var7;
               }

               if (this.ch >= -1) {
                  if (!$assertionsDisabled && this.exception != null) {
                     throw new AssertionError();
                  }
                  break;
               }

               if (!var4) {
                  var1 -= System.currentTimeMillis() - var5;
               }
            }
         }

         int var9 = this.ch;
         if (!var3) {
            this.ch = -2;
         }

         return var9;
      }
   }

   private void run() {
      Log.debug("NonBlockingReader start");

      while(true) {
         boolean var20 = false;

         try {
            var20 = true;
            synchronized(this) {
               boolean var1 = this.threadIsReading;

               try {
                  if (!var1) {
                     this.wait(this.threadDelay);
                  }
               } catch (InterruptedException var24) {
               }

               var1 = this.threadIsReading;
               if (!var1) {
                  var20 = false;
                  break;
               }
            }

            int var2 = -2;
            IOException var3 = null;

            try {
               var2 = this.in.read();
            } catch (IOException var26) {
               var3 = var26;
            }

            synchronized(this) {
               this.exception = var3;
               this.ch = var2;
               this.threadIsReading = false;
               this.notify();
               continue;
            }
         } catch (Throwable var28) {
            Log.warn("Error in NonBlockingReader thread", var28);
            var20 = false;
         } finally {
            if (var20) {
               Log.debug("NonBlockingReader shutdown");
               synchronized(this) {
                  this.thread = null;
                  this.threadIsReading = false;
               }
            }
         }

         Log.debug("NonBlockingReader shutdown");
         synchronized(this) {
            this.thread = null;
            this.threadIsReading = false;
         }

         return;
      }

      Log.debug("NonBlockingReader shutdown");
      synchronized(this) {
         this.thread = null;
         this.threadIsReading = false;
      }

   }

   public synchronized void clear() throws IOException {
      while(this.ready()) {
         this.read();
      }

   }
}
