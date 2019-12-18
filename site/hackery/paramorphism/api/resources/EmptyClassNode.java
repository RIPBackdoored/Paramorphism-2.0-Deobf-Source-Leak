package site.hackery.paramorphism.api.resources;

import kotlin.Metadata;
import org.objectweb.asm.tree.ClassNode;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/EmptyClassNode;", "Lorg/objectweb/asm/tree/ClassNode;", "()V", "paramorphism"}
)
public final class EmptyClassNode extends ClassNode {
   public static final EmptyClassNode INSTANCE;

   private EmptyClassNode() {
      super(458752);
   }

   static {
      EmptyClassNode var0 = new EmptyClassNode();
      INSTANCE = var0;
      var0.name = "";
      var0.version = -1;
   }
}
