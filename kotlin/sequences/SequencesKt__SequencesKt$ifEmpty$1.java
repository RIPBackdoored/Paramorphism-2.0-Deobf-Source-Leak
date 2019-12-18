package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@DebugMetadata(
   f = "Sequences.kt",
   l = {67, 69},
   i = {0, 0, 1, 1},
   s = {"L$0", "L$1", "L$0", "L$1"},
   n = {"$this$sequence", "iterator", "$this$sequence", "iterator"},
   m = "invokeSuspend",
   c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1"
)
@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"},
   d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}
)
final class SequencesKt__SequencesKt$ifEmpty$1 extends RestrictedSuspendLambda implements Function2 {
   private SequenceScope p$;
   Object L$0;
   Object L$1;
   int label;
   final Sequence $this_ifEmpty;
   final Function0 $defaultValue;

   @Nullable
   public final Object invokeSuspend(@NotNull Object var1) {
      Object var4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      SequenceScope var2;
      Iterator var3;
      switch(this.label) {
      case 0:
         ResultKt.throwOnFailure(var1);
         var2 = this.p$;
         var3 = this.$this_ifEmpty.iterator();
         if (var3.hasNext()) {
            this.L$0 = var2;
            this.L$1 = var3;
            this.label = 1;
            if (var2.yieldAll((Iterator)var3, this) == var4) {
               return var4;
            }
         } else {
            Sequence var10001 = (Sequence)this.$defaultValue.invoke();
            this.L$0 = var2;
            this.L$1 = var3;
            this.label = 2;
            if (var2.yieldAll((Sequence)var10001, this) == var4) {
               return var4;
            }
         }
         break;
      case 1:
         var3 = (Iterator)this.L$1;
         var2 = (SequenceScope)this.L$0;
         ResultKt.throwOnFailure(var1);
         break;
      case 2:
         var3 = (Iterator)this.L$1;
         var2 = (SequenceScope)this.L$0;
         ResultKt.throwOnFailure(var1);
         break;
      default:
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      return Unit.INSTANCE;
   }

   SequencesKt__SequencesKt$ifEmpty$1(Sequence var1, Function0 var2, Continuation var3) {
      super(2, var3);
      this.$this_ifEmpty = var1;
      this.$defaultValue = var2;
   }

   @NotNull
   public final Continuation create(@Nullable Object var1, @NotNull Continuation var2) {
      SequencesKt__SequencesKt$ifEmpty$1 var3 = new SequencesKt__SequencesKt$ifEmpty$1(this.$this_ifEmpty, this.$defaultValue, var2);
      var3.p$ = (SequenceScope)var1;
      return var3;
   }

   public final Object invoke(Object var1, Object var2) {
      return ((SequencesKt__SequencesKt$ifEmpty$1)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
   }
}
