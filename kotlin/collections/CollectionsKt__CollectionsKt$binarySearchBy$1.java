package kotlin.collections;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import kotlin.comparisons.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0005\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007" }, d2 = { "<anonymous>", "", "T", "K", "", "it", "invoke", "(Ljava/lang/Object;)I" })
public static final class CollectionsKt__CollectionsKt$binarySearchBy$1 extends Lambda implements Function1<T, Integer> {
    final Function1 $selector;
    final Comparable $key;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke(o);
    }
    
    @Override
    public final int invoke(final T t) {
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)this.$selector.invoke(t), this.$key);
    }
    
    public CollectionsKt__CollectionsKt$binarySearchBy$1(final Function1 $selector, final Comparable $key) {
        this.$selector = $selector;
        this.$key = $key;
        super(1);
    }
}