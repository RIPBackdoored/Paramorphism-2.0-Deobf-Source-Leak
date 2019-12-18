package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014ø\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\t"},
   d2 = {"kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1", "Lkotlin/coroutines/jvm/internal/RestrictedContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1 extends RestrictedContinuationImpl {
   private int label;
   final Function1 $block;
   final Continuation $completion;

   @Nullable
   protected Object invokeSuspend(@NotNull Object var1) {
      Object var10000;
      boolean var3;
      switch(this.label) {
      case 0:
         this.label = 1;
         var3 = false;
         ResultKt.throwOnFailure(var1);
         var10000 = this.$block.invoke(this);
         break;
      case 1:
         this.label = 2;
         var3 = false;
         ResultKt.throwOnFailure(var1);
         var10000 = var1;
         break;
      default:
         String var2 = "This coroutine had already completed";
         var3 = false;
         throw (Throwable)(new IllegalStateException(var2.toString()));
      }

      return var10000;
   }

   public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1(Function1 var1, Continuation var2, Continuation var3) {
      super(var3);
      this.$block = var1;
      this.$completion = var2;
   }
}
