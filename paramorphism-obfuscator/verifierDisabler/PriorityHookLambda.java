package paramorphism-obfuscator.verifierDisabler;

import java.util.Random;
import java.util.jar.Attributes.Name;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.ClassInfoList;
import paramorphism-obfuscator.kg;
import paramorphism-obfuscator.lu;
import paramorphism-obfuscator.sb;
import paramorphism-obfuscator.xi;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import site.hackery.paramorphism.api.config.naming.NameGenerationDictionaries;
import site.hackery.paramorphism.api.config.naming.UniformNameGenerationDictionaries;
import site.hackery.paramorphism.api.naming.Dictionaries;

public final class PriorityHookLambda extends Lambda implements Function1 {
   public final kg bhg;

   public Object invoke(Object var1) {
      this.a((ClassInfoList)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassInfoList var1) {
      var1.addClassNode(xi.a((AccessWrapper)PublicAccess.bln, "/->", 0, (String)null, (Function1)FirstLoadedClass.bhh, 12, (Object)null));
      ClassNode var3 = SaveDisabler.a(new lu((NameGenerationDictionaries)(new UniformNameGenerationDictionaries(Dictionaries.INSTANCE.getJAVA_KEYWORDS())), new Random(82786747749L)));
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      ClassWriter var8 = new ClassWriter(2);
      var3.accept((ClassVisitor)var8);
      ClassNode var9 = new ClassNode();
      ClassReader var10 = new ClassReader(var8.toByteArray());
      var10.accept((ClassVisitor)var9, 8);
      var1.addClassNode(var9);
      Type var11 = Type.getObjectType(StringsKt.replace$default(String.valueOf(this.bhg.g().getMainAttributes().get(Name.MAIN_CLASS)), '.', '/', false, 4, (Object)null));
      var1.addClassNode(xi.a((AccessWrapper)PublicAccess.bln, "dev/paramorphism/runtime/Main", 52, (String)null, (Function1)(new sb(var9, var11)), 8, (Object)null));
   }

   public PriorityHookLambda(kg var1) {
      super(1);
      this.bhg = var1;
   }
}
