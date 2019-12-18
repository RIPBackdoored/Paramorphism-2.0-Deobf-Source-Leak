package kotlin.coroutines.experimental.intrinsics;

import kotlin.jvm.functions.*;
import kotlin.coroutines.experimental.*;
import org.jetbrains.annotations.*;
import kotlin.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u00005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r¸\u0006\u0000" }, d2 = { "kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1", "Lkotlin/coroutines/experimental/Continuation;", "", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines" })
public static final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2 implements Continuation<Unit> {
    final Continuation $completion;
    final Function2 $this_createCoroutineUnchecked$inlined;
    final Object $receiver$inlined;
    final Continuation $completion$inlined;
    
    public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2(final Continuation $completion, final Function2 $this_createCoroutineUnchecked$inlined, final Object $receiver$inlined, final Continuation $completion$inlined) {
        this.$completion = $completion;
        this.$this_createCoroutineUnchecked$inlined = $this_createCoroutineUnchecked$inlined;
        this.$receiver$inlined = $receiver$inlined;
        this.$completion$inlined = $completion$inlined;
        super();
    }
    
    @NotNull
    @Override
    public CoroutineContext getContext() {
        return this.$completion.getContext();
    }
    
    @Override
    public void resume(@NotNull final Unit unit) {
        final Continuation $completion = this.$completion;
        try {
            final Function2 $this_createCoroutineUnchecked$inlined = this.$this_createCoroutineUnchecked$inlined;
            if ($this_createCoroutineUnchecked$inlined == null) {
                throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
            }
            final Object invoke = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this_createCoroutineUnchecked$inlined, 2)).invoke(this.$receiver$inlined, this.$completion$inlined);
            if (invoke != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                final Continuation continuation = $completion;
                if (continuation == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                }
                continuation.resume(invoke);
            }
        }
        catch (Throwable t) {
            $completion.resumeWithException(t);
        }
    }
    
    @Override
    public void resume(final Object o) {
        this.resume((Unit)o);
    }
    
    @Override
    public void resumeWithException(@NotNull final Throwable t) {
        this.$completion.resumeWithException(t);
    }
}