package kotlin.annotation;

import java.lang.annotation.*;
import kotlin.*;

@Target(allowedTargets = { AnnotationTarget.ANNOTATION_CLASS })
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ ElementType.ANNOTATION_TYPE })
@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002" }, d2 = { "Lkotlin/annotation/MustBeDocumented;", "", "kotlin-stdlib" })
public @interface MustBeDocumented {
}
