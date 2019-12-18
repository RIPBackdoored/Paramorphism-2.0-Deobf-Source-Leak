package org.jline.reader.impl;

import java.util.Objects;
import org.jline.reader.Buffer;

public class BufferImpl implements Buffer {
   private int cursor;
   private int cursorCol;
   private int[] buffer;
   private int g0;
   private int g1;

   public BufferImpl() {
      this(64);
   }

   public BufferImpl(int var1) {
      super();
      this.cursor = 0;
      this.cursorCol = -1;
      this.buffer = new int[var1];
      this.g0 = 0;
      this.g1 = this.buffer.length;
   }

   private BufferImpl(BufferImpl var1) {
      super();
      this.cursor = 0;
      this.cursorCol = -1;
      this.cursor = var1.cursor;
      this.cursorCol = var1.cursorCol;
      this.buffer = (int[])var1.buffer.clone();
      this.g0 = var1.g0;
      this.g1 = var1.g1;
   }

   public BufferImpl copy() {
      return new BufferImpl(this);
   }

   public int cursor() {
      return this.cursor;
   }

   public int length() {
      return this.buffer.length - (this.g1 - this.g0);
   }

   public boolean currChar(int var1) {
      if (this.cursor == this.length()) {
         return false;
      } else {
         this.buffer[this.adjust(this.cursor)] = var1;
         return true;
      }
   }

   public int currChar() {
      return this.cursor == this.length() ? 0 : this.atChar(this.cursor);
   }

   public int prevChar() {
      return this.cursor <= 0 ? 0 : this.atChar(this.cursor - 1);
   }

   public int nextChar() {
      return this.cursor >= this.length() - 1 ? 0 : this.atChar(this.cursor + 1);
   }

   public int atChar(int var1) {
      return var1 >= 0 && var1 < this.length() ? this.buffer[this.adjust(var1)] : 0;
   }

   private int adjust(int var1) {
      return var1 >= this.g0 ? var1 + this.g1 - this.g0 : var1;
   }

   public void write(int var1) {
      this.write(new int[]{var1});
   }

   public void write(int var1, boolean var2) {
      if (var2) {
         this.delete(1);
      }

      this.write(new int[]{var1});
   }

   public void write(CharSequence var1) {
      Objects.requireNonNull(var1);
      this.write(var1.codePoints().toArray());
   }

   public void write(CharSequence var1, boolean var2) {
      Objects.requireNonNull(var1);
      int[] var3 = var1.codePoints().toArray();
      if (var2) {
         this.delete(var3.length);
      }

      this.write(var3);
   }

   private void write(int[] var1) {
      this.moveGapToCursor();
      int var2 = this.length() + var1.length;
      int var3 = this.buffer.length;
      if (var3 < var2) {
         while(true) {
            if (var3 >= var2) {
               int[] var4 = new int[var3];
               System.arraycopy(this.buffer, 0, var4, 0, this.g0);
               System.arraycopy(this.buffer, this.g1, var4, this.g1 + var3 - this.buffer.length, this.buffer.length - this.g1);
               this.g1 += var3 - this.buffer.length;
               this.buffer = var4;
               break;
            }

            var3 *= 2;
         }
      }

      System.arraycopy(var1, 0, this.buffer, this.cursor, var1.length);
      this.g0 += var1.length;
      this.cursor += var1.length;
      this.cursorCol = -1;
   }

   public boolean clear() {
      if (this.length() == 0) {
         return false;
      } else {
         this.g0 = 0;
         this.g1 = this.buffer.length;
         this.cursor = 0;
         this.cursorCol = -1;
         return true;
      }
   }

   public String substring(int var1) {
      return this.substring(var1, this.length());
   }

   public String substring(int var1, int var2) {
      if (var1 < var2 && var1 >= 0 && var2 <= this.length()) {
         if (var2 <= this.g0) {
            return new String(this.buffer, var1, var2 - var1);
         } else if (var1 > this.g0) {
            return new String(this.buffer, this.g1 - this.g0 + var1, var2 - var1);
         } else {
            int[] var3 = (int[])this.buffer.clone();
            System.arraycopy(var3, this.g1, var3, this.g0, var3.length - this.g1);
            return new String(var3, var1, var2 - var1);
         }
      } else {
         return "";
      }
   }

