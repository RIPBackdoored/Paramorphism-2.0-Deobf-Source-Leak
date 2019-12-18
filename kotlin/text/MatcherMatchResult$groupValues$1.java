package kotlin.text;

import kotlin.Metadata;
import kotlin.collections.AbstractList;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0004H\u0096\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"},
   d2 = {"kotlin/text/MatcherMatchResult$groupValues$1", "Lkotlin/collections/AbstractList;", "", "size", "", "getSize", "()I", "get", "index", "kotlin-stdlib"}
)
public final class MatcherMatchResult$groupValues$1 extends AbstractList {
   final MatcherMatchResult this$0;

   public int getSize() {
      return MatcherMatchResult.access$getMatchResult$p(this.this$0).groupCount() + 1;
   }

   @NotNull
   public String get(int var1) {
      String var10000 = MatcherMatchResult.access$getMatchResult$p(this.this$0).group(var1);
      if (var10000 == null) {
         var10000 = "";
      }

      return var10000;
   }

   public Object get(int var1) {
      return this.get(var1);
   }

   MatcherMatchResult$groupValues$1(MatcherMatchResult var1) {
      super();
      this.this$0 = var1;
   }

   public int indexOf(String var1) {
      return super.indexOf(var1);
   }

   public final int indexOf(Object var1) {
      return var1 instanceof String ? this.indexOf((String)var1) : -1;
   }

   public int lastIndexOf(String var1) {
      return super.lastIndexOf(var1);
   }

   public final int lastIndexOf(Object var1) {
      return var1 instanceof String ? this.lastIndexOf((String)var1) : -1;
   }

   public boolean contains(String var1) {
      return super.contains(var1);
   }

   public final boolean contains(Object var1) {
      return var1 instanceof String ? this.contains((String)var1) : false;
   }
}
