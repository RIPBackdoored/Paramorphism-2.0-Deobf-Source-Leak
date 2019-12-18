package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl extends FunctionReference {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public FunctionReferenceImpl(int var1, KDeclarationContainer var2, String var3, String var4) {
      super(var1);
      this.owner = var2;
      this.name = var3;
      this.signature = var4;
   }

   public KDeclarationContainer getOwner() {
      return this.owner;
   }

   public String getName() {
      return this.name;
   }

   public String getSignature() {
      return this.signature;
   }
}
