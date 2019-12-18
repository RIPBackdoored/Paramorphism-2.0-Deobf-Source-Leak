package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$MOUSE_EVENT_RECORD extends Structure {
   public Kernel32$COORD dwMousePosition;
   public int dwButtonState;
   public int dwControlKeyState;
   public int dwEventFlags;
   private static String[] fieldOrder = new String[]{"dwMousePosition", "dwButtonState", "dwControlKeyState", "dwEventFlags"};

   public Kernel32$MOUSE_EVENT_RECORD() {
      super();
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
