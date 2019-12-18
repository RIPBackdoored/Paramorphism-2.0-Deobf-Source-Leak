package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result$Companion;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b!\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u00032\u00020\u0004B\u0017\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\u0002\u0010\u0006J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013H$ø\u0001\u0000¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\rH\u0014J\u001e\u0010\u0016\u001a\u00020\r2\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"},
   d2 = {"Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Ljava/io/Serializable;", "completion", "(Lkotlin/coroutines/Continuation;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCompletion", "()Lkotlin/coroutines/Continuation;", "create", "", "value", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "resumeWith", "(Ljava/lang/Object;)V", "toString", "", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public abstract class BaseContinuationImpl implements Continuation, CoroutineStackFrame, Serializable {
   @Nullable
   private final Continuation completion;

   public final void resumeWith(@NotNull Object var1) {
      BaseContinuationImpl var2 = (BaseContinuationImpl)this;
      Object var3 = var1;

      while(true) {
         DebugProbesKt.probeCoroutineResumed((Continuation)var2);
         boolean var5 = false;
         boolean var6 = false;
         BaseContinuationImpl var7 = var2;
         boolean var8 = false;
         Continuation var10000 = var2.completion;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         Continuation var9 = var10000;

         Object var10;
         try {
            var10 = var7.invokeSuspend(var3);
            if (var10 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               return;
            }

            Result$Companion var11 = Result.Companion;
            boolean var16 = false;
            var10 = Result.constructor-impl(var10);
         } catch (Throwable var15) {
            Result$Companion var12 = Result.Companion;
            boolean var13 = false;
            var10 = Result.constructor-impl(ResultKt.createFailure(var15));
         }

         var2.releaseIntercepted();
         if (!(var9 instanceof BaseContinuationImpl)) {
            var9.resumeWith(var10);
            return;
         }

         var2 = (BaseContinuationImpl)var9;
         var3 = var10;
      }
   }

   @Nullable
   protected abstract Object invokeSuspend(@NotNull Object var1);

   protected void releaseIntercepted() {
   }

   @NotNull
   public Continuation create(@NotNull Continuation var1) {
      throw (Throwable)(new UnsupportedOperationException("create(Continuation) has not been overridden"));
   }

   @NotNull
   public Continuation create(@Nullable Object var1, @NotNull Continuation var2) {
      throw (Throwable)(new UnsupportedOperationException("create(Any?;Continuation) has not been overridden"));
   }

   @NotNull
   public String toString() {
      StringBuilder var10000 = (new StringBuilder()).append("Continuation at ");
      StackTraceElement var10001 = this.getStackTraceElement();
      return var10000.append(var10001 != null ? (Serializable)var10001 : (Serializable)this.getClass().getName()).toString();
   }

   @Nullable
   public CoroutineStackFrame getCallerFrame() {
      Continuation var10000 = this.completion;
      if (!(var10000 instanceof CoroutineStackFrame)) {
         var10000 = null;
      }

      return (CoroutineStackFrame)var10000;
   }

   @Nullable
   public StackTraceElement getStackTraceElement() {
      return DebugMetadataKt.getStackTraceElement(this);
   }

   @Nullable
   public final Continuation getCompletion() {
      return this.completion;
   }

   public BaseContinuationImpl(@Nullable Continuation var1) {
      super();
      this.completion = var1;
   }
}
