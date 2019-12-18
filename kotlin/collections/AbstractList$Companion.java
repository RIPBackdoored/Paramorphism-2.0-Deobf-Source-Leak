package kotlin.collections;

import kotlin.*;
import org.jetbrains.annotations.*;
import java.util.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\tJ\u001d\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000eJ%\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0012J%\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u0018J\u0019\u0010\u0019\u001a\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u001a¨\u0006\u001b" }, d2 = { "Lkotlin/collections/AbstractList$Companion;", "", "()V", "checkBoundsIndexes", "", "startIndex", "", "endIndex", "size", "checkBoundsIndexes$kotlin_stdlib", "checkElementIndex", "index", "checkElementIndex$kotlin_stdlib", "checkPositionIndex", "checkPositionIndex$kotlin_stdlib", "checkRangeIndexes", "fromIndex", "toIndex", "checkRangeIndexes$kotlin_stdlib", "orderedEquals", "", "c", "", "other", "orderedEquals$kotlin_stdlib", "orderedHashCode", "orderedHashCode$kotlin_stdlib", "kotlin-stdlib" })
public static final class Companion
{
    public final void checkElementIndex$kotlin_stdlib(final int n, final int n2) {
        if (n < 0 || n >= n2) {
            throw new IndexOutOfBoundsException("index: " + n + ", size: " + n2);
        }
    }
    
    public final void checkPositionIndex$kotlin_stdlib(final int n, final int n2) {
        if (n < 0 || n > n2) {
            throw new IndexOutOfBoundsException("index: " + n + ", size: " + n2);
        }
    }
    
    public final void checkRangeIndexes$kotlin_stdlib(final int n, final int n2, final int n3) {
        if (n < 0 || n2 > n3) {
            throw new IndexOutOfBoundsException("fromIndex: " + n + ", toIndex: " + n2 + ", size: " + n3);
        }
        if (n > n2) {
            throw new IllegalArgumentException("fromIndex: " + n + " > toIndex: " + n2);
        }
    }
    
    public final void checkBoundsIndexes$kotlin_stdlib(final int n, final int n2, final int n3) {
        if (n < 0 || n2 > n3) {
            throw new IndexOutOfBoundsException("startIndex: " + n + ", endIndex: " + n2 + ", size: " + n3);
        }
        if (n > n2) {
            throw new IllegalArgumentException("startIndex: " + n + " > endIndex: " + n2);
        }
    }
    
    public final int orderedHashCode$kotlin_stdlib(@NotNull final Collection<?> collection) {
        int n = 1;
        for (final Object next : collection) {
            final int n2 = 31 * n;
            final Object o = next;
            n = n2 + ((o != null) ? o.hashCode() : 0);
        }
        return n;
    }
    
    public final boolean orderedEquals$kotlin_stdlib(@NotNull final Collection<?> collection, @NotNull final Collection<?> collection2) {
        if (collection.size() != collection2.size()) {
            return false;
        }
        final Iterator<?> iterator = collection2.iterator();
        final Iterator<?> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            if (Intrinsics.areEqual(iterator2.next(), iterator.next()) ^ true) {
                return false;
            }
        }
        return true;
    }
    
    private Companion() {
        super();
    }
    
    public Companion(final DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
