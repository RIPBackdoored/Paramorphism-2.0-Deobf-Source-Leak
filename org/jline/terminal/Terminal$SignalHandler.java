package org.jline.terminal;

import org.jline.terminal.impl.NativeSignalHandler;

public interface Terminal$SignalHandler {
   Terminal$SignalHandler SIG_DFL = NativeSignalHandler.SIG_DFL;
   Terminal$SignalHandler SIG_IGN = NativeSignalHandler.SIG_IGN;

   void handle(Terminal$Signal var1);
}
