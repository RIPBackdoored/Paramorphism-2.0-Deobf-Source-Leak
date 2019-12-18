package org.yaml.snakeyaml.introspector;

public final class BeanAccess extends Enum {
   public static final BeanAccess DEFAULT = new BeanAccess("DEFAULT", 0);
   public static final BeanAccess FIELD = new BeanAccess("FIELD", 1);
   public static final BeanAccess PROPERTY = new BeanAccess("PROPERTY", 2);
   private static final BeanAccess[] $VALUES = new BeanAccess[]{DEFAULT, FIELD, PROPERTY};

   public static BeanAccess[] values() {
      return (BeanAccess[])$VALUES.clone();
   }

   public static BeanAccess valueOf(String var0) {
      return (BeanAccess)Enum.valueOf(BeanAccess.class, var0);
   }

   private BeanAccess(String var1, int var2) {
      super(var1, var2);
   }
}
