package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "", "acc", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}
)
final class CombinedContext$toString$1 extends Lambda implements Function2 {
   public static final CombinedContext$toString$1 INSTANCE = new CombinedContext$toString$1();

   public Object invoke(Object var1, Object var2) {
      return this.invoke((String)var1, (CoroutineContext$Element)var2);
   }

   @NotNull
   public final String invoke(@NotNull String var1, @NotNull CoroutineContext$Element var2) {
      CharSequence var3 = (CharSequence)var1;
      boolean var4 = false;
      return var3.length() == 0 ? var2.toString() : var1 + ", " + var2;
   }

   CombinedContext$toString$1() {
      super(2);
   }
}
