package kotlin.sequences;

import java.util.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0015\n\u0000\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u000e\u0010\t\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u000b" }, d2 = { "kotlin/sequences/MergingSequence$iterator$1", "", "iterator1", "getIterator1", "()Ljava/util/Iterator;", "iterator2", "getIterator2", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib" })
public static final class MergingSequence$iterator$1 implements Iterator<V>, KMappedMarker {
    @NotNull
    private final Iterator<T1> iterator1 = MergingSequence.access$getSequence1$p(this$0).iterator();
    @NotNull
    private final Iterator<T2> iterator2 = MergingSequence.access$getSequence2$p(this$0).iterator();
    final MergingSequence this$0;
    
    @NotNull
    public final Iterator<T1> getIterator1() {
        return (Iterator<T1>)this.iterator1;
    }
    
    @NotNull
    public final Iterator<T2> getIterator2() {
        return (Iterator<T2>)this.iterator2;
    }
    
    @Override
    public V next() {
        return MergingSequence.access$getTransform$p(this.this$0).invoke(this.iterator1.next(), this.iterator2.next());
    }
    
    @Override
    public boolean hasNext() {
        return this.iterator1.hasNext() && this.iterator2.hasNext();
    }
    
    MergingSequence$iterator$1(final MergingSequence this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}