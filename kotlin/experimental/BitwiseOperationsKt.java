package kotlin.experimental;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0002\b\u0004\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0000\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0003*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0005\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\f¨\u0006\u0007"},
   d2 = {"and", "", "other", "", "inv", "or", "xor", "kotlin-stdlib"}
)
public final class BitwiseOperationsKt {
   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte and(byte var0, byte var1) {
      byte var2 = 0;
      return (byte)(var0 & var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte or(byte var0, byte var1) {
      byte var2 = 0;
      return (byte)(var0 | var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte xor(byte var0, byte var1) {
      byte var2 = 0;
      return (byte)(var0 ^ var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte inv(byte var0) {
      byte var1 = 0;
      return (byte)(~var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short and(short var0, short var1) {
      byte var2 = 0;
      return (short)(var0 & var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short or(short var0, short var1) {
      byte var2 = 0;
      return (short)(var0 | var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short xor(short var0, short var1) {
      byte var2 = 0;
      return (short)(var0 ^ var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short inv(short var0) {
      byte var1 = 0;
      return (short)(~var0);
   }
}
