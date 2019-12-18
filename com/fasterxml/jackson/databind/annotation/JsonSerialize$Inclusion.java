package com.fasterxml.jackson.databind.annotation;

/** @deprecated */
@Deprecated
public final class JsonSerialize$Inclusion extends Enum {
   public static final JsonSerialize$Inclusion ALWAYS = new JsonSerialize$Inclusion("ALWAYS", 0);
   public static final JsonSerialize$Inclusion NON_NULL = new JsonSerialize$Inclusion("NON_NULL", 1);
   public static final JsonSerialize$Inclusion NON_DEFAULT = new JsonSerialize$Inclusion("NON_DEFAULT", 2);
   public static final JsonSerialize$Inclusion NON_EMPTY = new JsonSerialize$Inclusion("NON_EMPTY", 3);
   public static final JsonSerialize$Inclusion DEFAULT_INCLUSION = new JsonSerialize$Inclusion("DEFAULT_INCLUSION", 4);
   private static final JsonSerialize$Inclusion[] $VALUES = new JsonSerialize$Inclusion[]{ALWAYS, NON_NULL, NON_DEFAULT, NON_EMPTY, DEFAULT_INCLUSION};

   public static JsonSerialize$Inclusion[] values() {
      return (JsonSerialize$Inclusion[])$VALUES.clone();
   }

   public static JsonSerialize$Inclusion valueOf(String var0) {
      return (JsonSerialize$Inclusion)Enum.valueOf(JsonSerialize$Inclusion.class, var0);
   }

   private JsonSerialize$Inclusion(String var1, int var2) {
      super(var1, var2);
   }
}
