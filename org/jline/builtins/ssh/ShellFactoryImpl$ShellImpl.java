package org.jline.builtins.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.Signal;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.session.ServerSession;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.TerminalBuilder;

public class ShellFactoryImpl$ShellImpl implements Command, SessionAware {
   private InputStream in;
   private OutputStream out;
   private OutputStream err;
   private ExitCallback callback;
   private ServerSession session;
   private boolean closed;
   final ShellFactoryImpl this$0;

   public ShellFactoryImpl$ShellImpl(ShellFactoryImpl var1) {
      super();
      this.this$0 = var1;
   }

   public void setInputStream(InputStream var1) {
      this.in = var1;
   }

   public void setOutputStream(OutputStream var1) {
      this.out = var1;
   }

   public void setErrorStream(OutputStream var1) {
      this.err = var1;
   }

   public void setExitCallback(ExitCallback var1) {
      this.callback = var1;
   }

   public void setSession(ServerSession var1) {
      this.session = var1;
   }

   public void start(Environment var1) throws IOException {
      try {
         (new Thread(this::lambda$start$0)).start();
      } catch (Exception var3) {
         throw new IOException("Unable to start shell", var3);
      }

   }

   public void run(Environment var1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void destroy() {
      if (!this.closed) {
         this.closed = true;
         ShellFactoryImpl.access$100(new OutputStream[]{this.out, this.err});
         ShellFactoryImpl.close(this.in, this.out, this.err);
         this.callback.onExit(0);
      }

   }

   private static void lambda$run$1(Terminal var0, Environment var1, Signal var2) {
      var0.setSize(new Size(Integer.parseInt((String)var1.getEnv().get("COLUMNS")), Integer.parseInt((String)var1.getEnv().get("LINES"))));
      var0.raise(Terminal$Signal.WINCH);
   }

   private void lambda$start$0(Environment var1) {
      try {
         this.run(var1);
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

   }
}
