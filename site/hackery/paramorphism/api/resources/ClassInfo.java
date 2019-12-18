package site.hackery.paramorphism.api.resources;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\bHÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0018\u001a\u00020\bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"},
   d2 = {"Lsite/hackery/paramorphism/api/resources/ClassInfo;", "", "node", "Lorg/objectweb/asm/tree/ClassNode;", "order", "", "(Lorg/objectweb/asm/tree/ClassNode;I)V", "originalName", "", "(Ljava/lang/String;Lorg/objectweb/asm/tree/ClassNode;I)V", "getNode", "()Lorg/objectweb/asm/tree/ClassNode;", "getOrder", "()I", "getOriginalName", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "paramorphism"}
)
public final class ClassInfo {
   @NotNull
   private final String originalName;
   @NotNull
   private final ClassNode node;
   private final int order;

   @NotNull
   public final String getOriginalName() {
      return this.originalName;
   }

   @NotNull
   public final ClassNode getNode() {
      return this.node;
   }

   public final int getOrder() {
      return this.order;
   }

   public ClassInfo(@NotNull String var1, @NotNull ClassNode var2, int var3) {
      super();
      this.originalName = var1;
      this.node = var2;
      this.order = var3;
   }

   public ClassInfo(String var1, ClassNode var2, int var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      this(var1, var2, var3);
   }

   public ClassInfo(@NotNull ClassNode var1, int var2) {
      this(var1.name, var1, var2);
   }

   public ClassInfo(ClassNode var1, int var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      this(var1, var2);
   }

   @NotNull
   public final String component1() {
      return this.originalName;
   }

   @NotNull
   public final ClassNode component2() {
      return this.node;
   }

   public final int component3() {
      return this.order;
   }

   @NotNull
   public final ClassInfo copy(@NotNull String var1, @NotNull ClassNode var2, int var3) {
      return new ClassInfo(var1, var2, var3);
   }

   public static ClassInfo copy$default(ClassInfo var0, String var1, ClassNode var2, int var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.originalName;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.node;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.order;
      }

      return var0.copy(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "ClassInfo(originalName=" + this.originalName + ", node=" + this.node + ", order=" + this.order + ")";
   }

   public int hashCode() {
      String var10000 = this.originalName;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      ClassNode var10001 = this.node;
      return (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31 + Integer.hashCode(this.order);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ClassInfo) {
            ClassInfo var2 = (ClassInfo)var1;
            if (Intrinsics.areEqual((Object)this.originalName, (Object)var2.originalName) && Intrinsics.areEqual((Object)this.node, (Object)var2.node) && this.order == var2.order) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
