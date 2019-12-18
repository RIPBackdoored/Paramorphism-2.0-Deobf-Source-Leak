package kotlin.random;

import org.jetbrains.annotations.*;
import kotlin.*;
import kotlin.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 2, d1 = { "\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0000\u001a\f\u0010\t\u001a\u00020\n*\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0001*\u00020\nH\u0007¨\u0006\f" }, d2 = { "defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "fastLog2", "value", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib" })
public final class PlatformRandomKt
{
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final java.util.Random asJavaRandom(@NotNull final Random random) {
        Random random2 = random;
        if (!(random instanceof AbstractPlatformRandom)) {
            random2 = null;
        }
        final AbstractPlatformRandom abstractPlatformRandom = (AbstractPlatformRandom)random2;
        java.util.Random impl;
        if (abstractPlatformRandom == null || (impl = abstractPlatformRandom.getImpl()) == null) {
            impl = new KotlinRandom(random);
        }
        return impl;
    }
    
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random asKotlinRandom(@NotNull final java.util.Random random) {
        java.util.Random random2 = random;
        if (!(random instanceof KotlinRandom)) {
            random2 = null;
        }
        final KotlinRandom kotlinRandom = (KotlinRandom)random2;
        Random impl;
        if (kotlinRandom == null || (impl = kotlinRandom.getImpl()) == null) {
            impl = new PlatformRandom(random);
        }
        return impl;
    }
    
    @InlineOnly
    private static final Random defaultPlatformRandom() {
        return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
    }
    
    public static final int fastLog2(final int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }
    
    public static final double doubleFromParts(final int n, final int n2) {
        return (((long)n << 27) + n2) / (double)9007199254740992L;
    }
}
