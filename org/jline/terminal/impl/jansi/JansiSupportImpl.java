package org.jline.terminal.impl.jansi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fusesource.jansi.Ansi;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.terminal.impl.jansi.freebsd.FreeBsdNativePty;
import org.jline.terminal.impl.jansi.linux.LinuxNativePty;
import org.jline.terminal.impl.jansi.osx.OsXNativePty;
import org.jline.terminal.impl.jansi.win.JansiWinSysTerminal;
import org.jline.terminal.spi.JansiSupport;
import org.jline.terminal.spi.Pty;

public class JansiSupportImpl implements JansiSupport {
   static final int JANSI_MAJOR_VERSION;
   static final int JANSI_MINOR_VERSION;

   public JansiSupportImpl() {
      super();
   }

   public static int getJansiMajorVersion() {
      return JANSI_MAJOR_VERSION;
   }

   public static int getJansiMinorVersion() {
      return JANSI_MINOR_VERSION;
   }

   public static boolean isAtLeast(int var0, int var1) {
      return JANSI_MAJOR_VERSION > var0 || JANSI_MAJOR_VERSION == var0 && JANSI_MINOR_VERSION >= var1;
   }

   public Pty current() throws IOException {
      String var1 = System.getProperty("os.name");
      if (var1.startsWith("Linux")) {
         if (isAtLeast(1, 16)) {
            return LinuxNativePty.current();
         }
      } else if (!var1.startsWith("Mac") && !var1.startsWith("Darwin")) {
         if (!var1.startsWith("Solaris") && !var1.startsWith("SunOS") && var1.startsWith("FreeBSD") && isAtLeast(1, 16)) {
            return FreeBsdNativePty.current();
         }
      } else if (isAtLeast(1, 12)) {
         return OsXNativePty.current();
      }

      throw new UnsupportedOperationException();
   }

   public Pty open(Attributes var1, Size var2) throws IOException {
      if (isAtLeast(1, 16)) {
         String var3 = System.getProperty("os.name");
         if (var3.startsWith("Linux")) {
            return LinuxNativePty.open(var1, var2);
         }

         if (var3.startsWith("Mac") || var3.startsWith("Darwin")) {
            return OsXNativePty.open(var1, var2);
         }

         if (!var3.startsWith("Solaris") && !var3.startsWith("SunOS") && var3.startsWith("FreeBSD")) {
            return FreeBsdNativePty.open(var1, var2);
         }
      }

      throw new UnsupportedOperationException();
   }

   public Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7) throws IOException {
      return this.winSysTerminal(var1, var2, var3, var4, var5, var6, var7, false);
   }

   public Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7, boolean var8) throws IOException {
      if (isAtLeast(1, 12)) {
         JansiWinSysTerminal var9 = JansiWinSysTerminal.createTerminal(var1, var2, var3, var4, var5, var6, var7, var8);
         if (!isAtLeast(1, 16)) {
            var9.disableScrolling();
         }

         return var9;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   static {
      int var0 = 0;
      int var1 = 0;

      try {
         String var2 = null;

         try {
            InputStream var3 = Ansi.class.getResourceAsStream("jansi.properties");
            Throwable var4 = null;
            boolean var14 = false;

            try {
               var14 = true;
               if (var3 != null) {
                  Properties var5 = new Properties();
                  var5.load(var3);
                  var2 = var5.getProperty("version");
                  var14 = false;
               } else {
                  var14 = false;
               }
            } catch (Throwable var17) {
               var4 = var17;
               throw var17;
            } finally {
               if (var14) {
                  if (var3 != null) {
                     if (var4 != null) {
                        try {
                           var3.close();
                        } catch (Throwable var15) {
                           var4.addSuppressed(var15);
                        }
                     } else {
                        var3.close();
                     }
                  }

               }
            }

            if (var3 != null) {
               if (var4 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var16) {
                     var4.addSuppressed(var16);
                  }
               } else {
                  var3.close();
               }
            }
         } catch (IOException var19) {
         }

         if (var2 == null) {
            var2 = Ansi.class.getPackage().getImplementationVersion();
         }

         if (var2 != null) {
            Matcher var21 = Pattern.compile("([0-9]+)\\.([0-9]+)([\\.-]\\S+)?").matcher(var2);
            if (var21.matches()) {
               var0 = Integer.parseInt(var21.group(1));
               var1 = Integer.parseInt(var21.group(2));
            }
         }
      } catch (Throwable var20) {
      }

      JANSI_MAJOR_VERSION = var0;
      JANSI_MINOR_VERSION = var1;
   }
}
