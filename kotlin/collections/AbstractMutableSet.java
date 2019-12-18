package kotlin.collections;

import java.util.*;
import kotlin.jvm.internal.markers.*;
import kotlin.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\b¨\u0006\t" }, d2 = { "Lkotlin/collections/AbstractMutableSet;", "E", "", "Ljava/util/AbstractSet;", "()V", "add", "", "element", "(Ljava/lang/Object;)Z", "kotlin-stdlib" })
@SinceKotlin(version = "1.1")
public abstract class AbstractMutableSet<E> extends AbstractSet<E> implements Set<E>, KMutableSet
{
    @Override
    public abstract boolean add(final E p0);
    
    protected AbstractMutableSet() {
        super();
    }
    
    @Override
    public final int size() {
        return this.getSize();
    }
    
    public abstract int getSize();
}
