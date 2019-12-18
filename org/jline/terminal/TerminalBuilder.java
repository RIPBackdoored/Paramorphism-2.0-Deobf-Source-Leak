package org.jline.terminal;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;
import java.util.ServiceLoader;
import org.jline.terminal.impl.AbstractPosixTerminal;
import org.jline.terminal.impl.DumbTerminal;
import org.jline.terminal.impl.ExecPty;
import org.jline.terminal.impl.ExternalTerminal;
import org.jline.terminal.impl.PosixPtyTerminal;
import org.jline.terminal.impl.PosixSysTerminal;
import org.jline.terminal.spi.JansiSupport;
import org.jline.terminal.spi.JnaSupport;
import org.jline.terminal.spi.Pty;
import org.jline.utils.Log;
import org.jline.utils.OSUtils;

public final class TerminalBuilder {
   public static final String PROP_ENCODING = "org.jline.terminal.encoding";
   public static final String PROP_CODEPAGE = "org.jline.terminal.codepage";
   public static final String PROP_TYPE = "org.jline.terminal.type";
   public static final String PROP_JNA = "org.jline.terminal.jna";
   public static final String PROP_JANSI = "org.jline.terminal.jansi";
   public static final String PROP_EXEC = "org.jline.terminal.exec";
   public static final String PROP_DUMB = "org.jline.terminal.dumb";
   public static final String PROP_DUMB_COLOR = "org.jline.terminal.dumb.color";
   public static final String PROP_NON_BLOCKING_READS = "org.jline.terminal.pty.nonBlockingReads";
   public static final String PROP_COLOR_DISTANCE = "org.jline.utils.colorDistance";
   public static final String PROP_DISABLE_ALTERNATE_CHARSET = "org.jline.utils.disableAlternateCharset";
   private String name;
   private InputStream in;
   private OutputStream out;
   private String type;
   private Charset encoding;
   private int codepage;
   private Boolean system;
   private Boolean jna;
   private Boolean jansi;
   private Boolean exec;
   private Boolean dumb;
   private Attributes attributes;
   private Size size;
   private boolean nativeSignals = false;
   private Terminal$SignalHandler signalHandler;
   private boolean paused;

   public static Terminal terminal() throws IOException {
      return builder().build();
   }

   public static TerminalBuilder builder() {
      return new TerminalBuilder();
   }

   private TerminalBuilder() {
      super();
      this.signalHandler = Terminal$SignalHandler.SIG_DFL;
      this.paused = false;
   }

   public TerminalBuilder name(String var1) {
      this.name = var1;
      return this;
   }

   public TerminalBuilder streams(InputStream var1, OutputStream var2) {
      this.in = var1;
      this.out = var2;
      return this;
   }

   public TerminalBuilder system(boolean var1) {
      this.system = var1;
      return this;
   }

   public TerminalBuilder jna(boolean var1) {
      this.jna = var1;
      return this;
   }

   public TerminalBuilder jansi(boolean var1) {
      this.jansi = var1;
      return this;
   }

   public TerminalBuilder exec(boolean var1) {
      this.exec = var1;
      return this;
   }

   public TerminalBuilder dumb(boolean var1) {
      this.dumb = var1;
      return this;
   }

   public TerminalBuilder type(String var1) {
      this.type = var1;
      return this;
   }

   public TerminalBuilder encoding(String var1) throws UnsupportedCharsetException {
      return this.encoding(var1 != null ? Charset.forName(var1) : null);
   }

