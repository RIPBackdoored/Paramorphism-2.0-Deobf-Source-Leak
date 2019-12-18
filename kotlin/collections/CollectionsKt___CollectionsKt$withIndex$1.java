package kotlin.collections;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import java.util.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\n\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003" }, d2 = { "<anonymous>", "", "T", "invoke" })
static final class CollectionsKt___CollectionsKt$withIndex$1 extends Lambda implements Function0<Iterator<? extends T>> {
    final Iterable $this_withIndex;
    
    @Override
    public Object invoke() {
        return this.invoke();
    }
    
    @NotNull
    @Override
    public final Iterator<T> invoke() {
        return this.$this_withIndex.iterator();
    }
    
    CollectionsKt___CollectionsKt$withIndex$1(final Iterable $this_withIndex) {
        this.$this_withIndex = $this_withIndex;
        super(0);
    }
}