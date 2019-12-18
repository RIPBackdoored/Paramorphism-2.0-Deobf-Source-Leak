package kotlin.text;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import kotlin.collections.*;
import kotlin.ranges.*;
import java.util.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004" }, d2 = { "<anonymous>", "", "", "Lkotlin/text/CharDirectionality;", "invoke" })
static final class CharDirectionality$Companion$directionalityMap$2 extends Lambda implements Function0<Map<Integer, ? extends CharDirectionality>> {
    public static final CharDirectionality$Companion$directionalityMap$2 INSTANCE;
    
    @Override
    public Object invoke() {
        return this.invoke();
    }
    
    @NotNull
    @Override
    public final Map<Integer, CharDirectionality> invoke() {
        final CharDirectionality[] values = CharDirectionality.values();
        final int coerceAtLeast = RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsKt.mapCapacity(values.length), 16);
        final CharDirectionality[] array = values;
        final LinkedHashMap<Integer, CharDirectionality> linkedHashMap = new LinkedHashMap<Integer, CharDirectionality>(coerceAtLeast);
        for (final CharDirectionality charDirectionality : array) {
            linkedHashMap.put(charDirectionality.getValue(), charDirectionality);
        }
        return linkedHashMap;
    }
    
    CharDirectionality$Companion$directionalityMap$2() {
        super(0);
    }
    
    static {
        CharDirectionality$Companion$directionalityMap$2.INSTANCE = new CharDirectionality$Companion$directionalityMap$2();
    }
}