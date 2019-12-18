package kotlin.text;

import kotlin.Metadata;
import kotlin.collections.CharIterator;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\t\u0010\u0004\u001a\u00020\u0005H\u0096\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
   d2 = {"kotlin/text/StringsKt__StringsKt$iterator$1", "Lkotlin/collections/CharIterator;", "index", "", "hasNext", "", "nextChar", "", "kotlin-stdlib"}
)
public final class StringsKt__StringsKt$iterator$1 extends CharIterator {
   private int index;
   final CharSequence $this_iterator;

   public char nextChar() {
      CharSequence var10000 = this.$this_iterator;
      int var1;
      this.index = (var1 = this.index) + 1;
      return var10000.charAt(var1);
   }

   public boolean hasNext() {
      return this.index < this.$this_iterator.length();
   }

   StringsKt__StringsKt$iterator$1(CharSequence var1) {
      super();
      this.$this_iterator = var1;
   }
}
