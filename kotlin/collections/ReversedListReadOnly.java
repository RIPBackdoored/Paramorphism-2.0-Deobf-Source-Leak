package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0012\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0016\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"},
   d2 = {"Lkotlin/collections/ReversedListReadOnly;", "T", "Lkotlin/collections/AbstractList;", "delegate", "", "(Ljava/util/List;)V", "size", "", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "kotlin-stdlib"}
)
class ReversedListReadOnly extends AbstractList {
   private final List delegate;

   public int getSize() {
      return this.delegate.size();
   }

   public Object get(int var1) {
      return this.delegate.get(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, var1));
   }

   public ReversedListReadOnly(@NotNull List var1) {
      super();
      this.delegate = var1;
   }
}
