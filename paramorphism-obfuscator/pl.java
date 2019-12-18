package paramorphism-obfuscator;

import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;
import org.objectweb.asm.tree.AnnotationNode;

public final class pl implements Predicate {
   public static final pl bbd = new pl();

   public boolean test(Object var1) {
      return this.a((AnnotationNode)var1);
   }

   public final boolean a(AnnotationNode var1) {
      return Intrinsics.areEqual((Object)var1.desc, (Object)"Lkotlin/Metadata;");
   }

   public pl() {
      super();
   }
}
