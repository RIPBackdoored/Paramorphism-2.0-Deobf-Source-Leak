package kotlin.coroutines.experimental.jvm.internal;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\b&\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016J\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016J\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00022\b\u0010\u0016\u001a\u0004\u0018\u00010\u00022\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H$J\u0012\u0010\u0019\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00038\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u00058\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lkotlin/coroutines/experimental/jvm/internal/CoroutineImpl;", "Lkotlin/jvm/internal/Lambda;", "", "Lkotlin/coroutines/experimental/Continuation;", "arity", "", "completion", "(ILkotlin/coroutines/experimental/Continuation;)V", "_context", "Lkotlin/coroutines/experimental/CoroutineContext;", "_facade", "context", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "facade", "getFacade", "()Lkotlin/coroutines/experimental/Continuation;", "label", "create", "", "value", "doResume", "data", "exception", "", "resume", "resumeWithException", "kotlin-stdlib-coroutines"}
)
public abstract class CoroutineImpl extends Lambda implements Continuation {
   @JvmField
   protected int label;
   private final CoroutineContext _context;
   private Continuation _facade;
   @JvmField
   @Nullable
   protected Continuation completion;

   @NotNull
   public CoroutineContext getContext() {
      CoroutineContext var10000 = this._context;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   @NotNull
   public final Continuation getFacade() {
      if (this._facade == null) {
         CoroutineContext var10001 = this._context;
         if (var10001 == null) {
            Intrinsics.throwNpe();
         }

         this._facade = CoroutineIntrinsics.interceptContinuationIfNeeded(var10001, (Continuation)this);
      }

      Continuation var10000 = this._facade;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   public void resume(@Nullable Object var1) {
      Continuation var10000 = this.completion;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Continuation var2 = var10000;
      boolean var3 = false;

      try {
         boolean var4 = false;
         Object var6 = this.doResume(var1, (Throwable)null);
         if (var6 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }

            var2.resume(var6);
         }
      } catch (Throwable var5) {
         var2.resumeWithException(var5);
      }

   }

   public void resumeWithException(@NotNull Throwable var1) {
      Continuation var10000 = this.completion;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Continuation var2 = var10000;
      boolean var3 = false;

      try {
         boolean var4 = false;
         Object var6 = this.doResume((Object)null, var1);
         if (var6 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }

            var2.resume(var6);
         }
      } catch (Throwable var5) {
         var2.resumeWithException(var5);
      }

   }

   @Nullable
   protected abstract Object doResume(@Nullable Object var1, @Nullable Throwable var2);

   @NotNull
   public Continuation create(@NotNull Continuation var1) {
      throw (Throwable)(new IllegalStateException("create(Continuation) has not been overridden"));
   }

   @NotNull
   public Continuation create(@Nullable Object var1, @NotNull Continuation var2) {
      throw (Throwable)(new IllegalStateException("create(Any?;Continuation) has not been overridden"));
   }

   public CoroutineImpl(int var1, @Nullable Continuation var2) {
      super(var1);
      this.completion = var2;
      this.label = this.completion != null ? 0 : -1;
      Continuation var10001 = this.completion;
      this._context = var10001 != null ? var10001.getContext() : null;
   }
}
