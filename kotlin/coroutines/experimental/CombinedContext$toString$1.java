package kotlin.coroutines.experimental;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005" }, d2 = { "<anonymous>", "", "acc", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "invoke" })
static final class CombinedContext$toString$1 extends Lambda implements Function2<String, Element, String> {
    public static final CombinedContext$toString$1 INSTANCE;
    
    @Override
    public Object invoke(final Object o, final Object o2) {
        return this.invoke((String)o, (Element)o2);
    }
    
    @NotNull
    @Override
    public final String invoke(@NotNull final String s, @NotNull final Element element) {
        return (s.length() == 0) ? element.toString() : (s + ", " + element);
    }
    
    CombinedContext$toString$1() {
        super(2);
    }
    
    static {
        CombinedContext$toString$1.INSTANCE = new CombinedContext$toString$1();
    }
}