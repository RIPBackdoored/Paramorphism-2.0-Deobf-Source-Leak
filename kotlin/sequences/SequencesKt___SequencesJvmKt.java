package kotlin.sequences;

import org.jetbrains.annotations.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import java.util.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001aA\u0010\u0005\u001a\u0002H\u0006\"\u0010\b\u0000\u0010\u0006*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0007\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\b\u001a\u0002H\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004¢\u0006\u0002\u0010\t\u001a&\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u000e\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0001\u001a8\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00012\u001a\u0010\u000e\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\f0\u000fj\n\u0012\u0006\b\u0000\u0012\u0002H\f`\u0010¨\u0006\u0011" }, d2 = { "filterIsInstance", "Lkotlin/sequences/Sequence;", "R", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "toSortedSet", "Ljava/util/SortedSet;", "T", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib" }, xs = "kotlin/sequences/SequencesKt")
class SequencesKt___SequencesJvmKt extends SequencesKt__SequencesKt
{
    @NotNull
    public static final <R> Sequence<R> filterIsInstance(@NotNull final Sequence<?> sequence, @NotNull final Class<R> clazz) {
        final Sequence<Object> filter = (Sequence<Object>)SequencesKt___SequencesKt.filter((Sequence<? extends R>)sequence, (Function1<? super R, Boolean>)new SequencesKt___SequencesJvmKt$filterIsInstance.SequencesKt___SequencesJvmKt$filterIsInstance$1((Class)clazz));
        if (filter == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.sequences.Sequence<R>");
        }
        return (Sequence<R>)filter;
    }
    
    @NotNull
    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(@NotNull final Sequence<?> sequence, @NotNull final C c, @NotNull final Class<R> clazz) {
        for (final Object next : sequence) {
            if (clazz.isInstance(next)) {
                c.add((Object)next);
            }
        }
        return c;
    }
    
    @NotNull
    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(@NotNull final Sequence<? extends T> sequence) {
        return SequencesKt___SequencesKt.toCollection((Sequence<?>)sequence, (TreeSet<T>)new TreeSet<T>());
    }
    
    @NotNull
    public static final <T> SortedSet<T> toSortedSet(@NotNull final Sequence<? extends T> sequence, @NotNull final Comparator<? super T> comparator) {
        return SequencesKt___SequencesKt.toCollection((Sequence<?>)sequence, (TreeSet<T>)new TreeSet<T>(comparator));
    }
    
    public SequencesKt___SequencesJvmKt() {
        super();
    }
}
