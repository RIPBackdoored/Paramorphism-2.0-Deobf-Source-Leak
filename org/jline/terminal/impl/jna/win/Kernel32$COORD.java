package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import com.sun.jna.Structure.ByValue;
import java.util.Arrays;
import java.util.List;

public class Kernel32$COORD extends Structure implements ByValue {
   public short X;
   public short Y;
   private static String[] fieldOrder = new String[]{"X", "Y"};

   public Kernel32$COORD() {
      super();
   }

   public Kernel32$COORD(short var1, short var2) {
      super();
      this.X = var1;
      this.Y = var2;
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
