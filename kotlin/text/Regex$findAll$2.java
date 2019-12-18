package kotlin.text;

import kotlin.jvm.functions.*;
import kotlin.*;
import org.jetbrains.annotations.*;
import kotlin.reflect.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\b\u0003" }, d2 = { "<anonymous>", "Lkotlin/text/MatchResult;", "p1", "invoke" })
static final class Regex$findAll$2 extends FunctionReference implements Function1<MatchResult, MatchResult> {
    public static final Regex$findAll$2 INSTANCE;
    
    @Override
    public Object invoke(final Object o) {
        return this.invoke((MatchResult)o);
    }
    
    @Nullable
    @Override
    public final MatchResult invoke(@NotNull final MatchResult matchResult) {
        return matchResult.next();
    }
    
    @Override
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(MatchResult.class);
    }
    
    @Override
    public final String getName() {
        return "next";
    }
    
    @Override
    public final String getSignature() {
        return "next()Lkotlin/text/MatchResult;";
    }
    
    Regex$findAll$2() {
        super(1);
    }
    
    static {
        Regex$findAll$2.INSTANCE = new Regex$findAll$2();
    }
}