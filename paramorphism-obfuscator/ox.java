package paramorphism-obfuscator;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ox extends Lambda implements Function1 {
   public final ow bak;
   public final kg bal;

   public Object invoke(Object var1) {
      this.a((mg)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mg var1) {
      String[] var2 = this.bak.a().getManifestEntries();
      boolean var3 = false;
      boolean var5 = false;
      if (var2.length != 0) {
         Attributes var14 = this.bal.g().getMainAttributes();
         String[] var16 = this.bak.a().getManifestEntries();
         int var6 = var16.length;

         for(int var4 = 0; var4 < var6; ++var4) {
            String var15 = var16[var4];
            Name var7 = new Name(var15);
            String var10000 = (String)var14.get(var7);
            String var8 = var10000 != null ? StringsKt.replace$default(var10000, '.', '/', false, 4, (Object)null) : null;
            if (var8 != null) {
               String var9 = ow.a(this.bak).map(var8);
               boolean var10 = false;
               boolean var11 = false;
               boolean var13 = false;
               ((Map)this.bal.g().getMainAttributes()).put(var7, StringsKt.replace$default(var9, '/', '.', false, 4, (Object)null));
            }
         }
      }

   }

   public ox(ow var1, kg var2) {
      super(1);
      this.bak = var1;
      this.bal = var2;
   }
}
