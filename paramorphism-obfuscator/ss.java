package paramorphism-obfuscator;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipException;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.ClassSetGroup;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class ss extends Lambda implements Function0 {
   public final kg bhk;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final ClassSetGroup a() {
      File[] var2 = this.bhk.i().getLibraries();
      boolean var3 = false;
      Collection var5 = (Collection)(new ArrayList());
      boolean var6 = false;
      File[] var7 = var2;
      int var8 = var2.length;

      boolean var12;
      for(int var9 = 0; var9 < var8; ++var9) {
         File var10 = var7[var9];
         var12 = false;
         List var10000;
         if (var10.isDirectory()) {
            label111: {
               File[] var33 = var10.listFiles((FileFilter)ug.bjf);
               if (var33 != null) {
                  var10000 = ArraysKt.toList(var33);
                  if (var10000 != null) {
                     break label111;
                  }
               }

               var10000 = CollectionsKt.emptyList();
            }
         } else {
            var10000 = CollectionsKt.listOf(var10);
         }

         Iterable var11 = (Iterable)var10000;
         CollectionsKt.addAll(var5, var11);
      }

      Iterable var27 = (Iterable)((List)var5);
      var3 = false;
      var5 = (Collection)(new ArrayList());
      var6 = false;
      boolean var30 = false;
      Iterator var31 = var27.iterator();

      while(var31.hasNext()) {
         Object var32 = var31.next();
         var12 = false;
         File var13 = (File)var32;
         boolean var14 = false;

         sy var15;
         try {
            var15 = new sy((ResourceSet)(new tc(var13)), 1);
         } catch (ZipException var26) {
            lh.tv.b("Error loading " + var13 + ": " + var26.getMessage());
            var15 = null;
         }

         if (var15 != null) {
            boolean var18 = false;
            boolean var19 = false;
            boolean var21 = false;
            var5.add(var15);
         }
      }

      List var1 = CollectionsKt.toMutableList((Collection)((List)var5));
      ElementMask var28 = this.bhk.i().getShadedLibraries();
      if (var28 != null) {
         var1.add(0, new tt(var28, (ClassSet)(new sy((ResourceSet)(new tc(this.bhk.i().getInput())), 1))));
      }

      if (this.bhk.i().getUseJavaRuntime()) {
         var1.add(0, ObtainRuntimeClass.a());
      }

      Collection var29 = (Collection)var1;
      boolean var4 = false;
      if (var29 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
      } else {
         Object[] var34 = var29.toArray(new ClassSet[0]);
         if (var34 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } else {
            Object[] var24 = var34;
            ClassSet[] var25 = (ClassSet[])var24;
            return new ClassSetGroup(var25);
         }
      }
   }

   public ss(kg var1) {
      super(0);
      this.bhk = var1;
   }
}
