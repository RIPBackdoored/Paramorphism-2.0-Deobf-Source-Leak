package org.jline.builtins.ssh;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.scp.ScpCommandFactory.Builder;
import org.jline.builtins.Options;
import org.jline.builtins.Options$HelpException;
import org.jline.reader.LineReader;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Attributes$OutputFlag;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;

public class Ssh {
   public static final String[] functions = new String[]{"ssh", "sshd"};
   private static final int defaultPort = 2022;
   private final Consumer shell;
   private final Consumer execute;
   private final Supplier serverBuilder;
   private final Supplier clientBuilder;
   private SshServer server;
   private int port;
   private String ip;

   public Ssh(Consumer var1, Consumer var2, Supplier var3, Supplier var4) {
      super();
      this.shell = var1;
      this.execute = var2;
      this.serverBuilder = var3;
      this.clientBuilder = var4;
   }

   public void ssh(Terminal var1, LineReader var2, String var3, InputStream var4, PrintStream var5, PrintStream var6, String[] var7) throws Exception {
      String[] var8 = new String[]{"ssh - connect to a server using ssh", "Usage: ssh [user@]hostname [command]", "  -? --help                show help"};
      Options var9 = Options.compile(var8).parse((Object[])var7, true);
      List var10 = var9.args();
      if (!var9.isSet("help") && !var10.isEmpty()) {
         String var11 = var3;
         String var12 = (String)var10.remove(0);
         int var13 = this.port;
         String var14 = null;
         int var15 = var12.indexOf(64);
         if (var15 >= 0) {
            var11 = var12.substring(0, var15);
            var12 = var12.substring(var15 + 1);
         }

         var15 = var12.indexOf(58);
         if (var15 >= 0) {
            var13 = Integer.parseInt(var12.substring(var15 + 1));
            var12 = var12.substring(0, var15);
         }

         if (!var10.isEmpty()) {
            var14 = String.join(" ", var10);
         }

         SshClient var16 = (SshClient)this.clientBuilder.get();
         Throwable var17 = null;
         boolean var44 = false;

         try {
            var44 = true;
            Ssh$JLineUserInteraction var18 = new Ssh$JLineUserInteraction(var1, var2, var6);
            var16.setFilePasswordProvider(var18);
            var16.setUserInteraction(var18);
            var16.start();
            ClientSession var19 = this.connectWithRetries(var1.writer(), var16, var11, var12, var13, 3);
            Throwable var20 = null;
            boolean var55 = false;

            try {
               var55 = true;
               var19.auth().verify();
               if (var14 != null) {
                  ClientChannel var21 = var19.createChannel("exec", var14 + "\n");
                  var21.setIn(new ByteArrayInputStream(new byte[0]));
                  var21.setOut(new NoCloseOutputStream(var5));
                  var21.setErr(new NoCloseOutputStream(var6));
                  var21.open().verify();
                  var21.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
                  var55 = false;
               } else {
                  ChannelShell var88 = var19.createShellChannel();
                  Attributes var22 = var1.enterRawMode();
                  boolean var66 = false;

                  try {
                     var66 = true;
                     HashMap var23 = new HashMap();
                     var23.put(PtyMode.VINTR, var22.getControlChar(Attributes$ControlChar.VINTR));
                     var23.put(PtyMode.VQUIT, var22.getControlChar(Attributes$ControlChar.VQUIT));
                     var23.put(PtyMode.VERASE, var22.getControlChar(Attributes$ControlChar.VERASE));
                     var23.put(PtyMode.VKILL, var22.getControlChar(Attributes$ControlChar.VKILL));
                     var23.put(PtyMode.VEOF, var22.getControlChar(Attributes$ControlChar.VEOF));
                     var23.put(PtyMode.VEOL, var22.getControlChar(Attributes$ControlChar.VEOL));
                     var23.put(PtyMode.VEOL2, var22.getControlChar(Attributes$ControlChar.VEOL2));
                     var23.put(PtyMode.VSTART, var22.getControlChar(Attributes$ControlChar.VSTART));
                     var23.put(PtyMode.VSTOP, var22.getControlChar(Attributes$ControlChar.VSTOP));
                     var23.put(PtyMode.VSUSP, var22.getControlChar(Attributes$ControlChar.VSUSP));
                     var23.put(PtyMode.VDSUSP, var22.getControlChar(Attributes$ControlChar.VDSUSP));
                     var23.put(PtyMode.VREPRINT, var22.getControlChar(Attributes$ControlChar.VREPRINT));
                     var23.put(PtyMode.VWERASE, var22.getControlChar(Attributes$ControlChar.VWERASE));
                     var23.put(PtyMode.VLNEXT, var22.getControlChar(Attributes$ControlChar.VLNEXT));
                     var23.put(PtyMode.VSTATUS, var22.getControlChar(Attributes$ControlChar.VSTATUS));
                     var23.put(PtyMode.VDISCARD, var22.getControlChar(Attributes$ControlChar.VDISCARD));
                     var23.put(PtyMode.IGNPAR, getFlag(var22, Attributes$InputFlag.IGNPAR));
                     var23.put(PtyMode.PARMRK, getFlag(var22, Attributes$InputFlag.PARMRK));
                     var23.put(PtyMode.INPCK, getFlag(var22, Attributes$InputFlag.INPCK));
                     var23.put(PtyMode.ISTRIP, getFlag(var22, Attributes$InputFlag.ISTRIP));
                     var23.put(PtyMode.INLCR, getFlag(var22, Attributes$InputFlag.INLCR));
                     var23.put(PtyMode.IGNCR, getFlag(var22, Attributes$InputFlag.IGNCR));
                     var23.put(PtyMode.ICRNL, getFlag(var22, Attributes$InputFlag.ICRNL));
                     var23.put(PtyMode.IXON, getFlag(var22, Attributes$InputFlag.IXON));
                     var23.put(PtyMode.IXANY, getFlag(var22, Attributes$InputFlag.IXANY));
                     var23.put(PtyMode.IXOFF, getFlag(var22, Attributes$InputFlag.IXOFF));
                     var23.put(PtyMode.ISIG, getFlag(var22, Attributes$LocalFlag.ISIG));
                     var23.put(PtyMode.ICANON, getFlag(var22, Attributes$LocalFlag.ICANON));
                     var23.put(PtyMode.ECHO, getFlag(var22, Attributes$LocalFlag.ECHO));
                     var23.put(PtyMode.ECHOE, getFlag(var22, Attributes$LocalFlag.ECHOE));
                     var23.put(PtyMode.ECHOK, getFlag(var22, Attributes$LocalFlag.ECHOK));
                     var23.put(PtyMode.ECHONL, getFlag(var22, Attributes$LocalFlag.ECHONL));
                     var23.put(PtyMode.NOFLSH, getFlag(var22, Attributes$LocalFlag.NOFLSH));
                     var23.put(PtyMode.TOSTOP, getFlag(var22, Attributes$LocalFlag.TOSTOP));
                     var23.put(PtyMode.IEXTEN, getFlag(var22, Attributes$LocalFlag.IEXTEN));
                     var23.put(PtyMode.OPOST, getFlag(var22, Attributes$OutputFlag.OPOST));
                     var23.put(PtyMode.ONLCR, getFlag(var22, Attributes$OutputFlag.ONLCR));
                     var23.put(PtyMode.OCRNL, getFlag(var22, Attributes$OutputFlag.OCRNL));
                     var23.put(PtyMode.ONOCR, getFlag(var22, Attributes$OutputFlag.ONOCR));
                     var23.put(PtyMode.ONLRET, getFlag(var22, Attributes$OutputFlag.ONLRET));
                     var88.setPtyModes(var23);
                     var88.setPtyColumns(var1.getWidth());
                     var88.setPtyLines(var1.getHeight());
                     var88.setAgentForwarding(true);
                     var88.setEnv("TERM", var1.getType());
                     var88.setIn(new NoCloseInputStream(var4));
                     var88.setOut(new NoCloseOutputStream(var5));
                     var88.setErr(new NoCloseOutputStream(var6));
                     var88.open().verify();
                     Terminal$SignalHandler var24 = var1.handle(Terminal$Signal.WINCH, Ssh::lambda$ssh$0);
                     Terminal$SignalHandler var25 = var1.handle(Terminal$Signal.QUIT, Ssh::lambda$ssh$1);
                     Terminal$SignalHandler var26 = var1.handle(Terminal$Signal.INT, Ssh::lambda$ssh$2);
                     Terminal$SignalHandler var27 = var1.handle(Terminal$Signal.TSTP, Ssh::lambda$ssh$3);
                     boolean var77 = false;

                     try {
                        var77 = true;
                        var88.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
                        var77 = false;
                     } finally {
                        if (var77) {
                           var1.handle(Terminal$Signal.WINCH, var24);
                           var1.handle(Terminal$Signal.INT, var26);
                           var1.handle(Terminal$Signal.TSTP, var27);
                           var1.handle(Terminal$Signal.QUIT, var25);
                        }
                     }

                     var1.handle(Terminal$Signal.WINCH, var24);
                     var1.handle(Terminal$Signal.INT, var26);
                     var1.handle(Terminal$Signal.TSTP, var27);
                     var1.handle(Terminal$Signal.QUIT, var25);
                     var66 = false;
                  } finally {
                     if (var66) {
                        var1.setAttributes(var22);
                     }
                  }

                  var1.setAttributes(var22);
                  var55 = false;
               }
            } catch (Throwable var84) {
               var20 = var84;
               throw var84;
            } finally {
               if (var55) {
                  if (var19 != null) {
                     if (var20 != null) {
                        try {
                           var19.close();
                        } catch (Throwable var79) {
                           var20.addSuppressed(var79);
                        }
                     } else {
                        var19.close();
                     }
                  }

               }
            }

            if (var19 != null) {
               if (var20 != null) {
                  try {
                     var19.close();
                     var44 = false;
                  } catch (Throwable var81) {
                     var20.addSuppressed(var81);
                     var44 = false;
                  }
               } else {
                  var19.close();
                  var44 = false;
               }
            } else {
               var44 = false;
            }
         } catch (Throwable var86) {
            var17 = var86;
            throw var86;
         } finally {
            if (var44) {
               if (var16 != null) {
                  if (var17 != null) {
                     try {
                        var16.close();
                     } catch (Throwable var78) {
                        var17.addSuppressed(var78);
                     }
                  } else {
                     var16.close();
                  }
               }

            }
         }

         if (var16 != null) {
            if (var17 != null) {
               try {
                  var16.close();
               } catch (Throwable var80) {
                  var17.addSuppressed(var80);
               }
            } else {
               var16.close();
            }
         }

      } else {
         throw new Options$HelpException(var9.usage());
      }
   }

