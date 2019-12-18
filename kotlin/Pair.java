package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00012\u00060\u0003j\u0002`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00028\u0001¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\tJ\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\tJ.\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\t¨\u0006\u0018"},
   d2 = {"Lkotlin/Pair;", "A", "B", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}
)
public final class Pair implements Serializable {
   private final Object first;
   private final Object second;

   @NotNull
   public String toString() {
      return "" + '(' + this.first + ", " + this.second + ')';
   }

   public final Object getFirst() {
      return this.first;
   }

   public final Object getSecond() {
      return this.second;
   }

   public Pair(Object var1, Object var2) {
      super();
      this.first = var1;
      this.second = var2;
   }

   public final Object component1() {
      return this.first;
   }

   public final Object component2() {
      return this.second;
   }

   @NotNull
   public final Pair copy(Object var1, Object var2) {
      return new Pair(var1, var2);
   }

   public static Pair copy$default(Pair var0, Object var1, Object var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.first;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.second;
      }

      return var0.copy(var1, var2);
   }

   public int hashCode() {
      Object var10000 = this.first;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      Object var10001 = this.second;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Pair) {
            Pair var2 = (Pair)var1;
            if (Intrinsics.areEqual(this.first, var2.first) && Intrinsics.areEqual(this.second, var2.second)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
