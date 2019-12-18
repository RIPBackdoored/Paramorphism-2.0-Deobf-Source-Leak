package kotlin.sequences;

import kotlin.jvm.internal.markers.*;
import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import java.util.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\t\u0010\u0010\u001a\u00020\u0011H\u0096\u0002J\u000e\u0010\u0012\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0004R\u001e\u0010\u0002\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0007\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0013" }, d2 = { "kotlin/sequences/GeneratorSequence$iterator$1", "", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib" })
public static final class GeneratorSequence$iterator$1 implements Iterator<T>, KMappedMarker {
    @Nullable
    private T nextItem;
    private int nextState = -2;
    final GeneratorSequence this$0;
    
    @Nullable
    public final T getNextItem() {
        return (T)this.nextItem;
    }
    
    public final void setNextItem(@Nullable final T nextItem) {
        this.nextItem = nextItem;
    }
    
    public final int getNextState() {
        return this.nextState;
    }
    
    public final void setNextState(final int nextState) {
        this.nextState = nextState;
    }
    
    private final void calcNext() {
        Object nextItem;
        if (this.nextState == -2) {
            nextItem = GeneratorSequence.access$getGetInitialValue$p(this.this$0).invoke();
        }
        else {
            final Function1 access$getGetNextValue$p = GeneratorSequence.access$getGetNextValue$p(this.this$0);
            final Object nextItem2 = this.nextItem;
            if (nextItem2 == null) {
                Intrinsics.throwNpe();
            }
            nextItem = access$getGetNextValue$p.invoke(nextItem2);
        }
        this.nextItem = nextItem;
        this.nextState = ((this.nextItem != null) ? 1 : 0);
    }
    
    @NotNull
    @Override
    public T next() {
        if (this.nextState < 0) {
            this.calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        final Object nextItem = this.nextItem;
        if (nextItem == null) {
            throw new TypeCastException("null cannot be cast to non-null type T");
        }
        final T t = (T)nextItem;
        this.nextState = -1;
        return t;
    }
    
    @Override
    public boolean hasNext() {
        if (this.nextState < 0) {
            this.calcNext();
        }
        return this.nextState == 1;
    }
    
    GeneratorSequence$iterator$1(final GeneratorSequence this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}