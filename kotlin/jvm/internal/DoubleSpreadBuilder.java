package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/jvm/internal/DoubleSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "", "toArray", "getSize", "kotlin-stdlib"}
)
public final class DoubleSpreadBuilder extends PrimitiveSpreadBuilder {
   private final double[] values;

   protected int getSize(@NotNull double[] var1) {
      return var1.length;
   }

   public int getSize(Object var1) {
      return this.getSize((double[])var1);
   }

   public final void add(double var1) {
      double[] var10000 = this.values;
      int var3;
      this.setPosition((var3 = this.getPosition()) + 1);
      var10000[var3] = var1;
   }

   @NotNull
   public final double[] toArray() {
      return (double[])this.toArray(this.values, new double[this.size()]);
   }

   public DoubleSpreadBuilder(int var1) {
      super(var1);
      this.values = new double[var1];
   }
}
