package kotlin.random.jdk8;

import kotlin.random.*;
import kotlin.*;
import java.util.*;
import java.util.concurrent.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f" }, d2 = { "Lkotlin/random/jdk8/PlatformThreadLocalRandom;", "Lkotlin/random/AbstractPlatformRandom;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "nextDouble", "", "until", "nextInt", "", "from", "nextLong", "", "kotlin-stdlib-jdk8" })
public final class PlatformThreadLocalRandom extends AbstractPlatformRandom
{
    @NotNull
    @Override
    public java.util.Random getImpl() {
        return ThreadLocalRandom.current();
    }
    
    @Override
    public int nextInt(final int n, final int n2) {
        return ThreadLocalRandom.current().nextInt(n, n2);
    }
    
    @Override
    public long nextLong(final long n) {
        return ThreadLocalRandom.current().nextLong(n);
    }
    
    @Override
    public long nextLong(final long n, final long n2) {
        return ThreadLocalRandom.current().nextLong(n, n2);
    }
    
    @Override
    public double nextDouble(final double n) {
        return ThreadLocalRandom.current().nextDouble(n);
    }
    
    public PlatformThreadLocalRandom() {
        super();
    }
}
