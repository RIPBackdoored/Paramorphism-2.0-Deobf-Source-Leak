package kotlin.collections;

import kotlin.jvm.functions.*;
import kotlin.internal.*;
import java.util.*;
import org.jetbrains.annotations.*;
import kotlin.*;
import kotlin.sequences.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000h\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001aG\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001aG\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aY\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b\u001ar\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001aG\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b\u001aS\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001aY\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b\u001ar\u0010\u001e\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001al\u0010\u001f\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001ae\u0010 \u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0087\b\u001ai\u0010#\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&H\u0087\b\u001ae\u0010'\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001af\u0010(\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&\u001a$\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aV\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010+*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002H+2\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b¢\u0006\u0002\u0010,\u001a6\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030.0\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004¨\u0006/" }, d2 = { "all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "flatMap", "", "R", "transform", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "forEach", "", "action", "map", "mapNotNull", "", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "minBy", "minWith", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib" }, xs = "kotlin/collections/MapsKt")
class MapsKt___MapsKt extends MapsKt__MapsKt
{
    @NotNull
    public static final <K, V> List<Pair<K, V>> toList(@NotNull final Map<? extends K, ? extends V> map) {
        if (map.size() == 0) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        if (!iterator.hasNext()) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        final Map.Entry<? extends K, ? extends V> entry = iterator.next();
        if (!iterator.hasNext()) {
            final Map.Entry<? extends K, ? extends V> entry2 = entry;
            return CollectionsKt__CollectionsJVMKt.listOf(new Pair<K, V>(entry2.getKey(), entry2.getValue()));
        }
        final ArrayList<Pair<Object, V>> list2;
        final ArrayList<Pair<Object, V>> list = list2 = (ArrayList<Pair<Object, V>>)new ArrayList(map.size());
        final Map.Entry<? extends K, ? extends V> entry3 = entry;
        list2.add((Pair<Object, V>)new Pair<Object, Object>(entry3.getKey(), entry3.getValue()));
        do {
            final ArrayList<Pair<Object, V>> list3 = (ArrayList<Pair<Object, V>>)list;
            final Map.Entry<? extends K, ? extends V> entry4 = iterator.next();
            list3.add(new Pair<Object, V>(entry4.getKey(), entry4.getValue()));
        } while (iterator.hasNext());
        return (ArrayList<Pair<K, V>>)list;
    }
    
