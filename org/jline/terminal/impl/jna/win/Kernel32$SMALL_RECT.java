package org.jline.terminal.impl.jna.win;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class Kernel32$SMALL_RECT extends Structure {
   public short Left;
   public short Top;
   public short Right;
   public short Bottom;
   private static String[] fieldOrder = new String[]{"Left", "Top", "Right", "Bottom"};

   public Kernel32$SMALL_RECT() {
      super();
   }

   public Kernel32$SMALL_RECT(Kernel32$SMALL_RECT var1) {
      this(var1.Top, var1.Left, var1.Bottom, var1.Right);
   }

   public Kernel32$SMALL_RECT(short var1, short var2, short var3, short var4) {
      super();
      this.Top = var1;
      this.Left = var2;
      this.Bottom = var3;
      this.Right = var4;
   }

   protected List getFieldOrder() {
      return Arrays.asList(fieldOrder);
   }

   public short width() {
      return (short)(this.Right - this.Left);
   }

   public short height() {
      return (short)(this.Bottom - this.Top);
   }
}
