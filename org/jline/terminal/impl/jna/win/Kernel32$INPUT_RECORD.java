package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$INPUT_RECORD extends Structure {
   public static final short KEY_EVENT = 1;
   public static final short MOUSE_EVENT = 2;
   public static final short WINDOW_BUFFER_SIZE_EVENT = 4;
   public static final short MENU_EVENT = 8;
   public static final short FOCUS_EVENT = 16;
   public short EventType;
   public Kernel32$INPUT_RECORD$EventUnion Event;
   private static String[] fieldOrder = new String[]{"EventType", "Event"};

   public Kernel32$INPUT_RECORD() {
      super();
   }

   public void read() {
      this.readField("EventType");
      switch(this.EventType) {
      case 1:
         this.Event.setType(Kernel32$KEY_EVENT_RECORD.class);
         break;
      case 2:
         this.Event.setType(Kernel32$MOUSE_EVENT_RECORD.class);
         break;
      case 4:
         this.Event.setType(Kernel32$WINDOW_BUFFER_SIZE_RECORD.class);
         break;
      case 8:
         this.Event.setType(Kernel32$MENU_EVENT_RECORD.class);
         break;
      case 16:
         this.Event.setType(Kernel32$MENU_EVENT_RECORD.class);
      }

      super.read();
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
