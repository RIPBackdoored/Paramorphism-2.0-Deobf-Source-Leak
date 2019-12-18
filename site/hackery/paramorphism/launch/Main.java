package site.hackery.paramorphism.launch;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.jg;
import paramorphism-obfuscator.lh;
import paramorphism-obfuscator.mk;
import site.hackery.paramorphism.api.config.ParamorphismConfig;

@JvmName(
   name = "Main"
)
public final class Main {
   public static final void main(@NotNull String[] var0) {
      boolean var2 = false;
      if (var0.length == 0) {
         lh.tv.a("Usage: paramorphism.jar [config]");
      } else {
         try {
            Instant var1 = Instant.now();
            jg var12 = new jg(new File(ArraysKt.joinToString$default(var0, (CharSequence)" ", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null)));
            runParamorphism((ParamorphismConfig)var12);
            double var3 = (double)Duration.between((Temporal)var1, (Temporal)Instant.now()).toMillis() / 1000.0D;
            lh var10000 = lh.tv;
            StringBuilder var10001 = (new StringBuilder()).append("Completed in ");
            String var5 = "%.3f";
            Object[] var6 = new Object[]{var3};
            StringBuilder var9 = var10001;
            lh var8 = var10000;
            boolean var7 = false;
            String var10 = String.format(var5, Arrays.copyOf(var6, var6.length));
            var8.a(var9.append(var10).append("s.").toString());
         } catch (Exception var11) {
            lh.tv.b("An error occurred during obfuscation: " + var11.getMessage());
            var11.printStackTrace();
         }

      }
   }

   public static final void runParamorphism(@NotNull ParamorphismConfig var0) {
      mk.a(var0);
   }
}
