package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.DeprecationLevel;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;

/** @deprecated */
@Deprecated
@kotlin.Deprecated(
   message = "This class is no longer supported, do not use it.",
   level = DeprecationLevel.ERROR
)
public abstract class FunctionImpl implements Function, Serializable, Function0, Function1, Function2, Function3, Function4, Function5, Function6, Function7, Function8, Function9, Function10, Function11, Function12, Function13, Function14, Function15, Function16, Function17, Function18, Function19, Function20, Function21, Function22 {
   public FunctionImpl() {
      super();
   }

   public abstract int getArity();

   public Object invokeVararg(Object... var1) {
      throw new UnsupportedOperationException();
   }

   private void checkArity(int var1) {
      if (this.getArity() != var1) {
         this.throwWrongArity(var1);
      }

   }

   private void throwWrongArity(int var1) {
      throw new IllegalStateException("Wrong function arity, expected: " + var1 + ", actual: " + this.getArity());
   }

   public Object invoke() {
      this.checkArity(0);
      return this.invokeVararg();
   }

   public Object invoke(Object var1) {
      this.checkArity(1);
      return this.invokeVararg(var1);
   }

   public Object invoke(Object var1, Object var2) {
      this.checkArity(2);
      return this.invokeVararg(var1, var2);
   }

   public Object invoke(Object var1, Object var2, Object var3) {
      this.checkArity(3);
      return this.invokeVararg(var1, var2, var3);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4) {
      this.checkArity(4);
      return this.invokeVararg(var1, var2, var3, var4);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5) {
      this.checkArity(5);
      return this.invokeVararg(var1, var2, var3, var4, var5);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
      this.checkArity(6);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7) {
      this.checkArity(7);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8) {
      this.checkArity(8);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9) {
      this.checkArity(9);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10) {
      this.checkArity(10);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11) {
      this.checkArity(11);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12) {
      this.checkArity(12);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13) {
      this.checkArity(13);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14) {
      this.checkArity(14);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15) {
      this.checkArity(15);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16) {
      this.checkArity(16);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17) {
      this.checkArity(17);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17, Object var18) {
      this.checkArity(18);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17, Object var18, Object var19) {
      this.checkArity(19);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17, Object var18, Object var19, Object var20) {
      this.checkArity(20);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17, Object var18, Object var19, Object var20, Object var21) {
      this.checkArity(21);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21);
   }

   public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, Object var16, Object var17, Object var18, Object var19, Object var20, Object var21, Object var22) {
      this.checkArity(22);
      return this.invokeVararg(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21, var22);
   }
}