   public TerminalBuilder encoding(Charset var1) {
      this.encoding = var1;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public TerminalBuilder codepage(int var1) {
      this.codepage = var1;
      return this;
   }

   public TerminalBuilder attributes(Attributes var1) {
      this.attributes = var1;
      return this;
   }

   public TerminalBuilder size(Size var1) {
      this.size = var1;
      return this;
   }

   public TerminalBuilder nativeSignals(boolean var1) {
      this.nativeSignals = var1;
      return this;
   }

   public TerminalBuilder signalHandler(Terminal$SignalHandler var1) {
      this.signalHandler = var1;
      return this;
   }

   public TerminalBuilder paused(boolean var1) {
      this.paused = var1;
      return this;
   }

   public Terminal build() throws IOException {
      Terminal var1 = this.doBuild();
      Log.debug(TerminalBuilder::lambda$build$0);
      if (var1 instanceof AbstractPosixTerminal) {
         Log.debug(TerminalBuilder::lambda$build$1);
      }

      return var1;
   }

   private Terminal doBuild() throws IOException {
      String var1 = this.name;
      if (var1 == null) {
         var1 = "JLine terminal";
      }

      Charset var2 = this.encoding;
      if (var2 == null) {
         String var3 = System.getProperty("org.jline.terminal.encoding");
         if (var3 != null && Charset.isSupported(var3)) {
            var2 = Charset.forName(var3);
         }
      }

      int var21 = this.codepage;
      String var4;
      if (var21 <= 0) {
         var4 = System.getProperty("org.jline.terminal.codepage");
         if (var4 != null) {
            var21 = Integer.parseInt(var4);
         }
      }

      var4 = this.type;
      if (var4 == null) {
         var4 = System.getProperty("org.jline.terminal.type");
      }

      if (var4 == null) {
         var4 = System.getenv("TERM");
      }

      Boolean var5 = this.jna;
      if (var5 == null) {
         var5 = getBoolean("org.jline.terminal.jna", true);
      }

      Boolean var6 = this.jansi;
      if (var6 == null) {
         var6 = getBoolean("org.jline.terminal.jansi", true);
      }

      Boolean var7 = this.exec;
      if (var7 == null) {
         var7 = getBoolean("org.jline.terminal.exec", true);
      }

      Boolean var8 = this.dumb;
      if (var8 == null) {
         var8 = getBoolean("org.jline.terminal.dumb", (Boolean)null);
      }

      if (this.system != null && this.system || this.system == null && this.in == null && this.out == null) {
         if (this.attributes != null || this.size != null) {
            Log.warn("Attributes and size fields are ignored when creating a system terminal");
         }

         IllegalStateException var23 = new IllegalStateException("Unable to create a system terminal");
         boolean var10;
         PosixSysTerminal var27;
         if (OSUtils.IS_WINDOWS) {
            var10 = "cygwin".equals(System.getenv("TERM"));
            boolean var11 = OSUtils.IS_CONEMU;
            if ((OSUtils.IS_CYGWIN || OSUtils.IS_MSYSTEM) && var7 && !var10) {
               label175: {
                  try {
                     Pty var12 = ExecPty.current();
                     if ("xterm".equals(var4) && this.type == null && System.getProperty("org.jline.terminal.type") == null) {
                        var4 = "xterm-256color";
                     }

                     var27 = new PosixSysTerminal(var1, var4, var12, var2, this.nativeSignals, this.signalHandler);
                  } catch (IOException var18) {
                     Log.debug("Error creating EXEC based terminal: ", var18.getMessage(), var18);
                     var23.addSuppressed(var18);
                     break label175;
                  }

                  return var27;
               }
            }

            Terminal var26;
            if (var5) {
               label170: {
                  try {
                     var26 = ((JnaSupport)this.load(JnaSupport.class)).winSysTerminal(var1, var4, var11, var2, var21, this.nativeSignals, this.signalHandler, this.paused);
                  } catch (Throwable var17) {
                     Log.debug("Error creating JNA based terminal: ", var17.getMessage(), var17);
                     var23.addSuppressed(var17);
                     break label170;
                  }

                  return var26;
               }
            }

            if (var6) {
               label166: {
                  try {
                     var26 = ((JansiSupport)this.load(JansiSupport.class)).winSysTerminal(var1, var4, var11, var2, var21, this.nativeSignals, this.signalHandler, this.paused);
                  } catch (Throwable var16) {
                     Log.debug("Error creating JANSI based terminal: ", var16.getMessage(), var16);
                     var23.addSuppressed(var16);
                     break label166;
                  }

                  return var26;
               }
            }
         } else {
            Pty var25;
            if (var5) {
               label161: {
                  try {
                     var25 = ((JnaSupport)this.load(JnaSupport.class)).current();
                     var27 = new PosixSysTerminal(var1, var4, var25, var2, this.nativeSignals, this.signalHandler);
                  } catch (Throwable var15) {
                     Log.debug("Error creating JNA based terminal: ", var15.getMessage(), var15);
                     var23.addSuppressed(var15);
                     break label161;
                  }

                  return var27;
               }
            }

            if (var6) {
               label156: {
                  try {
                     var25 = ((JansiSupport)this.load(JansiSupport.class)).current();
                     var27 = new PosixSysTerminal(var1, var4, var25, var2, this.nativeSignals, this.signalHandler);
                  } catch (Throwable var14) {
                     Log.debug("Error creating JANSI based terminal: ", var14.getMessage(), var14);
                     var23.addSuppressed(var14);
                     break label156;
                  }

                  return var27;
               }
            }

            if (var7) {
               label152: {
                  try {
                     var25 = ExecPty.current();
                     var27 = new PosixSysTerminal(var1, var4, var25, var2, this.nativeSignals, this.signalHandler);
                  } catch (Throwable var13) {
                     Log.debug("Error creating EXEC based terminal: ", var13.getMessage(), var13);
                     var23.addSuppressed(var13);
                     break label152;
                  }

                  return var27;
               }
            }
         }

         if (var8 != null && !var8) {
            throw var23;
         } else {
            var10 = getBoolean("org.jline.terminal.dumb.color", false);
            if (!var10) {
               var10 = System.getenv("INSIDE_EMACS") != null;
            }

            if (!var10) {
               String var24 = getParentProcessCommand();
               var10 = var24 != null && var24.contains("idea");
            }

            if (!var10 && var8 == null) {
               if (Log.isDebugEnabled()) {
                  Log.warn("Creating a dumb terminal", var23);
               } else {
                  Log.warn("Unable to create a system terminal, creating a dumb terminal (enable debug logging for more information)");
               }
            }

            return new DumbTerminal(var1, var10 ? "dumb-color" : "dumb", new FileInputStream(FileDescriptor.in), new FileOutputStream(FileDescriptor.out), var2, this.signalHandler);
         }
      } else {
         PosixPtyTerminal var10000;
         Pty var22;
         if (var5) {
            label200: {
               try {
                  var22 = ((JnaSupport)this.load(JnaSupport.class)).open(this.attributes, this.size);
                  var10000 = new PosixPtyTerminal(var1, var4, var22, this.in, this.out, var2, this.signalHandler, this.paused);
               } catch (Throwable var20) {
                  Log.debug("Error creating JNA based terminal: ", var20.getMessage(), var20);
                  break label200;
               }

               return var10000;
            }
         }

         if (var6) {
            label194: {
               try {
                  var22 = ((JansiSupport)this.load(JansiSupport.class)).open(this.attributes, this.size);
                  var10000 = new PosixPtyTerminal(var1, var4, var22, this.in, this.out, var2, this.signalHandler, this.paused);
               } catch (Throwable var19) {
                  Log.debug("Error creating JANSI based terminal: ", var19.getMessage(), var19);
                  break label194;
               }

               return var10000;
            }
         }

         ExternalTerminal var9 = new ExternalTerminal(var1, var4, this.in, this.out, var2, this.signalHandler, this.paused);
         if (this.attributes != null) {
            var9.setAttributes(this.attributes);
         }

         if (this.size != null) {
            var9.setSize(this.size);
         }

         return var9;
      }
   }

   private static String getParentProcessCommand() {
      String var10000;
      try {
         Class var0 = Class.forName("java.lang.ProcessHandle");
         Object var1 = var0.getMethod("current").invoke((Object)null);
         Object var2 = ((Optional)var0.getMethod("parent").invoke(var1)).orElse((Object)null);
         Method var3 = var0.getMethod("info");
         Object var4 = var3.invoke(var2);
         Object var5 = ((Optional)var3.getReturnType().getMethod("command").invoke(var4)).orElse((Object)null);
         var10000 = (String)var5;
      } catch (Throwable var6) {
         return null;
      }

      return var10000;
   }

   private static Boolean getBoolean(String var0, Boolean var1) {
      try {
         String var2 = System.getProperty(var0);
         if (var2 != null) {
            Boolean var10000 = Boolean.parseBoolean(var2);
            return var10000;
         }
      } catch (NullPointerException | IllegalArgumentException var3) {
      }

      return var1;
   }

   private Object load(Class var1) {
      return ServiceLoader.load(var1, var1.getClassLoader()).iterator().next();
   }

   private static String lambda$build$1(Terminal var0) {
      return "Using pty " + ((AbstractPosixTerminal)var0).getPty().getClass().getSimpleName();
   }

   private static String lambda$build$0(Terminal var0) {
      return "Using terminal " + var0.getClass().getSimpleName();
   }
}
