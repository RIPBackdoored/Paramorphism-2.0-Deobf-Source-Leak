package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$MENU_EVENT_RECORD extends Structure {
   public int dwCommandId;
   private static String[] fieldOrder = new String[]{"dwCommandId"};

   public Kernel32$MENU_EVENT_RECORD() {
      super();
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
