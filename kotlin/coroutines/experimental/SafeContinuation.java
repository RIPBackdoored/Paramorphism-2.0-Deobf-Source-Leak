package kotlin.coroutines.experimental;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0001\u0018\u0000 \u0015*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002\u0015\u0016B\u0015\b\u0011\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0004B\u001f\b\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\n\u0010\r\u001a\u0004\u0018\u00010\u0006H\u0001J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lkotlin/coroutines/experimental/SafeContinuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "delegate", "(Lkotlin/coroutines/experimental/Continuation;)V", "initialResult", "", "(Lkotlin/coroutines/experimental/Continuation;Ljava/lang/Object;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "result", "getResult", "resume", "", "value", "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "Companion", "Fail", "kotlin-stdlib-coroutines"}
)
@PublishedApi
public final class SafeContinuation implements Continuation {
   private Object result;
   private final Continuation delegate;
   private static final Object UNDECIDED = new Object();
   private static final Object RESUMED = new Object();
   private static final AtomicReferenceFieldUpdater RESULT = AtomicReferenceFieldUpdater.newUpdater(SafeContinuation.class, Object.class, "result");
   public static final SafeContinuation$Companion Companion = new SafeContinuation$Companion((DefaultConstructorMarker)null);

   @NotNull
   public CoroutineContext getContext() {
      return this.delegate.getContext();
   }

   public void resume(Object var1) {
      while(true) {
         Object var2 = this.result;
         if (var2 == UNDECIDED) {
            if (RESULT.compareAndSet(this, UNDECIDED, var1)) {
               return;
            }
         } else {
            if (var2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               if (!RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), RESUMED)) {
                  continue;
               }

               this.delegate.resume(var1);
               return;
            }

            throw (Throwable)(new IllegalStateException("Already resumed"));
         }
      }
   }

   public void resumeWithException(@NotNull Throwable var1) {
      while(true) {
         Object var2 = this.result;
         if (var2 == UNDECIDED) {
            if (RESULT.compareAndSet(this, UNDECIDED, new SafeContinuation$Fail(var1))) {
               return;
            }
         } else {
            if (var2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               if (!RESULT.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), RESUMED)) {
                  continue;
               }

               this.delegate.resumeWithException(var1);
               return;
            }

            throw (Throwable)(new IllegalStateException("Already resumed"));
         }
      }
   }

   @PublishedApi
   @Nullable
   public final Object getResult() {
      Object var1 = this.result;
      if (var1 == UNDECIDED) {
         if (RESULT.compareAndSet(this, UNDECIDED, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
         }

         var1 = this.result;
      }

      if (var1 == RESUMED) {
         return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else if (var1 instanceof SafeContinuation$Fail) {
         throw ((SafeContinuation$Fail)var1).getException();
      } else {
         return var1;
      }
   }

   public SafeContinuation(@NotNull Continuation var1, @Nullable Object var2) {
      super();
      this.delegate = var1;
      this.result = var2;
   }

   @PublishedApi
   public SafeContinuation(@NotNull Continuation var1) {
      this(var1, UNDECIDED);
   }
}
