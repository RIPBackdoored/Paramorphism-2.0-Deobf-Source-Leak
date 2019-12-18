package org.jline.terminal.impl;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$ControlFlag;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Attributes$OutputFlag;
import org.jline.terminal.Size;
import org.jline.terminal.spi.Pty;
import org.jline.utils.ExecHelper;
import org.jline.utils.OSUtils;

public class ExecPty extends AbstractPty implements Pty {
   private final String name;
   private final boolean system;

   public static Pty current() throws IOException {
      ExecPty var10000;
      try {
         String var0 = ExecHelper.exec(true, OSUtils.TTY_COMMAND);
         var10000 = new ExecPty(var0.trim(), true);
      } catch (IOException var1) {
         throw new IOException("Not a tty", var1);
      }

      return var10000;
   }

   protected ExecPty(String var1, boolean var2) {
      super();
      this.name = var1;
      this.system = var2;
   }

   public void close() throws IOException {
   }

   public String getName() {
      return this.name;
   }

   public InputStream getMasterInput() {
      throw new UnsupportedOperationException();
   }

   public OutputStream getMasterOutput() {
      throw new UnsupportedOperationException();
   }

   protected InputStream doGetSlaveInput() throws IOException {
      return this.system ? new FileInputStream(FileDescriptor.in) : new FileInputStream(this.getName());
   }

   public OutputStream getSlaveOutput() throws IOException {
      return this.system ? new FileOutputStream(FileDescriptor.out) : new FileOutputStream(this.getName());
   }

   public Attributes getAttr() throws IOException {
      String var1 = this.doGetConfig();
      return doGetAttr(var1);
   }

   protected void doSetAttr(Attributes var1) throws IOException {
      List var2 = this.getFlagsToSet(var1, this.getAttr());
      if (!var2.isEmpty()) {
         var2.add(0, OSUtils.STTY_COMMAND);
         if (!this.system) {
            var2.add(1, OSUtils.STTY_F_OPTION);
            var2.add(2, this.getName());
         }

         try {
            ExecHelper.exec(this.system, (String[])var2.toArray(new String[var2.size()]));
         } catch (IOException var4) {
            if (!var4.toString().contains("unable to perform all requested operations")) {
               throw var4;
            }

            var2 = this.getFlagsToSet(var1, this.getAttr());
            if (!var2.isEmpty()) {
               throw new IOException("Could not set the following flags: " + String.join(", ", var2), var4);
            }
         }
      }

   }

   protected List getFlagsToSet(Attributes var1, Attributes var2) {
      ArrayList var3 = new ArrayList();
      Attributes$InputFlag[] var4 = Attributes$InputFlag.values();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Attributes$InputFlag var7 = var4[var6];
         if (var1.getInputFlag(var7) != var2.getInputFlag(var7)) {
            var3.add((var1.getInputFlag(var7) ? var7.name() : "-" + var7.name()).toLowerCase());
         }
      }

      Attributes$OutputFlag[] var11 = Attributes$OutputFlag.values();
      var5 = var11.length;

      for(var6 = 0; var6 < var5; ++var6) {
         Attributes$OutputFlag var16 = var11[var6];
         if (var1.getOutputFlag(var16) != var2.getOutputFlag(var16)) {
            var3.add((var1.getOutputFlag(var16) ? var16.name() : "-" + var16.name()).toLowerCase());
         }
      }

      Attributes$ControlFlag[] var12 = Attributes$ControlFlag.values();
      var5 = var12.length;

      for(var6 = 0; var6 < var5; ++var6) {
         Attributes$ControlFlag var17 = var12[var6];
         if (var1.getControlFlag(var17) != var2.getControlFlag(var17)) {
            var3.add((var1.getControlFlag(var17) ? var17.name() : "-" + var17.name()).toLowerCase());
         }
      }

      Attributes$LocalFlag[] var13 = Attributes$LocalFlag.values();
      var5 = var13.length;

      for(var6 = 0; var6 < var5; ++var6) {
         Attributes$LocalFlag var18 = var13[var6];
         if (var1.getLocalFlag(var18) != var2.getLocalFlag(var18)) {
            var3.add((var1.getLocalFlag(var18) ? var18.name() : "-" + var18.name()).toLowerCase());
         }
      }

      String var14 = System.getProperty("os.name").toLowerCase().startsWith("hp") ? "^-" : "undef";
      Attributes$ControlChar[] var15 = Attributes$ControlChar.values();
      var6 = var15.length;

      for(int var19 = 0; var19 < var6; ++var19) {
         Attributes$ControlChar var8 = var15[var19];
         int var9 = var1.getControlChar(var8);
         if (var9 >= 0 && var9 != var2.getControlChar(var8)) {
            String var10 = "";
            var3.add(var8.name().toLowerCase().substring(1));
            if (var8 != Attributes$ControlChar.VMIN && var8 != Attributes$ControlChar.VTIME) {
               if (var9 == 0) {
                  var3.add(var14);
               } else {
                  if (var9 >= 128) {
                     var9 -= 128;
                     var10 = var10 + "M-";
                  }

                  if (var9 < 32 || var9 == 127) {
                     var9 ^= 64;
                     var10 = var10 + "^";
                  }

                  var10 = var10 + (char)var9;
                  var3.add(var10);
               }
            } else {
               var3.add(Integer.toString(var9));
            }
         }
      }

