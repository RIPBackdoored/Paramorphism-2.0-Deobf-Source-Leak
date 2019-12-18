package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference1Impl extends MutablePropertyReference1 {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public MutablePropertyReference1Impl(KDeclarationContainer var1, String var2, String var3) {
      super();
      this.owner = var1;
      this.name = var2;
      this.signature = var3;
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

   public Object get(Object var1) {
      return this.getGetter().call(new Object[]{var1});
   }

   public void set(Object var1, Object var2) {
      this.getSetter().call(new Object[]{var1, var2});
   }
}
