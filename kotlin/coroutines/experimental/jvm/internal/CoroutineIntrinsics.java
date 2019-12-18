package kotlin.coroutines.experimental.jvm.internal;

import kotlin.*;
import kotlin.jvm.*;
import org.jetbrains.annotations.*;
import kotlin.coroutines.experimental.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 2, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\u0007" }, d2 = { "interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines" })
@JvmName(name = "CoroutineIntrinsics")
public final class CoroutineIntrinsics
{
    @NotNull
    public static final <T> Continuation<T> normalizeContinuation(@NotNull final Continuation<? super T> continuation) {
        CoroutineImpl coroutineImpl = (CoroutineImpl)continuation;
        if (!(continuation instanceof CoroutineImpl)) {
            coroutineImpl = null;
        }
        final CoroutineImpl coroutineImpl2 = coroutineImpl;
        Continuation<Object> facade;
        if (coroutineImpl2 == null || (facade = coroutineImpl2.getFacade()) == null) {
            facade = (Continuation<Object>)continuation;
        }
        return (Continuation<T>)facade;
    }
    
    @NotNull
    public static final <T> Continuation<T> interceptContinuationIfNeeded(@NotNull final CoroutineContext coroutineContext, @NotNull final Continuation<? super T> continuation) {
        final ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor)coroutineContext.get((CoroutineContext.Key<CoroutineContext.Element>)ContinuationInterceptor.Key);
        Continuation<? super Object> interceptContinuation;
        if (continuationInterceptor == null || (interceptContinuation = continuationInterceptor.interceptContinuation((Continuation<? super Object>)continuation)) == null) {
            interceptContinuation = (Continuation<? super Object>)continuation;
        }
        return (Continuation<T>)interceptContinuation;
    }
}
