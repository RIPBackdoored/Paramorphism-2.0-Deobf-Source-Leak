package kotlin.sequences;

import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;
import java.util.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u001d\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f" }, d2 = { "kotlin/sequences/TakeSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "left", "", "getLeft", "()I", "setLeft", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib" })
public static final class TakeSequence$iterator$1 implements Iterator<T>, KMappedMarker {
    private int left = TakeSequence.access$getCount$p(this$0);
    @NotNull
    private final Iterator<T> iterator = TakeSequence.access$getSequence$p(this$0).iterator();
    final TakeSequence this$0;
    
    public final int getLeft() {
        return this.left;
    }
    
    public final void setLeft(final int left) {
        this.left = left;
    }
    
    @NotNull
    public final Iterator<T> getIterator() {
        return (Iterator<T>)this.iterator;
    }
    
    @Override
    public T next() {
        if (this.left == 0) {
            throw new NoSuchElementException();
        }
        this.left--;
        return this.iterator.next();
    }
    
    @Override
    public boolean hasNext() {
        return this.left > 0 && this.iterator.hasNext();
    }
    
    TakeSequence$iterator$1(final TakeSequence this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}