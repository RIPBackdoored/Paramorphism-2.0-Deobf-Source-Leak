package kotlin.text;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\n¢\u0006\u0002\b\u0002" }, d2 = { "<anonymous>", "Lkotlin/text/MatchResult;", "invoke" })
static final class Regex$findAll$1 extends Lambda implements Function0<MatchResult> {
    final Regex this$0;
    final CharSequence $input;
    final int $startIndex;
    
    @Override
    public Object invoke() {
        return this.invoke();
    }
    
    @Nullable
    @Override
    public final MatchResult invoke() {
        return this.this$0.find(this.$input, this.$startIndex);
    }
    
    Regex$findAll$1(final Regex this$0, final CharSequence $input, final int $startIndex) {
        this.this$0 = this$0;
        this.$input = $input;
        this.$startIndex = $startIndex;
        super(0);
    }
}