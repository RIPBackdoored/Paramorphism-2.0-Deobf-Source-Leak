package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;

public final class pb extends Remapper {
   private final Map bar;
   private final Map bas;
   private final Map bat;
   @NotNull
   private final Remapper bau;

   @Nullable
   public String map(@NotNull String var1) {
      Map var2 = this.bar;
      boolean var3 = false;
      Object var4 = var2.get(var1);
      Object var10000;
      if (var4 == null) {
         boolean var5 = false;
         String var6 = this.bau.map(var1);
         var2.put(var1, var6);
         var10000 = var6;
      } else {
         var10000 = var4;
      }

      return (String)var10000;
   }

   @Nullable
   public String mapMethodName(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      Map var4 = this.bas;
      String var5 = var1 + ' ' + var2 + ' ' + var3;
      boolean var6 = false;
      Object var7 = var4.get(var5);
      Object var10000;
      if (var7 == null) {
         boolean var8 = false;
         String var9 = this.bau.mapMethodName(var1, var2, var3);
         var4.put(var5, var9);
         var10000 = var9;
      } else {
         var10000 = var7;
      }

      return (String)var10000;
   }

   @Nullable
   public String mapFieldName(@Nullable String var1, @Nullable String var2, @Nullable String var3) {
      Map var4 = this.bat;
      String var5 = var1 + ' ' + var2 + ' ' + var3;
      boolean var6 = false;
      Object var7 = var4.get(var5);
      Object var10000;
      if (var7 == null) {
         boolean var8 = false;
         String var9 = this.bau.mapFieldName(var1, var2, var3);
         var4.put(var5, var9);
         var10000 = var9;
      } else {
         var10000 = var7;
      }

      return (String)var10000;
   }

   @Nullable
   public String mapInvokeDynamicMethodName(@Nullable String var1, @Nullable String var2) {
      return this.bau.mapInvokeDynamicMethodName(var1, var2);
   }

   @Nullable
   public String[] mapTypes(@Nullable String[] var1) {
      return this.bau.mapTypes(var1);
   }

   @Nullable
   public String mapMethodDesc(@Nullable String var1) {
      return this.bau.mapMethodDesc(var1);
   }

   @Nullable
   public String mapInnerClassName(@Nullable String var1, @Nullable String var2, @Nullable String var3) {
      return this.bau.mapInnerClassName(var1, var2, var3);
   }

   @Nullable
   public String mapSignature(@Nullable String var1, boolean var2) {
      return this.bau.mapSignature(var1, var2);
   }

   @Nullable
   public String mapModuleName(@Nullable String var1) {
      return this.bau.mapModuleName(var1);
   }

   @Nullable
   public String mapPackageName(@Nullable String var1) {
      return this.bau.mapPackageName(var1);
   }

   @Nullable
   public Object mapValue(@Nullable Object var1) {
      return this.bau.mapValue(var1);
   }

   @Nullable
   public String mapType(@Nullable String var1) {
      return this.bau.mapType(var1);
   }

   @Nullable
   public String mapDesc(@Nullable String var1) {
      return this.bau.mapDesc(var1);
   }

   @NotNull
   public final Remapper a() {
      return this.bau;
   }

   public pb(@NotNull Remapper var1) {
      super();
      this.bau = var1;
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.bar = var4;
      var2 = false;
      var4 = (Map)(new LinkedHashMap());
      this.bas = var4;
      var2 = false;
      var4 = (Map)(new LinkedHashMap());
      this.bat = var4;
   }
}
