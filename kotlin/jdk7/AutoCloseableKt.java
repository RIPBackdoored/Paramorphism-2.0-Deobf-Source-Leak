package kotlin.jdk7;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a8\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\b¢\u0006\u0002\u0010\n¨\u0006\u000b"},
   d2 = {"closeFinally", "", "Ljava/lang/AutoCloseable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"},
   pn = "kotlin"
)
@JvmName(
   name = "AutoCloseableKt"
)
public final class AutoCloseableKt {
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final Object use(AutoCloseable var0, Function1 var1) {
      byte var2 = 0;
      Throwable var3 = (Throwable)null;
      boolean var7 = false;

      Object var4;
      try {
         var7 = true;
         var4 = var1.invoke(var0);
         var7 = false;
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         if (var7) {
            InlineMarker.finallyStart(1);
            closeFinally(var0, var3);
            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      closeFinally(var0, var3);
      InlineMarker.finallyEnd(1);
      return var4;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @PublishedApi
   public static final void closeFinally(@Nullable AutoCloseable var0, @Nullable Throwable var1) {
      if (var0 != null) {
         if (var1 == null) {
            var0.close();
         } else {
            try {
               var0.close();
            } catch (Throwable var3) {
               var1.addSuppressed(var3);
            }
         }
      }

   }
}
