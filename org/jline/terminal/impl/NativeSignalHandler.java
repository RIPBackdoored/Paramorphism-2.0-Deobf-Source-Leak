package org.jline.terminal.impl;

import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;

public final class NativeSignalHandler implements Terminal$SignalHandler {
   public static final NativeSignalHandler SIG_DFL = new NativeSignalHandler();
   public static final NativeSignalHandler SIG_IGN = new NativeSignalHandler();

   private NativeSignalHandler() {
      super();
   }

   public void handle(Terminal$Signal var1) {
      throw new UnsupportedOperationException();
   }
}
