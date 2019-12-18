package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext$Element;
import kotlin.coroutines.CoroutineContext$Key;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b!\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005B!\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J\b\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "completion", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "_context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "intercepted", "releaseIntercepted", "", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public abstract class ContinuationImpl extends BaseContinuationImpl {
   private transient Continuation intercepted;
   private final CoroutineContext _context;

   @NotNull
   public CoroutineContext getContext() {
      CoroutineContext var10000 = this._context;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   @NotNull
   public final Continuation intercepted() {
      Continuation var10000 = this.intercepted;
      if (var10000 == null) {
         label15: {
            ContinuationInterceptor var6 = (ContinuationInterceptor)this.getContext().get((CoroutineContext$Key)ContinuationInterceptor.Key);
            if (var6 != null) {
               var10000 = var6.interceptContinuation((Continuation)this);
               if (var10000 != null) {
                  break label15;
               }
            }

            var10000 = (Continuation)this;
         }

         Continuation var1 = var10000;
         boolean var2 = false;
         boolean var3 = false;
         boolean var5 = false;
         this.intercepted = var1;
         var10000 = var1;
      }

      return var10000;
   }

   protected void releaseIntercepted() {
      Continuation var1 = this.intercepted;
      if (var1 != null && var1 != (ContinuationImpl)this) {
         CoroutineContext$Element var10000 = this.getContext().get((CoroutineContext$Key)ContinuationInterceptor.Key);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         ((ContinuationInterceptor)var10000).releaseInterceptedContinuation(var1);
      }

      this.intercepted = (Continuation)CompletedContinuation.INSTANCE;
   }

   public ContinuationImpl(@Nullable Continuation var1, @Nullable CoroutineContext var2) {
      super(var1);
      this._context = var2;
   }

   public ContinuationImpl(@Nullable Continuation var1) {
      this(var1, var1 != null ? var1.getContext() : null);
   }
}
