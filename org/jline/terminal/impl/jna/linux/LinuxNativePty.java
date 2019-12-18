package org.jline.terminal.impl.jna.linux;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import java.io.FileDescriptor;
import java.io.IOException;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.impl.jna.JnaNativePty;

public class LinuxNativePty extends JnaNativePty {
   private static final CLibrary C_LIBRARY;

   public static LinuxNativePty current() throws IOException {
      byte var0 = 0;
      byte[] var1 = new byte[64];
      C_LIBRARY.ttyname_r(var0, var1, var1.length);

      int var2;
      for(var2 = 0; var1[var2] != 0; ++var2) {
      }

      String var3 = new String(var1, 0, var2);
      return new LinuxNativePty(-1, (FileDescriptor)null, var0, FileDescriptor.in, 1, FileDescriptor.out, var3);
   }

   public static LinuxNativePty open(Attributes var0, Size var1) throws IOException {
      int[] var2 = new int[1];
      int[] var3 = new int[1];
      byte[] var4 = new byte[64];
      LinuxNativePty$UtilLibrary.INSTANCE.openpty(var2, var3, var4, var0 != null ? new CLibrary$termios(var0) : null, var1 != null ? new CLibrary$winsize(var1) : null);

      int var5;
      for(var5 = 0; var4[var5] != 0; ++var5) {
      }

      String var6 = new String(var4, 0, var5);
      return new LinuxNativePty(var2[0], newDescriptor(var2[0]), var3[0], newDescriptor(var3[0]), var6);
   }

   public LinuxNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, String var5) {
      super(var1, var2, var3, var4, var5);
   }

   public LinuxNativePty(int var1, FileDescriptor var2, int var3, FileDescriptor var4, int var5, FileDescriptor var6, String var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public Attributes getAttr() throws IOException {
      CLibrary$termios var1 = new CLibrary$termios();
      C_LIBRARY.tcgetattr(this.getSlave(), var1);
      return var1.toAttributes();
   }

   protected void doSetAttr(Attributes var1) throws IOException {
      CLibrary$termios var2 = new CLibrary$termios(var1);
      CLibrary$termios var3 = new CLibrary$termios();
      C_LIBRARY.tcgetattr(this.getSlave(), var3);
      var3.c_iflag = var2.c_iflag;
      var3.c_oflag = var2.c_oflag;
      var3.c_lflag = var2.c_lflag;
      System.arraycopy(var2.c_cc, 0, var3.c_cc, 0, var2.c_cc.length);
      C_LIBRARY.tcsetattr(this.getSlave(), 1, var3);
   }

   public Size getSize() throws IOException {
      CLibrary$winsize var1 = new CLibrary$winsize();
      C_LIBRARY.ioctl(this.getSlave(), 21523, var1);
      return var1.toSize();
   }

   public void setSize(Size var1) throws IOException {
      CLibrary$winsize var2 = new CLibrary$winsize(var1);
      C_LIBRARY.ioctl(this.getSlave(), 21524, var2);
   }

   static {
      C_LIBRARY = (CLibrary)Native.loadLibrary(Platform.C_LIBRARY_NAME, CLibrary.class);
   }
}
