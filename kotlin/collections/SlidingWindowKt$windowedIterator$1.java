package kotlin.collections;

import kotlin.jvm.functions.*;
import kotlin.sequences.*;
import kotlin.coroutines.*;
import kotlin.coroutines.jvm.internal.*;
import kotlin.coroutines.intrinsics.*;
import kotlin.*;
import java.util.*;
import org.jetbrains.annotations.*;

@DebugMetadata(f = "SlidingWindow.kt", l = { 33, 39, 46, 52, 55 }, i = { 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4 }, s = { "L$0", "I$0", "L$1", "I$1", "L$2", "L$0", "I$0", "L$1", "I$1", "L$0", "I$0", "L$1", "L$2", "L$0", "I$0", "L$1", "L$0", "I$0", "L$1" }, n = { "$this$iterator", "gap", "buffer", "skip", "e", "$this$iterator", "gap", "buffer", "skip", "$this$iterator", "gap", "buffer", "e", "$this$iterator", "gap", "buffer", "$this$iterator", "gap", "buffer" }, m = "invokeSuspend", c = "kotlin.collections.SlidingWindowKt$windowedIterator$1")
@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 3, d1 = { "\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006" }, d2 = { "<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;" })
static final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
    private SequenceScope p$;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int I$0;
    int I$1;
    int label;
    final int $step;
    final int $size;
    final Iterator $iterator;
    final boolean $reuseBuffer;
    final boolean $partialWindows;
    
    @Nullable
    public final Object invokeSuspend(@NotNull final Object o) {
        final Object coroutine_SUSPENDED = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
    Label_0617:
        while (true) {
            RingBuffer l$1 = null;
            Label_0721: {
                SequenceScope p$ = null;
                int i$0 = 0;
                while (true) {
                    Label_0607: {
                        Label_0597: {
                            Iterator $iterator2;
                            while (true) {
                                Label_0288: {
                                    ArrayList<Object> list = null;
                                    Label_0256: {
                                        int n = 0;
                                        Iterator $iterator = null;
                                        switch (this.label) {
                                            case 0: {
                                                ResultKt.throwOnFailure(o);
                                                p$ = this.p$;
                                                i$0 = this.$step - this.$size;
                                                if (i$0 >= 0) {
                                                    list = new ArrayList<Object>(this.$size);
                                                    n = 0;
                                                    $iterator = this.$iterator;
                                                    break;
                                                }
                                                l$1 = new RingBuffer<Object>(this.$size);
                                                $iterator2 = this.$iterator;
                                                break Label_0444;
                                            }
                                            case 1: {
                                                $iterator = (Iterator)this.L$3;
                                                final Object l$2 = this.L$2;
                                                final int i$2 = this.I$1;
                                                list = (ArrayList<Object>)this.L$1;
                                                i$0 = this.I$0;
                                                p$ = (SequenceScope)this.L$0;
                                                ResultKt.throwOnFailure(o);
                                                break Label_0256;
                                            }
                                            case 2: {
                                                final int i$3 = this.I$1;
                                                final ArrayList list2 = (ArrayList)this.L$1;
                                                final int i$4 = this.I$0;
                                                final SequenceScope sequenceScope = (SequenceScope)this.L$0;
                                                ResultKt.throwOnFailure(o);
                                                return Unit.INSTANCE;
                                            }
                                            case 3: {
                                                $iterator2 = (Iterator)this.L$3;
                                                final Object l$3 = this.L$2;
                                                l$1 = (RingBuffer)this.L$1;
                                                i$0 = this.I$0;
                                                p$ = (SequenceScope)this.L$0;
                                                ResultKt.throwOnFailure(o);
                                                break Label_0597;
                                            }
                                            case 4: {
                                                l$1 = (RingBuffer)this.L$1;
                                                i$0 = this.I$0;
                                                p$ = (SequenceScope)this.L$0;
                                                ResultKt.throwOnFailure(o);
                                                break Label_0721;
                                            }
                                            case 5: {
                                                final RingBuffer ringBuffer = (RingBuffer)this.L$1;
                                                final int i$5 = this.I$0;
                                                final SequenceScope sequenceScope2 = (SequenceScope)this.L$0;
                                                ResultKt.throwOnFailure(o);
                                                return Unit.INSTANCE;
                                            }
                                            default: {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                        }
                                        if ($iterator.hasNext()) {
                                            final Object next = $iterator.next();
                                            if (n > 0) {
                                                --n;
                                                break Label_0288;
                                            }
                                            list.add(next);
                                            if (list.size() != this.$size) {
                                                break Label_0288;
                                            }
                                            final SequenceScope sequenceScope3 = p$;
                                            final ArrayList<Object> list3 = list;
                                            this.L$0 = p$;
                                            this.I$0 = i$0;
                                            this.L$1 = list;
                                            this.I$1 = n;
                                            this.L$2 = next;
                                            this.L$3 = $iterator;
                                            this.label = 1;
                                            if (sequenceScope3.yield(list3, this) == coroutine_SUSPENDED) {
                                                return coroutine_SUSPENDED;
                                            }
                                        }
                                        else {
                                            if (list.isEmpty() || (!this.$partialWindows && list.size() != this.$size)) {
                                                return Unit.INSTANCE;
                                            }
                                            final SequenceScope sequenceScope4 = p$;
                                            final ArrayList<Object> list4 = list;
                                            this.L$0 = p$;
                                            this.I$0 = i$0;
                                            this.L$1 = list;
                                            this.I$1 = n;
                                            this.label = 2;
                                            if (sequenceScope4.yield(list4, this) == coroutine_SUSPENDED) {
                                                return coroutine_SUSPENDED;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    if (this.$reuseBuffer) {
                                        list.clear();
                                    }
                                    else {
                                        list = new ArrayList<Object>(this.$size);
                                    }
                                    int n = i$0;
                                }
                                continue;
                            }
                            if ($iterator2.hasNext()) {
                                final Object next2 = $iterator2.next();
                                l$1.add(next2);
                                if (!l$1.isFull()) {
                                    break Label_0607;
                                }
                                final SequenceScope sequenceScope5 = p$;
                                final RandomAccess randomAccess = this.$reuseBuffer ? ((RingBuffer<Object>)l$1) : ((ArrayList<Object>)new ArrayList<Object>(l$1));
                                this.L$0 = p$;
                                this.I$0 = i$0;
                                this.L$1 = l$1;
                                this.L$2 = next2;
                                this.L$3 = $iterator2;
                                this.label = 3;
                                if (sequenceScope5.yield(randomAccess, this) == coroutine_SUSPENDED) {
                                    return coroutine_SUSPENDED;
                                }
                            }
                            else {
                                if (this.$partialWindows) {
                                    break Label_0617;
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        l$1.removeFirst(this.$step);
                    }
                    continue;
                }
                if (l$1.size() > this.$step) {
                    final SequenceScope sequenceScope6 = p$;
                    final RandomAccess randomAccess2 = this.$reuseBuffer ? ((RingBuffer<Object>)l$1) : ((ArrayList<Object>)new ArrayList<Object>(l$1));
                    this.L$0 = p$;
                    this.I$0 = i$0;
                    this.L$1 = l$1;
                    this.label = 4;
                    if (sequenceScope6.yield(randomAccess2, this) == coroutine_SUSPENDED) {
                        return coroutine_SUSPENDED;
                    }
                }
                else {
                    if (((RingBuffer<Object>)l$1).isEmpty()) {
                        return Unit.INSTANCE;
                    }
                    final SequenceScope sequenceScope7 = p$;
                    final RingBuffer<Object> ringBuffer2 = (RingBuffer<Object>)l$1;
                    this.L$0 = p$;
                    this.I$0 = i$0;
                    this.L$1 = l$1;
                    this.label = 5;
                    if (sequenceScope7.yield(ringBuffer2, this) == coroutine_SUSPENDED) {
                        return coroutine_SUSPENDED;
                    }
                    return Unit.INSTANCE;
                }
            }
            l$1.removeFirst(this.$step);
            continue Label_0617;
        }
    }
    
    SlidingWindowKt$windowedIterator$1(final int $step, final int $size, final Iterator $iterator, final boolean $reuseBuffer, final boolean $partialWindows, final Continuation continuation) {
        this.$step = $step;
        this.$size = $size;
        this.$iterator = $iterator;
        this.$reuseBuffer = $reuseBuffer;
        this.$partialWindows = $partialWindows;
        super(2, continuation);
    }
    
    @NotNull
    @Override
    public final Continuation<Unit> create(@Nullable final Object o, @NotNull final Continuation<?> continuation) {
        final SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$step, this.$size, this.$iterator, this.$reuseBuffer, this.$partialWindows, (Continuation)continuation);
        (SequenceScope)o;
        slidingWindowKt$windowedIterator$1.p$ = (SequenceScope)o;
        return (Continuation<Unit>)slidingWindowKt$windowedIterator$1;
    }
    
    @Override
    public final Object invoke(final Object o, final Object o2) {
        return ((SlidingWindowKt$windowedIterator$1)this.create(o, (Continuation)o2)).invokeSuspend((Object)Unit.INSTANCE);
    }
}