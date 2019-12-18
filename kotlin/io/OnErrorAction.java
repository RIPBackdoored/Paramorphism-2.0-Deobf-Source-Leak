package kotlin.io;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/io/OnErrorAction;", "", "(Ljava/lang/String;I)V", "SKIP", "TERMINATE", "kotlin-stdlib"}
)
public final class OnErrorAction extends Enum {
   public static final OnErrorAction SKIP;
   public static final OnErrorAction TERMINATE;
   private static final OnErrorAction[] $VALUES = new OnErrorAction[]{SKIP = new OnErrorAction("SKIP", 0), TERMINATE = new OnErrorAction("TERMINATE", 1)};

   private OnErrorAction(String var1, int var2) {
      super(var1, var2);
   }

   public static OnErrorAction[] values() {
      return (OnErrorAction[])$VALUES.clone();
   }

   public static OnErrorAction valueOf(String var0) {
      return (OnErrorAction)Enum.valueOf(OnErrorAction.class, var0);
   }
}
