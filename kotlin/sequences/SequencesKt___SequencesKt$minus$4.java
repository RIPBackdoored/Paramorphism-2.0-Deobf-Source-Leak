package kotlin.sequences;

import kotlin.*;
import kotlin.jvm.functions.*;
import java.util.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004" }, d2 = { "kotlin/sequences/SequencesKt___SequencesKt$minus$4", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib" })
public static final class SequencesKt___SequencesKt$minus$4 implements Sequence<T> {
    final Sequence $this_minus;
    final Sequence $elements;
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        final HashSet<Object> hashSet = SequencesKt___SequencesKt.toHashSet((Sequence<?>)this.$elements);
        if (hashSet.isEmpty()) {
            return this.$this_minus.iterator();
        }
        return SequencesKt___SequencesKt.filterNot((Sequence<?>)this.$this_minus, (Function1<? super Object, Boolean>)new SequencesKt___SequencesKt$minus$4$iterator.SequencesKt___SequencesKt$minus$4$iterator$1((HashSet)hashSet)).iterator();
    }
    
    SequencesKt___SequencesKt$minus$4(final Sequence<? extends T> $this_minus, final Sequence $elements) {
        this.$this_minus = $this_minus;
        this.$elements = $elements;
        super();
    }
}