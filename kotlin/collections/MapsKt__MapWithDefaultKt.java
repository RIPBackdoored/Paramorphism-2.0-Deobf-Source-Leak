package kotlin.collections;

import org.jetbrains.annotations.*;
import java.util.*;
import kotlin.jvm.*;
import kotlin.*;
import kotlin.jvm.functions.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\u001a3\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001aQ\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\t\u001aX\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f2!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\tH\u0007¢\u0006\u0002\b\r¨\u0006\u000e" }, d2 = { "getOrImplicitDefault", "V", "K", "", "key", "getOrImplicitDefaultNullable", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "withDefault", "defaultValue", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "withDefaultMutable", "kotlin-stdlib" }, xs = "kotlin/collections/MapsKt")
class MapsKt__MapWithDefaultKt
{
    @JvmName(name = "getOrImplicitDefaultNullable")
    @PublishedApi
    public static final <K, V> V getOrImplicitDefaultNullable(@NotNull final Map<K, ? extends V> map, final K k) {
        if (map instanceof MapWithDefault) {
            return ((MapWithDefault<K, V>)map).getOrImplicitDefault(k);
        }
        final Object value = map.get(k);
        if (value == null && !map.containsKey(k)) {
            throw new NoSuchElementException("Key " + k + " is missing in the map.");
        }
        return (V)value;
    }
    
    @NotNull
    public static final <K, V> Map<K, V> withDefault(@NotNull final Map<K, ? extends V> map, @NotNull final Function1<? super K, ? extends V> function1) {
        return (Map<K, V>)((map instanceof MapWithDefault) ? withDefault((Map<Object, ?>)((MapWithDefault<K, ? extends V>)map).getMap(), (Function1<? super Object, ?>)function1) : ((MapWithDefaultImpl<Object, Object>)new MapWithDefaultImpl<Object, Object>((Map<Object, ?>)map, (Function1<? super Object, ?>)function1)));
    }
    
    @JvmName(name = "withDefaultMutable")
    @NotNull
    public static final <K, V> Map<K, V> withDefaultMutable(@NotNull final Map<K, V> map, @NotNull final Function1<? super K, ? extends V> function1) {
        return (Map<K, V>)((map instanceof MutableMapWithDefault) ? withDefaultMutable((Map<Object, Object>)((MutableMapWithDefault<K, V>)map).getMap(), (Function1<? super Object, ?>)function1) : ((MutableMapWithDefaultImpl<Object, Object>)new MutableMapWithDefaultImpl<Object, Object>((Map<Object, Object>)map, (Function1<? super Object, ?>)function1)));
    }
    
    public MapsKt__MapWithDefaultKt() {
        super();
    }
}
