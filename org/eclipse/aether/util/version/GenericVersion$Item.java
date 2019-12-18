package org.eclipse.aether.util.version;

import java.math.BigInteger;

final class GenericVersion$Item {
   static final int KIND_MAX = 8;
   static final int KIND_BIGINT = 5;
   static final int KIND_INT = 4;
   static final int KIND_STRING = 3;
   static final int KIND_QUALIFIER = 2;
   static final int KIND_MIN = 0;
   static final GenericVersion$Item MAX = new GenericVersion$Item(8, "max");
   static final GenericVersion$Item MIN = new GenericVersion$Item(0, "min");
   private final int kind;
   private final Object value;

   GenericVersion$Item(int var1, Object var2) {
      super();
      this.kind = var1;
      this.value = var2;
   }

   public boolean isNumber() {
      return (this.kind & 2) == 0;
   }

   public int compareTo(GenericVersion$Item var1) {
      int var2;
      if (var1 == null) {
         switch(this.kind) {
         case 0:
            var2 = -1;
            break;
         case 1:
         case 6:
         case 7:
         default:
            throw new IllegalStateException("unknown version item kind " + this.kind);
         case 2:
         case 4:
            var2 = (Integer)this.value;
            break;
         case 3:
         case 5:
         case 8:
            var2 = 1;
         }
      } else {
         var2 = this.kind - var1.kind;
         if (var2 == 0) {
            switch(this.kind) {
            case 0:
            case 8:
               break;
            case 1:
            case 6:
            case 7:
            default:
               throw new IllegalStateException("unknown version item kind " + this.kind);
            case 2:
            case 4:
               var2 = ((Integer)this.value).compareTo((Integer)var1.value);
               break;
            case 3:
               var2 = ((String)this.value).compareToIgnoreCase((String)var1.value);
               break;
            case 5:
               var2 = ((BigInteger)this.value).compareTo((BigInteger)var1.value);
            }
         }
      }

      return var2;
   }

   public boolean equals(Object var1) {
      return var1 instanceof GenericVersion$Item && this.compareTo((GenericVersion$Item)var1) == 0;
   }

   public int hashCode() {
      return this.value.hashCode() + this.kind * 31;
   }

   public String toString() {
      return String.valueOf(this.value);
   }
}