   private static int getFlag(Attributes var0, Attributes$InputFlag var1) {
      return var0.getInputFlag(var1) ? 1 : 0;
   }

   private static int getFlag(Attributes var0, Attributes$OutputFlag var1) {
      return var0.getOutputFlag(var1) ? 1 : 0;
   }

   private static int getFlag(Attributes var0, Attributes$LocalFlag var1) {
      return var0.getLocalFlag(var1) ? 1 : 0;
   }

   private ClientSession connectWithRetries(PrintWriter var1, SshClient var2, String var3, String var4, int var5, int var6) throws Exception {
      ClientSession var7 = null;
      int var8 = 0;

      do {
         ConnectFuture var9 = var2.connect(var3, var4, var5);
         var9.await();

         try {
            var7 = var9.getSession();
         } catch (Exception var11) {
            if (var8++ >= var6) {
               throw var11;
            }

            Thread.sleep(2000L);
            var1.println("retrying (attempt " + var8 + ") ...");
         }
      } while(var7 == null);

      return var7;
   }

   public void sshd(PrintStream var1, PrintStream var2, String[] var3) throws Exception {
      String[] var4 = new String[]{"sshd - start an ssh server", "Usage: sshd [-i ip] [-p port] start | stop | status", "  -i --ip=INTERFACE        listen interface (default=127.0.0.1)", "  -p --port=PORT           listen port (default=2022)", "  -? --help                show help"};
      Options var5 = Options.compile(var4).parse((Object[])var3, true);
      List var6 = var5.args();
      if (!var5.isSet("help") && !var6.isEmpty()) {
         String var7 = (String)var6.get(0);
         if ("start".equals(var7)) {
            if (this.server != null) {
               throw new IllegalStateException("sshd is already running on port " + this.port);
            }

            this.ip = var5.get("ip");
            this.port = var5.getNumber("port");
            this.start();
            this.status(var1);
         } else if ("stop".equals(var7)) {
            if (this.server == null) {
               throw new IllegalStateException("sshd is not running.");
            }

            this.stop();
         } else {
            if (!"status".equals(var7)) {
               throw var5.usageError("bad command: " + var7);
            }

            this.status(var1);
         }

      } else {
         throw new Options$HelpException(var5.usage());
      }
   }

