package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class nb extends Lambda implements Function1 {
   public final mx wd;
   public final String we;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      FieldNode var2 = ClassWrapper.addField(var1, (AccessWrapper)var1.f(), Reflection.getOrCreateKotlinClass(ClassLoader.class), "parent", (String)null, (Object)null, 24, (Object)null);
      ClassWrapper.addMethod(var1, (AccessWrapper)var1.e(), "<init>", var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(ClassLoader.class)}, (String)null, (Type[])null, (Function1)(new nl(var1, var2)), 48, (Object)null);
      AccessWrapper var10001 = (AccessWrapper)var1.g();
      KClass var10003 = Reflection.getOrCreateKotlinClass(Class.class);
      Type[] var10004 = new Type[]{var1.a(Reflection.getOrCreateKotlinClass(ClassNotFoundException.class))};
      Object[] var10006 = new Object[]{Reflection.getOrCreateKotlinClass(String.class)};
      Function1 var3 = (Function1)(new mv(this, var1, var2));
      Object[] var4 = var10006;
      Object var5 = null;
      Type[] var6 = var10004;
      ClassWrapper.addMethod(var1, var10001, "findClass", var10003, var4, (String)var5, var6, var3, 16, (Object)null);
      ClassWrapper.addMethod(var1, var1.e().a((AccessWrapper)var1.h()), "main", var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(String[].class)}, (String)null, (Type[])null, (Function1)(new nh(this, var1)), 48, (Object)null);
   }

   public nb(mx var1, String var2) {
      super(1);
      this.wd = var1;
      this.we = var2;
   }
}
