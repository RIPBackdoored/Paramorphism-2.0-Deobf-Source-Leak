package paramorphism-obfuscator.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.AccessImpls;
import paramorphism-obfuscator.IAccess;
import paramorphism-obfuscator.IPrimitives;
import paramorphism-obfuscator.PrimitiveImpls;
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

public final class ClassWrapper implements IAccess, IPrimitives {
   @NotNull
   private final ClassNode classNode;

   @NotNull
   public final AccessWrapper getAccess() {
      AccessWrapper var10000 = new AccessWrapper;
      var10000.setAccess(this.classNode.access);
      return var10000;
   }

   public final void setAccess(@NotNull AccessWrapper var1) {
      this.classNode.access = var1.getAccess();
   }

   @NotNull
   public final String getName() {
      return this.classNode.name;
   }

   public final void setName(@NotNull String var1) {
      this.classNode.name = var1;
   }

   public final int getVersion() {
      return this.classNode.version;
   }

   public final void setVersion(int var1) {
      this.classNode.version = var1;
   }

   @Nullable
   public final Type getParentType() {
      String var10000 = this.classNode.superName;
      Type var7;
      if (var10000 != null) {
         String var1 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         var7 = ((ClassWrapper)this).a(var1);
      } else {
         var7 = null;
      }

      return var7;
   }

   public final void setParentType(@Nullable Type var1) {
      this.classNode.superName = var1 != null ? var1.getInternalName() : null;
   }

   @Nullable
   public final List getInterfaces() {
      List var10000 = this.classNode.interfaces;
      if (var10000 != null) {
         Iterable var1 = (Iterable)var10000;
         ClassWrapper var2 = this;
         boolean var3 = false;
         Collection var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10)));
         boolean var6 = false;
         Iterator var7 = var1.iterator();

         while(var7.hasNext()) {
            Object var8 = var7.next();
            boolean var10 = false;
            Type var12 = ((ClassWrapper)var2).a(var8);
            var5.add(var12);
         }

         var10000 = (List)var5;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public final void setInterfaces(@Nullable List var1) {
      ClassNode var10000 = this.classNode;
      List var10001;
      if (var1 != null) {
         Iterable var2 = (Iterable)var1;
         ClassNode var11 = var10000;
         boolean var3 = false;
         Collection var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10)));
         boolean var6 = false;
         Iterator var7 = var2.iterator();

         while(var7.hasNext()) {
            Object var8 = var7.next();
            Type var9 = (Type)var8;
            boolean var10 = false;
            String var13 = var9.getInternalName();
            var5.add(var13);
         }

         List var12 = (List)var5;
         var10000 = var11;
         var10001 = var12;
      } else {
         var10001 = null;
      }

      var10000.interfaces = var10001;
   }

   @NotNull
   public final Type getType() {
      return this.a(this.classNode.name);
   }

   @NotNull
   public final FieldNode addField(@NotNull AccessWrapper var1, @NotNull Object var2, @NotNull String var3, @Nullable String var4, @Nullable Object var5) {
      FieldNode var6 = new FieldNode(458752, var1.getAccess(), var3, this.a(var2).getDescriptor(), var4, var5);
      this.classNode.fields.add(var6);
      return var6;
   }

   @NotNull
   public static FieldNode addField(ClassWrapper var0, AccessWrapper var1, Object var2, String var3, String var4, Object var5, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var4 = (String)null;
      }

      if ((var6 & 16) != 0) {
         var5 = null;
      }

      return var0.addField(var1, var2, var3, var4, var5);
   }

   @NotNull
   public final MethodNode addMethod(@NotNull AccessWrapper var1, @NotNull String var2, @NotNull Object var3, @NotNull Object[] var4, @Nullable String var5, @Nullable Type[] var6, @NotNull Function1 var7) {
      Type var10000 = this.a(var3);
      ClassWrapper var10 = this;
      Type var21 = var10000;
      boolean var11 = false;
      Collection var13 = (Collection)(new ArrayList(var4.length));
      boolean var14 = false;
      Object[] var15 = var4;
      int var16 = var4.length;

      int var17;
      boolean var20;
      for(var17 = 0; var17 < var16; ++var17) {
         Object var18 = var15[var17];
         var20 = false;
         Type var23 = ((ClassWrapper)var10).a(var18);
         var13.add(var23);
      }

      List var22 = (List)var13;
      Collection var9 = (Collection)var22;
      boolean var37 = false;
      Object[] var41 = var9.toArray(new Type[0]);
      if (var41 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         Object[] var44 = var41;
         String var8 = Type.getMethodDescriptor(var21, (Type[])Arrays.copyOf((Type[])var44, ((Type[])var44).length));
         int var42 = 458752;
         int var10001 = var1.getAccess();
         String var10002 = var2;
         String var10003 = var8;
         String var10004 = var5;
         String[] var10005;
         if (var6 != null) {
            int var24 = var10001;
            int var45 = 458752;
            var11 = false;
            var13 = (Collection)(new ArrayList(var6.length));
            var14 = false;
            Type[] var40 = var6;
            var16 = var6.length;

            for(var17 = 0; var17 < var16; ++var17) {
               Type var43 = var40[var17];
               var20 = false;
               String var29 = var43.getInternalName();
               var13.add(var29);
            }

            List var28 = (List)var13;
            Collection var38 = (Collection)var28;
            var11 = false;
            var41 = var38.toArray(new String[0]);
            if (var41 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            Object[] var46 = var41;
            var42 = var45;
            var10001 = var24;
            var10002 = var2;
            var10003 = var8;
            var10004 = var5;
            var10005 = (String[])var46;
         } else {
            var10005 = null;
         }

         String[] var30 = var10005;
         String var31 = var10004;
         String var32 = var10003;
         String var33 = var10002;
         int var34 = var10001;
         int var35 = var42;
         MethodNode var36 = new MethodNode(var35, var34, var33, var32, var31, var30);
         MethodWrapper var39 = new MethodWrapper(var36);
         var7.invoke(var39);
         this.classNode.methods.add(var36);
         return var36;
      }
   }

   @NotNull
   public static MethodNode addMethod(ClassWrapper var0, AccessWrapper var1, String var2, Object var3, Object[] var4, String var5, Type[] var6, Function1 var7, int var8, Object var9) {
      if ((var8 & 16) != 0) {
         var5 = (String)null;
      }

      if ((var8 & 32) != 0) {
         var6 = (Type[])null;
      }

      return var0.addMethod(var1, var2, var3, var4, var5, var6, var7);
   }

   @NotNull
   public final ClassNode getClassNode() {
      return this.classNode;
   }

   public ClassWrapper(@NotNull ClassNode var1) {
      super();
      this.classNode = var1;
   }

   public ClassWrapper(@NotNull AccessWrapper var1, @NotNull String var2, int var3, @NotNull String var4) {
      ClassNode var5 = new ClassNode(458752);
      boolean var6 = false;
      boolean var7 = false;
      boolean var9 = false;
      var5.name = var2;
      var5.version = var3;
      var5.superName = var4;
      var5.access = var1.getAccess();
      this(var5);
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
