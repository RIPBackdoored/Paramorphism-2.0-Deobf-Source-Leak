package org.jline.terminal;

public class Size {
   private int rows;
   private int cols;

   public Size() {
      super();
   }

   public Size(int var1, int var2) {
      this();
      this.setColumns(var1);
      this.setRows(var2);
   }

   public int getColumns() {
      return this.cols;
   }

   public void setColumns(int var1) {
      this.cols = (short)var1;
   }

   public int getRows() {
      return this.rows;
   }

   public void setRows(int var1) {
      this.rows = (short)var1;
   }

   public int cursorPos(int var1, int var2) {
      return var1 * (this.cols + 1) + var2;
   }

   public void copy(Size var1) {
      this.setColumns(var1.getColumns());
      this.setRows(var1.getRows());
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Size)) {
         return false;
      } else {
         Size var2 = (Size)var1;
         return this.rows == var2.rows && this.cols == var2.cols;
      }
   }

   public int hashCode() {
      return this.rows * 31 + this.cols;
   }

   public String toString() {
      return "Size[cols=" + this.cols + ", rows=" + this.rows + ']';
   }
}
