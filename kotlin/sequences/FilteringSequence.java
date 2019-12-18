package kotlin.sequences;

import kotlin.*;
import kotlin.jvm.functions.*;
import java.util.*;
import org.jetbrains.annotations.*;
import kotlin.jvm.internal.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0096\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b" }, d2 = { "Lkotlin/sequences/FilteringSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "sendWhen", "", "predicate", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;ZLkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib" })
public final class FilteringSequence<T> implements Sequence<T>
{
    private final Sequence<T> sequence;
    private final boolean sendWhen;
    private final Function1<T, Boolean> predicate;
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>)new FilteringSequence$iterator.FilteringSequence$iterator$1(this);
    }
    
    public FilteringSequence(@NotNull final Sequence<? extends T> sequence, final boolean sendWhen, @NotNull final Function1<? super T, Boolean> predicate) {
        super();
        this.sequence = (Sequence<T>)sequence;
        this.sendWhen = sendWhen;
        this.predicate = (Function1<T, Boolean>)predicate;
    }
    
    public FilteringSequence(final Sequence sequence, boolean b, final Function1 function1, final int n, final DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x2) != 0x0) {
            b = true;
        }
        this(sequence, b, function1);
    }
    
    public static final boolean access$getSendWhen$p(final FilteringSequence filteringSequence) {
        return filteringSequence.sendWhen;
    }
    
    public static final Function1 access$getPredicate$p(final FilteringSequence filteringSequence) {
        return filteringSequence.predicate;
    }
    
    public static final Sequence access$getSequence$p(final FilteringSequence filteringSequence) {
        return filteringSequence.sequence;
    }
}
