package paramorphism-obfuscator;

import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.AbstractInsnNode;

public final class ls extends Lambda implements Function0 {
   public final AbstractInsnNode[] ue;
   public final Ref$IntRef uf;
   public final lr[] ug;

   public Object invoke() {
      return this.a();
   }

   @Nullable
   public final AbstractInsnNode[] a() {
      while(this.uf.element + this.ug.length <= ArraysKt.getLastIndex(this.ue)) {
         boolean var1 = true;
         int var2 = 0;

         int var3;
         for(var3 = this.ug.length; var2 < var3; ++var2) {
            AbstractInsnNode var4 = this.ue[this.uf.element + var2];
            if (var4 != null) {
               lr var5 = this.ug[var2];
               if (!var5.a(var4)) {
                  var1 = false;
                  break;
               }
            }
         }

         int var10001;
         if (var1) {
            var3 = this.ug.length;
            AbstractInsnNode[] var11 = new AbstractInsnNode[var3];

            for(int var12 = 0; var12 < var3; ++var12) {
               boolean var7 = false;
               AbstractInsnNode var10000 = this.ue[this.uf.element + var12];
               if (var10000 == null) {
                  Intrinsics.throwNpe();
               }

               AbstractInsnNode var10 = var10000;
               var11[var12] = var10;
            }

            var10001 = this.uf.element++;
            return var11;
         }

         var10001 = this.uf.element++;
      }

      return null;
   }

   public ls(AbstractInsnNode[] var1, Ref$IntRef var2, lr[] var3) {
      super(0);
      this.ue = var1;
      this.uf = var2;
      this.ug = var3;
   }
}
