package org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;
import org.jline.terminal.Cursor;
import org.jline.terminal.Terminal$SignalHandler;

public class ExternalTerminal extends LineDisciplineTerminal {
   protected final AtomicBoolean closed;
   protected final InputStream masterInput;
   protected final Object lock;
   protected boolean paused;
   protected Thread pumpThread;

   public ExternalTerminal(String var1, String var2, InputStream var3, OutputStream var4, Charset var5) throws IOException {
      this(var1, var2, var3, var4, var5, Terminal$SignalHandler.SIG_DFL);
   }

   public ExternalTerminal(String var1, String var2, InputStream var3, OutputStream var4, Charset var5, Terminal$SignalHandler var6) throws IOException {
      this(var1, var2, var3, var4, var5, var6, false);
   }

   public ExternalTerminal(String var1, String var2, InputStream var3, OutputStream var4, Charset var5, Terminal$SignalHandler var6, boolean var7) throws IOException {
      super(var1, var2, var4, var5, var6);
      this.closed = new AtomicBoolean();
      this.lock = new Object();
      this.paused = true;
      this.masterInput = var3;
      if (!var7) {
         this.resume();
      }

   }

   public void close() throws IOException {
      if (this.closed.compareAndSet(false, true)) {
         this.pause();
         super.close();
      }

   }

   public boolean canPauseResume() {
      return true;
   }

   public void pause() {
      synchronized(this.lock) {
         this.paused = true;
      }

   }

   public void pause(boolean var1) throws InterruptedException {
      Thread var2;
      synchronized(this.lock) {
         this.paused = true;
         var2 = this.pumpThread;
      }

      if (var2 != null) {
         var2.interrupt();
         var2.join();
      }

   }

   public void resume() {
      synchronized(this.lock) {
         this.paused = false;
         if (this.pumpThread == null) {
            this.pumpThread = new Thread(this::pump, this.toString() + " input pump thread");
            this.pumpThread.setDaemon(true);
            this.pumpThread.start();
         }
      }

   }

   public boolean paused() {
      boolean var10000;
      synchronized(this.lock) {
         var10000 = this.paused;
      }

      return var10000;
   }

   public void pump() {
      boolean var20 = false;

      label177: {
         label178: {
            label179: {
               try {
                  var20 = true;
                  byte[] var1 = new byte[1024];

                  while(true) {
                     int var2 = this.masterInput.read(var1);
                     if (var2 >= 0) {
                        this.processInputBytes(var1, 0, var2);
                     }

                     if (var2 < 0) {
                        var20 = false;
                        break label179;
                     }

                     if (this.closed.get()) {
                        var20 = false;
                        break label179;
                     }

                     synchronized(this.lock) {
                        if (this.paused) {
                           this.pumpThread = null;
                           var20 = false;
                           break label177;
                        }
                     }
                  }
               } catch (IOException var27) {
                  this.processIOException(var27);
                  var20 = false;
               } finally {
                  if (var20) {
                     synchronized(this.lock) {
                        this.pumpThread = null;
                     }
                  }
               }

               synchronized(this.lock) {
                  this.pumpThread = null;
                  break label178;
               }
            }

            synchronized(this.lock) {
               this.pumpThread = null;
            }
         }

         try {
            this.slaveInput.close();
         } catch (IOException var23) {
         }

         return;
      }

      synchronized(this.lock) {
         this.pumpThread = null;
      }

   }

   public Cursor getCursorPosition(IntConsumer var1) {
      return CursorSupport.getCursorPosition(this, var1);
   }
}
