package org.jline.terminal.impl.jansi;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import org.fusesource.jansi.internal.CLibrary;
import org.fusesource.jansi.internal.CLibrary.Termios;
import org.fusesource.jansi.internal.CLibrary.WinSize;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.impl.AbstractPty;
import org.jline.terminal.spi.Pty;
import org.jline.utils.ExecHelper;
import org.jline.utils.OSUtils;

public abstract class JansiNativePty extends AbstractPty implements Pty {
   private final int master;
   private final int slave;
   private final int slaveOut;
   private final String name;
   private final FileDescriptor masterFD;
   private final FileDescriptor slaveFD;
   private final FileDescriptor slaveOutFD;

   public JansiNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, String var5) {
      this(var1, var2, var3, var4, var3, var4, var5);
   }

   public JansiNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, int var5, FileDescriptor var6, String var7) {
      super();
      this.master = var1;
      this.slave = var3;
      this.slaveOut = var5;
      this.name = var7;
      this.masterFD = var2;
      this.slaveFD = var4;
      this.slaveOutFD = var6;
   }

   protected static String ttyname() throws IOException {
      String var0;
      if (JansiSupportImpl.JANSI_MAJOR_VERSION <= 1 && (JansiSupportImpl.JANSI_MAJOR_VERSION != 1 || JansiSupportImpl.JANSI_MINOR_VERSION < 16)) {
         try {
            var0 = ExecHelper.exec(true, OSUtils.TTY_COMMAND);
         } catch (IOException var2) {
            throw new IOException("Not a tty", var2);
         }
      } else {
         var0 = CLibrary.ttyname(0);
      }

      if (var0 != null) {
         var0 = var0.trim();
      }

      if (var0 != null && !var0.isEmpty()) {
         return var0;
      } else {
         throw new IOException("Not a tty");
      }
   }

   public void close() throws IOException {
      if (this.master > 0) {
         this.getMasterInput().close();
      }

      if (this.slave > 0) {
         this.getSlaveInput().close();
      }

   }

   public int getMaster() {
      return this.master;
   }

   public int getSlave() {
      return this.slave;
   }

   public int getSlaveOut() {
      return this.slaveOut;
   }

   public String getName() {
      return this.name;
   }

   public FileDescriptor getMasterFD() {
      return this.masterFD;
   }

   public FileDescriptor getSlaveFD() {
      return this.slaveFD;
   }

   public FileDescriptor getSlaveOutFD() {
      return this.slaveOutFD;
   }

   public InputStream getMasterInput() {
      return new FileInputStream(this.getMasterFD());
   }

   public OutputStream getMasterOutput() {
      return new FileOutputStream(this.getMasterFD());
   }

   protected InputStream doGetSlaveInput() {
      return new FileInputStream(this.getSlaveFD());
   }

   public OutputStream getSlaveOutput() {
      return new FileOutputStream(this.getSlaveOutFD());
   }

   public Attributes getAttr() throws IOException {
      Termios var1 = new Termios();
      CLibrary.tcgetattr(this.slave, var1);
      return this.toAttributes(var1);
   }

   protected void doSetAttr(Attributes var1) throws IOException {
      Termios var2 = this.toTermios(var1);
      CLibrary.tcsetattr(this.slave, CLibrary.TCSANOW, var2);
   }

   public Size getSize() throws IOException {
      WinSize var1 = new WinSize();
      CLibrary.ioctl(this.slave, CLibrary.TIOCGWINSZ, var1);
      return new Size(var1.ws_col, var1.ws_row);
   }

   public void setSize(Size var1) throws IOException {
      WinSize var2 = new WinSize((short)var1.getRows(), (short)var1.getColumns());
      CLibrary.ioctl(this.slave, CLibrary.TIOCSWINSZ, var2);
   }

   protected abstract Termios toTermios(Attributes var1);

   protected abstract Attributes toAttributes(Termios var1);

   public String toString() {
      return "JansiNativePty[" + this.getName() + "]";
   }

   protected static FileDescriptor newDescriptor(int var0) {
      FileDescriptor var10000;
      try {
         Constructor var1 = FileDescriptor.class.getDeclaredConstructor(Integer.TYPE);
         var1.setAccessible(true);
         var10000 = (FileDescriptor)var1.newInstance(var0);
      } catch (Throwable var2) {
         throw new RuntimeException("Unable to create FileDescriptor", var2);
      }

      return var10000;
   }
}
