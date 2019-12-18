package kotlin.collections;

import java.util.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0005\b'\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005�\u0006\u0002\u0010\u0003J\f\u0010\u0004\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\u0000J\u0010\u0010\u0005\u001a\u00020\u0002H&\u00f8\u0001\u0000�\u0006\u0002\u0010\u0006\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019�\u0006\u0007" }, d2 = { "Lkotlin/collections/UShortIterator;", "", "Lkotlin/UShort;", "()V", "next", "nextUShort", "()S", "kotlin-stdlib" })
@SinceKotlin(version = "1.3")
@ExperimentalUnsignedTypes
public abstract class UShortIterator implements Iterator<UShort>, KMappedMarker
{
    @NotNull
    @Override
    public final UShort next() {
        return UShort.box-impl(this.nextUShort());
    }
    
    @Override
    public Object next() {
        return this.next();
    }
    
    public abstract short nextUShort();
    
    public UShortIterator() {
        super();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
