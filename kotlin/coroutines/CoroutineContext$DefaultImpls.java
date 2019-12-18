package kotlin.coroutines;

import kotlin.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.functions.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3)
public static final class DefaultImpls
{
    @NotNull
    public static CoroutineContext plus(final CoroutineContext coroutineContext, @NotNull final CoroutineContext coroutineContext2) {
        return (coroutineContext2 == EmptyCoroutineContext.INSTANCE) ? coroutineContext : coroutineContext2.fold(coroutineContext, (Function2<? super CoroutineContext, ? super Element, ? extends CoroutineContext>)CoroutineContext$plus.CoroutineContext$plus$1.INSTANCE);
    }
}
