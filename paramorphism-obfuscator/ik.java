package paramorphism-obfuscator;

import java.io.Closeable;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class ik implements Closeable {
   private long mt;
   private final ij mu;
   private final Instant mv;
   private Instant mw;
   private final String mx;
   private final long my;
   private final int mz;

   public final void a() {
      this.a(1);
   }

   public final void a(int var1) {
      this.mt += (long)var1;
      this.mw = Instant.now();
      if (Duration.between((Temporal)this.mv, (Temporal)this.mw).toMillis() >= (long)this.mz) {
         a(this, false, 1, (Object)null);
      }

   }

   public final void a(long var1) {
      this.mt = var1;
      this.mw = Instant.now();
      if (Duration.between((Temporal)this.mv, (Temporal)this.mw).toMillis() >= (long)this.mz) {
         a(this, false, 1, (Object)null);
      }

   }

   public final void a(boolean var1) {
      if (this.my > 0L) {
         String var2 = this.mx + ": [";
         double var3 = (double)this.mt / (double)this.my;
         String var6 = "%5.2f%%";
         Object[] var7 = new Object[]{(double)100 * var3};
         boolean var8 = false;
         String var5 = String.format(var6, Arrays.copyOf(var7, var7.length));
         var6 = "] " + var5 + " (" + this.mt + " / " + this.my + ')';
         if (var1) {
            var6 = var6 + " (" + Duration.between((Temporal)this.mv, (Temporal)Instant.now()).toMillis() + "ms)";
         }

         this.mu.a(var2, var3, var6);
      }
   }

   public static void a(ik var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.a(var1);
   }

   public void close() {
      this.a(this.my);
      if (this.my != 0L) {
         this.a(true);
         this.mu.a().println();
      }

   }

   public ik(@NotNull String var1, long var2, int var4) {
      super();
      this.mx = var1;
      this.my = var2;
      this.mz = var4;
      this.mu = new ij((PrintStream)null, 1, (DefaultConstructorMarker)null);
      this.mv = Instant.now();
      this.mw = Instant.now();
   }

   public ik(String var1, long var2, int var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         var4 = 16;
      }

      this(var1, var2, var4);
   }
}
