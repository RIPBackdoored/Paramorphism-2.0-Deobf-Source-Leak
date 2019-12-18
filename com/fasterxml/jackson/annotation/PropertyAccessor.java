package com.fasterxml.jackson.annotation;

public final class PropertyAccessor extends Enum {
   public static final PropertyAccessor GETTER = new PropertyAccessor("GETTER", 0);
   public static final PropertyAccessor SETTER = new PropertyAccessor("SETTER", 1);
   public static final PropertyAccessor CREATOR = new PropertyAccessor("CREATOR", 2);
   public static final PropertyAccessor FIELD = new PropertyAccessor("FIELD", 3);
   public static final PropertyAccessor IS_GETTER = new PropertyAccessor("IS_GETTER", 4);
   public static final PropertyAccessor NONE = new PropertyAccessor("NONE", 5);
   public static final PropertyAccessor ALL = new PropertyAccessor("ALL", 6);
   private static final PropertyAccessor[] $VALUES = new PropertyAccessor[]{GETTER, SETTER, CREATOR, FIELD, IS_GETTER, NONE, ALL};

   public static PropertyAccessor[] values() {
      return (PropertyAccessor[])$VALUES.clone();
   }

   public static PropertyAccessor valueOf(String var0) {
      return (PropertyAccessor)Enum.valueOf(PropertyAccessor.class, var0);
   }

   private PropertyAccessor(String var1, int var2) {
      super(var1, var2);
   }

   public boolean creatorEnabled() {
      return this == CREATOR || this == ALL;
   }

   public boolean getterEnabled() {
      return this == GETTER || this == ALL;
   }

   public boolean isGetterEnabled() {
      return this == IS_GETTER || this == ALL;
   }

   public boolean setterEnabled() {
      return this == SETTER || this == ALL;
   }

   public boolean fieldEnabled() {
      return this == FIELD || this == ALL;
   }
}
