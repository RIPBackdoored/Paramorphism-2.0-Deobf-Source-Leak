package org.jline.utils;

import java.util.LinkedList;
import java.util.List;

public class DiffHelper {
   public DiffHelper() {
      super();
   }

   public static List diff(AttributedString var0, AttributedString var1) {
      int var2 = var0.length();
      int var3 = var1.length();
      int var4 = Math.min(var2, var3);
      int var5 = 0;

      int var6;
      for(var6 = -1; var5 < var4 && var0.charAt(var5) == var1.charAt(var5) && var0.styleAt(var5).equals(var1.styleAt(var5)); ++var5) {
         if (var0.isHidden(var5)) {
            if (var6 < 0) {
               var6 = var5;
            }
         } else {
            var6 = -1;
         }
      }

      if (var6 >= 0 && (var2 > var5 && var0.isHidden(var5) || var3 > var5 && var1.isHidden(var5))) {
         var5 = var6;
      }

      var6 = -1;

      int var7;
      for(var7 = 0; var7 < var4 - var5 && var0.charAt(var2 - var7 - 1) == var1.charAt(var3 - var7 - 1) && var0.styleAt(var2 - var7 - 1).equals(var1.styleAt(var3 - var7 - 1)); ++var7) {
         if (var0.isHidden(var2 - var7 - 1)) {
            if (var6 < 0) {
               var6 = var7;
            }
         } else {
            var6 = -1;
         }
      }

      if (var6 >= 0) {
         var7 = var6;
      }

      LinkedList var8 = new LinkedList();
      if (var5 > 0) {
         var8.add(new DiffHelper$Diff(DiffHelper$Operation.EQUAL, var0.subSequence(0, var5)));
      }

      if (var3 > var5 + var7) {
         var8.add(new DiffHelper$Diff(DiffHelper$Operation.INSERT, var1.subSequence(var5, var3 - var7)));
      }

      if (var2 > var5 + var7) {
         var8.add(new DiffHelper$Diff(DiffHelper$Operation.DELETE, var0.subSequence(var5, var2 - var7)));
      }

      if (var7 > 0) {
         var8.add(new DiffHelper$Diff(DiffHelper$Operation.EQUAL, var0.subSequence(var2 - var7, var2)));
      }

      return var8;
   }
}