      return var3;
   }

   public Size getSize() throws IOException {
      String var1 = this.doGetConfig();
      return doGetSize(var1);
   }

   protected String doGetConfig() throws IOException {
      return this.system ? ExecHelper.exec(true, OSUtils.STTY_COMMAND, "-a") : ExecHelper.exec(false, OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, this.getName(), "-a");
   }

   static Attributes doGetAttr(String var0) throws IOException {
      Attributes var1 = new Attributes();
      Attributes$InputFlag[] var2 = Attributes$InputFlag.values();
      int var3 = var2.length;

      int var4;
      Boolean var6;
      for(var4 = 0; var4 < var3; ++var4) {
         Attributes$InputFlag var5 = var2[var4];
         var6 = doGetFlag(var0, var5);
         if (var6 != null) {
            var1.setInputFlag(var5, var6);
         }
      }

      Attributes$OutputFlag[] var8 = Attributes$OutputFlag.values();
      var3 = var8.length;

      for(var4 = 0; var4 < var3; ++var4) {
         Attributes$OutputFlag var12 = var8[var4];
         var6 = doGetFlag(var0, var12);
         if (var6 != null) {
            var1.setOutputFlag(var12, var6);
         }
      }

      Attributes$ControlFlag[] var9 = Attributes$ControlFlag.values();
      var3 = var9.length;

      for(var4 = 0; var4 < var3; ++var4) {
         Attributes$ControlFlag var13 = var9[var4];
         var6 = doGetFlag(var0, var13);
         if (var6 != null) {
            var1.setControlFlag(var13, var6);
         }
      }

      Attributes$LocalFlag[] var10 = Attributes$LocalFlag.values();
      var3 = var10.length;

      for(var4 = 0; var4 < var3; ++var4) {
         Attributes$LocalFlag var14 = var10[var4];
         var6 = doGetFlag(var0, var14);
         if (var6 != null) {
            var1.setLocalFlag(var14, var6);
         }
      }

      Attributes$ControlChar[] var11 = Attributes$ControlChar.values();
      var3 = var11.length;

      for(var4 = 0; var4 < var3; ++var4) {
         Attributes$ControlChar var15 = var11[var4];
         String var16 = var15.name().toLowerCase().substring(1);
         if ("reprint".endsWith(var16)) {
            var16 = "(?:reprint|rprnt)";
         }

         Matcher var7 = Pattern.compile("[\\s;]" + var16 + "\\s*=\\s*(.+?)[\\s;]").matcher(var0);
         if (var7.find()) {
            var1.setControlChar(var15, parseControlChar(var7.group(1).toUpperCase()));
         }
      }

      return var1;
   }

   private static Boolean doGetFlag(String var0, Enum var1) {
      Matcher var2 = Pattern.compile("(?:^|[\\s;])(\\-?" + var1.name().toLowerCase() + ")(?:[\\s;]|$)").matcher(var0);
      return var2.find() ? !var2.group(1).startsWith("-") : null;
   }

   static int parseControlChar(String var0) {
      if ("<UNDEF>".equals(var0)) {
         return -1;
      } else if ("DEL".equalsIgnoreCase(var0)) {
         return 127;
      } else if (var0.charAt(0) == '0') {
         return Integer.parseInt(var0, 8);
      } else if (var0.charAt(0) >= '1' && var0.charAt(0) <= '9') {
         return Integer.parseInt(var0, 10);
      } else if (var0.charAt(0) == '^') {
         return var0.charAt(1) == '?' ? 127 : var0.charAt(1) - 64;
      } else if (var0.charAt(0) == 'M' && var0.charAt(1) == '-') {
         if (var0.charAt(2) == '^') {
            return var0.charAt(3) == '?' ? 255 : var0.charAt(3) - 64 + 128;
         } else {
            return var0.charAt(2) + 128;
         }
      } else {
         return var0.charAt(0);
      }
   }

   static Size doGetSize(String var0) throws IOException {
      return new Size(doGetInt("columns", var0), doGetInt("rows", var0));
   }

   static int doGetInt(String var0, String var1) throws IOException {
      String[] var2 = new String[]{"\\b([0-9]+)\\s+" + var0 + "\\b", "\\b" + var0 + "\\s+([0-9]+)\\b", "\\b" + var0 + "\\s*=\\s*([0-9]+)\\b"};
      String[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         Matcher var7 = Pattern.compile(var6).matcher(var1);
         if (var7.find()) {
            return Integer.parseInt(var7.group(1));
         }
      }

      throw new IOException("Unable to parse " + var0);
   }

   public void setSize(Size var1) throws IOException {
      if (this.system) {
         ExecHelper.exec(true, OSUtils.STTY_COMMAND, "columns", Integer.toString(var1.getColumns()), "rows", Integer.toString(var1.getRows()));
      } else {
         ExecHelper.exec(false, OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, this.getName(), "columns", Integer.toString(var1.getColumns()), "rows", Integer.toString(var1.getRows()));
      }

   }

   public String toString() {
      return "ExecPty[" + this.getName() + (this.system ? ", system]" : "]");
   }
}
