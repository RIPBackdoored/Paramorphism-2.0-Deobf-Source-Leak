package kotlin.coroutines;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005" }, d2 = { "<anonymous>", "Lkotlin/coroutines/CoroutineContext;", "acc", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke" })
static final class CoroutineContext$plus$1 extends Lambda implements Function2<CoroutineContext, Element, CoroutineContext> {
    public static final CoroutineContext$plus$1 INSTANCE;
    
    @Override
    public Object invoke(final Object o, final Object o2) {
        return this.invoke((CoroutineContext)o, (Element)o2);
    }
    
    @NotNull
    @Override
    public final CoroutineContext invoke(@NotNull final CoroutineContext coroutineContext, @NotNull final Element element) {
        final CoroutineContext minusKey = coroutineContext.minusKey(element.getKey());
        Object o;
        if (minusKey == EmptyCoroutineContext.INSTANCE) {
            o = element;
        }
        else {
            final ContinuationInterceptor continuationInterceptor = minusKey.get((CoroutineContext.Key<ContinuationInterceptor>)ContinuationInterceptor.Key);
            CombinedContext combinedContext;
            if (continuationInterceptor == null) {
                combinedContext = new CombinedContext(minusKey, element);
            }
            else {
                final CoroutineContext minusKey2 = minusKey.minusKey((CoroutineContext.Key<?>)ContinuationInterceptor.Key);
                combinedContext = ((minusKey2 == EmptyCoroutineContext.INSTANCE) ? new CombinedContext(element, continuationInterceptor) : new CombinedContext(new CombinedContext(minusKey2, element), continuationInterceptor));
            }
            o = combinedContext;
        }
        return (CoroutineContext)o;
    }
    
    CoroutineContext$plus$1() {
        super(2);
    }
    
    static {
        CoroutineContext$plus$1.INSTANCE = new CoroutineContext$plus$1();
    }
}