    @NotNull
    public static final <K, V, R> List<R> flatMap(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> function1) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll((Collection<? super Object>)list, (Iterable<?>)function1.invoke((Object)iterator.next()));
        }
        return (List<R>)list;
    }
    
    @NotNull
    public static final <K, V, R, C extends Collection<? super R>> C flatMapTo(@NotNull final Map<? extends K, ? extends V> map, @NotNull final C c, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> function1) {
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll((Collection<? super Object>)c, (Iterable<?>)function1.invoke((Object)iterator.next()));
        }
        return c;
    }
    
    @NotNull
    public static final <K, V, R> List<R> map(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final ArrayList<Object> list = new ArrayList<Object>(map.size());
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            list.add(function1.invoke((Object)iterator.next()));
        }
        return (List<R>)list;
    }
    
    @NotNull
    public static final <K, V, R> List<R> mapNotNull(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final R invoke = (R)function1.invoke((Object)iterator.next());
            if (invoke != null) {
                list.add(invoke);
            }
        }
        return (List<R>)list;
    }
    
    @NotNull
    public static final <K, V, R, C extends Collection<? super R>> C mapNotNullTo(@NotNull final Map<? extends K, ? extends V> map, @NotNull final C c, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final R invoke = (R)function1.invoke((Object)iterator.next());
            if (invoke != null) {
                c.add((Object)invoke);
            }
        }
        return c;
    }
    
    @NotNull
    public static final <K, V, R, C extends Collection<? super R>> C mapTo(@NotNull final Map<? extends K, ? extends V> map, @NotNull final C c, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            c.add((Object)function1.invoke((Object)iterator.next()));
        }
        return c;
    }
    
    public static final <K, V> boolean all(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> function1) {
        if (map.isEmpty()) {
            return true;
        }
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!function1.invoke((Object)iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public static final <K, V> boolean any(@NotNull final Map<? extends K, ? extends V> map) {
        return !map.isEmpty();
    }
    
    public static final <K, V> boolean any(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> function1) {
        if (map.isEmpty()) {
            return false;
        }
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (function1.invoke((Object)iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    @InlineOnly
    private static final <K, V> int count(@NotNull final Map<? extends K, ? extends V> map) {
        return map.size();
    }
    
    public static final <K, V> int count(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> function1) {
        if (map.isEmpty()) {
            return 0;
        }
        int n = 0;
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (function1.invoke((Object)iterator.next())) {
                ++n;
            }
        }
        return n;
    }
    
    @HidesMembers
    public static final <K, V> void forEach(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Unit> function1) {
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            function1.invoke((Object)iterator.next());
        }
    }
    
    @InlineOnly
    private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> maxBy(@NotNull final Map<? extends K, ? extends V> map, final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final Iterator<Object> iterator = map.entrySet().iterator();
        Object o;
        if (!iterator.hasNext()) {
            o = null;
        }
        else {
            Object next = iterator.next();
            if (!iterator.hasNext()) {
                o = next;
            }
            else {
                Comparable<Comparable> comparable = (Comparable<Comparable>)function1.invoke((Object)next);
                do {
                    final Object next2 = iterator.next();
                    final Comparable comparable2 = (Comparable)function1.invoke((Object)next2);
                    if (comparable.compareTo(comparable2) < 0) {
                        next = next2;
                        comparable = (Comparable<Comparable>)comparable2;
                    }
                } while (iterator.hasNext());
                o = next;
            }
        }
        return (Map.Entry<K, V>)o;
    }
    
    @InlineOnly
    private static final <K, V> Map.Entry<K, V> maxWith(@NotNull final Map<? extends K, ? extends V> map, final Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        return (Map.Entry<K, V>)CollectionsKt___CollectionsKt.maxWith((Iterable<? extends Map.Entry>)map.entrySet(), (Comparator<? super Map.Entry>)comparator);
    }
    
    @Nullable
    public static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> minBy(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> function1) {
        final Iterator<Object> iterator = map.entrySet().iterator();
        Object o;
        if (!iterator.hasNext()) {
            o = null;
        }
        else {
            Object next = iterator.next();
            if (!iterator.hasNext()) {
                o = next;
            }
            else {
                Comparable<Comparable> comparable = (Comparable<Comparable>)function1.invoke((Object)next);
                do {
                    final Object next2 = iterator.next();
                    final Comparable comparable2 = (Comparable)function1.invoke((Object)next2);
                    if (comparable.compareTo(comparable2) > 0) {
                        next = next2;
                        comparable = (Comparable<Comparable>)comparable2;
                    }
                } while (iterator.hasNext());
                o = next;
            }
        }
        return (Map.Entry<K, V>)o;
    }
    
    @Nullable
    public static final <K, V> Map.Entry<K, V> minWith(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        return (Map.Entry<K, V>)CollectionsKt___CollectionsKt.minWith((Iterable<? extends Map.Entry>)map.entrySet(), (Comparator<? super Map.Entry>)comparator);
    }
    
    public static final <K, V> boolean none(@NotNull final Map<? extends K, ? extends V> map) {
        return map.isEmpty();
    }
    
    public static final <K, V> boolean none(@NotNull final Map<? extends K, ? extends V> map, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> function1) {
        if (map.isEmpty()) {
            return true;
        }
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            if (function1.invoke((Object)iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V, M extends Map<? extends K, ? extends V>> M onEach(@NotNull final M m, @NotNull final Function1<? super Map.Entry<? extends K, ? extends V>, Unit> function1) {
        final Iterator<Map.Entry<? extends K, ? extends V>> iterator = m.entrySet().iterator();
        while (iterator.hasNext()) {
            function1.invoke((Object)iterator.next());
        }
        return m;
    }
    
    @InlineOnly
    private static final <K, V> Iterable<Map.Entry<K, V>> asIterable(@NotNull final Map<? extends K, ? extends V> map) {
        return (Iterable<Map.Entry<K, V>>)map.entrySet();
    }
    
    @NotNull
    public static final <K, V> Sequence<Map.Entry<K, V>> asSequence(@NotNull final Map<? extends K, ? extends V> map) {
        return CollectionsKt___CollectionsKt.asSequence((Iterable<? extends Map.Entry<K, V>>)map.entrySet());
    }
    
    public MapsKt___MapsKt() {
        super();
    }
}
