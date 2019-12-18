package org.jline.terminal.impl.jna.freebsd;

import com.sun.jna.*;

public interface UtilLibrary extends Library
{
    public static final UtilLibrary INSTANCE = (UtilLibrary)Native.loadLibrary("util", (Class)UtilLibrary.class);
    
    void openpty(final int[] p0, final int[] p1, final byte[] p2, final CLibrary.termios p3, final CLibrary.winsize p4) throws LastErrorException;
}
