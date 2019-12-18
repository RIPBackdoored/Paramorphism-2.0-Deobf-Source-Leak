package org.jline.terminal;

public class Cursor {
   private final int x;
   private final int y;

   public Cursor(int var1, int var2) {
      super();
      this.x = var1;
      this.y = var2;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Cursor)) {
         return false;
      } else {
         Cursor var2 = (Cursor)var1;
         return this.x == var2.x && this.y == var2.y;
      }
   }

   public int hashCode() {
      return this.x * 31 + this.y;
   }

   public String toString() {
      return "Cursor[x=" + this.x + ", y=" + this.y + ']';
   }
}
