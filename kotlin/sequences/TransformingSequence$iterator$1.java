package kotlin.sequences;

import java.util.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0015\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\u000e\u0010\u0007\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t" }, d2 = { "kotlin/sequences/TransformingSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib" })
public static final class TransformingSequence$iterator$1 implements Iterator<R>, KMappedMarker {
    @NotNull
    private final Iterator<T> iterator = TransformingSequence.access$getSequence$p(this$0).iterator();
    final TransformingSequence this$0;
    
    @NotNull
    public final Iterator<T> getIterator() {
        return (Iterator<T>)this.iterator;
    }
    
    @Override
    public R next() {
        return TransformingSequence.access$getTransformer$p(this.this$0).invoke(this.iterator.next());
    }
    
    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }
    
    TransformingSequence$iterator$1(final TransformingSequence this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}