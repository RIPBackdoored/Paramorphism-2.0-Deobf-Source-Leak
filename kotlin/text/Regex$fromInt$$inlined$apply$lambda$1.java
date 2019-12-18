package kotlin.text;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import kotlin.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\u0010\u0005\u001a\n \u0006*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t" }, d2 = { "<anonymous>", "", "T", "Lkotlin/text/FlagEnum;", "", "it", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Enum;)Z", "kotlin/text/RegexKt$fromInt$1$1" })
public static final class Regex$fromInt$$inlined$apply$lambda$1 extends Lambda implements Function1<T, Boolean> {
    final int $value$inlined;
    
    public Regex$fromInt$$inlined$apply$lambda$1(final int $value$inlined) {
        this.$value$inlined = $value$inlined;
        super(1);
    }
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke((Enum)o);
    }
    
    @Override
    public final boolean invoke(final T t) {
        return (this.$value$inlined & ((FlagEnum)t).getMask()) == ((FlagEnum)t).getValue();
    }
}