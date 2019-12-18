package kotlin.text;

import kotlin.*;
import org.jetbrains.annotations.*;
import java.util.regex.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007¨\u0006\f" }, d2 = { "Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib" })
public static final class Companion
{
    @NotNull
    public final Regex fromLiteral(@NotNull final String s) {
        return new Regex(s, RegexOption.LITERAL);
    }
    
    @NotNull
    public final String escape(@NotNull final String s) {
        return Pattern.quote(s);
    }
    
    @NotNull
    public final String escapeReplacement(@NotNull final String s) {
        return Matcher.quoteReplacement(s);
    }
    
    private final int ensureUnicodeCase(final int n) {
        return ((n & 0x2) != 0x0) ? (n | 0x40) : n;
    }
    
    private Companion() {
        super();
    }
    
    public static final int access$ensureUnicodeCase(final Companion companion, final int n) {
        return companion.ensureUnicodeCase(n);
    }
    
    public Companion(final DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
