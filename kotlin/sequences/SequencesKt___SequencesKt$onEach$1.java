package kotlin.sequences;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004" }, d2 = { "<anonymous>", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;" })
static final class SequencesKt___SequencesKt$onEach$1 extends Lambda implements Function1<T, T> {
    final Function1 $action;
    
    @Override
    public final T invoke(final T t) {
        this.$action.invoke(t);
        return t;
    }
    
    SequencesKt___SequencesKt$onEach$1(final Function1 $action) {
        this.$action = $action;
        super(1);
    }
}