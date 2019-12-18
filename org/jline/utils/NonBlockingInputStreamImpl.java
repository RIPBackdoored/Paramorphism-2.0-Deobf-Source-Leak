package org.jline.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class NonBlockingInputStreamImpl extends NonBlockingInputStream {
   private InputStream in;
   private int b = -2;
   private String name;
   private boolean threadIsReading = false;
   private IOException exception = null;
   private long threadDelay = 60000L;
   private Thread thread;
   static final boolean $assertionsDisabled = !NonBlockingInputStreamImpl.class.desiredAssertionStatus();

   public NonBlockingInputStreamImpl(String var1, InputStream var2) {
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

   public synchronized int read(long var1, boolean var3) throws IOException {
      if (this.exception != null) {
         if (!$assertionsDisabled && this.b != -2) {
            throw new AssertionError();
         } else {
            IOException var10 = this.exception;
            if (!var3) {
               this.exception = null;
            }

            throw var10;
         }
      } else {
         if (this.b >= -1) {
            if (!$assertionsDisabled && this.exception != null) {
               throw new AssertionError();
            }
         } else if (!var3 && var1 <= 0L && !this.threadIsReading) {
            this.b = this.in.read();
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
                  if (!$assertionsDisabled && this.b != -2) {
                     throw new AssertionError();
                  }

                  IOException var7 = this.exception;
                  if (!var3) {
                     this.exception = null;
                  }

                  throw var7;
               }

               if (this.b >= -1) {
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

         int var9 = this.b;
         if (!var3) {
            this.b = -2;
         }

         return var9;
      }
   }

   private void run() {
      Log.debug("NonBlockingInputStream start");

      while(true) {
         boolean var22 = false;

         label206: {
            label207: {
               try {
                  var22 = true;
                  synchronized(this) {
                     boolean var1 = this.threadIsReading;

                     try {
                        if (!var1) {
                           this.wait(this.threadDelay);
                        }
                     } catch (InterruptedException var29) {
                     }

                     var1 = this.threadIsReading;
                     if (!var1) {
                        var22 = false;
                        break label207;
                     }
                  }

                  int var2 = -2;
                  IOException var3 = null;

                  try {
                     var2 = this.in.read();
                  } catch (IOException var28) {
                     var3 = var28;
                  }

                  synchronized(this) {
                     this.exception = var3;
                     this.b = var2;
                     this.threadIsReading = false;
                     this.notify();
                  }

                  if (var2 >= 0) {
                     continue;
                  }

                  var22 = false;
               } catch (Throwable var31) {
                  Log.warn("Error in NonBlockingInputStream thread", var31);
                  var22 = false;
                  break label206;
               } finally {
                  if (var22) {
                     Log.debug("NonBlockingInputStream shutdown");
                     synchronized(this) {
                        this.thread = null;
                        this.threadIsReading = false;
                     }
                  }
               }

               Log.debug("NonBlockingInputStream shutdown");
               synchronized(this) {
                  this.thread = null;
                  this.threadIsReading = false;
               }

               return;
            }

            Log.debug("NonBlockingInputStream shutdown");
            synchronized(this) {
               this.thread = null;
               this.threadIsReading = false;
            }

            return;
         }

         Log.debug("NonBlockingInputStream shutdown");
         synchronized(this) {
            this.thread = null;
            this.threadIsReading = false;
         }

         return;
      }
   }
}
