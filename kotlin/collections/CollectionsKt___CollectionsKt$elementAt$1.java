package kotlin.collections;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005" }, d2 = { "<anonymous>", "", "T", "it", "", "invoke" })
static final class CollectionsKt___CollectionsKt$elementAt$1 extends Lambda implements Function1 {
    final int $index;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke(((Number)o).intValue());
    }
    
    @NotNull
    public final Void invoke(final int n) {
        throw new IndexOutOfBoundsException("Collection doesn't contain element at index " + this.$index + '.');
    }
    
    CollectionsKt___CollectionsKt$elementAt$1(final int $index) {
        this.$index = $index;
        super(1);
    }
}