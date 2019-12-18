package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0004\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a=\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a*\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a=\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n¨\u0006\u0010"},
   d2 = {"check", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "checkNotNull", "T", "(Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "message", "require", "requireNotNull", "kotlin-stdlib"},
   xs = "kotlin/PreconditionsKt"
)
class PreconditionsKt__PreconditionsKt extends PreconditionsKt__AssertionsJVMKt {
   @InlineOnly
   private static final void require(boolean var0) {
      byte var1 = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (!var0) {
         boolean var4 = false;
         String var5 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   @InlineOnly
   private static final void require(boolean var0, Function0 var1) {
      byte var2 = 0;
      boolean var3 = false;
      if (!var0) {
         Object var4 = var1.invoke();
         throw (Throwable)(new IllegalArgumentException(var4.toString()));
      }
   }

   @InlineOnly
   private static final Object requireNotNull(Object var0) {
      byte var1 = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (var0 == null) {
         boolean var4 = false;
         String var5 = "Required value was null.";
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      } else {
         return var0;
      }
   }

   @InlineOnly
   private static final Object requireNotNull(Object var0, Function0 var1) {
      byte var2 = 0;
      boolean var3 = false;
      if (var0 == null) {
         Object var4 = var1.invoke();
         throw (Throwable)(new IllegalArgumentException(var4.toString()));
      } else {
         return var0;
      }
   }

   @InlineOnly
   private static final void check(boolean var0) {
      byte var1 = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (!var0) {
         boolean var4 = false;
         String var5 = "Check failed.";
         throw (Throwable)(new IllegalStateException(var5.toString()));
      }
   }

   @InlineOnly
   private static final void check(boolean var0, Function0 var1) {
      byte var2 = 0;
      boolean var3 = false;
      if (!var0) {
         Object var4 = var1.invoke();
         throw (Throwable)(new IllegalStateException(var4.toString()));
      }
   }

   @InlineOnly
   private static final Object checkNotNull(Object var0) {
      byte var1 = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (var0 == null) {
         boolean var4 = false;
         String var5 = "Required value was null.";
         throw (Throwable)(new IllegalStateException(var5.toString()));
      } else {
         return var0;
      }
   }

   @InlineOnly
   private static final Object checkNotNull(Object var0, Function0 var1) {
      byte var2 = 0;
      boolean var3 = false;
      if (var0 == null) {
         Object var4 = var1.invoke();
         throw (Throwable)(new IllegalStateException(var4.toString()));
      } else {
         return var0;
      }
   }

   @InlineOnly
   private static final Void error(Object var0) {
      byte var1 = 0;
      throw (Throwable)(new IllegalStateException(var0.toString()));
   }

   public PreconditionsKt__PreconditionsKt() {
      super();
   }
}
