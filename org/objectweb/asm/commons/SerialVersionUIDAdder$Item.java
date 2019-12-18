package org.objectweb.asm.commons;

final class SerialVersionUIDAdder$Item implements Comparable {
   final String name;
   final int access;
   final String descriptor;

   SerialVersionUIDAdder$Item(String var1, int var2, String var3) {
      super();
      this.name = var1;
      this.access = var2;
      this.descriptor = var3;
   }

   public int compareTo(SerialVersionUIDAdder$Item var1) {
      int var2 = this.name.compareTo(var1.name);
      if (var2 == 0) {
         var2 = this.descriptor.compareTo(var1.descriptor);
      }

      return var2;
   }

   public boolean equals(Object var1) {
      if (var1 instanceof SerialVersionUIDAdder$Item) {
         return this.compareTo((SerialVersionUIDAdder$Item)var1) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode() ^ this.descriptor.hashCode();
   }

   public int compareTo(Object var1) {
      return this.compareTo((SerialVersionUIDAdder$Item)var1);
   }
}
