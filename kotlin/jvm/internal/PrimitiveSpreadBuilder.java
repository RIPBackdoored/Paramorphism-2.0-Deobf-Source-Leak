package kotlin.jvm.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0004H\u0004J\u001d\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0004*\u00028\u0000H$¢\u0006\u0002\u0010\u0018R\u001a\u0010\u0006\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000bX\u0082\u0004¢\u0006\n\n\u0002\u0010\u000e\u0012\u0004\b\f\u0010\r¨\u0006\u0019"},
   d2 = {"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "spreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"}
)
public abstract class PrimitiveSpreadBuilder {
   private int position;
   private final Object[] spreads;
   private final int size;

   protected abstract int getSize(@NotNull Object var1);

   protected final int getPosition() {
      return this.position;
   }

   protected final void setPosition(int var1) {
      this.position = var1;
   }

   /** @deprecated */
   private static void spreads$annotations() {
   }

   public final void addSpread(@NotNull Object var1) {
      Object[] var10000 = this.spreads;
      int var2;
      this.position = (var2 = this.position) + 1;
      var10000[var2] = var1;
   }

   protected final int size() {
      int var1 = 0;
      int var2 = 0;
      int var3 = this.size - 1;
      if (var2 <= var3) {
         while(true) {
            Object var10001 = this.spreads[var2];
            var1 += var10001 != null ? this.getSize(var10001) : 1;
            if (var2 == var3) {
               break;
            }

            ++var2;
         }
      }

      return var1;
   }

   @NotNull
   protected final Object toArray(@NotNull Object var1, @NotNull Object var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = this.size - 1;
      if (var5 <= var6) {
         while(true) {
            Object var7 = this.spreads[var5];
            if (var7 != null) {
               if (var4 < var5) {
                  System.arraycopy(var1, var4, var2, var3, var5 - var4);
                  var3 += var5 - var4;
               }

               int var8 = this.getSize(var7);
               System.arraycopy(var7, 0, var2, var3, var8);
               var3 += var8;
               var4 = var5 + 1;
            }

            if (var5 == var6) {
               break;
            }

            ++var5;
         }
      }

      if (var4 < this.size) {
         System.arraycopy(var1, var4, var2, var3, this.size - var4);
      }

      return var2;
   }

   public PrimitiveSpreadBuilder(int var1) {
      super();
      this.size = var1;
      this.spreads = new Object[this.size];
   }
}
