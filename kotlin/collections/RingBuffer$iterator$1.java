package kotlin.collections;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u0005\u001a\u00020\u0006H\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"},
   d2 = {"kotlin/collections/RingBuffer$iterator$1", "Lkotlin/collections/AbstractIterator;", "count", "", "index", "computeNext", "", "kotlin-stdlib"}
)
public final class RingBuffer$iterator$1 extends AbstractIterator {
   private int count;
   private int index;
   final RingBuffer this$0;

   protected void computeNext() {
      if (this.count == 0) {
         this.done();
      } else {
         this.setNext(RingBuffer.access$getBuffer$p(this.this$0)[this.index]);
         int var2 = this.index;
         RingBuffer var1 = this.this$0;
         byte var3 = 1;
         boolean var4 = false;
         int var6 = (var2 + var3) % var1.getCapacity();
         this.index = var6;
         this.count += -1;
      }

   }

   RingBuffer$iterator$1(RingBuffer var1) {
      super();
      this.this$0 = var1;
      this.count = var1.size();
      this.index = RingBuffer.access$getStartIndex$p(var1);
   }
}
