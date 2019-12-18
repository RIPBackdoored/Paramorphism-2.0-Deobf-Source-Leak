package org.jline.terminal.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.terminal.spi.Pty;
import org.jline.utils.NonBlocking;
import org.jline.utils.NonBlockingInputStream;
import org.jline.utils.NonBlockingReader;
import org.jline.utils.ShutdownHooks;
import org.jline.utils.ShutdownHooks$Task;
import org.jline.utils.Signals;

public class PosixSysTerminal extends AbstractPosixTerminal {
   protected final NonBlockingInputStream input;
   protected final OutputStream output;
   protected final NonBlockingReader reader;
   protected final PrintWriter writer;
   protected final Map nativeHandlers = new HashMap();
   protected final ShutdownHooks$Task closer;

   public PosixSysTerminal(String var1, String var2, Pty var3, Charset var4, boolean var5, Terminal$SignalHandler var6) throws IOException {
      super(var1, var2, var3, var4, var6);
      this.input = NonBlocking.nonBlocking(this.getName(), var3.getSlaveInput());
      this.output = var3.getSlaveOutput();
      this.reader = NonBlocking.nonBlocking(this.getName(), this.input, this.encoding());
      this.writer = new PrintWriter(new OutputStreamWriter(this.output, this.encoding()));
      this.parseInfoCmp();
      if (var5) {
         Terminal$Signal[] var7 = Terminal$Signal.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Terminal$Signal var10 = var7[var9];
            if (var6 == Terminal$SignalHandler.SIG_DFL) {
               this.nativeHandlers.put(var10, Signals.registerDefault(var10.name()));
            } else {
               this.nativeHandlers.put(var10, Signals.register(var10.name(), this::lambda$new$0));
            }
         }
      }

      this.closer = this::close;
      ShutdownHooks.add(this.closer);
   }

   public Terminal$SignalHandler handle(Terminal$Signal var1, Terminal$SignalHandler var2) {
      Terminal$SignalHandler var3 = super.handle(var1, var2);
      if (var3 != var2) {
         if (var2 == Terminal$SignalHandler.SIG_DFL) {
            Signals.registerDefault(var1.name());
         } else {
            Signals.register(var1.name(), this::lambda$handle$1);
         }
      }

      return var3;
   }

   public NonBlockingReader reader() {
      return this.reader;
   }

   public PrintWriter writer() {
      return this.writer;
   }

   public InputStream input() {
      return this.input;
   }

   public OutputStream output() {
      return this.output;
   }

   public void close() throws IOException {
      ShutdownHooks.remove(this.closer);
      Iterator var1 = this.nativeHandlers.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         Signals.unregister(((Terminal$Signal)var2.getKey()).name(), var2.getValue());
      }

      super.close();
      this.reader.shutdown();
   }

   private void lambda$handle$1(Terminal$Signal var1) {
      this.raise(var1);
   }

   private void lambda$new$0(Terminal$Signal var1) {
      this.raise(var1);
   }
}
