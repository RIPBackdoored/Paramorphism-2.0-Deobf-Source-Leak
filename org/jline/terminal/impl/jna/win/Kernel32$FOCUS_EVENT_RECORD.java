package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$FOCUS_EVENT_RECORD extends Structure {
   public boolean bSetFocus;
   private static String[] fieldOrder = new String[]{"bSetFocus"};

   public Kernel32$FOCUS_EVENT_RECORD() {
      super();
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
