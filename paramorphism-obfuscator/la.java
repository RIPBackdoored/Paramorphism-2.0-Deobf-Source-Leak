package paramorphism-obfuscator;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;

public final class la extends Remapper {
   private boolean tf;
   private final Remapper tg;

   public final boolean a() {
      return this.tf;
   }

   public final void b() {
      this.tf = false;
   }

   @NotNull
   public String[] mapTypes(@Nullable String[] var1) {
      String[] var2 = this.tg.mapTypes(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if (!Arrays.equals(var1, var2)) {
         this.tf = true;
      }

      return var2;
   }

   @NotNull
   public String mapMethodDesc(@Nullable String var1) {
      String var2 = this.tg.mapMethodDesc(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual((Object)var2, (Object)var1) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   @NotNull
   public String mapMethodName(@Nullable String var1, @Nullable String var2, @Nullable String var3) {
      String var4 = this.tg.mapMethodName(var1, var2, var3);
      boolean var5 = false;
      boolean var6 = false;
      boolean var8 = false;
      if ((Intrinsics.areEqual((Object)var2, (Object)var4) ^ 1) != 0) {
         this.tf = true;
      }

      return var4;
   }

   @NotNull
   public String mapInnerClassName(@Nullable String var1, @Nullable String var2, @Nullable String var3) {
      String var4 = this.tg.mapInnerClassName(var1, var2, var3);
      boolean var5 = false;
      boolean var6 = false;
      boolean var8 = false;
      if ((Intrinsics.areEqual((Object)var3, (Object)var4) ^ 1) != 0) {
         this.tf = true;
      }

      return var4;
   }

   @Nullable
   public String mapSignature(@Nullable String var1, boolean var2) {
      String var10000 = this.tg.mapSignature(var1, var2);
      if (var10000 != null) {
         String var3 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         boolean var7 = false;
         if ((Intrinsics.areEqual((Object)var1, (Object)var3) ^ 1) != 0) {
            this.tf = true;
         }

         var10000 = var3;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public String mapModuleName(@Nullable String var1) {
      String var2 = this.tg.mapModuleName(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual((Object)var1, (Object)var2) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   @NotNull
   public String mapPackageName(@Nullable String var1) {
      String var2 = this.tg.mapPackageName(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual((Object)var1, (Object)var2) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   @NotNull
   public Object mapValue(@Nullable Object var1) {
      Object var2 = this.tg.mapValue(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual(var1, var2) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   @NotNull
   public String mapFieldName(@Nullable String var1, @Nullable String var2, @Nullable String var3) {
      String var4 = this.tg.mapFieldName(var1, var2, var3);
      boolean var5 = false;
      boolean var6 = false;
      boolean var8 = false;
      if ((Intrinsics.areEqual((Object)var2, (Object)var4) ^ 1) != 0) {
         this.tf = true;
      }

      return var4;
   }

   @NotNull
   public String mapInvokeDynamicMethodName(@Nullable String var1, @Nullable String var2) {
      String var3 = this.tg.mapInvokeDynamicMethodName(var1, var2);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      if ((Intrinsics.areEqual((Object)var1, (Object)var3) ^ 1) != 0) {
         this.tf = true;
      }

      return var3;
   }

   @NotNull
   public String map(@Nullable String var1) {
      String var2 = this.tg.map(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual((Object)var1, (Object)var2) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   @Nullable
   public String mapType(@Nullable String var1) {
      String var10000 = this.tg.mapType(var1);
      if (var10000 != null) {
         String var2 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         if ((Intrinsics.areEqual((Object)var1, (Object)var2) ^ 1) != 0) {
            this.tf = true;
         }

         var10000 = var2;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public String mapDesc(@Nullable String var1) {
      String var2 = this.tg.mapDesc(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      if ((Intrinsics.areEqual((Object)var1, (Object)var2) ^ 1) != 0) {
         this.tf = true;
      }

      return var2;
   }

   public la(@NotNull Remapper var1) {
      super();
      this.tg = var1;
   }
}
