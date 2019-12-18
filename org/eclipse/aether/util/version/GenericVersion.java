package org.eclipse.aether.util.version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.aether.version.Version;

final class GenericVersion implements Version {
   private final String version;
   private final GenericVersion$Item[] items;
   private final int hash;

   GenericVersion(String var1) {
      super();
      this.version = var1;
      this.items = parse(var1);
      this.hash = Arrays.hashCode(this.items);
   }

   private static GenericVersion$Item[] parse(String var0) {
      ArrayList var1 = new ArrayList();
      GenericVersion$Tokenizer var2 = new GenericVersion$Tokenizer(var0);

      while(var2.next()) {
         GenericVersion$Item var3 = var2.toItem();
         var1.add(var3);
      }

      trimPadding(var1);
      return (GenericVersion$Item[])var1.toArray(new GenericVersion$Item[var1.size()]);
   }

   private static void trimPadding(List var0) {
      Boolean var1 = null;
      int var2 = var0.size() - 1;

      for(int var3 = var2; var3 > 0; --var3) {
         GenericVersion$Item var4 = (GenericVersion$Item)var0.get(var3);
         if (!Boolean.valueOf(var4.isNumber()).equals(var1)) {
            var2 = var3;
            var1 = var4.isNumber();
         }

         if (var2 == var3 && (var3 == var0.size() - 1 || ((GenericVersion$Item)var0.get(var3 - 1)).isNumber() == var4.isNumber()) && var4.compareTo((GenericVersion$Item)null) == 0) {
            var0.remove(var3);
            --var2;
         }
      }

   }

   public int compareTo(Version var1) {
      GenericVersion$Item[] var2 = this.items;
      GenericVersion$Item[] var3 = ((GenericVersion)var1).items;
      boolean var4 = true;

      for(int var5 = 0; var5 < var2.length || var5 < var3.length; ++var5) {
         if (var5 >= var2.length) {
            return -comparePadding(var3, var5, (Boolean)null);
         }

         if (var5 >= var3.length) {
            return comparePadding(var2, var5, (Boolean)null);
         }

         GenericVersion$Item var6 = var2[var5];
         GenericVersion$Item var7 = var3[var5];
         if (var6.isNumber() != var7.isNumber()) {
            if (var4 == var6.isNumber()) {
               return comparePadding(var2, var5, var4);
            }

            return -comparePadding(var3, var5, var4);
         }

         int var8 = var6.compareTo(var7);
         if (var8 != 0) {
            return var8;
         }

         var4 = var6.isNumber();
      }

      return 0;
   }

   private static int comparePadding(GenericVersion$Item[] var0, int var1, Boolean var2) {
      int var3 = 0;

      for(int var4 = var1; var4 < var0.length; ++var4) {
         GenericVersion$Item var5 = var0[var4];
         if (var2 != null && var2 != var5.isNumber()) {
            break;
         }

         var3 = var5.compareTo((GenericVersion$Item)null);
         if (var3 != 0) {
            break;
         }
      }

      return var3;
   }

   public boolean equals(Object var1) {
      return var1 instanceof GenericVersion && this.compareTo((Version)((GenericVersion)var1)) == 0;
   }

   public int hashCode() {
      return this.hash;
   }

   public String toString() {
      return this.version;
   }

   public int compareTo(Object var1) {
      return this.compareTo((Version)var1);
   }
}
