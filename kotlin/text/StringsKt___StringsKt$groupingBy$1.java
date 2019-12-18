package kotlin.text;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.Grouping;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00028\u00000\u0001J\u0015\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0016¨\u0006\b"},
   d2 = {"kotlin/text/StringsKt___StringsKt$groupingBy$1", "Lkotlin/collections/Grouping;", "", "keyOf", "element", "(C)Ljava/lang/Object;", "sourceIterator", "", "kotlin-stdlib"}
)
public final class StringsKt___StringsKt$groupingBy$1 implements Grouping {
   final CharSequence $this_groupingBy;
   final Function1 $keySelector;

   @NotNull
   public Iterator sourceIterator() {
      return (Iterator)StringsKt.iterator(this.$this_groupingBy);
   }

   public Object keyOf(char var1) {
      return this.$keySelector.invoke(var1);
   }

   public Object keyOf(Object var1) {
      return this.keyOf((Character)var1);
   }

   public StringsKt___StringsKt$groupingBy$1(CharSequence var1, Function1 var2) {
      super();
      this.$this_groupingBy = var1;
      this.$keySelector = var2;
   }
}
