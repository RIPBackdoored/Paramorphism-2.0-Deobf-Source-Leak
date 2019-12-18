package kotlin.ranges;

import kotlin.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002\u00f8\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004\u00f8\u0001\u0000¢\u0006\u0006\u001a\u0004\b\t\u0010\b\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018" }, d2 = { "Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/ULong;", "getStart", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib" })
@SinceKotlin(version = "1.3")
@ExperimentalUnsignedTypes
public final class ULongRange extends ULongProgression implements ClosedRange<ULong>
{
    @NotNull
    private static final ULongRange EMPTY;
    public static final Companion Companion;
    
    @NotNull
    @Override
    public ULong getStart() {
        return ULong.box-impl(this.getFirst());
    }
    
    @Override
    public Comparable getStart() {
        return this.getStart();
    }
    
    @NotNull
    @Override
    public ULong getEndInclusive() {
        return ULong.box-impl(this.getLast());
    }
    
    @Override
    public Comparable getEndInclusive() {
        return this.getEndInclusive();
    }
    
    public boolean contains-VKZWuLQ(final long n) {
        return UnsignedKt.ulongCompare(this.getFirst(), n) <= 0 && UnsignedKt.ulongCompare(n, this.getLast()) <= 0;
    }
    
    @Override
    public boolean contains(final Comparable comparable) {
        return this.contains-VKZWuLQ(((ULong)comparable).unbox-impl());
    }
    
    @Override
    public boolean isEmpty() {
        return UnsignedKt.ulongCompare(this.getFirst(), this.getLast()) > 0;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof ULongRange && ((this.isEmpty() && ((ULongRange)o).isEmpty()) || (this.getFirst() == ((ULongRange)o).getFirst() && this.getLast() == ((ULongRange)o).getLast()));
    }
    
    @Override
    public int hashCode() {
        return this.isEmpty() ? -1 : (31 * (int)ULong.constructor-impl(this.getFirst() ^ ULong.constructor-impl(this.getFirst() >>> 32)) + (int)ULong.constructor-impl(this.getLast() ^ ULong.constructor-impl(this.getLast() >>> 32)));
    }
    
    @NotNull
    @Override
    public String toString() {
        return ULong.toString-impl(this.getFirst()) + ".." + ULong.toString-impl(this.getLast());
    }
    
    private ULongRange(final long n, final long n2) {
        super(n, n2, 1L, null);
    }
    
    static {
        Companion = new Companion(null);
        EMPTY = new ULongRange(-1L, 0L, null);
    }
    
    public ULongRange(final long n, final long n2, final DefaultConstructorMarker defaultConstructorMarker) {
        this(n, n2);
    }
    
    public static final ULongRange access$getEMPTY$cp() {
        return ULongRange.EMPTY;
    }
    
    @Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007" }, d2 = { "Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib" })
    public static final class Companion
    {
        @NotNull
        public final ULongRange getEMPTY() {
            return ULongRange.access$getEMPTY$cp();
        }
        
        private Companion() {
            super();
        }
        
        public Companion(final DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
