package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000(\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0005\u001a5\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0007\u001a\u0019\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0087\b\u001a!\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\bH\u0087\b\u001a\u0019\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\tH\u0087\b\u001a!\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u001a\u0019\u0010\u0000\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\nH\u0087\b\u001a!\u0010\u0000\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\nH\u0087\b\u001a\u0019\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u0019\u0010\u0000\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\fH\u0087\b\u001a!\u0010\u0000\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\b\u001a\u0019\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u001a-\u0010\u000e\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0005\u001a5\u0010\u000e\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007¢\u0006\u0002\u0010\u0007\u001a\u0019\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0087\b\u001a!\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\bH\u0087\b\u001a\u0019\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\tH\u0087\b\u001a!\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u001a\u0019\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\nH\u0087\b\u001a!\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\nH\u0087\b\u001a\u0019\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\fH\u0087\b\u001a!\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\b\u001a\u0019\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b¨\u0006\u000f"},
   d2 = {"maxOf", "T", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "c", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "minOf", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt___ComparisonsJvmKt extends ComparisonsKt__ComparisonsKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable maxOf(@NotNull Comparable var0, @NotNull Comparable var1) {
      return var0.compareTo(var1) >= 0 ? var0 : var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte maxOf(byte var0, byte var1) {
      byte var2 = 0;
      return (byte)Math.max(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short maxOf(short var0, short var1) {
      byte var2 = 0;
      return (short)Math.max(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int maxOf(int var0, int var1) {
      byte var2 = 0;
      return Math.max(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long maxOf(long var0, long var2) {
      byte var4 = 0;
      return Math.max(var0, var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float maxOf(float var0, float var1) {
      byte var2 = 0;
      return Math.max(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double maxOf(double var0, double var2) {
      byte var4 = 0;
      return Math.max(var0, var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable maxOf(@NotNull Comparable var0, @NotNull Comparable var1, @NotNull Comparable var2) {
      return ComparisonsKt.maxOf(var0, ComparisonsKt.maxOf(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte maxOf(byte var0, byte var1, byte var2) {
      byte var3 = 0;
      return (byte)Math.max(var0, Math.max(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short maxOf(short var0, short var1, short var2) {
      byte var3 = 0;
      return (short)Math.max(var0, Math.max(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int maxOf(int var0, int var1, int var2) {
      byte var3 = 0;
      boolean var4 = false;
      int var6 = Math.max(var1, var2);
      boolean var5 = false;
      return Math.max(var0, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long maxOf(long var0, long var2, long var4) {
      byte var6 = 0;
      boolean var7 = false;
      long var10 = Math.max(var2, var4);
      boolean var9 = false;
      return Math.max(var0, var10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float maxOf(float var0, float var1, float var2) {
      byte var3 = 0;
      boolean var4 = false;
      float var6 = Math.max(var1, var2);
      boolean var5 = false;
      return Math.max(var0, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double maxOf(double var0, double var2, double var4) {
      byte var6 = 0;
      boolean var7 = false;
      double var10 = Math.max(var2, var4);
      boolean var9 = false;
      return Math.max(var0, var10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable minOf(@NotNull Comparable var0, @NotNull Comparable var1) {
      return var0.compareTo(var1) <= 0 ? var0 : var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte minOf(byte var0, byte var1) {
      byte var2 = 0;
      return (byte)Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short minOf(short var0, short var1) {
      byte var2 = 0;
      return (short)Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int minOf(int var0, int var1) {
      byte var2 = 0;
      return Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long minOf(long var0, long var2) {
      byte var4 = 0;
      return Math.min(var0, var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float minOf(float var0, float var1) {
      byte var2 = 0;
      return Math.min(var0, var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double minOf(double var0, double var2) {
      byte var4 = 0;
      return Math.min(var0, var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable minOf(@NotNull Comparable var0, @NotNull Comparable var1, @NotNull Comparable var2) {
      return ComparisonsKt.minOf(var0, ComparisonsKt.minOf(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte minOf(byte var0, byte var1, byte var2) {
      byte var3 = 0;
      return (byte)Math.min(var0, Math.min(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short minOf(short var0, short var1, short var2) {
      byte var3 = 0;
      return (short)Math.min(var0, Math.min(var1, var2));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int minOf(int var0, int var1, int var2) {
      byte var3 = 0;
      boolean var4 = false;
      int var6 = Math.min(var1, var2);
      boolean var5 = false;
      return Math.min(var0, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long minOf(long var0, long var2, long var4) {
      byte var6 = 0;
      boolean var7 = false;
      long var10 = Math.min(var2, var4);
      boolean var9 = false;
      return Math.min(var0, var10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final float minOf(float var0, float var1, float var2) {
      byte var3 = 0;
      boolean var4 = false;
      float var6 = Math.min(var1, var2);
      boolean var5 = false;
      return Math.min(var0, var6);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final double minOf(double var0, double var2, double var4) {
      byte var6 = 0;
      boolean var7 = false;
      double var10 = Math.min(var2, var4);
      boolean var9 = false;
      return Math.min(var0, var10);
   }

   public ComparisonsKt___ComparisonsJvmKt() {
      super();
   }
}
