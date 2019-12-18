package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0014¨\u0006\u0015"},
   d2 = {"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringNumberConversionsKt extends StringsKt__StringNumberConversionsJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Byte toByteOrNull(@NotNull String var0) {
      return StringsKt.toByteOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Byte toByteOrNull(@NotNull String var0, int var1) {
      Integer var10000 = StringsKt.toIntOrNull(var0, var1);
      if (var10000 != null) {
         int var2 = var10000;
         return var2 >= -128 && var2 <= 127 ? (byte)var2 : null;
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Short toShortOrNull(@NotNull String var0) {
      return StringsKt.toShortOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Short toShortOrNull(@NotNull String var0, int var1) {
      Integer var10000 = StringsKt.toIntOrNull(var0, var1);
      if (var10000 != null) {
         int var2 = var10000;
         return var2 >= -32768 && var2 <= 32767 ? (short)var2 : null;
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Integer toIntOrNull(@NotNull String var0) {
      return StringsKt.toIntOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Integer toIntOrNull(@NotNull String var0, int var1) {
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         boolean var3 = false;
         boolean var4 = false;
         boolean var5 = false;
         char var6 = var0.charAt(0);
         byte var12;
         int var13;
         if (var6 < '0') {
            if (var2 == 1) {
               return null;
            }

            var12 = 1;
            if (var6 == '-') {
               var4 = true;
               var13 = Integer.MIN_VALUE;
            } else {
               if (var6 != '+') {
                  return null;
               }

               var4 = false;
               var13 = -2147483647;
            }
         } else {
            var12 = 0;
            var4 = false;
            var13 = -2147483647;
         }

         int var7 = var13 / var1;
         int var8 = 0;
         int var9 = var12;
         int var10 = var2 - 1;
         if (var12 <= var10) {
            while(true) {
               int var11 = CharsKt.digitOf(var0.charAt(var9), var1);
               if (var11 < 0) {
                  return null;
               }

               if (var8 < var7) {
                  return null;
               }

               var8 *= var1;
               if (var8 < var13 + var11) {
                  return null;
               }

               var8 -= var11;
               if (var9 == var10) {
                  break;
               }

               ++var9;
            }
         }

         return var4 ? var8 : -var8;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Long toLongOrNull(@NotNull String var0) {
      return StringsKt.toLongOrNull(var0, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Long toLongOrNull(@NotNull String var0, int var1) {
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         boolean var3 = false;
         boolean var4 = false;
         long var5 = 0L;
         char var7 = var0.charAt(0);
         byte var15;
         if (var7 < '0') {
            if (var2 == 1) {
               return null;
            }

            var15 = 1;
            if (var7 == '-') {
               var4 = true;
               var5 = Long.MIN_VALUE;
            } else {
               if (var7 != '+') {
                  return null;
               }

               var4 = false;
               var5 = -9223372036854775807L;
            }
         } else {
            var15 = 0;
            var4 = false;
            var5 = -9223372036854775807L;
         }

         long var8 = var5 / (long)var1;
         long var10 = 0L;
         int var12 = var15;
         int var13 = var2 - 1;
         if (var15 <= var13) {
            while(true) {
               int var14 = CharsKt.digitOf(var0.charAt(var12), var1);
               if (var14 < 0) {
                  return null;
               }

               if (var10 < var8) {
                  return null;
               }

               var10 *= (long)var1;
               if (var10 < var5 + (long)var14) {
                  return null;
               }

               var10 -= (long)var14;
               if (var12 == var13) {
                  break;
               }

               ++var12;
            }
         }

         return var4 ? var10 : -var10;
      }
   }

   @NotNull
   public static final Void numberFormatError(@NotNull String var0) {
      throw (Throwable)(new NumberFormatException("Invalid number format: '" + var0 + '\''));
   }

   public StringsKt__StringNumberConversionsKt() {
      super();
   }
}
