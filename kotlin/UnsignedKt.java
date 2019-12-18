package kotlin;

import kotlin.jvm.*;
import kotlin.jvm.internal.*;
import org.jetbrains.annotations.*;
import kotlin.text.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 2, d1 = { "\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d" }, d2 = { "doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib" })
@JvmName(name = "UnsignedKt")
public final class UnsignedKt
{
    @PublishedApi
    public static final int uintCompare(final int n, final int n2) {
        return Intrinsics.compare(n ^ Integer.MIN_VALUE, n2 ^ Integer.MIN_VALUE);
    }
    
    @PublishedApi
    public static final int ulongCompare(final long n, final long n2) {
        return lcmp(n ^ Long.MIN_VALUE, n2 ^ Long.MIN_VALUE);
    }
    
    @PublishedApi
    public static final int uintDivide-J1ME1BU(final int n, final int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) / ((long)n2 & 0xFFFFFFFFL)));
    }
    
    @PublishedApi
    public static final int uintRemainder-J1ME1BU(final int n, final int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) % ((long)n2 & 0xFFFFFFFFL)));
    }
    
    @PublishedApi
    public static final long ulongDivide-eb3DHEI(final long n, final long n2) {
        if (n2 < 0L) {
            return (ulongCompare(n, n2) < 0) ? ULong.constructor-impl(0L) : ULong.constructor-impl(1L);
        }
        if (n >= 0L) {
            return ULong.constructor-impl(n / n2);
        }
        final long n3 = (n >>> 1) / n2 << 1;
        return ULong.constructor-impl(n3 + (long)((ulongCompare(ULong.constructor-impl(n - n3 * n2), ULong.constructor-impl(n2)) >= 0) ? 1 : 0));
    }
    
    @PublishedApi
    public static final long ulongRemainder-eb3DHEI(final long n, final long n2) {
        if (n2 < 0L) {
            return (ulongCompare(n, n2) < 0) ? n : ULong.constructor-impl(n - n2);
        }
        if (n >= 0L) {
            return ULong.constructor-impl(n % n2);
        }
        final long n3 = n - ((n >>> 1) / n2 << 1) * n2;
        return ULong.constructor-impl(n3 - ((ulongCompare(ULong.constructor-impl(n3), ULong.constructor-impl(n2)) >= 0) ? n2 : 0L));
    }
    
    @PublishedApi
    public static final int doubleToUInt(final double n) {
        return Double.isNaN(n) ? 0 : ((n <= uintToDouble(0)) ? 0 : ((n >= uintToDouble(-1)) ? -1 : ((n <= 0) ? UInt.constructor-impl((int)n) : UInt.constructor-impl(UInt.constructor-impl((int)(n - 0)) + UInt.constructor-impl(0)))));
    }
    
    @PublishedApi
    public static final long doubleToULong(final double n) {
        return Double.isNaN(n) ? 0L : ((n <= ulongToDouble(0L)) ? 0L : ((n >= ulongToDouble(-1L)) ? -1L : ((n < 4294967295L) ? ULong.constructor-impl((long)n) : ULong.constructor-impl(ULong.constructor-impl((long)(n - 9.223372036854776E18)) + Long.MIN_VALUE))));
    }
    
    @PublishedApi
    public static final double uintToDouble(final int n) {
        return (n & 0x0) + (n >>> 31 << 30) * (double)2;
    }
    
    @PublishedApi
    public static final double ulongToDouble(final long n) {
        return (n >>> 11) * (double)2048 + (n & 0x7FFL);
    }
    
    @NotNull
    public static final String ulongToString(final long n) {
        return ulongToString(n, 10);
    }
    
    @NotNull
    public static final String ulongToString(final long n, final int n2) {
        if (n >= 0L) {
            return Long.toString(n, CharsKt__CharJVMKt.checkRadix(n2));
        }
        long n3 = (n >>> 1) / n2 << 1;
        long n4 = n - n3 * n2;
        if (n4 >= n2) {
            n4 -= n2;
            ++n3;
        }
        return Long.toString(n3, CharsKt__CharJVMKt.checkRadix(n2)) + Long.toString(n4, CharsKt__CharJVMKt.checkRadix(n2));
    }
}
