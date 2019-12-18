package kotlin.coroutines.experimental;

import kotlin.jvm.functions.*;
import org.jetbrains.annotations.*;
import kotlin.sequences.*;
import java.util.*;
import kotlin.*;
import kotlin.coroutines.experimental.intrinsics.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000:\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aM\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2/\b\u0001\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0007\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aM\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\u0014\"\u0004\b\u0000\u0010\n2/\b\u0001\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0007\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0015\"\u0012\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0003\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0004\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0005\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0007\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000*\f\b\u0002\u0010\u0016\"\u00020\u00012\u00020\u0001\u0082\u0002\u0004\n\u0002\b\t¨\u0006\u0017" }, d2 = { "State_Done", "", "Lkotlin/coroutines/experimental/State;", "State_Failed", "State_ManyNotReady", "State_ManyReady", "State_NotReady", "State_Ready", "buildIterator", "", "T", "builderAction", "Lkotlin/Function2;", "Lkotlin/coroutines/experimental/SequenceBuilder;", "Lkotlin/coroutines/experimental/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "buildSequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/jvm/functions/Function2;)Lkotlin/sequences/Sequence;", "State", "kotlin-stdlib-coroutines" }, xs = "kotlin/coroutines/experimental/SequenceBuilderKt")
class SequenceBuilderKt__SequenceBuilderKt
{
    private static final int State_NotReady = 0;
    private static final int State_ManyNotReady = 1;
    private static final int State_ManyReady = 2;
    private static final int State_Ready = 3;
    private static final int State_Done = 4;
    private static final int State_Failed = 5;
    
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T> Sequence<T> buildSequence(@BuilderInference @NotNull final Function2<? super SequenceBuilder<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        return new Sequence<T>(function2) {
            final Function2 $builderAction$inlined;
            
            public SequenceBuilderKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1(final Function2 $builderAction$inlined) {
                this.$builderAction$inlined = $builderAction$inlined;
                super();
            }
            
            @NotNull
            @Override
            public Iterator<T> iterator() {
                return SequenceBuilderKt__SequenceBuilderKt.buildIterator((Function2<? super SequenceBuilder<? super T>, ? super Continuation<? super Unit>, ?>)this.$builderAction$inlined);
            }
        };
    }
    
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T> Iterator<T> buildIterator(@BuilderInference @NotNull final Function2<? super SequenceBuilder<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        final SequenceBuilderIterator sequenceBuilderIterator = new SequenceBuilderIterator<T>();
        sequenceBuilderIterator.setNextStep(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnchecked((Function2<? super SequenceBuilderIterator, ? super Continuation<? super Object>, ?>)function2, sequenceBuilderIterator, (Continuation<? super Object>)sequenceBuilderIterator));
        return (SequenceBuilderIterator<T>)sequenceBuilderIterator;
    }
    
    public SequenceBuilderKt__SequenceBuilderKt() {
        super();
    }
}
