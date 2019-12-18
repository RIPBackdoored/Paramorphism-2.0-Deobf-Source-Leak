package kotlin.sequences;

import java.util.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;
import kotlin.collections.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u001f\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f" }, d2 = { "kotlin/sequences/IndexingSequence$iterator$1", "", "Lkotlin/collections/IndexedValue;", "index", "", "getIndex", "()I", "setIndex", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "kotlin-stdlib" })
public static final class IndexingSequence$iterator$1 implements Iterator<IndexedValue<? extends T>>, KMappedMarker {
    @NotNull
    private final Iterator<T> iterator = IndexingSequence.access$getSequence$p(this$0).iterator();
    private int index;
    final IndexingSequence this$0;
    
    @NotNull
    public final Iterator<T> getIterator() {
        return (Iterator<T>)this.iterator;
    }
    
    public final int getIndex() {
        return this.index;
    }
    
    public final void setIndex(final int index) {
        this.index = index;
    }
    
    @NotNull
    @Override
    public IndexedValue<T> next() {
        final int index;
        this.index = (index = this.index) + 1;
        final int n = index;
        if (n < 0) {
            CollectionsKt.throwIndexOverflow();
        }
        return new IndexedValue<T>(n, this.iterator.next());
    }
    
    @Override
    public Object next() {
        return this.next();
    }
    
    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }
    
    IndexingSequence$iterator$1(final IndexingSequence this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}