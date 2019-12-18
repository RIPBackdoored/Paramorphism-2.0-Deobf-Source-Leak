package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u001e\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0010R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"},
   d2 = {"Lkotlin/coroutines/experimental/migration/ContinuationMigration;", "T", "Lkotlin/coroutines/Continuation;", "continuation", "Lkotlin/coroutines/experimental/Continuation;", "(Lkotlin/coroutines/experimental/Continuation;)V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getContinuation", "()Lkotlin/coroutines/experimental/Continuation;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib-coroutines"}
)
final class ContinuationMigration implements Continuation {
   @NotNull
   private final CoroutineContext context;
   @NotNull
   private final kotlin.coroutines.experimental.Continuation continuation;

   @NotNull
   public CoroutineContext getContext() {
      return this.context;
   }

   public void resumeWith(@NotNull Object var1) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6;
      if (Result.isSuccess-impl(var1)) {
         var6 = false;
         this.continuation.resume(var1);
      }

      var3 = false;
      var4 = false;
      Throwable var10000 = Result.exceptionOrNull-impl(var1);
      if (var10000 != null) {
         Throwable var11 = var10000;
         boolean var5 = false;
         var6 = false;
         boolean var8 = false;
         boolean var10 = false;
         this.continuation.resumeWithException(var11);
      }

   }

   @NotNull
   public final kotlin.coroutines.experimental.Continuation getContinuation() {
      return this.continuation;
   }

   public ContinuationMigration(@NotNull kotlin.coroutines.experimental.Continuation var1) {
      super();
      this.continuation = var1;
      this.context = CoroutinesMigrationKt.toCoroutineContext(this.continuation.getContext());
   }
}
