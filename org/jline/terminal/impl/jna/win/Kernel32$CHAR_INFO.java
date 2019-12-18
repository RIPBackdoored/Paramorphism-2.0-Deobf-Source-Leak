package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$CHAR_INFO extends Structure {
   public Kernel32$UnionChar uChar;
   public short Attributes;
   private static String[] fieldOrder = new String[]{"uChar", "Attributes"};

   public Kernel32$CHAR_INFO() {
      super();
   }

   public Kernel32$CHAR_INFO(char var1, short var2) {
      super();
      this.uChar = new Kernel32$UnionChar(var1);
      this.Attributes = var2;
   }

   public Kernel32$CHAR_INFO(byte var1, short var2) {
      super();
      this.uChar = new Kernel32$UnionChar(var1);
      this.Attributes = var2;
   }

   public static Kernel32$CHAR_INFO[] createArray(int var0) {
      return (Kernel32$CHAR_INFO[])((Kernel32$CHAR_INFO[])(new Kernel32$CHAR_INFO()).toArray(var0));
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }
}
