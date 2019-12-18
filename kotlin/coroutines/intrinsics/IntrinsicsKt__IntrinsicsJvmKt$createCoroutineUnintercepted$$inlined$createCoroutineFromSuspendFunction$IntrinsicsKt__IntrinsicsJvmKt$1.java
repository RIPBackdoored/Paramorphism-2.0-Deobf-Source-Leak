package kotlin.coroutines.intrinsics;

import kotlin.coroutines.jvm.internal.*;
import kotlin.coroutines.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import kotlin.jvm.internal.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014\u00f8\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\t¸\u0006\u0000" }, d2 = { "kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1", "Lkotlin/coroutines/jvm/internal/RestrictedContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib" })
public static final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1 extends RestrictedContinuationImpl {
    private int label;
    final Continuation $completion;
    final Function1 $this_createCoroutineUnintercepted$inlined;
    
    public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1(final Continuation $completion, final Continuation continuation, final Function1 $this_createCoroutineUnintercepted$inlined) {
        this.$completion = $completion;
        this.$this_createCoroutineUnintercepted$inlined = $this_createCoroutineUnintercepted$inlined;
        super(continuation);
    }
    
    @Nullable
    @Override
    protected Object invokeSuspend(@NotNull final Object o) {
        Object invoke = null;
        switch (this.label) {
            case 0: {
                this.label = 1;
                ResultKt.throwOnFailure(o);
                final RestrictedContinuationImpl restrictedContinuationImpl = this;
                final Function1 $this_createCoroutineUnintercepted$inlined = this.$this_createCoroutineUnintercepted$inlined;
                if ($this_createCoroutineUnintercepted$inlined == null) {
                    throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                }
                invoke = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this_createCoroutineUnintercepted$inlined, 1)).invoke(restrictedContinuationImpl);
                break;
            }
            case 1: {
                this.label = 2;
                ResultKt.throwOnFailure(o);
                invoke = o;
                break;
            }
            default: {
                throw new IllegalStateException("This coroutine had already completed".toString());
            }
        }
        return invoke;
    }
}