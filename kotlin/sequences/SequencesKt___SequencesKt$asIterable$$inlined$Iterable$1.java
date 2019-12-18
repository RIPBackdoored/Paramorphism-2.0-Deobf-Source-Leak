package kotlin.sequences;

import kotlin.jvm.internal.markers.*;
import kotlin.*;
import java.util.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0011\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000" }, d2 = { "kotlin/collections/CollectionsKt__IterablesKt$Iterable$1", "", "iterator", "", "kotlin-stdlib" })
public static final class SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 implements Iterable<T>, KMappedMarker {
    final Sequence $this_asIterable$inlined;
    
    public SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(final Sequence $this_asIterable$inlined) {
        this.$this_asIterable$inlined = $this_asIterable$inlined;
        super();
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.$this_asIterable$inlined.iterator();
    }
}