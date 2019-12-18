package org.jline.terminal.impl.jna.win;

import com.sun.jna.Union;

public class Kernel32$UnionChar extends Union {
   public char UnicodeChar;
   public byte AsciiChar;

   public Kernel32$UnionChar() {
      super();
   }

   public Kernel32$UnionChar(char var1) {
      super();
      this.setType(Character.TYPE);
      this.UnicodeChar = var1;
   }

   public Kernel32$UnionChar(byte var1) {
      super();
      this.setType(Byte.TYPE);
      this.AsciiChar = var1;
   }

   public void set(char var1) {
      this.setType(Character.TYPE);
      this.UnicodeChar = var1;
   }

   public void set(byte var1) {
      this.setType(Byte.TYPE);
      this.AsciiChar = var1;
   }
}
