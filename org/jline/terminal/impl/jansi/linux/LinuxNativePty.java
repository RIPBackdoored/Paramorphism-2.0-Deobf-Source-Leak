package org.jline.terminal.impl.jansi.linux;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import org.fusesource.jansi.internal.CLibrary;
import org.fusesource.jansi.internal.CLibrary.Termios;
import org.fusesource.jansi.internal.CLibrary.WinSize;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$ControlFlag;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Attributes$OutputFlag;
import org.jline.terminal.Size;
import org.jline.terminal.impl.jansi.JansiNativePty;

public class LinuxNativePty extends JansiNativePty {
   private static final int VINTR = 0;
   private static final int VQUIT = 1;
   private static final int VERASE = 2;
   private static final int VKILL = 3;
   private static final int VEOF = 4;
   private static final int VTIME = 5;
   private static final int VMIN = 6;
   private static final int VSWTC = 7;
   private static final int VSTART = 8;
   private static final int VSTOP = 9;
   private static final int VSUSP = 10;
   private static final int VEOL = 11;
   private static final int VREPRINT = 12;
   private static final int VDISCARD = 13;
   private static final int VWERASE = 14;
   private static final int VLNEXT = 15;
   private static final int VEOL2 = 16;
   private static final int IGNBRK = 1;
   private static final int BRKINT = 2;
   private static final int IGNPAR = 4;
   private static final int PARMRK = 8;
   private static final int INPCK = 16;
   private static final int ISTRIP = 32;
   private static final int INLCR = 64;
   private static final int IGNCR = 128;
   private static final int ICRNL = 256;
   private static final int IUCLC = 512;
   private static final int IXON = 1024;
   private static final int IXANY = 2048;
   private static final int IXOFF = 4096;
   private static final int IMAXBEL = 8192;
   private static final int IUTF8 = 16384;
   private static final int OPOST = 1;
   private static final int OLCUC = 2;
   private static final int ONLCR = 4;
   private static final int OCRNL = 8;
   private static final int ONOCR = 16;
   private static final int ONLRET = 32;
   private static final int OFILL = 64;
   private static final int OFDEL = 128;
   private static final int NLDLY = 256;
   private static final int NL0 = 0;
   private static final int NL1 = 256;
   private static final int CRDLY = 1536;
   private static final int CR0 = 0;
   private static final int CR1 = 512;
   private static final int CR2 = 1024;
   private static final int CR3 = 1536;
   private static final int TABDLY = 6144;
   private static final int TAB0 = 0;
   private static final int TAB1 = 2048;
   private static final int TAB2 = 4096;
   private static final int TAB3 = 6144;
   private static final int XTABS = 6144;
   private static final int BSDLY = 8192;
   private static final int BS0 = 0;
   private static final int BS1 = 8192;
   private static final int VTDLY = 16384;
   private static final int VT0 = 0;
   private static final int VT1 = 16384;
   private static final int FFDLY = 32768;
   private static final int FF0 = 0;
   private static final int FF1 = 32768;
   private static final int CBAUD = 4111;
   private static final int B0 = 0;
   private static final int B50 = 1;
   private static final int B75 = 2;
   private static final int B110 = 3;
   private static final int B134 = 4;
   private static final int B150 = 5;
   private static final int B200 = 6;
   private static final int B300 = 7;
   private static final int B600 = 8;
   private static final int B1200 = 9;
   private static final int B1800 = 10;
   private static final int B2400 = 11;
   private static final int B4800 = 12;
   private static final int B9600 = 13;
   private static final int B19200 = 14;
   private static final int B38400 = 15;
   private static final int EXTA = 14;
   private static final int EXTB = 15;
   private static final int CSIZE = 48;
   private static final int CS5 = 0;
   private static final int CS6 = 16;
   private static final int CS7 = 32;
   private static final int CS8 = 48;
   private static final int CSTOPB = 64;
   private static final int CREAD = 128;
   private static final int PARENB = 256;
   private static final int PARODD = 512;
   private static final int HUPCL = 1024;
   private static final int CLOCAL = 2048;
   private static final int ISIG = 1;
   private static final int ICANON = 2;
   private static final int XCASE = 4;
   private static final int ECHO = 8;
   private static final int ECHOE = 16;
   private static final int ECHOK = 32;
   private static final int ECHONL = 64;
   private static final int NOFLSH = 128;
   private static final int TOSTOP = 256;
   private static final int ECHOCTL = 512;
   private static final int ECHOPRT = 1024;
   private static final int ECHOKE = 2048;
   private static final int FLUSHO = 4096;
   private static final int PENDIN = 8192;
   private static final int IEXTEN = 32768;
   private static final int EXTPROC = 65536;