   public String upToCursor() {
      return this.substring(0, this.cursor);
   }

   public boolean cursor(int var1) {
      if (var1 == this.cursor) {
         return true;
      } else {
         return this.move(var1 - this.cursor) != 0;
      }
   }

   public int move(int var1) {
      int var2 = var1;
      if (this.cursor == 0 && var1 <= 0) {
         return 0;
      } else if (this.cursor == this.length() && var1 >= 0) {
         return 0;
      } else {
         if (this.cursor + var1 < 0) {
            var2 = -this.cursor;
         } else if (this.cursor + var1 > this.length()) {
            var2 = this.length() - this.cursor;
         }

         this.cursor += var2;
         this.cursorCol = -1;
         return var2;
      }
   }

   public boolean up() {
      int var1 = this.getCursorCol();

      int var2;
      for(var2 = this.cursor - 1; var2 >= 0 && this.atChar(var2) != 10; --var2) {
      }

      if (var2 < 0) {
         return false;
      } else {
         int var3;
         for(var3 = var2 - 1; var3 >= 0 && this.atChar(var3) != 10; --var3) {
         }

         this.cursor = Math.min(var3 + var1 + 1, var2);
         return true;
      }
   }

   public boolean down() {
      int var1 = this.getCursorCol();

      int var2;
      for(var2 = this.cursor; var2 < this.length() && this.atChar(var2) != 10; ++var2) {
      }

      if (var2 >= this.length()) {
         return false;
      } else {
         int var3;
         for(var3 = var2 + 1; var3 < this.length() && this.atChar(var3) != 10; ++var3) {
         }

         this.cursor = Math.min(var2 + var1 + 1, var3);
         return true;
      }
   }

   public boolean moveXY(int var1, int var2) {
      int var3;
      for(var3 = 0; this.prevChar() != 10 && this.move(-1) == -1; ++var3) {
      }

      for(this.cursorCol = 0; var2 < 0; ++var2) {
         this.up();
      }

      while(var2 > 0) {
         this.down();
         --var2;
      }

      var3 = Math.max(var3 + var1, 0);

      for(int var4 = 0; var4 < var3 && this.move(1) == 1 && this.currChar() != 10; ++var4) {
      }

      this.cursorCol = var3;
      return true;
   }

   private int getCursorCol() {
      if (this.cursorCol < 0) {
         this.cursorCol = 0;

         int var1;
         for(var1 = this.cursor - 1; var1 >= 0 && this.atChar(var1) != 10; --var1) {
         }

         this.cursorCol = this.cursor - var1 - 1;
      }

      return this.cursorCol;
   }

   public int backspace(int var1) {
      int var2 = Math.max(Math.min(this.cursor, var1), 0);
      this.moveGapToCursor();
      this.cursor -= var2;
      this.g0 -= var2;
      this.cursorCol = -1;
      return var2;
   }

   public boolean backspace() {
      return this.backspace(1) == 1;
   }

   public int delete(int var1) {
      int var2 = Math.max(Math.min(this.length() - this.cursor, var1), 0);
      this.moveGapToCursor();
      this.g1 += var2;
      this.cursorCol = -1;
      return var2;
   }

   public boolean delete() {
      return this.delete(1) == 1;
   }

   public String toString() {
      return this.substring(0, this.length());
   }

   public void copyFrom(Buffer var1) {
      if (!(var1 instanceof BufferImpl)) {
         throw new IllegalStateException();
      } else {
         BufferImpl var2 = (BufferImpl)var1;
         this.g0 = var2.g0;
         this.g1 = var2.g1;
         this.buffer = (int[])var2.buffer.clone();
         this.cursor = var2.cursor;
         this.cursorCol = var2.cursorCol;
      }
   }

   private void moveGapToCursor() {
      int var1;
      if (this.cursor < this.g0) {
         var1 = this.g0 - this.cursor;
         System.arraycopy(this.buffer, this.cursor, this.buffer, this.g1 - var1, var1);
         this.g0 -= var1;
         this.g1 -= var1;
      } else if (this.cursor > this.g0) {
         var1 = this.cursor - this.g0;
         System.arraycopy(this.buffer, this.g1, this.buffer, this.g0, var1);
         this.g0 += var1;
         this.g1 += var1;
      }

   }

   public Buffer copy() {
      return this.copy();
   }
}
