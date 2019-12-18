package org.jline.terminal.impl.jna.freebsd;

import org.jline.terminal.impl.jna.*;
import java.io.*;
import org.jline.terminal.*;
import com.sun.jna.*;

public class FreeBsdNativePty extends JnaNativePty
{
    private static final CLibrary C_LIBRARY;
    
    public static FreeBsdNativePty current() throws IOException {
        final int n = 0;
        final byte[] array = new byte[64];
        FreeBsdNativePty.C_LIBRARY.ttyname_r(n, array, array.length);
        int n2;
        for (n2 = 0; array[n2] != 0; ++n2) {}
        return new FreeBsdNativePty(-1, null, n, FileDescriptor.in, 1, FileDescriptor.out, new String(array, 0, n2));
    }
    
    public static FreeBsdNativePty open(final Attributes attributes, final Size size) throws IOException {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final byte[] array3 = new byte[64];
        UtilLibrary.INSTANCE.openpty(array, array2, array3, (attributes != null) ? new CLibrary.termios(attributes) : null, (size != null) ? new CLibrary.winsize(size) : null);
        int n;
        for (n = 0; array3[n] != 0; ++n) {}
        return new FreeBsdNativePty(array[0], JnaNativePty.newDescriptor(array[0]), array2[0], JnaNativePty.newDescriptor(array2[0]), new String(array3, 0, n));
    }
    
    public FreeBsdNativePty(final int n, final FileDescriptor fileDescriptor, final int n2, final FileDescriptor fileDescriptor2, final String s) {
        super(n, fileDescriptor, n2, fileDescriptor2, s);
    }
    
    public FreeBsdNativePty(final int n, final FileDescriptor fileDescriptor, final int n2, final FileDescriptor fileDescriptor2, final int n3, final FileDescriptor fileDescriptor3, final String s) {
        super(n, fileDescriptor, n2, fileDescriptor2, n3, fileDescriptor3, s);
    }
    
    @Override
    public Attributes getAttr() throws IOException {
        final CLibrary.termios termios = new CLibrary.termios();
        FreeBsdNativePty.C_LIBRARY.tcgetattr(this.getSlave(), termios);
        return termios.toAttributes();
    }
    
    @Override
    protected void doSetAttr(final Attributes attributes) throws IOException {
        FreeBsdNativePty.C_LIBRARY.tcsetattr(this.getSlave(), 0, new CLibrary.termios(attributes));
    }
    
    @Override
    public Size getSize() throws IOException {
        final CLibrary.winsize winsize = new CLibrary.winsize();
        FreeBsdNativePty.C_LIBRARY.ioctl(this.getSlave(), 1074295912L, winsize);
        return winsize.toSize();
    }
    
    @Override
    public void setSize(final Size size) throws IOException {
        FreeBsdNativePty.C_LIBRARY.ioctl(this.getSlave(), -2146929561L, new CLibrary.winsize(size));
    }
    
    static {
        C_LIBRARY = (CLibrary)Native.loadLibrary(Platform.C_LIBRARY_NAME, (Class)CLibrary.class);
    }
    
    public interface UtilLibrary extends Library
    {
        public static final UtilLibrary INSTANCE = (UtilLibrary)Native.loadLibrary("util", (Class)UtilLibrary.class);
        
        void openpty(final int[] p0, final int[] p1, final byte[] p2, final CLibrary.termios p3, final CLibrary.winsize p4) throws LastErrorException;
    }
}