   public static LinuxNativePty current() throws IOException {
      LinuxNativePty var10000;
      try {
         String var0 = ttyname();
         var10000 = new LinuxNativePty(-1, (FileDescriptor)null, 0, FileDescriptor.in, 1, FileDescriptor.out, var0);
      } catch (IOException var1) {
         throw new IOException("Not a tty", var1);
      }

      return var10000;
   }

   public static LinuxNativePty open(Attributes var0, Size var1) throws IOException {
      int[] var2 = new int[1];
      int[] var3 = new int[1];
      byte[] var4 = new byte[64];
      CLibrary.openpty(var2, var3, var4, var0 != null ? termios(var0) : null, var1 != null ? new WinSize((short)var1.getRows(), (short)var1.getColumns()) : null);

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

   protected Termios toTermios(Attributes var1) {
      return termios(var1);
   }

   static Termios termios(Attributes var0) {
      Termios var1 = new Termios();
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IGNBRK), 1L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.BRKINT), 2L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IGNPAR), 4L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.PARMRK), 8L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.INPCK), 16L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.ISTRIP), 32L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.INLCR), 64L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IGNCR), 128L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.ICRNL), 256L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IXON), 1024L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IXOFF), 4096L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IXANY), 2048L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IMAXBEL), 8192L, var1.c_iflag);
      var1.c_iflag = setFlag(var0.getInputFlag(Attributes$InputFlag.IUTF8), 16384L, var1.c_iflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.OPOST), 1L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.ONLCR), 4L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.OCRNL), 8L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.ONOCR), 16L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.ONLRET), 32L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.OFILL), 64L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.NLDLY), 256L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.TABDLY), 6144L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.CRDLY), 1536L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.FFDLY), 32768L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.BSDLY), 8192L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.VTDLY), 16384L, var1.c_oflag);
      var1.c_oflag = setFlag(var0.getOutputFlag(Attributes$OutputFlag.OFDEL), 128L, var1.c_oflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CS5), 0L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CS6), 16L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CS7), 32L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CS8), 48L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CSTOPB), 64L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CREAD), 128L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.PARENB), 256L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.PARODD), 512L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.HUPCL), 1024L, var1.c_cflag);
      var1.c_cflag = setFlag(var0.getControlFlag(Attributes$ControlFlag.CLOCAL), 2048L, var1.c_cflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHOKE), 2048L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHOE), 16L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHOK), 32L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHO), 8L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHONL), 64L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHOPRT), 1024L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ECHOCTL), 512L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ISIG), 1L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.ICANON), 2L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.IEXTEN), 32768L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.EXTPROC), 65536L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.TOSTOP), 256L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.FLUSHO), 4096L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.PENDIN), 8192L, var1.c_lflag);
      var1.c_lflag = setFlag(var0.getLocalFlag(Attributes$LocalFlag.NOFLSH), 128L, var1.c_lflag);
      var1.c_cc[4] = (byte)var0.getControlChar(Attributes$ControlChar.VEOF);
      var1.c_cc[11] = (byte)var0.getControlChar(Attributes$ControlChar.VEOL);
      var1.c_cc[16] = (byte)var0.getControlChar(Attributes$ControlChar.VEOL2);
      var1.c_cc[2] = (byte)var0.getControlChar(Attributes$ControlChar.VERASE);
      var1.c_cc[14] = (byte)var0.getControlChar(Attributes$ControlChar.VWERASE);
      var1.c_cc[3] = (byte)var0.getControlChar(Attributes$ControlChar.VKILL);
      var1.c_cc[12] = (byte)var0.getControlChar(Attributes$ControlChar.VREPRINT);
      var1.c_cc[0] = (byte)var0.getControlChar(Attributes$ControlChar.VINTR);
      var1.c_cc[1] = (byte)var0.getControlChar(Attributes$ControlChar.VQUIT);
      var1.c_cc[10] = (byte)var0.getControlChar(Attributes$ControlChar.VSUSP);
      var1.c_cc[8] = (byte)var0.getControlChar(Attributes$ControlChar.VSTART);
      var1.c_cc[9] = (byte)var0.getControlChar(Attributes$ControlChar.VSTOP);
      var1.c_cc[15] = (byte)var0.getControlChar(Attributes$ControlChar.VLNEXT);
      var1.c_cc[13] = (byte)var0.getControlChar(Attributes$ControlChar.VDISCARD);
      var1.c_cc[6] = (byte)var0.getControlChar(Attributes$ControlChar.VMIN);
      var1.c_cc[5] = (byte)var0.getControlChar(Attributes$ControlChar.VTIME);
      return var1;
   }

   protected Attributes toAttributes(Termios var1) {
      Attributes var2 = new Attributes();
      EnumSet var3 = var2.getInputFlags();
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IGNBRK, 1);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IGNBRK, 1);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.BRKINT, 2);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IGNPAR, 4);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.PARMRK, 8);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.INPCK, 16);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.ISTRIP, 32);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.INLCR, 64);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IGNCR, 128);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.ICRNL, 256);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IXON, 1024);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IXOFF, 4096);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IXANY, 2048);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IMAXBEL, 8192);
      addFlag(var1.c_iflag, var3, Attributes$InputFlag.IUTF8, 16384);
      EnumSet var4 = var2.getOutputFlags();
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.OPOST, 1);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.ONLCR, 4);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.OCRNL, 8);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.ONOCR, 16);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.ONLRET, 32);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.OFILL, 64);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.NLDLY, 256);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.TABDLY, 6144);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.CRDLY, 1536);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.FFDLY, 32768);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.BSDLY, 8192);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.VTDLY, 16384);
      addFlag(var1.c_oflag, var4, Attributes$OutputFlag.OFDEL, 128);
      EnumSet var5 = var2.getControlFlags();
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CS5, 0);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CS6, 16);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CS7, 32);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CS8, 48);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CSTOPB, 64);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CREAD, 128);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.PARENB, 256);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.PARODD, 512);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.HUPCL, 1024);
      addFlag(var1.c_cflag, var5, Attributes$ControlFlag.CLOCAL, 2048);
      EnumSet var6 = var2.getLocalFlags();
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHOKE, 2048);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHOE, 16);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHOK, 32);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHO, 8);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHONL, 64);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHOPRT, 1024);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ECHOCTL, 512);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ISIG, 1);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.ICANON, 2);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.IEXTEN, 32768);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.EXTPROC, 65536);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.TOSTOP, 256);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.FLUSHO, 4096);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.PENDIN, 8192);
      addFlag(var1.c_lflag, var6, Attributes$LocalFlag.NOFLSH, 128);
      EnumMap var7 = var2.getControlChars();
      var7.put(Attributes$ControlChar.VEOF, Integer.valueOf(var1.c_cc[4]));
      var7.put(Attributes$ControlChar.VEOL, Integer.valueOf(var1.c_cc[11]));
      var7.put(Attributes$ControlChar.VEOL2, Integer.valueOf(var1.c_cc[16]));
      var7.put(Attributes$ControlChar.VERASE, Integer.valueOf(var1.c_cc[2]));
      var7.put(Attributes$ControlChar.VWERASE, Integer.valueOf(var1.c_cc[14]));
      var7.put(Attributes$ControlChar.VKILL, Integer.valueOf(var1.c_cc[3]));
      var7.put(Attributes$ControlChar.VREPRINT, Integer.valueOf(var1.c_cc[12]));
      var7.put(Attributes$ControlChar.VINTR, Integer.valueOf(var1.c_cc[0]));
      var7.put(Attributes$ControlChar.VQUIT, Integer.valueOf(var1.c_cc[1]));
      var7.put(Attributes$ControlChar.VSUSP, Integer.valueOf(var1.c_cc[10]));
      var7.put(Attributes$ControlChar.VSTART, Integer.valueOf(var1.c_cc[8]));
      var7.put(Attributes$ControlChar.VSTOP, Integer.valueOf(var1.c_cc[9]));
      var7.put(Attributes$ControlChar.VLNEXT, Integer.valueOf(var1.c_cc[15]));
      var7.put(Attributes$ControlChar.VDISCARD, Integer.valueOf(var1.c_cc[13]));
      var7.put(Attributes$ControlChar.VMIN, Integer.valueOf(var1.c_cc[6]));
      var7.put(Attributes$ControlChar.VTIME, Integer.valueOf(var1.c_cc[5]));
      return var2;
   }

   private static long setFlag(boolean var0, long var1, long var3) {
      return var0 ? var3 | var1 : var3;
   }

   private static void addFlag(long var0, EnumSet var2, Enum var3, int var4) {
      if ((var0 & (long)var4) != 0L) {
         var2.add(var3);
      }

   }
}
