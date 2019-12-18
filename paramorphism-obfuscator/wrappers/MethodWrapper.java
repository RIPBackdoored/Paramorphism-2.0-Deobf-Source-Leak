package paramorphism-obfuscator.wrappers;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.AccessImpls;
import paramorphism-obfuscator.IAccess;
import paramorphism-obfuscator.IPrimitives;
import paramorphism-obfuscator.PrimitiveImpls;
import paramorphism-obfuscator.xl;
import paramorphism-obfuscator.yi;
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

public final class MethodWrapper implements IInstructionWrapper, xl, yi, IAccess, IPrimitives {
   @NotNull
   private final LabelsWrapper labels;
   @NotNull
   private final MethodNode method;

   @NotNull
   public InsnList getInstructions() {
      return this.method.instructions;
   }

   @NotNull
   public List getExceptions() {
      return this.method.tryCatchBlocks;
   }

   @NotNull
   public LabelsWrapper getLabels() {
      return this.labels;
   }

   public final int getMaxStack() {
      return this.method.maxStack;
   }

   public final void setMaxStack(int var1) {
      this.method.maxStack = var1;
   }

   public final int getMaxLocals() {
      return this.method.maxLocals;
   }

   public final void setMaxLocals(int var1) {
      this.method.maxLocals = var1;
   }

   @NotNull
   public final MethodNode getMethod() {
      return this.method;
   }

   public MethodWrapper(@NotNull MethodNode var1) {
      super();
      this.method = var1;
      this.labels = new LabelsWrapper((IInstructionWrapper)this);
   }

   @NotNull
   public DefaultAccess d() {
      return AccessImpls.getDefault(this);
   }

   @NotNull
   public PublicAccess e() {
      return AccessImpls.getPublic(this);
   }

   @NotNull
   public PrivateAccess f() {
      return AccessImpls.getPrivate(this);
   }

   @NotNull
   public ProtectedAccess g() {
      return AccessImpls.getProtected(this);
   }

   @NotNull
   public StaticAccess h() {
      return AccessImpls.getStatic(this);
   }

   @NotNull
   public FinalAccess i() {
      return AccessImpls.getFinal(this);
   }

   @NotNull
   public SynchronizedAccess j() {
      return AccessImpls.getSynchronized(this);
   }

   @NotNull
   public SynchronizedAccess k() {
      return AccessImpls.getSynchronizedDuplicate(this);
   }

   @NotNull
   public OpenAccess l() {
      return AccessImpls.getOpen(this);
   }

   @NotNull
   public SuperAccess m() {
      return AccessImpls.getSuper(this);
   }

   @NotNull
   public TransitiveAccess n() {
      return AccessImpls.getTransitive(this);
   }

   @NotNull
   public BridgeAccess o() {
      return AccessImpls.getBridge(this);
   }

   @NotNull
   public StaticPhaseAccess p() {
      return AccessImpls.getStaticPhase(this);
   }

   @NotNull
   public VolatileAccess q() {
      return AccessImpls.getVolatile(this);
   }

   @NotNull
   public TransientAccess r() {
      return AccessImpls.getTransient(this);
   }

   @NotNull
   public VarargsAccess s() {
      return AccessImpls.getVarargs(this);
   }

   @NotNull
   public NativeAccess t() {
      return AccessImpls.getNative(this);
   }

   @NotNull
   public InterfaceAccess u() {
      return AccessImpls.getInterface(this);
   }

   @NotNull
   public InterfaceAccess v() {
      return AccessImpls.getInterfaceDuplicate(this);
   }

   @NotNull
   public AbstractAccess w() {
      return AccessImpls.getAbstract(this);
   }

   @NotNull
   public StrictAccess x() {
      return AccessImpls.getStrict(this);
   }

   @NotNull
   public SyntheticAccess y() {
      return AccessImpls.getSynthetic(this);
   }

   @NotNull
   public AnnotationAccess z() {
      return AccessImpls.getAnnotation(this);
   }

   @NotNull
   public EnumAccess ba() {
      return AccessImpls.getEnum(this);
   }

   @NotNull
   public MandatedAccess bb() {
      return AccessImpls.getMandated(this);
   }

   @NotNull
   public ModuleAccess bc() {
      return AccessImpls.getModule(this);
   }

   @NotNull
   public DeprecatedAccess bd() {
      return AccessImpls.getDeprecated(this);
   }

   public Type be() {
      return PrimitiveImpls.getVoid(this);
   }

   public Type bf() {
      return PrimitiveImpls.getChar(this);
   }

   public Type bg() {
      return PrimitiveImpls.getByte(this);
   }

   public Type bh() {
      return PrimitiveImpls.getShort(this);
   }

   public Type bi() {
      return PrimitiveImpls.getInt(this);
   }

   public Type bj() {
      return PrimitiveImpls.getFloat(this);
   }

   public Type bk() {
      return PrimitiveImpls.getLong(this);
   }

   public Type bl() {
      return PrimitiveImpls.getDouble(this);
   }

   public Type bm() {
      return PrimitiveImpls.getBoolean(this);
   }

   @NotNull
   public Type a(@NotNull Object var1) {
      return PrimitiveImpls.getType(this, var1);
   }
}
