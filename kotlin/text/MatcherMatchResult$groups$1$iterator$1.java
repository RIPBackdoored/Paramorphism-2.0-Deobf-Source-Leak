package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "Lkotlin/text/MatchGroup;", "it", "", "invoke"}
)
final class MatcherMatchResult$groups$1$iterator$1 extends Lambda implements Function1 {
   final MatcherMatchResult$groups$1 this$0;

   public Object invoke(Object var1) {
      return this.invoke(((Number)var1).intValue());
   }

   @Nullable
   public final MatchGroup invoke(int var1) {
      return this.this$0.get(var1);
   }

   MatcherMatchResult$groups$1$iterator$1(MatcherMatchResult$groups$1 var1) {
      super(1);
      this.this$0 = var1;
   }
}
