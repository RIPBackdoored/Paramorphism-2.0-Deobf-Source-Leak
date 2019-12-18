package kotlin.text;

import kotlin.sequences.*;
import kotlin.*;
import java.util.*;
import kotlin.collections.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000" }, d2 = { "kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib" })
public static final class StringsKt___StringsKt$asSequence$$inlined$Sequence$1 implements Sequence<Character> {
    final CharSequence $this_asSequence$inlined;
    
    public StringsKt___StringsKt$asSequence$$inlined$Sequence$1(final CharSequence $this_asSequence$inlined) {
        this.$this_asSequence$inlined = $this_asSequence$inlined;
        super();
    }
    
    @NotNull
    @Override
    public Iterator<Character> iterator() {
        return StringsKt__StringsKt.iterator(this.$this_asSequence$inlined);
    }
}