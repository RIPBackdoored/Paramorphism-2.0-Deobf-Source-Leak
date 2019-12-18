package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0006\u0010\u000e\u001a\u00020\u0002J\u001e\u0010\u000f\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R%\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\tX\u0086\u000eø\u0001\u0000¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"},
   d2 = {"Lkotlin/coroutines/jvm/internal/RunSuspend;", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "result", "Lkotlin/Result;", "getResult", "()Lkotlin/Result;", "setResult", "(Lkotlin/Result;)V", "await", "resumeWith", "(Ljava/lang/Object;)V", "kotlin-stdlib"}
)
final class RunSuspend implements Continuation {
   @Nullable
   private Result result;

   @NotNull
   public CoroutineContext getContext() {
      return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
   }

   @Nullable
   public final Result getResult() {
      return this.result;
   }

   public final void setResult(@Nullable Result var1) {
      this.result = var1;
   }

   public void resumeWith(@NotNull Object var1) {
      boolean var3 = false;
      boolean var4 = false;
      synchronized(this){}
      boolean var7 = false;

      try {
         var7 = true;
         boolean var5 = false;
         this.result = Result.box-impl(var1);
         if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
         }

         ((Object)this).notifyAll();
         Unit var9 = Unit.INSTANCE;
         var7 = false;
      } finally {
         if (var7) {
            ;
         }
      }

   }

   public final void await() {
      boolean var2 = false;
      boolean var3 = false;
      synchronized(this){}
      boolean var9 = false;

      try {
         var9 = true;
         boolean var4 = false;

         while(true) {
            Result var5 = this.result;
            if (var5 != null) {
               Object var6 = var5.unbox-impl();
               boolean var7 = false;
               ResultKt.throwOnFailure(var6);
               var9 = false;
               return;
            }

            if (this == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
            }

            ((Object)this).wait();
         }
      } finally {
         if (var9) {
            ;
         }
      }
   }

   public RunSuspend() {
      super();
   }
}
