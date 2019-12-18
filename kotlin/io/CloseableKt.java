package kotlin.io;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a;\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\b\n\u0006\b\u0011(\n0\u0001¨\u0006\f"},
   d2 = {"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "CloseableKt"
)
public final class CloseableKt {
   @InlineOnly
   private static final Object use(Closeable var0, Function1 var1) {
      byte var2 = 0;
      Throwable var3 = (Throwable)null;
      boolean var9 = false;

      Object var4;
      try {
         var9 = true;
         var4 = var1.invoke(var0);
         var9 = false;
      } catch (Throwable var11) {
         var3 = var11;
         throw var11;
      } finally {
         if (var9) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               closeFinally(var0, var3);
            } else if (var0 != null) {
               if (var3 == null) {
                  var0.close();
               } else {
                  try {
                     var0.close();
                  } catch (Throwable var10) {
                  }
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         closeFinally(var0, var3);
      } else if (var0 != null) {
         var0.close();
      }

      InlineMarker.finallyEnd(1);
      return var4;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @PublishedApi
   public static final void closeFinally(@Nullable Closeable var0, @Nullable Throwable var1) {
      if (var0 != null) {
         if (var1 == null) {
            var0.close();
         } else {
            try {
               var0.close();
            } catch (Throwable var3) {
               kotlin.ExceptionsKt.addSuppressed(var1, var3);
            }
         }
      }

   }
}
