package org.jline.terminal.impl.jna.osx;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$ControlFlag;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Attributes$OutputFlag;

public class CLibrary$termios extends Structure {
   public NativeLong c_iflag;
   public NativeLong c_oflag;
   public NativeLong c_cflag;
   public NativeLong c_lflag;
   public byte[] c_cc = new byte[20];
   public NativeLong c_ispeed;
   public NativeLong c_ospeed;

   protected List getFieldOrder() {
      return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed");
   }

   public CLibrary$termios() {
      super();
   }

   public CLibrary$termios(Attributes var1) {
      super();
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNBRK), 1L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.BRKINT), 2L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNPAR), 4L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.PARMRK), 8L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.INPCK), 16L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.ISTRIP), 32L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.INLCR), 64L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNCR), 128L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.ICRNL), 256L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXON), 512L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXOFF), 1024L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXANY), 2048L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IMAXBEL), 8192L, this.c_iflag);
      this.setFlag(var1.getInputFlag(Attributes$InputFlag.IUTF8), 16384L, this.c_iflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OPOST), 1L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONLCR), 2L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OXTABS), 4L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONOEOT), 8L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OCRNL), 16L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONOCR), 32L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONLRET), 64L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OFILL), 128L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.NLDLY), 768L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.TABDLY), 3076L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.CRDLY), 12288L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.FFDLY), 16384L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.BSDLY), 32768L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.VTDLY), 65536L, this.c_oflag);
      this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OFDEL), 131072L, this.c_oflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CIGNORE), 1L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS5), 0L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS6), 256L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS7), 512L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS8), 768L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CSTOPB), 1024L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CREAD), 2048L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.PARENB), 4096L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.PARODD), 8192L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.HUPCL), 16384L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CLOCAL), 32768L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CCTS_OFLOW), 65536L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CRTS_IFLOW), 131072L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CDTR_IFLOW), 262144L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CDSR_OFLOW), 524288L, this.c_cflag);
      this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CCAR_OFLOW), 1048576L, this.c_cflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOKE), 1L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOE), 2L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOK), 4L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHO), 8L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHONL), 16L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOPRT), 32L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOCTL), 64L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ISIG), 128L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ICANON), 256L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ALTWERASE), 512L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.IEXTEN), 1024L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.EXTPROC), 2048L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.TOSTOP), 4194304L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.FLUSHO), 8388608L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.NOKERNINFO), 33554432L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.PENDIN), 536870912L, this.c_lflag);
      this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.NOFLSH), -2147483648L, this.c_lflag);
      this.c_cc[0] = (byte)var1.getControlChar(Attributes$ControlChar.VEOF);
      this.c_cc[1] = (byte)var1.getControlChar(Attributes$ControlChar.VEOL);
      this.c_cc[2] = (byte)var1.getControlChar(Attributes$ControlChar.VEOL2);
      this.c_cc[3] = (byte)var1.getControlChar(Attributes$ControlChar.VERASE);
      this.c_cc[4] = (byte)var1.getControlChar(Attributes$ControlChar.VWERASE);
      this.c_cc[5] = (byte)var1.getControlChar(Attributes$ControlChar.VKILL);
      this.c_cc[6] = (byte)var1.getControlChar(Attributes$ControlChar.VREPRINT);
      this.c_cc[8] = (byte)var1.getControlChar(Attributes$ControlChar.VINTR);
      this.c_cc[9] = (byte)var1.getControlChar(Attributes$ControlChar.VQUIT);
      this.c_cc[10] = (byte)var1.getControlChar(Attributes$ControlChar.VSUSP);
      this.c_cc[11] = (byte)var1.getControlChar(Attributes$ControlChar.VDSUSP);
      this.c_cc[12] = (byte)var1.getControlChar(Attributes$ControlChar.VSTART);
      this.c_cc[13] = (byte)var1.getControlChar(Attributes$ControlChar.VSTOP);
      this.c_cc[14] = (byte)var1.getControlChar(Attributes$ControlChar.VLNEXT);
      this.c_cc[15] = (byte)var1.getControlChar(Attributes$ControlChar.VDISCARD);
      this.c_cc[16] = (byte)var1.getControlChar(Attributes$ControlChar.VMIN);
      this.c_cc[17] = (byte)var1.getControlChar(Attributes$ControlChar.VTIME);
      this.c_cc[18] = (byte)var1.getControlChar(Attributes$ControlChar.VSTATUS);
   }

   private void setFlag(boolean var1, long var2, NativeLong var4) {
      var4.setValue(var1 ? var4.longValue() | var2 : var4.longValue());
   }

   public Attributes toAttributes() {
      Attributes var1 = new Attributes();
      EnumSet var2 = var1.getInputFlags();
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IGNBRK, 1);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IGNBRK, 1);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.BRKINT, 2);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IGNPAR, 4);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.PARMRK, 8);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.INPCK, 16);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.ISTRIP, 32);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.INLCR, 64);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IGNCR, 128);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.ICRNL, 256);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IXON, 512);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IXOFF, 1024);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IXANY, 2048);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IMAXBEL, 8192);
      this.addFlag(this.c_iflag.longValue(), var2, Attributes$InputFlag.IUTF8, 16384);
      EnumSet var3 = var1.getOutputFlags();
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.OPOST, 1);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.ONLCR, 2);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.OXTABS, 4);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.ONOEOT, 8);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.OCRNL, 16);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.ONOCR, 32);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.ONLRET, 64);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.OFILL, 128);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.NLDLY, 768);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.TABDLY, 3076);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.CRDLY, 12288);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.FFDLY, 16384);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.BSDLY, 32768);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.VTDLY, 65536);
      this.addFlag(this.c_oflag.longValue(), var3, Attributes$OutputFlag.OFDEL, 131072);
      EnumSet var4 = var1.getControlFlags();
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CIGNORE, 1);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CS5, 0);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CS6, 256);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CS7, 512);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CS8, 768);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CSTOPB, 1024);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CREAD, 2048);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.PARENB, 4096);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.PARODD, 8192);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.HUPCL, 16384);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CLOCAL, 32768);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CCTS_OFLOW, 65536);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CRTS_IFLOW, 131072);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CDSR_OFLOW, 524288);
      this.addFlag(this.c_cflag.longValue(), var4, Attributes$ControlFlag.CCAR_OFLOW, 1048576);
      EnumSet var5 = var1.getLocalFlags();
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHOKE, 1);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHOE, 2);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHOK, 4);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHO, 8);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHONL, 16);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHOPRT, 32);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ECHOCTL, 64);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ISIG, 128);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ICANON, 256);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.ALTWERASE, 512);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.IEXTEN, 1024);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.EXTPROC, 2048);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.TOSTOP, 4194304);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.FLUSHO, 8388608);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.NOKERNINFO, 33554432);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.PENDIN, 536870912);
      this.addFlag(this.c_lflag.longValue(), var5, Attributes$LocalFlag.NOFLSH, Integer.MIN_VALUE);
      EnumMap var6 = var1.getControlChars();
      var6.put(Attributes$ControlChar.VEOF, Integer.valueOf(this.c_cc[0]));
      var6.put(Attributes$ControlChar.VEOL, Integer.valueOf(this.c_cc[1]));
      var6.put(Attributes$ControlChar.VEOL2, Integer.valueOf(this.c_cc[2]));
      var6.put(Attributes$ControlChar.VERASE, Integer.valueOf(this.c_cc[3]));
      var6.put(Attributes$ControlChar.VWERASE, Integer.valueOf(this.c_cc[4]));
      var6.put(Attributes$ControlChar.VKILL, Integer.valueOf(this.c_cc[5]));
      var6.put(Attributes$ControlChar.VREPRINT, Integer.valueOf(this.c_cc[6]));
      var6.put(Attributes$ControlChar.VINTR, Integer.valueOf(this.c_cc[8]));
      var6.put(Attributes$ControlChar.VQUIT, Integer.valueOf(this.c_cc[9]));
      var6.put(Attributes$ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
      var6.put(Attributes$ControlChar.VDSUSP, Integer.valueOf(this.c_cc[11]));
      var6.put(Attributes$ControlChar.VSTART, Integer.valueOf(this.c_cc[12]));
      var6.put(Attributes$ControlChar.VSTOP, Integer.valueOf(this.c_cc[13]));
      var6.put(Attributes$ControlChar.VLNEXT, Integer.valueOf(this.c_cc[14]));
      var6.put(Attributes$ControlChar.VDISCARD, Integer.valueOf(this.c_cc[15]));
      var6.put(Attributes$ControlChar.VMIN, Integer.valueOf(this.c_cc[16]));
      var6.put(Attributes$ControlChar.VTIME, Integer.valueOf(this.c_cc[17]));
      var6.put(Attributes$ControlChar.VSTATUS, Integer.valueOf(this.c_cc[18]));
      return var1;
   }

   private void addFlag(long var1, EnumSet var3, Enum var4, int var5) {
      if ((var1 & (long)var5) != 0L) {
         var3.add(var4);
      }

   }
}
