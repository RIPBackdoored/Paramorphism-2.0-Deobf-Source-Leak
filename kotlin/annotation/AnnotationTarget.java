package kotlin.annotation;

import kotlin.Metadata;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0011\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011¨\u0006\u0012"},
   d2 = {"Lkotlin/annotation/AnnotationTarget;", "", "(Ljava/lang/String;I)V", "CLASS", "ANNOTATION_CLASS", "TYPE_PARAMETER", "PROPERTY", "FIELD", "LOCAL_VARIABLE", "VALUE_PARAMETER", "CONSTRUCTOR", "FUNCTION", "PROPERTY_GETTER", "PROPERTY_SETTER", "TYPE", "EXPRESSION", "FILE", "TYPEALIAS", "kotlin-stdlib"}
)
public final class AnnotationTarget extends Enum {
   public static final AnnotationTarget CLASS;
   public static final AnnotationTarget ANNOTATION_CLASS;
   public static final AnnotationTarget TYPE_PARAMETER;
   public static final AnnotationTarget PROPERTY;
   public static final AnnotationTarget FIELD;
   public static final AnnotationTarget LOCAL_VARIABLE;
   public static final AnnotationTarget VALUE_PARAMETER;
   public static final AnnotationTarget CONSTRUCTOR;
   public static final AnnotationTarget FUNCTION;
   public static final AnnotationTarget PROPERTY_GETTER;
   public static final AnnotationTarget PROPERTY_SETTER;
   public static final AnnotationTarget TYPE;
   public static final AnnotationTarget EXPRESSION;
   public static final AnnotationTarget FILE;
   @SinceKotlin(
      version = "1.1"
   )
   public static final AnnotationTarget TYPEALIAS;
   private static final AnnotationTarget[] $VALUES = new AnnotationTarget[]{CLASS = new AnnotationTarget("CLASS", 0), ANNOTATION_CLASS = new AnnotationTarget("ANNOTATION_CLASS", 1), TYPE_PARAMETER = new AnnotationTarget("TYPE_PARAMETER", 2), PROPERTY = new AnnotationTarget("PROPERTY", 3), FIELD = new AnnotationTarget("FIELD", 4), LOCAL_VARIABLE = new AnnotationTarget("LOCAL_VARIABLE", 5), VALUE_PARAMETER = new AnnotationTarget("VALUE_PARAMETER", 6), CONSTRUCTOR = new AnnotationTarget("CONSTRUCTOR", 7), FUNCTION = new AnnotationTarget("FUNCTION", 8), PROPERTY_GETTER = new AnnotationTarget("PROPERTY_GETTER", 9), PROPERTY_SETTER = new AnnotationTarget("PROPERTY_SETTER", 10), TYPE = new AnnotationTarget("TYPE", 11), EXPRESSION = new AnnotationTarget("EXPRESSION", 12), FILE = new AnnotationTarget("FILE", 13), TYPEALIAS = new AnnotationTarget("TYPEALIAS", 14)};

   private AnnotationTarget(String var1, int var2) {
      super(var1, var2);
   }

   public static AnnotationTarget[] values() {
      return (AnnotationTarget[])$VALUES.clone();
   }

   public static AnnotationTarget valueOf(String var0) {
      return (AnnotationTarget)Enum.valueOf(AnnotationTarget.class, var0);
   }
}
