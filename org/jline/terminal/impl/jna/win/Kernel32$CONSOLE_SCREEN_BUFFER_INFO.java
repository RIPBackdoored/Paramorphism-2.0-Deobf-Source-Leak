package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$CONSOLE_SCREEN_BUFFER_INFO extends Structure {
   public Kernel32$COORD dwSize;
   public Kernel32$COORD dwCursorPosition;
   public short wAttributes;
   public Kernel32$SMALL_RECT srWindow;
   public Kernel32$COORD dwMaximumWindowSize;
   private static String[] fieldOrder = new String[]{"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"};

   public Kernel32$CONSOLE_SCREEN_BUFFER_INFO() {
      super();
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }

   public int windowWidth() {
      return this.srWindow.width() + 1;
   }

   public int windowHeight() {
      return this.srWindow.height() + 1;
   }
}
