package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class rv extends Lambda implements Function1 {
   public final List bgb;
   public final lu bgc;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      FieldNode var2 = ClassWrapper.addField(var1, var1.f().a((AccessWrapper)var1.h()), Reflection.getOrCreateKotlinClass(Object[].class), "a", (String)null, (Object)null, 24, (Object)null);
      AccessWrapper var10001 = (AccessWrapper)var1.h();
      Type var10003 = var1.be();
      Function1 var10004 = (Function1)(new rx(this, var1, var2));
      Object[] var3 = new Object[0];
      ra var4 = null;
      Iterator var5 = null;
      Function1 var6 = var10004;
      ClassWrapper.addMethod(var1, var10001, "<clinit>", var10003, var3, var5, var4, var6, 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.e().a((AccessWrapper)var1.h()), "bootstrap", Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bk()}, (String)null, (Type[])null, (Function1)(new rg(var1)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), "a", Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bi(), var1.bi()}, (String)null, (Type[])null, (Function1)(new rr(this, var1, var2)), 48, (Object)null);
      int var7 = 0;

      for(var5 = ((Iterable)this.bgb).iterator(); var5.hasNext(); ++var7) {
         var4 = (ra)var5.next();
         ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), "" + 'b' + var7, var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(Object[].class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class)}, (String)null, (Type[])null, (Function1)(new rt(this, var4)), 48, (Object)null);
      }

   }

   public rv(List var1, lu var2) {
      super(1);
      this.bgb = var1;
      this.bgc = var2;
   }
}
