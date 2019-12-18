package org.jline.terminal.impl.jansi.solaris;

import org.jline.terminal.impl.jansi.*;
import java.io.*;
import org.jline.terminal.*;
import org.fusesource.jansi.internal.*;
import java.util.*;

public class SolarisNativePty extends JansiNativePty
{
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
    private static final int PARMRK = 16;
    private static final int INPCK = 32;
    private static final int ISTRIP = 64;
    private static final int INLCR = 256;
    private static final int IGNCR = 512;
    private static final int ICRNL = 1024;
    private static final int IUCLC = 4096;
    private static final int IXON = 8192;
    private static final int IXANY = 16384;
    private static final int IXOFF = 65536;
    private static final int IMAXBEL = 131072;
    private static final int IUTF8 = 262144;
    private static final int OPOST = 1;
    private static final int OLCUC = 2;
    private static final int ONLCR = 4;
    private static final int OCRNL = 16;
    private static final int ONOCR = 32;
    private static final int ONLRET = 64;
    private static final int OFILL = 256;
    private static final int OFDEL = 512;
    private static final int NLDLY = 1024;
    private static final int NL0 = 0;
    private static final int NL1 = 1024;
    private static final int CRDLY = 12288;
    private static final int CR0 = 0;
    private static final int CR1 = 4096;
    private static final int CR2 = 8192;
    private static final int CR3 = 12288;
    private static final int TABDLY = 81920;
    private static final int TAB0 = 0;
    private static final int TAB1 = 16384;
    private static final int TAB2 = 65536;
    private static final int TAB3 = 81920;
    private static final int XTABS = 81920;
    private static final int BSDLY = 131072;
    private static final int BS0 = 0;
    private static final int BS1 = 131072;
    private static final int VTDLY = 262144;
    private static final int VT0 = 0;
    private static final int VT1 = 262144;
    private static final int FFDLY = 1048576;
    private static final int FF0 = 0;
    private static final int FF1 = 1048576;
    private static final int CBAUD = 65559;
    private static final int B0 = 0;
    private static final int B50 = 1;
    private static final int B75 = 2;
    private static final int B110 = 3;
    private static final int B134 = 4;
    private static final int B150 = 5;
    private static final int B200 = 6;
    private static final int B300 = 7;
    private static final int B600 = 16;
    private static final int B1200 = 17;
    private static final int B1800 = 18;
    private static final int B2400 = 19;
    private static final int B4800 = 20;
    private static final int B9600 = 21;
    private static final int B19200 = 22;
    private static final int B38400 = 23;
    private static final int EXTA = 11637248;
    private static final int EXTB = 11764736;
    private static final int CSIZE = 96;
    private static final int CS5 = 0;
    private static final int CS6 = 32;
    private static final int CS7 = 64;
    private static final int CS8 = 96;
    private static final int CSTOPB = 256;
    private static final int CREAD = 512;
    private static final int PARENB = 1024;
    private static final int PARODD = 4096;
    private static final int HUPCL = 8192;
    private static final int CLOCAL = 16384;
    private static final int ISIG = 1;
    private static final int ICANON = 2;
    private static final int XCASE = 4;
    private static final int ECHO = 16;
    private static final int ECHOE = 32;
    private static final int ECHOK = 64;
    private static final int ECHONL = 256;
    private static final int NOFLSH = 512;
    private static final int TOSTOP = 1024;
    private static final int ECHOCTL = 4096;
    private static final int ECHOPRT = 8192;
    private static final int ECHOKE = 16384;
    private static final int FLUSHO = 65536;
    private static final int PENDIN = 262144;
    private static final int IEXTEN = 1048576;
    private static final int EXTPROC = 2097152;
    
    public static SolarisNativePty current() throws IOException {
        try {
            return new SolarisNativePty(-1, null, 0, FileDescriptor.in, 1, FileDescriptor.out, JansiNativePty.ttyname());
        }
        catch (IOException ex) {
            throw new IOException("Not a tty", ex);
        }
    }
    
    public SolarisNativePty(final int n, final FileDescriptor fileDescriptor, final int n2, final FileDescriptor fileDescriptor2, final String s) {
        super(n, fileDescriptor, n2, fileDescriptor2, s);
    }
    
    public SolarisNativePty(final int n, final FileDescriptor fileDescriptor, final int n2, final FileDescriptor fileDescriptor2, final int n3, final FileDescriptor fileDescriptor3, final String s) {
        super(n, fileDescriptor, n2, fileDescriptor2, n3, fileDescriptor3, s);
    }
    
