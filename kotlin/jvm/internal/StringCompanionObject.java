package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lkotlin/jvm/internal/StringCompanionObject;", "", "()V", "kotlin-stdlib"}
)
public final class StringCompanionObject {
   public static final StringCompanionObject INSTANCE;

   private StringCompanionObject() {
      super();
   }

   static {
      StringCompanionObject var0 = new StringCompanionObject();
      INSTANCE = var0;
   }
}
