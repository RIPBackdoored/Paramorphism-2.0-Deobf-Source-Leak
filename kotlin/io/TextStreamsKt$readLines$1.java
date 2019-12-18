package kotlin.io;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import java.util.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004" }, d2 = { "<anonymous>", "", "it", "", "invoke" })
static final class TextStreamsKt$readLines$1 extends Lambda implements Function1<String, Unit> {
    final ArrayList $result;
    
    @Override
    public Object invoke(final Object o) {
        this.invoke((String)o);
        return Unit.INSTANCE;
    }
    
    public final void invoke(@NotNull final String s) {
        this.$result.add(s);
    }
    
    TextStreamsKt$readLines$1(final ArrayList $result) {
        this.$result = $result;
        super(1);
    }
}