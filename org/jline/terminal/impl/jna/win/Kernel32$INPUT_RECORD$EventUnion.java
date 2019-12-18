package org.jline.terminal.impl.jna.win;

import com.sun.jna.Union;

public class Kernel32$INPUT_RECORD$EventUnion extends Union {
   public Kernel32$KEY_EVENT_RECORD KeyEvent;
   public Kernel32$MOUSE_EVENT_RECORD MouseEvent;
   public Kernel32$WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;
   public Kernel32$MENU_EVENT_RECORD MenuEvent;
   public Kernel32$FOCUS_EVENT_RECORD FocusEvent;

   public Kernel32$INPUT_RECORD$EventUnion() {
      super();
   }
}
