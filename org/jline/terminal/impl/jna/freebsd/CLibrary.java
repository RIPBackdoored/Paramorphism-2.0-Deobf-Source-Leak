package org.jline.terminal.impl.jna.freebsd;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;

public interface CLibrary extends Library {
   int TIOCGWINSZ = 1074295912;
   int TIOCSWINSZ = -2146929561;
   int VEOF = 0;
   int VEOL = 1;
   int VEOL2 = 2;
   int VERASE = 3;
   int VWERASE = 4;
   int VKILL = 5;
   int VREPRINT = 6;
   int VERASE2 = 7;
   int VINTR = 8;
   int VQUIT = 9;
   int VSUSP = 10;
   int VDSUSP = 11;
   int VSTART = 12;
   int VSTOP = 13;
   int VLNEXT = 14;
   int VDISCARD = 15;
   int VMIN = 16;
   int VTIME = 17;
   int VSTATUS = 18;
   int IGNBRK = 1;
   int BRKINT = 2;
   int IGNPAR = 4;
   int PARMRK = 8;
   int INPCK = 16;
   int ISTRIP = 32;
   int INLCR = 64;
   int IGNCR = 128;
   int ICRNL = 256;
   int IXON = 512;
   int IXOFF = 1024;
   int IXANY = 2048;
   int IMAXBEL = 8192;
   int OPOST = 1;
   int ONLCR = 2;
   int TABDLY = 4;
   int TAB0 = 0;
   int TAB3 = 4;
   int ONOEOT = 8;
   int OCRNL = 16;
   int ONLRET = 64;
   int CIGNORE = 1;
   int CSIZE = 768;
   int CS5 = 0;
   int CS6 = 256;
   int CS7 = 512;
   int CS8 = 768;
   int CSTOPB = 1024;
   int CREAD = 2048;
   int PARENB = 4096;
   int PARODD = 8192;
   int HUPCL = 16384;
   int CLOCAL = 32768;
   int ECHOKE = 1;
   int ECHOE = 2;
   int ECHOK = 4;
   int ECHO = 8;
   int ECHONL = 16;
   int ECHOPRT = 32;
   int ECHOCTL = 64;
   int ISIG = 128;
   int ICANON = 256;
   int ALTWERASE = 512;
   int IEXTEN = 1024;
   int EXTPROC = 2048;
   int TOSTOP = 4194304;
   int FLUSHO = 8388608;
   int PENDIN = 33554432;
   int NOFLSH = 134217728;
   int TCSANOW = 0;
   int TCSADRAIN = 1;
   int TCSAFLUSH = 2;

   void tcgetattr(int var1, CLibrary$termios var2) throws LastErrorException;

   void tcsetattr(int var1, int var2, CLibrary$termios var3) throws LastErrorException;

   void ioctl(int var1, long var2, CLibrary$winsize var4) throws LastErrorException;

   void ttyname_r(int var1, byte[] var2, int var3) throws LastErrorException;
}
