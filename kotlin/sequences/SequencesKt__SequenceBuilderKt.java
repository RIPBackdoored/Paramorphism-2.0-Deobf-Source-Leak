package kotlin.sequences;

import kotlin.jvm.functions.*;
import kotlin.coroutines.*;
import org.jetbrains.annotations.*;
import java.util.*;
import kotlin.*;
import kotlin.internal.*;
import kotlin.coroutines.intrinsics.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000R\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aN\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2/\b\t\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0087\b\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aN\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\u0014\"\u0004\b\u0000\u0010\n2/\b\t\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0087\b\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aM\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2/\b\u0001\u0010\u0017\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0007\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aM\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\n0\u0014\"\u0004\b\u0000\u0010\n2/\b\u0001\u0010\u0017\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f¢\u0006\u0002\b\u0011H\u0007\u00f8\u0001\u0000¢\u0006\u0002\u0010\u0015\"\u0012\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0003\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0004\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0005\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0012\u0010\u0007\u001a\u00060\u0001j\u0002`\u0002X\u0082T¢\u0006\u0002\n\u0000*V\b\u0007\u0010\u0019\u001a\u0004\b\u0000\u0010\n\"\b\u0012\u0004\u0012\u0002H\n0\r2\b\u0012\u0004\u0012\u0002H\n0\rB6\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u001c\b\u001d\u0012\u0018\b\u000bB\u0014\b\u001e\u0012\u0006\b\u001f\u0012\u0002\b\f\u0012\b\b \u0012\u0004\b\b(!\u0012\n\b\"\u0012\u0006\b\n0#8$*\f\b\u0002\u0010%\"\u00020\u00012\u00020\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006&" }, d2 = { "State_Done", "", "Lkotlin/sequences/State;", "State_Failed", "State_ManyNotReady", "State_ManyReady", "State_NotReady", "State_Ready", "buildIterator", "", "T", "builderAction", "Lkotlin/Function2;", "Lkotlin/sequences/SequenceScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "buildSequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/jvm/functions/Function2;)Lkotlin/sequences/Sequence;", "iterator", "block", "sequence", "SequenceBuilder", "Lkotlin/Deprecated;", "message", "Use SequenceScope class instead.", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "expression", "SequenceScope<T>", "level", "Lkotlin/DeprecationLevel;", "ERROR", "State", "kotlin-stdlib" }, xs = "kotlin/sequences/SequencesKt")
class SequencesKt__SequenceBuilderKt
{
    private static final int State_NotReady = 0;
    private static final int State_ManyNotReady = 1;
    private static final int State_ManyReady = 2;
    private static final int State_Ready = 3;
    private static final int State_Done = 4;
    private static final int State_Failed = 5;
    
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> Sequence<T> sequence(@BuilderInference @NotNull final Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        return new Sequence<T>(function2) {
            final Function2 $block$inlined;
            
            public SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(final Function2 $block$inlined) {
                this.$block$inlined = $block$inlined;
                super();
            }
            
            @NotNull
            @Override
            public Iterator<T> iterator() {
                return SequencesKt__SequenceBuilderKt.iterator((Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?>)this.$block$inlined);
            }
        };
    }
    
    @Deprecated(message = "Use 'sequence { }' function instead.", replaceWith = @ReplaceWith(imports = {}, expression = "sequence(builderAction)"), level = DeprecationLevel.ERROR)
    @SinceKotlin(version = "1.3")
    @InlineOnly
    @java.lang.Deprecated
    private static final <T> Sequence<T> buildSequence(@BuilderInference final Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        return new Sequence<T>(function2) {
            final Function2 $builderAction$inlined;
            
            public SequencesKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1(final Function2 $builderAction$inlined) {
                this.$builderAction$inlined = $builderAction$inlined;
                super();
            }
            
            @NotNull
            @Override
            public Iterator<T> iterator() {
                return SequencesKt__SequenceBuilderKt.iterator((Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?>)this.$builderAction$inlined);
            }
        };
    }
    
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> Iterator<T> iterator(@BuilderInference @NotNull final Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        final SequenceBuilderIterator sequenceBuilderIterator = new SequenceBuilderIterator<T>();
        sequenceBuilderIterator.setNextStep(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted((Function2<? super SequenceBuilderIterator, ? super Continuation<? super Object>, ?>)function2, sequenceBuilderIterator, (Continuation<? super Object>)sequenceBuilderIterator));
        return (SequenceBuilderIterator<T>)sequenceBuilderIterator;
    }
    
    @Deprecated(message = "Use 'iterator { }' function instead.", replaceWith = @ReplaceWith(imports = {}, expression = "iterator(builderAction)"), level = DeprecationLevel.ERROR)
    @SinceKotlin(version = "1.3")
    @InlineOnly
    @java.lang.Deprecated
    private static final <T> Iterator<T> buildIterator(@BuilderInference final Function2<? super SequenceScope<? super T>, ? super Continuation<? super Unit>, ?> function2) {
        return (Iterator<T>)iterator((Function2<? super SequenceScope<? super Object>, ? super Continuation<? super Unit>, ?>)function2);
    }
    
    @Deprecated(message = "Use SequenceScope class instead.", replaceWith = @ReplaceWith(imports = {}, expression = "SequenceScope<T>"), level = DeprecationLevel.ERROR)
    @java.lang.Deprecated
    public static void SequenceBuilder$annotations() {
    }
    
    public SequencesKt__SequenceBuilderKt() {
        super();
    }
}
