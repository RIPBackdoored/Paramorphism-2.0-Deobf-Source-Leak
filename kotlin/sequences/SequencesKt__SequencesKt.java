package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000b\u001a=\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000bH\u0007¢\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010\"\u0002H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00050\u000bH\u0002¢\u0006\u0002\b\u0016\u001a)\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00170\u0001H\u0007¢\u0006\u0002\b\u0018\u001a\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a@\u0010\u001c\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001e0\u001d\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00150\u001d0\u0001¨\u0006\u001f"},
   d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"},
   xs = "kotlin/sequences/SequencesKt"
)
class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
   @InlineOnly
   private static final Sequence Sequence(Function0 var0) {
      byte var1 = 0;
      return (Sequence)(new SequencesKt__SequencesKt$Sequence$1(var0));
   }

   @NotNull
   public static final Sequence asSequence(@NotNull Iterator var0) {
      boolean var1 = false;
      return SequencesKt.constrainOnce((Sequence)(new SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1(var0)));
   }

   @NotNull
   public static final Sequence sequenceOf(@NotNull Object... var0) {
      boolean var2 = false;
      return var0.length == 0 ? SequencesKt.emptySequence() : ArraysKt.asSequence(var0);
   }

   @NotNull
   public static final Sequence emptySequence() {
      return (Sequence)EmptySequence.INSTANCE;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Sequence orEmpty(@Nullable Sequence var0) {
      byte var1 = 0;
      Sequence var10000 = var0;
      if (var0 == null) {
         var10000 = SequencesKt.emptySequence();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Sequence ifEmpty(@NotNull Sequence var0, @NotNull Function0 var1) {
      return SequencesKt.sequence((Function2)(new SequencesKt__SequencesKt$ifEmpty$1(var0, var1, (Continuation)null)));
   }

   @NotNull
   public static final Sequence flatten(@NotNull Sequence var0) {
      return flatten$SequencesKt__SequencesKt(var0, (Function1)SequencesKt__SequencesKt$flatten$1.INSTANCE);
   }

   @JvmName(
      name = "flattenSequenceOfIterable"
   )
   @NotNull
   public static final Sequence flattenSequenceOfIterable(@NotNull Sequence var0) {
      return flatten$SequencesKt__SequencesKt(var0, (Function1)SequencesKt__SequencesKt$flatten$2.INSTANCE);
   }

   private static final Sequence flatten$SequencesKt__SequencesKt(@NotNull Sequence var0, Function1 var1) {
      return var0 instanceof TransformingSequence ? ((TransformingSequence)var0).flatten$kotlin_stdlib(var1) : (Sequence)(new FlatteningSequence(var0, (Function1)SequencesKt__SequencesKt$flatten$3.INSTANCE, var1));
   }

   @NotNull
   public static final Pair unzip(@NotNull Sequence var0) {
      ArrayList var1 = new ArrayList();
      ArrayList var2 = new ArrayList();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Pair var3 = (Pair)var4.next();
         var1.add(var3.getFirst());
         var2.add(var3.getSecond());
      }

      return TuplesKt.to(var1, var2);
   }

   @NotNull
   public static final Sequence constrainOnce(@NotNull Sequence var0) {
      return var0 instanceof ConstrainedOnceSequence ? var0 : (Sequence)(new ConstrainedOnceSequence(var0));
   }

   @NotNull
   public static final Sequence generateSequence(@NotNull Function0 var0) {
      return SequencesKt.constrainOnce((Sequence)(new GeneratorSequence(var0, (Function1)(new SequencesKt__SequencesKt$generateSequence$1(var0)))));
   }

   @LowPriorityInOverloadResolution
   @NotNull
   public static final Sequence generateSequence(@Nullable Object var0, @NotNull Function1 var1) {
      return var0 == null ? (Sequence)EmptySequence.INSTANCE : (Sequence)(new GeneratorSequence((Function0)(new SequencesKt__SequencesKt$generateSequence$2(var0)), var1));
   }

   @NotNull
   public static final Sequence generateSequence(@NotNull Function0 var0, @NotNull Function1 var1) {
      return (Sequence)(new GeneratorSequence(var0, var1));
   }

   public SequencesKt__SequencesKt() {
      super();
   }
}
