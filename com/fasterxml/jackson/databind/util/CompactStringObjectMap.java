package com.fasterxml.jackson.databind.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class CompactStringObjectMap implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final CompactStringObjectMap EMPTY = new CompactStringObjectMap(1, 0, new Object[4]);
   private final int _hashMask;
   private final int _spillCount;
   private final Object[] _hashArea;

   private CompactStringObjectMap(int var1, int var2, Object[] var3) {
      super();
      this._hashMask = var1;
      this._spillCount = var2;
      this._hashArea = var3;
   }

   public static CompactStringObjectMap construct(Map var0) {
      if (var0.isEmpty()) {
         return EMPTY;
      } else {
         int var1 = findSize(var0.size());
         int var2 = var1 - 1;
         int var3 = (var1 + (var1 >> 1)) * 2;
         Object[] var4 = new Object[var3];
         int var5 = 0;

         Entry var7;
         int var10;
         for(Iterator var6 = var0.entrySet().iterator(); var6.hasNext(); var4[var10 + 1] = var7.getValue()) {
            var7 = (Entry)var6.next();
            String var8 = (String)var7.getKey();
            int var9 = var8.hashCode() & var2;
            var10 = var9 + var9;
            if (var4[var10] != null) {
               var10 = var1 + (var9 >> 1) << 1;
               if (var4[var10] != null) {
                  var10 = (var1 + (var1 >> 1) << 1) + var5;
                  var5 += 2;
                  if (var10 >= var4.length) {
                     var4 = Arrays.copyOf(var4, var4.length + 4);
                  }
               }
            }

            var4[var10] = var8;
         }

         return new CompactStringObjectMap(var2, var5, var4);
      }
   }

   private static final int findSize(int var0) {
      if (var0 <= 5) {
         return 8;
      } else if (var0 <= 12) {
         return 16;
      } else {
         int var1 = var0 + (var0 >> 2);

         int var2;
         for(var2 = 32; var2 < var1; var2 += var2) {
         }

         return var2;
      }
   }

   public Object find(String var1) {
      int var2 = var1.hashCode() & this._hashMask;
      int var3 = var2 << 1;
      Object var4 = this._hashArea[var3];
      return var4 != var1 && !var1.equals(var4) ? this._find2(var1, var2, var4) : this._hashArea[var3 + 1];
   }

   private final Object _find2(String var1, int var2, Object var3) {
      if (var3 == null) {
         return null;
      } else {
         int var4 = this._hashMask + 1;
         int var5 = var4 + (var2 >> 1) << 1;
         var3 = this._hashArea[var5];
         if (var1.equals(var3)) {
            return this._hashArea[var5 + 1];
         } else {
            if (var3 != null) {
               int var6 = var4 + (var4 >> 1) << 1;

               for(int var7 = var6 + this._spillCount; var6 < var7; var6 += 2) {
                  var3 = this._hashArea[var6];
                  if (var3 == var1 || var1.equals(var3)) {
                     return this._hashArea[var6 + 1];
                  }
               }
            }

            return null;
         }
      }
   }

   public Object findCaseInsensitive(String var1) {
      int var2 = 0;

      for(int var3 = this._hashArea.length; var2 < var3; var2 += 2) {
         Object var4 = this._hashArea[var2];
         if (var4 != null) {
            String var5 = (String)var4;
            if (var5.equalsIgnoreCase(var1)) {
               return this._hashArea[var2 + 1];
            }
         }
      }

      return null;
   }

   public List keys() {
      int var1 = this._hashArea.length;
      ArrayList var2 = new ArrayList(var1 >> 2);

      for(int var3 = 0; var3 < var1; var3 += 2) {
         Object var4 = this._hashArea[var3];
         if (var4 != null) {
            var2.add((String)var4);
         }
      }

      return var2;
   }
}