   private void status(PrintStream var1) {
      if (this.server != null) {
         var1.println("sshd is running on " + this.ip + ":" + this.port);
      } else {
         var1.println("sshd is not running.");
      }

   }

   private void start() throws IOException {
      this.server = (SshServer)this.serverBuilder.get();
      this.server.setPort(this.port);
      this.server.setHost(this.ip);
      this.server.setShellFactory(new ShellFactoryImpl(this.shell));
      this.server.setCommandFactory((new Builder()).withDelegate(this::lambda$start$4).build());
      this.server.setSubsystemFactories(Collections.singletonList((new org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory.Builder()).build()));
      this.server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
      this.server.start();
   }

   private void stop() throws IOException {
      boolean var3 = false;

      try {
         var3 = true;
         this.server.stop();
         var3 = false;
      } finally {
         if (var3) {
            this.server = null;
         }
      }

      this.server = null;
   }

   private Command lambda$start$4(String var1) {
      return new ShellCommand(this.execute, var1);
   }

   private static void lambda$ssh$3(ChannelShell var0, Attributes var1, Terminal$Signal var2) {
      try {
         var0.getInvertedIn().write(var1.getControlChar(Attributes$ControlChar.VDSUSP));
         var0.getInvertedIn().flush();
      } catch (IOException var4) {
      }

   }

   private static void lambda$ssh$2(ChannelShell var0, Attributes var1, Terminal$Signal var2) {
      try {
         var0.getInvertedIn().write(var1.getControlChar(Attributes$ControlChar.VINTR));
         var0.getInvertedIn().flush();
      } catch (IOException var4) {
      }

   }

   private static void lambda$ssh$1(ChannelShell var0, Attributes var1, Terminal$Signal var2) {
      try {
         var0.getInvertedIn().write(var1.getControlChar(Attributes$ControlChar.VQUIT));
         var0.getInvertedIn().flush();
      } catch (IOException var4) {
      }

   }

   private static void lambda$ssh$0(Terminal var0, ChannelShell var1, Terminal$Signal var2) {
      try {
         Size var3 = var0.getSize();
         var1.sendWindowChange(var3.getColumns(), var3.getRows());
      } catch (IOException var4) {
      }

   }
}
