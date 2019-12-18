package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class ClassInfoList extends mb {
   private final List classInfos;

   public final void addClassNode(@NotNull ClassNode var1) {
      this.addClassInfo(new ClassInfo(var1.name, var1, 0, 4, (DefaultConstructorMarker)null));
   }

   public final void addClassInfo(@NotNull ClassInfo var1) {
      this.classInfos.add(var1);
   }

   @NotNull
   public final List getClassInfos() {
      return CollectionsKt.toList((Iterable)this.classInfos);
   }

   public ClassInfoList(@NotNull mq var1) {
      super(var1);
      boolean var2 = false;
      List var4 = (List)(new ArrayList());
      this.classInfos = var4;
   }
}
