package kotlin;

import java.io.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u0001*\u0006\b\u0002\u0010\u0003 \u00012\u00060\u0004j\u0002`\u0005B\u001d\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001\u0012\u0006\u0010\b\u001a\u00028\u0002¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\u00028\u0000H\u00c6\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0010\u001a\u00028\u0001H\u00c6\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0011\u001a\u00028\u0002H\u00c6\u0003¢\u0006\u0002\u0010\u000bJ>\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00028\u00012\b\b\u0002\u0010\b\u001a\u00028\u0002H\u00c6\u0001¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u0013\u0010\b\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u001c" }, d2 = { "Lkotlin/Triple;", "A", "B", "C", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "third", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "getThird", "component1", "component2", "component3", "copy", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Triple;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib" })
public final class Triple<A, B, C> implements Serializable
{
    private final A first;
    private final B second;
    private final C third;
    
    @NotNull
    @Override
    public String toString() {
        return new StringBuilder().append('(').append(this.first).append(", ").append(this.second).append(", ").append(this.third).append(')').toString();
    }
    
    public final A getFirst() {
        return this.first;
    }
    
    public final B getSecond() {
        return this.second;
    }
    
    public final C getThird() {
        return this.third;
    }
    
    public Triple(final A first, final B second, final C third) {
        super();
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public final A component1() {
        return this.first;
    }
    
    public final B component2() {
        return this.second;
    }
    
    public final C component3() {
        return this.third;
    }
    
    @NotNull
    public final Triple<A, B, C> copy(final A a, final B b, final C c) {
        return new Triple<A, B, C>(a, b, c);
    }
    
    public static Triple copy$default(final Triple triple, Object first, Object second, Object third, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            first = triple.first;
        }
        if ((n & 0x2) != 0x0) {
            second = triple.second;
        }
        if ((n & 0x4) != 0x0) {
            third = triple.third;
        }
        return triple.copy(first, second, third);
    }
    
    @Override
    public int hashCode() {
        final A first = this.first;
        final int n = ((first != null) ? first.hashCode() : 0) * 31;
        final B second = this.second;
        final int n2 = (n + ((second != null) ? second.hashCode() : 0)) * 31;
        final C third = this.third;
        return n2 + ((third != null) ? third.hashCode() : 0);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (this != o) {
            if (o instanceof Triple) {
                final Triple triple = (Triple)o;
                if (Intrinsics.areEqual(this.first, triple.first) && Intrinsics.areEqual(this.second, triple.second) && Intrinsics.areEqual(this.third, triple.third)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
