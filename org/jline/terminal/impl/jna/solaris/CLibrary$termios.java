package org.jline.terminal.impl.jna.solaris;

import com.sun.jna.*;
import org.jline.terminal.*;
import java.util.*;

public static class termios extends Structure
{
    public int c_iflag;
    public int c_oflag;
    public int c_cflag;
    public int c_lflag;
    public byte[] c_cc;
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc");
    }
    
    public termios() {
        super();
        this.c_cc = new byte[32];
    }
    
    public termios(final Attributes attributes) {
        super();
        this.c_cc = new byte[32];
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 16, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 32, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 64, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 256, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 512, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 1024, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 8192, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 65536, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 16384, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 131072, this.c_iflag);
        this.c_iflag = this.setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 262144, this.c_iflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 4, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 256, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 1024, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 81920, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 1048576, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 131072, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 262144, this.c_oflag);
        this.c_oflag = this.setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 512, this.c_oflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 32, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 64, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 96, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 256, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 512, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 1024, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 4096, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 8192, this.c_cflag);
        this.c_cflag = this.setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 16384, this.c_cflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 16384, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 32, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 64, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 16, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 256, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 8192, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 4096, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 1, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 2, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1048576, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2097152, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 1024, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 65536, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), 262144, this.c_lflag);
        this.c_lflag = this.setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), 512, this.c_lflag);
        this.c_cc[4] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOF);
        this.c_cc[11] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL);
        this.c_cc[16] = (byte)attributes.getControlChar(Attributes.ControlChar.VEOL2);
        this.c_cc[2] = (byte)attributes.getControlChar(Attributes.ControlChar.VERASE);
        this.c_cc[14] = (byte)attributes.getControlChar(Attributes.ControlChar.VWERASE);
        this.c_cc[3] = (byte)attributes.getControlChar(Attributes.ControlChar.VKILL);
        this.c_cc[12] = (byte)attributes.getControlChar(Attributes.ControlChar.VREPRINT);
        this.c_cc[0] = (byte)attributes.getControlChar(Attributes.ControlChar.VINTR);
        this.c_cc[1] = (byte)attributes.getControlChar(Attributes.ControlChar.VQUIT);
        this.c_cc[10] = (byte)attributes.getControlChar(Attributes.ControlChar.VSUSP);
        this.c_cc[8] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTART);
        this.c_cc[9] = (byte)attributes.getControlChar(Attributes.ControlChar.VSTOP);
        this.c_cc[15] = (byte)attributes.getControlChar(Attributes.ControlChar.VLNEXT);
        this.c_cc[13] = (byte)attributes.getControlChar(Attributes.ControlChar.VDISCARD);
        this.c_cc[6] = (byte)attributes.getControlChar(Attributes.ControlChar.VMIN);
        this.c_cc[5] = (byte)attributes.getControlChar(Attributes.ControlChar.VTIME);
    }
    
    private int setFlag(final boolean b, final int n, final int n2) {
        return b ? (n2 | n) : n2;
    }
    
    public Attributes toAttributes() {
        final Attributes attributes = new Attributes();
        final EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.BRKINT, 2);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNPAR, 4);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.PARMRK, 16);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.INPCK, 32);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.ISTRIP, 64);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.INLCR, 256);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNCR, 512);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.ICRNL, 1024);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXON, 8192);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXOFF, 65536);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXANY, 16384);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IMAXBEL, 131072);
        this.addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IUTF8, 262144);
        final EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OPOST, 1);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONLCR, 4);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OCRNL, 16);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONOCR, 32);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONLRET, 64);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OFILL, 256);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.NLDLY, 1024);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.TABDLY, 81920);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.CRDLY, 12288);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.FFDLY, 1048576);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.BSDLY, 131072);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.VTDLY, 262144);
        this.addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OFDEL, 512);
        final EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS5, 0);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS6, 32);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS7, 64);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS8, 96);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CSTOPB, 256);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CREAD, 512);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.PARENB, 1024);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.PARODD, 4096);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.HUPCL, 8192);
        this.addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CLOCAL, 16384);
        final EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOKE, 16384);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOE, 32);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOK, 64);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHO, 16);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHONL, 256);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOPRT, 8192);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOCTL, 4096);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ISIG, 1);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ICANON, 2);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.IEXTEN, 1048576);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.EXTPROC, 2097152);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.TOSTOP, 1024);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.FLUSHO, 65536);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.PENDIN, 262144);
        this.addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.NOFLSH, 512);
        final EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
        controlChars.put(Attributes.ControlChar.VEOF, (int)this.c_cc[4]);
        controlChars.put(Attributes.ControlChar.VEOL, (int)this.c_cc[11]);
        controlChars.put(Attributes.ControlChar.VEOL2, (int)this.c_cc[16]);
        controlChars.put(Attributes.ControlChar.VERASE, (int)this.c_cc[2]);
        controlChars.put(Attributes.ControlChar.VWERASE, (int)this.c_cc[14]);
        controlChars.put(Attributes.ControlChar.VKILL, (int)this.c_cc[3]);
        controlChars.put(Attributes.ControlChar.VREPRINT, (int)this.c_cc[12]);
        controlChars.put(Attributes.ControlChar.VINTR, (int)this.c_cc[0]);
        controlChars.put(Attributes.ControlChar.VQUIT, (int)this.c_cc[1]);
        controlChars.put(Attributes.ControlChar.VSUSP, (int)this.c_cc[10]);
        controlChars.put(Attributes.ControlChar.VSTART, (int)this.c_cc[8]);
        controlChars.put(Attributes.ControlChar.VSTOP, (int)this.c_cc[9]);
        controlChars.put(Attributes.ControlChar.VLNEXT, (int)this.c_cc[15]);
        controlChars.put(Attributes.ControlChar.VDISCARD, (int)this.c_cc[13]);
        controlChars.put(Attributes.ControlChar.VMIN, (int)this.c_cc[6]);
        controlChars.put(Attributes.ControlChar.VTIME, (int)this.c_cc[5]);
        return attributes;
    }
    
    private <T extends Enum<T>> void addFlag(final int n, final EnumSet<T> set, final T t, final int n2) {
        if ((n & n2) != 0x0) {
            set.add(t);
        }
    }
}
