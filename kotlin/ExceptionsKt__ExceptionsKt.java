package kotlin;

import java.io.PrintStream;
import java.io.PrintWriter;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "stackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "addSuppressed", "", "exception", "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "kotlin-stdlib"},
   xs = "kotlin/ExceptionsKt"
)
class ExceptionsKt__ExceptionsKt {
   @InlineOnly
   private static final void printStackTrace(@NotNull Throwable var0) {
      var0.printStackTrace();
   }

   @InlineOnly
   private static final void printStackTrace(@NotNull Throwable var0, PrintWriter var1) {
      var0.printStackTrace(var1);
   }

   @InlineOnly
   private static final void printStackTrace(@NotNull Throwable var0, PrintStream var1) {
      var0.printStackTrace(var1);
   }

   /** @deprecated */
   public static void stackTrace$annotations(Throwable var0) {
   }

   @NotNull
   public static final StackTraceElement[] getStackTrace(@NotNull Throwable var0) {
      StackTraceElement[] var10000 = var0.getStackTrace();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   public static final void addSuppressed(@NotNull Throwable var0, @NotNull Throwable var1) {
      PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(var0, var1);
   }

   public ExceptionsKt__ExceptionsKt() {
      super();
   }
}
