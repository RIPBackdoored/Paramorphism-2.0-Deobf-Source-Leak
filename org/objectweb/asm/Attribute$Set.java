package org.objectweb.asm;

final class Attribute$Set {
   private static final int SIZE_INCREMENT = 6;
   private int size;
   private Attribute[] data = new Attribute[6];

   Attribute$Set() {
      super();
   }

   void addAttributes(Attribute var1) {
      for(Attribute var2 = var1; var2 != null; var2 = var2.nextAttribute) {
         if (!this.contains(var2)) {
            this.add(var2);
         }
      }

   }

   Attribute[] toArray() {
      Attribute[] var1 = new Attribute[this.size];
      System.arraycopy(this.data, 0, var1, 0, this.size);
      return var1;
   }

   private boolean contains(Attribute var1) {
      for(int var2 = 0; var2 < this.size; ++var2) {
         if (this.data[var2].type.equals(var1.type)) {
            return true;
         }
      }

      return false;
   }

   private void add(Attribute var1) {
      if (this.size >= this.data.length) {
         Attribute[] var2 = new Attribute[this.data.length + 6];
         System.arraycopy(this.data, 0, var2, 0, this.size);
         this.data = var2;
      }

      this.data[this.size++] = var1;
   }
}