    @Override
    protected CLibrary.Termios toTermios(final Attributes attributes) {
        final CLibrary.Termios termios = new CLibrary.Termios();
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 16L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 32L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 64L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 256L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 512L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 1024L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 8192L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 65536L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 16384L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 131072L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 262144L, termios.c_iflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 4L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 256L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 1024L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 81920L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 1048576L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 131072L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 262144L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 512L, termios.c_oflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 32L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 64L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 96L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 256L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 512L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 1024L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 4096L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 8192L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 16384L, termios.c_cflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 16384L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 32L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 64L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 16L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 256L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 8192L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 4096L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 1L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 2L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1048576L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2097152L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 1024L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 65536L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), 262144L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), 512L, termios.c_lflag);
        termios.c_cc[4] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOF);
        termios.c_cc[11] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL);
        termios.c_cc[16] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL2);
        termios.c_cc[2] = (byte)attributes.getControlChar(Attributes.ControlChar.VERASE);
        termios.c_cc[14] = (byte)attributes.getControlChar(Attributes.ControlChar.VWERASE);
        termios.c_cc[3] = (byte)attributes.getControlChar(Attributes.ControlChar.VKILL);
        termios.c_cc[12] = (byte)attributes.getControlChar(Attributes.ControlChar.VREPRINT);
        termios.c_cc[0] = (byte)attributes.getControlChar(Attributes.ControlChar.VINTR);
        termios.c_cc[1] = (byte)attributes.getControlChar(Attributes.ControlChar.VQUIT);
        termios.c_cc[10] = (byte)attributes.getControlChar(Attributes.ControlChar.VSUSP);
        termios.c_cc[8] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTART);
        termios.c_cc[9] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTOP);
        termios.c_cc[15] = (byte)attributes.getControlChar(Attributes.ControlChar.VLNEXT);
        termios.c_cc[13] = (byte)attributes.getControlChar(Attributes.ControlChar.VDISCARD);
        termios.c_cc[6] = (byte)attributes.getControlChar(Attributes.ControlChar.VMIN);
        termios.c_cc[5] = (byte)attributes.getControlChar(Attributes.ControlChar.VTIME);
        return termios;
    }
    
    @Override
    protected Attributes toAttributes(final CLibrary.Termios termios) {
        final Attributes attributes = new Attributes();
        final EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.BRKINT, 2);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNPAR, 4);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.PARMRK, 16);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.INPCK, 32);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.ISTRIP, 64);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.INLCR, 256);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNCR, 512);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.ICRNL, 1024);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXON, 8192);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXOFF, 65536);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXANY, 16384);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IMAXBEL, 131072);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IUTF8, 262144);
        final EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OPOST, 1);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONLCR, 4);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OCRNL, 16);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONOCR, 32);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONLRET, 64);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OFILL, 256);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.NLDLY, 1024);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.TABDLY, 81920);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.CRDLY, 12288);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.FFDLY, 1048576);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.BSDLY, 131072);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.VTDLY, 262144);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OFDEL, 512);
        final EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS5, 0);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS6, 32);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS7, 64);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS8, 96);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CSTOPB, 256);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CREAD, 512);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.PARENB, 1024);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.PARODD, 4096);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.HUPCL, 8192);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CLOCAL, 16384);
        final EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOKE, 16384);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOE, 32);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOK, 64);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHO, 16);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHONL, 256);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOPRT, 8192);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOCTL, 4096);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ISIG, 1);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ICANON, 2);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.IEXTEN, 1048576);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.EXTPROC, 2097152);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.TOSTOP, 1024);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.FLUSHO, 65536);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.PENDIN, 262144);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.NOFLSH, 512);
        final EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
        controlChars.put(Attributes.ControlChar.VEOF, (int)termios.c_cc[4]);
        controlChars.put(Attributes.ControlChar.VEOL, (int)termios.c_cc[11]);
        controlChars.put(Attributes.ControlChar.VEOL2, (int)termios.c_cc[16]);
        controlChars.put(Attributes.ControlChar.VERASE, (int)termios.c_cc[2]);
        controlChars.put(Attributes.ControlChar.VWERASE, (int)termios.c_cc[14]);
        controlChars.put(Attributes.ControlChar.VKILL, (int)termios.c_cc[3]);
        controlChars.put(Attributes.ControlChar.VREPRINT, (int)termios.c_cc[12]);
        controlChars.put(Attributes.ControlChar.VINTR, (int)termios.c_cc[0]);
        controlChars.put(Attributes.ControlChar.VQUIT, (int)termios.c_cc[1]);
        controlChars.put(Attributes.ControlChar.VSUSP, (int)termios.c_cc[10]);
        controlChars.put(Attributes.ControlChar.VSTART, (int)termios.c_cc[8]);
        controlChars.put(Attributes.ControlChar.VSTOP, (int)termios.c_cc[9]);
        controlChars.put(Attributes.ControlChar.VLNEXT, (int)termios.c_cc[15]);
        controlChars.put(Attributes.ControlChar.VDISCARD, (int)termios.c_cc[13]);
        controlChars.put(Attributes.ControlChar.VMIN, (int)termios.c_cc[6]);
        controlChars.put(Attributes.ControlChar.VTIME, (int)termios.c_cc[5]);
        return attributes;
    }
    
    private static long setFlag(final boolean b, final long n, final long n2) {
        return b ? (n2 | n) : n2;
    }
    
    private static <T extends Enum<T>> void addFlag(final long n, final EnumSet<T> set, final T t, final int n2) {
        if ((n & (long)n2) != 0x0L) {
            set.add(t);
        }
    }
}
