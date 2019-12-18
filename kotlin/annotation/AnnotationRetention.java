package kotlin.annotation;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/annotation/AnnotationRetention;", "", "(Ljava/lang/String;I)V", "SOURCE", "BINARY", "RUNTIME", "kotlin-stdlib"}
)
public final class AnnotationRetention extends Enum {
   public static final AnnotationRetention SOURCE;
   public static final AnnotationRetention BINARY;
   public static final AnnotationRetention RUNTIME;
   private static final AnnotationRetention[] $VALUES = new AnnotationRetention[]{SOURCE = new AnnotationRetention("SOURCE", 0), BINARY = new AnnotationRetention("BINARY", 1), RUNTIME = new AnnotationRetention("RUNTIME", 2)};

   private AnnotationRetention(String var1, int var2) {
      super(var1, var2);
   }

   public static AnnotationRetention[] values() {
      return (AnnotationRetention[])$VALUES.clone();
   }

   public static AnnotationRetention valueOf(String var0) {
      return (AnnotationRetention)Enum.valueOf(AnnotationRetention.class, var0);
   }
}
