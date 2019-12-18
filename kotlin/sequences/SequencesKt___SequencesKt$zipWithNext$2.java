package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@DebugMetadata(
   f = "_Sequences.kt",
   l = {1701},
   i = {0, 0, 0, 0},
   s = {"L$0", "L$1", "L$2", "L$3"},
   n = {"$this$result", "iterator", "current", "next"},
   m = "invokeSuspend",
   c = "kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2"
)
@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"},
   d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}
)
final class SequencesKt___SequencesKt$zipWithNext$2 extends RestrictedSuspendLambda implements Function2 {
   private SequenceScope p$;
   Object L$0;
   Object L$1;
   Object L$2;
   Object L$3;
   int label;
   final Sequence $this_zipWithNext;
   final Function2 $transform;

   @Nullable
   public final Object invokeSuspend(@NotNull Object var1) {
      Object var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      SequenceScope var2;
      Iterator var3;
      Object var4;
      Object var5;
      switch(this.label) {
      case 0:
         ResultKt.throwOnFailure(var1);
         var2 = this.p$;
         var3 = this.$this_zipWithNext.iterator();
         if (!var3.hasNext()) {
            return Unit.INSTANCE;
         }

         var4 = var3.next();
         break;
      case 1:
         var5 = this.L$3;
         var4 = this.L$2;
         var3 = (Iterator)this.L$1;
         var2 = (SequenceScope)this.L$0;
         ResultKt.throwOnFailure(var1);
         var4 = var5;
         break;
      default:
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      while(var3.hasNext()) {
         var5 = var3.next();
         Object var10001 = this.$transform.invoke(var4, var5);
         this.L$0 = var2;
         this.L$1 = var3;
         this.L$2 = var4;
         this.L$3 = var5;
         this.label = 1;
         if (var2.yield(var10001, this) == var6) {
            return var6;
         }

         var4 = var5;
      }

      return Unit.INSTANCE;
   }

   SequencesKt___SequencesKt$zipWithNext$2(Sequence var1, Function2 var2, Continuation var3) {
      super(2, var3);
      this.$this_zipWithNext = var1;
      this.$transform = var2;
   }

   @NotNull
   public final Continuation create(@Nullable Object var1, @NotNull Continuation var2) {
      SequencesKt___SequencesKt$zipWithNext$2 var3 = new SequencesKt___SequencesKt$zipWithNext$2(this.$this_zipWithNext, this.$transform, var2);
      var3.p$ = (SequenceScope)var1;
      return var3;
   }

   public final Object invoke(Object var1, Object var2) {
      return ((SequencesKt___SequencesKt$zipWithNext$2)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
   }
}
