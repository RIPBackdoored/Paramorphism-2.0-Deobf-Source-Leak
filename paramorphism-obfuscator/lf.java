package paramorphism-obfuscator;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassWriter;

public final class lf extends ClassWriter {
   private final lc tt;

   @NotNull
   protected String getCommonSuperClass(@NotNull String var1, @NotNull String var2) {
      le var3 = this.tt.a(var1);
      le var4 = this.tt.a(var2);
      if (var3 != null && var4 != null) {
         if (this.a(var3, var4)) {
            return var4.a();
         }

         if (this.a(var4, var3)) {
            return var3.a();
         }

         le var5 = var4;

         while(var5 != null) {
            le var10000 = var5.d();
            if (var10000 == null) {
               break;
            }

            var5 = var10000;
            if (this.a(var3, var5)) {
               return var5.a();
            }
         }
      }

      String var11;
      label92: {
         boolean var10001;
         String var10;
         label110: {
            label89:
            try {
               var10 = super.getCommonSuperClass(var1, var2);
               if (var10 == null) {
                  break label89;
               }
               break label110;
            } catch (Exception var9) {
               var10001 = false;
               break label92;
            }

            try {
               var10 = "java/lang/Object";
            } catch (Exception var8) {
               var10001 = false;
               break label92;
            }
         }

         try {
            var11 = var10;
            return var11;
         } catch (Exception var7) {
            var10001 = false;
         }
      }

      var11 = "java/lang/Object";
      return var11;
   }

   private final boolean a(le var1, le var2) {
      for(le var3 = var1; var3 != null; var3 = var3.d()) {
         if (Intrinsics.areEqual((Object)var3.d(), (Object)var2)) {
            return true;
         }

         if (ArraysKt.contains(var3.e(), var2)) {
            return true;
         }
      }

      return false;
   }

   public lf(@NotNull lc var1, int var2) {
      super(var2);
      this.tt = var1;
   }
}
