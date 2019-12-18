package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.access.AbstractAccess;
import paramorphism-obfuscator.access.AnnotationAccess;
import paramorphism-obfuscator.access.BridgeAccess;
import paramorphism-obfuscator.access.DefaultAccess;
import paramorphism-obfuscator.access.DeprecatedAccess;
import paramorphism-obfuscator.access.EnumAccess;
import paramorphism-obfuscator.access.FinalAccess;
import paramorphism-obfuscator.access.InterfaceAccess;
import paramorphism-obfuscator.access.MandatedAccess;
import paramorphism-obfuscator.access.ModuleAccess;
import paramorphism-obfuscator.access.NativeAccess;
import paramorphism-obfuscator.access.OpenAccess;
import paramorphism-obfuscator.access.PrivateAccess;
import paramorphism-obfuscator.access.ProtectedAccess;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.access.StaticAccess;
import paramorphism-obfuscator.access.StaticPhaseAccess;
import paramorphism-obfuscator.access.StrictAccess;
import paramorphism-obfuscator.access.SuperAccess;
import paramorphism-obfuscator.access.SynchronizedAccess;
import paramorphism-obfuscator.access.SyntheticAccess;
import paramorphism-obfuscator.access.TransientAccess;
import paramorphism-obfuscator.access.TransitiveAccess;
import paramorphism-obfuscator.access.VarargsAccess;
import paramorphism-obfuscator.access.VolatileAccess;

public final class AccessImpls {
   @NotNull
   public static DefaultAccess getDefault(IAccess var0) {
      return DefaultAccess.blk;
   }

   @NotNull
   public static PublicAccess getPublic(IAccess var0) {
      return PublicAccess.bln;
   }

   @NotNull
   public static PrivateAccess getPrivate(IAccess var0) {
      return PrivateAccess.bll;
   }

   @NotNull
   public static ProtectedAccess getProtected(IAccess var0) {
      return ProtectedAccess.blm;
   }

   @NotNull
   public static StaticAccess getStatic(IAccess var0) {
      return StaticAccess.blo;
   }

   @NotNull
   public static FinalAccess getFinal(IAccess var0) {
      return FinalAccess.ble;
   }

   @NotNull
   public static SynchronizedAccess getSynchronized(IAccess var0) {
      return SynchronizedAccess.blr;
   }

   @NotNull
   public static SynchronizedAccess getSynchronizedDuplicate(IAccess var0) {
      return yl.a();
   }

   @NotNull
   public static OpenAccess getOpen(IAccess var0) {
      return OpenAccess.bls;
   }

   @NotNull
   public static SuperAccess getSuper(IAccess var0) {
      return SuperAccess.blj;
   }

   @NotNull
   public static TransitiveAccess getTransitive(IAccess var0) {
      return TransitiveAccess.blv;
   }

   @NotNull
   public static BridgeAccess getBridge(IAccess var0) {
      return BridgeAccess.blx;
   }

   @NotNull
   public static StaticPhaseAccess getStaticPhase(IAccess var0) {
      return StaticPhaseAccess.blb;
   }

   @NotNull
   public static VolatileAccess getVolatile(IAccess var0) {
      return VolatileAccess.blp;
   }

   @NotNull
   public static TransientAccess getTransient(IAccess var0) {
      return TransientAccess.blw;
   }

   @NotNull
   public static VarargsAccess getVarargs(IAccess var0) {
      return VarargsAccess.blu;
   }

   @NotNull
   public static NativeAccess getNative(IAccess var0) {
      return NativeAccess.bli;
   }

   @NotNull
   public static InterfaceAccess getInterface(IAccess var0) {
      return InterfaceAccess.blf;
   }

   @NotNull
   public static InterfaceAccess getInterfaceDuplicate(IAccess var0) {
      return yl.b();
   }

   @NotNull
   public static AbstractAccess getAbstract(IAccess var0) {
      return AbstractAccess.bkz;
   }

   @NotNull
   public static StrictAccess getStrict(IAccess var0) {
      return StrictAccess.blq;
   }

   @NotNull
   public static SyntheticAccess getSynthetic(IAccess var0) {
      return SyntheticAccess.blt;
   }

   @NotNull
   public static AnnotationAccess getAnnotation(IAccess var0) {
      return AnnotationAccess.bla;
   }

   @NotNull
   public static EnumAccess getEnum(IAccess var0) {
      return EnumAccess.bld;
   }

   @NotNull
   public static MandatedAccess getMandated(IAccess var0) {
      return MandatedAccess.blg;
   }

   @NotNull
   public static ModuleAccess getModule(IAccess var0) {
      return ModuleAccess.blh;
   }

   @NotNull
   public static DeprecatedAccess getDeprecated(IAccess var0) {
      return DeprecatedAccess.blc;
   }
}
