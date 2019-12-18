package paramorphism-obfuscator;

import java.util.Map;
import java.util.jar.Attributes.Name;
import kotlin.io.FilesKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ParamorphismConfig;

public final class mk {
   public static final int va = 1;

   private static final void a(kg param0) {
      // $FF: Couldn't be decompiled
   }

   private static final void a(kg param0, mq param1) {
      // $FF: Couldn't be decompiled
   }

   private static final void b(kg param0) {
      // $FF: Couldn't be decompiled
   }

   private static final void c(kg param0) {
      // $FF: Couldn't be decompiled
   }

   private static final void d(kg param0) {
      // $FF: Couldn't be decompiled
   }

   public static final void a(@NotNull ParamorphismConfig var0) {
      kg var1 = new kg(var0);
      lh.tv.a("Starting Paramorphism 2.0...");
      a(var1);
      lh.tv.a("\nRemapping Transformations:");
      a(var1, mq.vg);
      lh.tv.a("\nMain Transformations:");
      a(var1, mq.vh);
      int var2 = 1;

      for(byte var3 = 1; var2 <= var3; ++var2) {
         lh.tv.a("\nControl Flow Transformations (" + var2 + "/1):");
         a(var1, mq.vi);
      }

      lh.tv.a("\nPacking Transformations:");
      a(var1, mq.vj);
      lh.tv.a("\nCorruption Transformations:");
      b(var1);
      a(var1, mq.vk);
      if (var1.i().getBranding()) {
         ((Map)var1.g().getMainAttributes()).put(new Name("Obfuscated-By"), "https://paramorphism.dev/");
      }

      lh.tv.a("\nOutput JAR File:");
      c(var1);
      d(var1);

      try {
         lh.tv.a("\nSaved obfuscated output to: " + FilesKt.toRelativeString(var1.i().getOutput(), var1.i().getWorkingDirectory()));
      } catch (Exception var4) {
         lh.tv.a("\nSaved obfuscated output to: " + var1.i().getOutput());
      }

   }

   private static final ow[] b(ParamorphismConfig var0) {
      return new ow[]{new ow(var0.getRemapper())};
   }

   private static final mm[] c(ParamorphismConfig var0) {
      return new mm[]{(mm)(new nr(var0.getSourceFileStripping())), (mm)(new nu(var0.getLineNumberStripping())), (mm)(new nt(var0.getLocalVariableStripping())), (mm)(new pn(var0.getKotlinIntrinsicsConcealing())), (mm)(new pm(var0.getKotlinMetadataStripping())), (mm)(new po(var0.getKotlinDelegatedPropertiesConcealing())), (mm)(new rn(var0.getStringConcealing())), (mm)(new qc(var0.getMethodIndirection())), (mm)(new nn(var0.getSpigotRemapPluginYML()))};
   }

   private static final nv[] d(ParamorphismConfig var0) {
      return new nv[]{new nv(var0.getJumpReplacement())};
   }

   private static final mz[] e(ParamorphismConfig var0) {
      return new mz[]{new mz(var0.getPacker())};
   }

   private static final mm[] f(ParamorphismConfig var0) {
      return new mm[]{(mm)(new sm(var0.getNullByteNaming())), (mm)(new sk(var0.getForceNoVerify())), (mm)(new sn(var0.getDeeplyNestedClassInjection()))};
   }
}
