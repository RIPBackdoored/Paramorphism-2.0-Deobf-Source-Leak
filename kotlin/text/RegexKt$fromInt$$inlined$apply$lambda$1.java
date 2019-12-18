package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\u0010\u0005\u001a\n \u0006*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"<anonymous>", "", "T", "Lkotlin/text/FlagEnum;", "", "it", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Enum;)Z", "kotlin/text/RegexKt$fromInt$1$1"}
)
public final class RegexKt$fromInt$$inlined$apply$lambda$1 extends Lambda implements Function1 {
   final int $value$inlined;

   public RegexKt$fromInt$$inlined$apply$lambda$1(int var1) {
      super(1);
      this.$value$inlined = var1;
   }

   public Object invoke(Object var1) {
      return this.invoke((Enum)var1);
   }

   public final boolean invoke(Enum var1) {
      return (this.$value$inlined & ((FlagEnum)var1).getMask()) == ((FlagEnum)var1).getValue();
   }
}
