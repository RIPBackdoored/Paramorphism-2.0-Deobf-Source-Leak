package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"},
   d2 = {"kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1", "Lkotlin/coroutines/experimental/Continuation;", "", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}
)
public final class IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1 implements Continuation {
   final Continuation $completion;
   final Function0 $block;

   @NotNull
   public CoroutineContext getContext() {
      return this.$completion.getContext();
   }

   public void resume(@NotNull Unit var1) {
      Continuation var2 = this.$completion;
      boolean var3 = false;

      try {
         Object var4 = this.$block.invoke();
         if (var4 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }

            var2.resume(var4);
         }
      } catch (Throwable var5) {
         var2.resumeWithException(var5);
      }

   }

   public void resume(Object var1) {
      this.resume((Unit)var1);
   }

   public void resumeWithException(@NotNull Throwable var1) {
      this.$completion.resumeWithException(var1);
   }

   public IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1(Continuation var1, Function0 var2) {
      super();
      this.$completion = var1;
      this.$block = var2;
   }
}
