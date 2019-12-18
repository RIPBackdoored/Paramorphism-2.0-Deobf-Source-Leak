package kotlin.sequences;

import kotlin.Metadata;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "", "T", "it", "Lkotlin/collections/IndexedValue;", "invoke"}
)
final class SequencesKt___SequencesKt$filterIndexed$1 extends Lambda implements Function1 {
   final Function2 $predicate;

   public Object invoke(Object var1) {
      return this.invoke((IndexedValue)var1);
   }

   public final boolean invoke(@NotNull IndexedValue var1) {
      return (Boolean)this.$predicate.invoke(var1.getIndex(), var1.getValue());
   }

   SequencesKt___SequencesKt$filterIndexed$1(Function2 var1) {
      super(1);
      this.$predicate = var1;
   }
}
