package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000%\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\t\u0010\u0019\u001a\u00020\u001aH\u0096\u0002J\t\u0010\u001b\u001a\u00020\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\b¨\u0006\u001c"},
   d2 = {"kotlin/text/DelimitedRangesSequence$iterator$1", "", "Lkotlin/ranges/IntRange;", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "currentStartIndex", "getCurrentStartIndex", "setCurrentStartIndex", "nextItem", "getNextItem", "()Lkotlin/ranges/IntRange;", "setNextItem", "(Lkotlin/ranges/IntRange;)V", "nextSearchIndex", "getNextSearchIndex", "setNextSearchIndex", "nextState", "getNextState", "setNextState", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"}
)
public final class DelimitedRangesSequence$iterator$1 implements Iterator, KMappedMarker {
   private int nextState;
   private int currentStartIndex;
   private int nextSearchIndex;
   @Nullable
   private IntRange nextItem;
   private int counter;
   final DelimitedRangesSequence this$0;

   public final int getNextState() {
      return this.nextState;
   }

   public final void setNextState(int var1) {
      this.nextState = var1;
   }

   public final int getCurrentStartIndex() {
      return this.currentStartIndex;
   }

   public final void setCurrentStartIndex(int var1) {
      this.currentStartIndex = var1;
   }

   public final int getNextSearchIndex() {
      return this.nextSearchIndex;
   }

   public final void setNextSearchIndex(int var1) {
      this.nextSearchIndex = var1;
   }

   @Nullable
   public final IntRange getNextItem() {
      return this.nextItem;
   }

   public final void setNextItem(@Nullable IntRange var1) {
      this.nextItem = var1;
   }

   public final int getCounter() {
      return this.counter;
   }

   public final void setCounter(int var1) {
      this.counter = var1;
   }

   private final void calcNext() {
      if (this.nextSearchIndex < 0) {
         this.nextState = 0;
         this.nextItem = (IntRange)null;
      } else {
         label27: {
            label26: {
               if (DelimitedRangesSequence.access$getLimit$p(this.this$0) > 0) {
                  ++this.counter;
                  if (this.counter >= DelimitedRangesSequence.access$getLimit$p(this.this$0)) {
                     break label26;
                  }
               }

               if (this.nextSearchIndex <= DelimitedRangesSequence.access$getInput$p(this.this$0).length()) {
                  Pair var1 = (Pair)DelimitedRangesSequence.access$getGetNextMatch$p(this.this$0).invoke(DelimitedRangesSequence.access$getInput$p(this.this$0), this.nextSearchIndex);
                  int var2;
                  if (var1 == null) {
                     var2 = this.currentStartIndex;
                     this.nextItem = new IntRange(var2, StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
                     this.nextSearchIndex = -1;
                  } else {
                     var2 = ((Number)var1.component1()).intValue();
                     int var3 = ((Number)var1.component2()).intValue();
                     this.nextItem = RangesKt.until(this.currentStartIndex, var2);
                     this.currentStartIndex = var2 + var3;
                     this.nextSearchIndex = this.currentStartIndex + (var3 == 0 ? 1 : 0);
                  }
                  break label27;
               }
            }

            int var5 = this.currentStartIndex;
            this.nextItem = new IntRange(var5, StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
            this.nextSearchIndex = -1;
         }

         this.nextState = 1;
      }

   }

   @NotNull
   public IntRange next() {
      if (this.nextState == -1) {
         this.calcNext();
      }

      if (this.nextState == 0) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         IntRange var10000 = this.nextItem;
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
         } else {
            IntRange var1 = var10000;
            this.nextItem = (IntRange)null;
            this.nextState = -1;
            return var1;
         }
      }
   }

   public Object next() {
      return this.next();
   }

   public boolean hasNext() {
      if (this.nextState == -1) {
         this.calcNext();
      }

      return this.nextState == 1;
   }

   DelimitedRangesSequence$iterator$1(DelimitedRangesSequence var1) {
      super();
      this.this$0 = var1;
      this.nextState = -1;
      this.currentStartIndex = RangesKt.coerceIn(DelimitedRangesSequence.access$getStartIndex$p(var1), 0, DelimitedRangesSequence.access$getInput$p(var1).length());
      this.nextSearchIndex = this.currentStartIndex;
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
