package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

public final class ih {
   private final Map mo;

   @NotNull
   public final Object a(@NotNull Object var1) {
      List var2 = (List)this.mo.get(var1.getClass());
      if (var2 != null) {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            ig var3 = (ig)var4.next();
            var3.b(var1);
         }
      }

      return var1;
   }

   public final void a(@NotNull KClass var1, @NotNull ig var2) {
      Map var3 = this.mo;
      Class var4 = JvmClassMappingKt.getJavaClass(var1);
      boolean var5 = false;
      Object var6 = var3.get(var4);
      Object var10000;
      if (var6 == null) {
         boolean var7 = false;
         boolean var8 = false;
         List var9 = (List)(new ArrayList());
         var3.put(var4, var9);
         var10000 = var9;
      } else {
         var10000 = var6;
      }

      ((List)var10000).add(var2);
   }

   public final void a(@NotNull KClass var1, @NotNull Function1 var2) {
      Map var3 = this.mo;
      Class var4 = JvmClassMappingKt.getJavaClass(var1);
      boolean var5 = false;
      Object var6 = var3.get(var4);
      Object var10000;
      if (var6 == null) {
         boolean var7 = false;
         boolean var8 = false;
         List var9 = (List)(new ArrayList());
         var3.put(var4, var9);
         var10000 = var9;
      } else {
         var10000 = var6;
      }

      ((List)var10000).add(new ii(var2));
   }

   public final void b(@NotNull KClass var1, @NotNull ig var2) {
      List var10000 = (List)this.mo.get(JvmClassMappingKt.getJavaClass(var1));
      if (var10000 != null) {
         var10000.remove(var2);
      }

   }

   public ih() {
      super();
      boolean var1 = false;
      Map var3 = (Map)(new LinkedHashMap());
      this.mo = var3;
   }
}
