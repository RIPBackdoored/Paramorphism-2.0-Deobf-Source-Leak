package kotlin.coroutines.experimental;

import kotlin.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.functions.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3)
public static final class DefaultImpls
{
    @Nullable
    public static <E extends Element> E get(final Element element, @NotNull final Key<E> key) {
        Element element2;
        if (element.getKey() == key) {
            if ((element2 = element) == null) {
                throw new TypeCastException("null cannot be cast to non-null type E");
            }
        }
        else {
            element2 = null;
        }
        return (E)element2;
    }
    
    public static <R> R fold(final Element element, final R r, @NotNull final Function2<? super R, ? super Element, ? extends R> function2) {
        return (R)function2.invoke(r, element);
    }
    
    @NotNull
    public static CoroutineContext minusKey(final Element element, @NotNull final Key<?> key) {
        return (CoroutineContext)((element.getKey() == key) ? ((EmptyCoroutineContext)EmptyCoroutineContext.INSTANCE) : ((Element)element));
    }
    
    @NotNull
    public static CoroutineContext plus(final Element element, @NotNull final CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(element, coroutineContext);
    }
}
