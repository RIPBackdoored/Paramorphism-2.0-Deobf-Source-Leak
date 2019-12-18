package kotlin.text;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003" }, d2 = { "<anonymous>", "", "it", "invoke" })
static final class StringsKt__IndentKt$prependIndent$1 extends Lambda implements Function1<String, String> {
    final String $indent;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke((String)o);
    }
    
    @NotNull
    @Override
    public final String invoke(@NotNull final String s) {
        return StringsKt__StringsJVMKt.isBlank(s) ? ((s.length() < this.$indent.length()) ? this.$indent : s) : (this.$indent + s);
    }
    
    StringsKt__IndentKt$prependIndent$1(final String $indent) {
        this.$indent = $indent;
        super(1);
    }
}