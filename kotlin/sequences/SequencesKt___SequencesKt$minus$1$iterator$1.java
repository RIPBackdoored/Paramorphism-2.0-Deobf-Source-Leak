package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"},
   d2 = {"<anonymous>", "", "T", "it", "invoke", "(Ljava/lang/Object;)Z"}
)
final class SequencesKt___SequencesKt$minus$1$iterator$1 extends Lambda implements Function1 {
   final SequencesKt___SequencesKt$minus$1 this$0;
   final Ref$BooleanRef $removed;

   public Object invoke(Object var1) {
      return this.invoke(var1);
   }

   public final boolean invoke(Object var1) {
      boolean var10000;
      if (!this.$removed.element && Intrinsics.areEqual(var1, this.this$0.$element)) {
         this.$removed.element = true;
         var10000 = false;
      } else {
         var10000 = true;
      }

      return var10000;
   }

   SequencesKt___SequencesKt$minus$1$iterator$1(SequencesKt___SequencesKt$minus$1 var1, Ref$BooleanRef var2) {
      super(1);
      this.this$0 = var1;
      this.$removed = var2;
   }
}
