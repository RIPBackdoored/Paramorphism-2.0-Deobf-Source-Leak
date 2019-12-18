package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0006H\u0001\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\fH\u0001\u001a\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000fH\u0001\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u0012H\u0001\u001a\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0015H\u0001\u001a\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0002\u001a\u00020\u0018H\u0001¨\u0006\u0019"},
   d2 = {"boxBoolean", "Ljava/lang/Boolean;", "primitive", "", "boxByte", "Ljava/lang/Byte;", "", "boxChar", "Ljava/lang/Character;", "", "boxDouble", "Ljava/lang/Double;", "", "boxFloat", "Ljava/lang/Float;", "", "boxInt", "Ljava/lang/Integer;", "", "boxLong", "Ljava/lang/Long;", "", "boxShort", "Ljava/lang/Short;", "", "kotlin-stdlib"}
)
@JvmName(
   name = "Boxing"
)
public final class Boxing {
   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Boolean boxBoolean(boolean var0) {
      return var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Byte boxByte(byte var0) {
      return var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Short boxShort(short var0) {
      return new Short(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Integer boxInt(int var0) {
      return new Integer(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Long boxLong(long var0) {
      return new Long(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Float boxFloat(float var0) {
      return new Float(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Double boxDouble(double var0) {
      return new Double(var0);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @NotNull
   public static final Character boxChar(char var0) {
      return new Character(var0);
   }
}
