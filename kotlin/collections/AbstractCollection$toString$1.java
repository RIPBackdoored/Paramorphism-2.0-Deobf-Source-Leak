package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002 \u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"},
   d2 = {"<anonymous>", "", "E", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/CharSequence;"}
)
final class AbstractCollection$toString$1 extends Lambda implements Function1 {
   final AbstractCollection this$0;

   public Object invoke(Object var1) {
      return this.invoke(var1);
   }

   @NotNull
   public final CharSequence invoke(Object var1) {
      return var1 == this.this$0 ? (CharSequence)"(this Collection)" : (CharSequence)String.valueOf(var1);
   }

   AbstractCollection$toString$1(AbstractCollection var1) {
      super(1);
      this.this$0 = var1;
   }
}
