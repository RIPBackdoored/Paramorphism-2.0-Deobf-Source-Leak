package kotlin.text;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import java.util.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005" }, d2 = { "<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke" })
static final class StringsKt__StringsKt$rangesDelimitedBy$4 extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
    final List $delimitersList;
    final boolean $ignoreCase;
    
    @Override
    public Object invoke(final Object o, final Object o2) {
        return this.invoke((CharSequence)o, ((Number)o2).intValue());
    }
    
    @Nullable
    public final Pair<Integer, Integer> invoke(@NotNull final CharSequence charSequence, final int n) {
        final Pair access$findAny = StringsKt__StringsKt.access$findAnyOf(charSequence, this.$delimitersList, n, this.$ignoreCase, false);
        Pair<Integer, Integer> to;
        if (access$findAny != null) {
            final Pair<A, B> pair = (Pair<A, B>)access$findAny;
            to = TuplesKt.to((Integer)pair.getFirst(), ((String)pair.getSecond()).length());
        }
        else {
            to = null;
        }
        return to;
    }
    
    StringsKt__StringsKt$rangesDelimitedBy$4(final List $delimitersList, final boolean $ignoreCase) {
        this.$delimitersList = $delimitersList;
        this.$ignoreCase = $ignoreCase;
        super(2);
    }
}