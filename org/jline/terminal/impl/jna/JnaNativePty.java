package org.jline.terminal.impl.jna;

import com.sun.jna.Platform;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.impl.AbstractPty;
import org.jline.terminal.impl.jna.freebsd.FreeBsdNativePty;
import org.jline.terminal.impl.jna.linux.LinuxNativePty;
import org.jline.terminal.impl.jna.osx.OsXNativePty;
import org.jline.terminal.impl.jna.solaris.SolarisNativePty;
import org.jline.terminal.spi.Pty;

public abstract class JnaNativePty extends AbstractPty implements Pty {
   private final int master;
   private final int slave;
   private final int slaveOut;
   private final String name;
   private final FileDescriptor masterFD;
   private final FileDescriptor slaveFD;
   private final FileDescriptor slaveOutFD;

   public static JnaNativePty current() throws IOException {
      if (Platform.isMac()) {
         return OsXNativePty.current();
      } else if (Platform.isLinux()) {
         return LinuxNativePty.current();
      } else if (Platform.isSolaris()) {
         return SolarisNativePty.current();
      } else if (Platform.isFreeBSD()) {
         return FreeBsdNativePty.current();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public static JnaNativePty open(Attributes var0, Size var1) throws IOException {
      if (Platform.isMac()) {
         return OsXNativePty.open(var0, var1);
      } else if (Platform.isLinux()) {
         return LinuxNativePty.open(var0, var1);
      } else if (Platform.isSolaris()) {
         return SolarisNativePty.open(var0, var1);
      } else if (Platform.isFreeBSD()) {
         return FreeBsdNativePty.open(var0, var1);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   protected JnaNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, String var5) {
      this(var1, var2, var3, var4, var3, var4, var5);
   }

   protected JnaNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, int var5, FileDescriptor var6, String var7) {
      super();
      this.master = var1;
      this.slave = var3;
      this.slaveOut = var5;
      this.name = var7;
      this.masterFD = var2;
      this.slaveFD = var4;
      this.slaveOutFD = var6;
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

   public String toString() {
      return "JnaNativePty[" + this.getName() + "]";
   }
}
