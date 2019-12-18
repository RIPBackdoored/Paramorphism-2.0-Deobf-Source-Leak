package org.jline.terminal.impl.jna.freebsd;

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
   public int c_iflag;
   public int c_oflag;
   public int c_cflag;
   public int c_lflag;
   public byte[] c_cc = new byte[20];
   public int c_ispeed;
   public int c_ospeed;

   protected List getFieldOrder() {
      return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed");
   }

   public CLibrary$termios() {
      super();
   }

   public CLibrary$termios(Attributes var1) {
      super();
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNBRK), 1, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.BRKINT), 2, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNPAR), 4, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.PARMRK), 8, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.INPCK), 16, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.ISTRIP), 32, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.INLCR), 64, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IGNCR), 128, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.ICRNL), 256, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXON), 512, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXOFF), 1024, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IXANY), 2048, this.c_iflag);
      this.c_iflag = this.setFlag(var1.getInputFlag(Attributes$InputFlag.IMAXBEL), 8192, this.c_iflag);
      this.c_oflag = this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OPOST), 1, this.c_oflag);
      this.c_oflag = this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONLCR), 2, this.c_oflag);
      this.c_oflag = this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.OCRNL), 16, this.c_oflag);
      this.c_oflag = this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.ONLRET), 64, this.c_oflag);
      this.c_oflag = this.setFlag(var1.getOutputFlag(Attributes$OutputFlag.TABDLY), 4, this.c_oflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS5), 0, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS6), 256, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS7), 512, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CS8), 768, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CSTOPB), 1024, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CREAD), 2048, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.PARENB), 4096, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.PARODD), 8192, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.HUPCL), 16384, this.c_cflag);
      this.c_cflag = this.setFlag(var1.getControlFlag(Attributes$ControlFlag.CLOCAL), 32768, this.c_cflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOKE), 1, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOE), 2, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOK), 4, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHO), 8, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHONL), 16, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOPRT), 32, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ECHOCTL), 64, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ISIG), 128, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.ICANON), 256, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.IEXTEN), 1024, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.EXTPROC), 2048, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.TOSTOP), 4194304, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.FLUSHO), 8388608, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.PENDIN), 33554432, this.c_lflag);
      this.c_lflag = this.setFlag(var1.getLocalFlag(Attributes$LocalFlag.NOFLSH), 134217728, this.c_lflag);
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
      this.c_cc[12] = (byte)var1.getControlChar(Attributes$ControlChar.VSTART);
      this.c_cc[13] = (byte)var1.getControlChar(Attributes$ControlChar.VSTOP);
      this.c_cc[14] = (byte)var1.getControlChar(Attributes$ControlChar.VLNEXT);
      this.c_cc[15] = (byte)var1.getControlChar(Attributes$ControlChar.VDISCARD);
      this.c_cc[16] = (byte)var1.getControlChar(Attributes$ControlChar.VMIN);
      this.c_cc[17] = (byte)var1.getControlChar(Attributes$ControlChar.VTIME);
   }

   private int setFlag(boolean var1, int var2, int var3) {
      return var1 ? var3 | var2 : var3;
   }

   public Attributes toAttributes() {
      Attributes var1 = new Attributes();
      EnumSet var2 = var1.getInputFlags();
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IGNBRK, 1);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IGNBRK, 1);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.BRKINT, 2);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IGNPAR, 4);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.PARMRK, 8);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.INPCK, 16);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.ISTRIP, 32);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.INLCR, 64);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IGNCR, 128);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.ICRNL, 256);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IXON, 512);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IXOFF, 1024);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IXANY, 2048);
      this.addFlag(this.c_iflag, var2, Attributes$InputFlag.IMAXBEL, 8192);
      EnumSet var3 = var1.getOutputFlags();
      this.addFlag(this.c_oflag, var3, Attributes$OutputFlag.OPOST, 1);
      this.addFlag(this.c_oflag, var3, Attributes$OutputFlag.ONLCR, 2);
      this.addFlag(this.c_oflag, var3, Attributes$OutputFlag.OCRNL, 16);
      this.addFlag(this.c_oflag, var3, Attributes$OutputFlag.ONLRET, 64);
      this.addFlag(this.c_oflag, var3, Attributes$OutputFlag.TABDLY, 4);
      EnumSet var4 = var1.getControlFlags();
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CS5, 0);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CS6, 256);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CS7, 512);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CS8, 768);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CSTOPB, 1024);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CREAD, 2048);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.PARENB, 4096);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.PARODD, 8192);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.HUPCL, 16384);
      this.addFlag(this.c_cflag, var4, Attributes$ControlFlag.CLOCAL, 32768);
      EnumSet var5 = var1.getLocalFlags();
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHOKE, 1);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHOE, 2);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHOK, 4);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHO, 8);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHONL, 16);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHOPRT, 32);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ECHOCTL, 64);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ISIG, 128);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.ICANON, 256);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.IEXTEN, 1024);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.EXTPROC, 2048);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.TOSTOP, 4194304);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.FLUSHO, 8388608);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.PENDIN, 33554432);
      this.addFlag(this.c_lflag, var5, Attributes$LocalFlag.NOFLSH, 134217728);
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
      var6.put(Attributes$ControlChar.VSTART, Integer.valueOf(this.c_cc[12]));
      var6.put(Attributes$ControlChar.VSTOP, Integer.valueOf(this.c_cc[13]));
      var6.put(Attributes$ControlChar.VLNEXT, Integer.valueOf(this.c_cc[14]));
      var6.put(Attributes$ControlChar.VDISCARD, Integer.valueOf(this.c_cc[15]));
      var6.put(Attributes$ControlChar.VMIN, Integer.valueOf(this.c_cc[16]));
      var6.put(Attributes$ControlChar.VTIME, Integer.valueOf(this.c_cc[17]));
      return var1;
   }

   private void addFlag(int var1, EnumSet var2, Enum var3, int var4) {
      if ((var1 & var4) != 0) {
         var2.add(var3);
      }

   }
}
