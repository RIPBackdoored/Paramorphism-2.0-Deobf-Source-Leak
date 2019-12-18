package org.jline.terminal.impl.jna.linux;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LinuxNativePty$UtilLibrary extends Library {
   LinuxNativePty$UtilLibrary INSTANCE = (LinuxNativePty$UtilLibrary)Native.loadLibrary("util", LinuxNativePty$UtilLibrary.class);

   void openpty(int[] var1, int[] var2, byte[] var3, CLibrary$termios var4, CLibrary$winsize var5) throws LastErrorException;
}
