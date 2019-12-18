package kotlin.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u001a \u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a \u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0001\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0002¨\u0006\u000b"},
   d2 = {"differenceModulo", "", "a", "b", "c", "", "getProgressionLastElement", "start", "end", "step", "mod", "kotlin-stdlib"}
)
public final class ProgressionUtilKt {
   private static final int mod(int var0, int var1) {
      int var2 = var0 % var1;
      return var2 >= 0 ? var2 : var2 + var1;
   }

   private static final long mod(long var0, long var2) {
      long var4 = var0 % var2;
      return var4 >= 0L ? var4 : var4 + var2;
   }

   private static final int differenceModulo(int var0, int var1, int var2) {
      return mod(mod(var0, var2) - mod(var1, var2), var2);
   }

   private static final long differenceModulo(long var0, long var2, long var4) {
      return mod(mod(var0, var4) - mod(var2, var4), var4);
   }

   @PublishedApi
   public static final int getProgressionLastElement(int var0, int var1, int var2) {
      int var10000;
      if (var2 > 0) {
         var10000 = var0 >= var1 ? var1 : var1 - differenceModulo(var1, var0, var2);
      } else {
         if (var2 >= 0) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         var10000 = var0 <= var1 ? var1 : var1 + differenceModulo(var0, var1, -var2);
      }

      return var10000;
   }

   @PublishedApi
   public static final long getProgressionLastElement(long var0, long var2, long var4) {
      long var10000;
      if (var4 > 0L) {
         var10000 = var0 >= var2 ? var2 : var2 - differenceModulo(var2, var0, var4);
      } else {
         if (var4 >= 0L) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         var10000 = var0 <= var2 ? var2 : var2 + differenceModulo(var0, var2, -var4);
      }

      return var10000;
   }
}
