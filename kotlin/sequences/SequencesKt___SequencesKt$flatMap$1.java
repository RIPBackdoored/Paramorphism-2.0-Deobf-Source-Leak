package kotlin.sequences;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import java.util.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\n¢\u0006\u0002\b\u0006" }, d2 = { "<anonymous>", "", "R", "T", "it", "Lkotlin/sequences/Sequence;", "invoke" })
static final class SequencesKt___SequencesKt$flatMap$1 extends Lambda implements Function1<Sequence<? extends R>, Iterator<? extends R>> {
    public static final SequencesKt___SequencesKt$flatMap$1 INSTANCE;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke((Sequence)o);
    }
    
    @NotNull
    @Override
    public final Iterator<R> invoke(@NotNull final Sequence<? extends R> sequence) {
        return (Iterator<R>)sequence.iterator();
    }
    
    SequencesKt___SequencesKt$flatMap$1() {
        super(1);
    }
    
    static {
        SequencesKt___SequencesKt$flatMap$1.INSTANCE = new SequencesKt___SequencesKt$flatMap$1();
    }
}