package kotlin.jvm.internal;

import kotlin.jvm.functions.*;
import kotlin.reflect.*;
import kotlin.*;
import org.jetbrains.annotations.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004" }, d2 = { "<anonymous>", "", "it", "Lkotlin/reflect/KTypeProjection;", "invoke" })
static final class TypeReference$asString$args$1 extends Lambda implements Function1<KTypeProjection, String> {
    final TypeReference this$0;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke((KTypeProjection)o);
    }
    
    @NotNull
    @Override
    public final String invoke(@NotNull final KTypeProjection kTypeProjection) {
        return TypeReference.access$asString(this.this$0, kTypeProjection);
    }
    
    TypeReference$asString$args$1(final TypeReference this$0) {
        this.this$0 = this$0;
        super(1);
    }
}