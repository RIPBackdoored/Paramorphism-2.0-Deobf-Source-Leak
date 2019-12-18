package paramorphism-obfuscator;

import java.io.Closeable;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class il implements Closeable {
   private long na;
   private long nb;
   private final Instant nc;
   private Instant nd;
   private final ij ne;
   private final String nf;
   private final long ng;
   private final int nh;

   public final void a(boolean var1) {
      if (this.ng > 0L) {
         String var2 = this.nf + ": [";
         double var3 = (double)this.nb / (double)this.ng;
         String var6 = "%5.2f%%";
         Object[] var7 = new Object[]{(double)100 * var3};
         boolean var8 = false;
         String var5 = String.format(var6, Arrays.copyOf(var7, var7.length));
         var6 = var1 ? "] 100.00% (" + this.na + " / " + this.na + ") (" + Duration.between((Temporal)this.nc, (Temporal)Instant.now()).toMillis() + "ms)" : "] ~" + var5 + " (" + this.na + " / ????)";
         this.ne.a(var2, var3, var6);
      }
   }

   public static void a(il var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.a(var1);
   }

   public final void a() {
      int var10001 = this.na++;
      this.nd = Instant.now();
      if (Duration.between((Temporal)this.nc, (Temporal)this.nd).toMillis() >= (long)this.nh) {
         a(this, false, 1, (Object)null);
      }

   }

   public final void b() {
      int var10001 = this.nb++;
      this.nd = Instant.now();
      if (Duration.between((Temporal)this.nc, (Temporal)this.nd).toMillis() >= (long)this.nh) {
         a(this, false, 1, (Object)null);
      }

   }

   public void close() {
      this.nb = this.ng;
      this.a(true);
      this.ne.a().println();
   }

   public il(@NotNull String var1, long var2, int var4) {
      super();
      this.nf = var1;
      this.ng = var2;
      this.nh = var4;
      this.nc = Instant.now();
      this.nd = Instant.now();
      this.ne = new ij((PrintStream)null, 1, (DefaultConstructorMarker)null);
   }

   public il(String var1, long var2, int var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         var4 = 16;
      }

      this(var1, var2, var4);
   }
}
