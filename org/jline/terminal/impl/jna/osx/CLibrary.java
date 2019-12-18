package org.jline.terminal.impl.jna.osx;

import com.sun.jna.*;
import org.jline.terminal.*;
import java.util.*;

public interface CLibrary extends Library
{
    public static final long TIOCGWINSZ = 1074295912L;
    public static final long TIOCSWINSZ = 2148037735L;
    public static final int TCSANOW = 0;
    public static final int VEOF = 0;
    public static final int VEOL = 1;
    public static final int VEOL2 = 2;
    public static final int VERASE = 3;
    public static final int VWERASE = 4;
    public static final int VKILL = 5;
    public static final int VREPRINT = 6;
    public static final int VINTR = 8;
    public static final int VQUIT = 9;
    public static final int VSUSP = 10;
    public static final int VDSUSP = 11;
    public static final int VSTART = 12;
    public static final int VSTOP = 13;
    public static final int VLNEXT = 14;
    public static final int VDISCARD = 15;
    public static final int VMIN = 16;
    public static final int VTIME = 17;
    public static final int VSTATUS = 18;
    public static final int IGNBRK = 1;
    public static final int BRKINT = 2;
    public static final int IGNPAR = 4;
    public static final int PARMRK = 8;
    public static final int INPCK = 16;
    public static final int ISTRIP = 32;
    public static final int INLCR = 64;
    public static final int IGNCR = 128;
    public static final int ICRNL = 256;
    public static final int IXON = 512;
    public static final int IXOFF = 1024;
    public static final int IXANY = 2048;
    public static final int IMAXBEL = 8192;
    public static final int IUTF8 = 16384;
    public static final int OPOST = 1;
    public static final int ONLCR = 2;
    public static final int OXTABS = 4;
    public static final int ONOEOT = 8;
    public static final int OCRNL = 16;
    public static final int ONOCR = 32;
    public static final int ONLRET = 64;
    public static final int OFILL = 128;
    public static final int NLDLY = 768;
    public static final int TABDLY = 3076;
    public static final int CRDLY = 12288;
    public static final int FFDLY = 16384;
    public static final int BSDLY = 32768;
    public static final int VTDLY = 65536;
    public static final int OFDEL = 131072;
    public static final int CIGNORE = 1;
    public static final int CS5 = 0;
    public static final int CS6 = 256;
    public static final int CS7 = 512;
    public static final int CS8 = 768;
    public static final int CSTOPB = 1024;
    public static final int CREAD = 2048;
    public static final int PARENB = 4096;
    public static final int PARODD = 8192;
    public static final int HUPCL = 16384;
    public static final int CLOCAL = 32768;
    public static final int CCTS_OFLOW = 65536;
    public static final int CRTS_IFLOW = 131072;
    public static final int CDTR_IFLOW = 262144;
    public static final int CDSR_OFLOW = 524288;
    public static final int CCAR_OFLOW = 1048576;
    public static final int ECHOKE = 1;
    public static final int ECHOE = 2;
    public static final int ECHOK = 4;
    public static final int ECHO = 8;
    public static final int ECHONL = 16;
    public static final int ECHOPRT = 32;
    public static final int ECHOCTL = 64;
    public static final int ISIG = 128;
    public static final int ICANON = 256;
    public static final int ALTWERASE = 512;
    public static final int IEXTEN = 1024;
    public static final int EXTPROC = 2048;
    public static final int TOSTOP = 4194304;
    public static final int FLUSHO = 8388608;
    public static final int NOKERNINFO = 33554432;
    public static final int PENDIN = 536870912;
    public static final int NOFLSH = Integer.MIN_VALUE;
    
    void tcgetattr(final int p0, final termios p1) throws LastErrorException;
    
    void tcsetattr(final int p0, final int p1, final termios p2) throws LastErrorException;
    
    void ioctl(final int p0, final NativeLong p1, final winsize p2) throws LastErrorException;
    
    void ttyname_r(final int p0, final byte[] p1, final int p2) throws LastErrorException;
    
    void openpty(final int[] p0, final int[] p1, final byte[] p2, final termios p3, final winsize p4) throws LastErrorException;
    
    public static class winsize extends Structure
    {
        public short ws_row;
        public short ws_col;
        public short ws_xpixel;
        public short ws_ypixel;
        
        public winsize() {
            super();
        }
        
        public winsize(final Size size) {
            super();
            this.ws_row = (short)size.getRows();
            this.ws_col = (short)size.getColumns();
        }
        
        public Size toSize() {
            return new Size(this.ws_col, this.ws_row);
        }
        
        protected List<String> getFieldOrder() {
            return Arrays.asList("ws_row", "ws_col", "ws_xpixel", "ws_ypixel");
        }
    }
    
    public static class termios extends Structure
    {
        public NativeLong c_iflag;
        public NativeLong c_oflag;
        public NativeLong c_cflag;
        public NativeLong c_lflag;
        public byte[] c_cc;
        public NativeLong c_ispeed;
        public NativeLong c_ospeed;
        
        protected List<String> getFieldOrder() {
            return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed");
        }
        
        public termios() {
            super();
            this.c_cc = new byte[20];
        }
        
