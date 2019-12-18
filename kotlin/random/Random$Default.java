package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "()V", "Companion", "Lkotlin/random/Random$Companion;", "Companion$annotations", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "kotlin-stdlib"}
)
public final class Random$Default extends Random {
   public int nextBits(int var1) {
      return Random.access$getDefaultRandom$cp().nextBits(var1);
   }

   public int nextInt() {
      return Random.access$getDefaultRandom$cp().nextInt();
   }

   public int nextInt(int var1) {
      return Random.access$getDefaultRandom$cp().nextInt(var1);
   }

   public int nextInt(int var1, int var2) {
      return Random.access$getDefaultRandom$cp().nextInt(var1, var2);
   }

   public long nextLong() {
      return Random.access$getDefaultRandom$cp().nextLong();
   }

   public long nextLong(long var1) {
      return Random.access$getDefaultRandom$cp().nextLong(var1);
   }

   public long nextLong(long var1, long var3) {
      return Random.access$getDefaultRandom$cp().nextLong(var1, var3);
   }

   public boolean nextBoolean() {
      return Random.access$getDefaultRandom$cp().nextBoolean();
   }

   public double nextDouble() {
      return Random.access$getDefaultRandom$cp().nextDouble();
   }

   public double nextDouble(double var1) {
      return Random.access$getDefaultRandom$cp().nextDouble(var1);
   }

   public double nextDouble(double var1, double var3) {
      return Random.access$getDefaultRandom$cp().nextDouble(var1, var3);
   }

   public float nextFloat() {
      return Random.access$getDefaultRandom$cp().nextFloat();
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] var1) {
      return Random.access$getDefaultRandom$cp().nextBytes(var1);
   }

   @NotNull
   public byte[] nextBytes(int var1) {
      return Random.access$getDefaultRandom$cp().nextBytes(var1);
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] var1, int var2, int var3) {
      return Random.access$getDefaultRandom$cp().nextBytes(var1, var2, var3);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use Default companion object instead",
      level = DeprecationLevel.HIDDEN
   )
   public static void Companion$annotations() {
   }

   private Random$Default() {
      super();
   }

   public Random$Default(DefaultConstructorMarker var1) {
      this();
   }
}