        public termios(final Attributes attributes) {
            super();
            this.c_cc = new byte[20];
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 8L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 16L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 64L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 128L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 256L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 512L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 2048L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, this.c_iflag);
            this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 16384L, this.c_iflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OXTABS), 4L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 128L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 768L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 3076L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 16384L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 32768L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 65536L, this.c_oflag);
            this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 131072L, this.c_oflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 256L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 512L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 768L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), 65536L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), 131072L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), 262144L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), 524288L, this.c_cflag);
            this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), 1048576L, this.c_cflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), 33554432L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), 536870912L, this.c_lflag);
            this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), -2147483648L, this.c_lflag);
            this.c_cc[0] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOF);
            this.c_cc[1] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL);
            this.c_cc[2] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL2);
            this.c_cc[3] = (byte)attributes.getControlChar(Attributes.ControlChar.VERASE);
            this.c_cc[4] = (byte)attributes.getControlChar(Attributes.ControlChar.VWERASE);
            this.c_cc[5] = (byte)attributes.getControlChar(Attributes.ControlChar.VKILL);
            this.c_cc[6] = (byte)attributes.getControlChar(Attributes.ControlChar.VREPRINT);
            this.c_cc[8] = (byte)attributes.getControlChar(Attributes.ControlChar.VINTR);
            this.c_cc[9] = (byte)attributes.getControlChar(Attributes.ControlChar.VQUIT);
            this.c_cc[10] = (byte)attributes.getControlChar(Attributes.ControlChar.VSUSP);
            this.c_cc[11] = (byte)attributes.getControlChar(Attributes.ControlChar.VDSUSP);
            this.c_cc[12] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTART);
            this.c_cc[13] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTOP);
            this.c_cc[14] = (byte)attributes.getControlChar(Attributes.ControlChar.VLNEXT);
            this.c_cc[15] = (byte)attributes.getControlChar(Attributes.ControlChar.VDISCARD);
            this.c_cc[16] = (byte)attributes.getControlChar(Attributes.ControlChar.VMIN);
            this.c_cc[17] = (byte)attributes.getControlChar(Attributes.ControlChar.VTIME);
            this.c_cc[18] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTATUS);
        }
        
        private void setFlag(final boolean b, final long n, final NativeLong nativeLong) {
            nativeLong.setValue(b ? (nativeLong.longValue() | n) : nativeLong.longValue());
        }
        
        public Attributes toAttributes() {
            final Attributes attributes = new Attributes();
            final EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNBRK, 1);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNBRK, 1);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.BRKINT, 2);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNPAR, 4);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.PARMRK, 8);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.INPCK, 16);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.ISTRIP, 32);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.INLCR, 64);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNCR, 128);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.ICRNL, 256);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXON, 512);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXOFF, 1024);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXANY, 2048);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IMAXBEL, 8192);
            this.addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IUTF8, 16384);
            final EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OPOST, 1);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONLCR, 2);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OXTABS, 4);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONOEOT, 8);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OCRNL, 16);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONOCR, 32);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONLRET, 64);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OFILL, 128);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.NLDLY, 768);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.TABDLY, 3076);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.CRDLY, 12288);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.FFDLY, 16384);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.BSDLY, 32768);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.VTDLY, 65536);
            this.addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OFDEL, 131072);
            final EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CIGNORE, 1);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS5, 0);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS6, 256);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS7, 512);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS8, 768);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CSTOPB, 1024);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CREAD, 2048);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.PARENB, 4096);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.PARODD, 8192);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.HUPCL, 16384);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CLOCAL, 32768);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CCTS_OFLOW, 65536);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CRTS_IFLOW, 131072);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CDSR_OFLOW, 524288);
            this.addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CCAR_OFLOW, 1048576);
            final EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOKE, 1);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOE, 2);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOK, 4);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHO, 8);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHONL, 16);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOPRT, 32);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOCTL, 64);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ISIG, 128);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ICANON, 256);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ALTWERASE, 512);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.IEXTEN, 1024);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.EXTPROC, 2048);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.TOSTOP, 4194304);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.FLUSHO, 8388608);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.NOKERNINFO, 33554432);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.PENDIN, 536870912);
            this.addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.NOFLSH, Integer.MIN_VALUE);
            final EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
            controlChars.put(Attributes.ControlChar.VEOF, (int)this.c_cc[0]);
            controlChars.put(Attributes.ControlChar.VEOL, (int)this.c_cc[1]);
            controlChars.put(Attributes.ControlChar.VEOL2, (int)this.c_cc[2]);
            controlChars.put(Attributes.ControlChar.VERASE, (int)this.c_cc[3]);
            controlChars.put(Attributes.ControlChar.VWERASE, (int)this.c_cc[4]);
            controlChars.put(Attributes.ControlChar.VKILL, (int)this.c_cc[5]);
            controlChars.put(Attributes.ControlChar.VREPRINT, (int)this.c_cc[6]);
            controlChars.put(Attributes.ControlChar.VINTR, (int)this.c_cc[8]);
            controlChars.put(Attributes.ControlChar.VQUIT, (int)this.c_cc[9]);
            controlChars.put(Attributes.ControlChar.VSUSP, (int)this.c_cc[10]);
            controlChars.put(Attributes.ControlChar.VDSUSP, (int)this.c_cc[11]);
            controlChars.put(Attributes.ControlChar.VSTART, (int)this.c_cc[12]);
            controlChars.put(Attributes.ControlChar.VSTOP, (int)this.c_cc[13]);
            controlChars.put(Attributes.ControlChar.VLNEXT, (int)this.c_cc[14]);
            controlChars.put(Attributes.ControlChar.VDISCARD, (int)this.c_cc[15]);
            controlChars.put(Attributes.ControlChar.VMIN, (int)this.c_cc[16]);
            controlChars.put(Attributes.ControlChar.VTIME, (int)this.c_cc[17]);
            controlChars.put(Attributes.ControlChar.VSTATUS, (int)this.c_cc[18]);
            return attributes;
        }
        
        private <T extends Enum<T>> void addFlag(final long n, final EnumSet<T> set, final T t, final int n2) {
            if ((n & (long)n2) != 0x0L) {
                set.add(t);
            }
        }
    }
}
