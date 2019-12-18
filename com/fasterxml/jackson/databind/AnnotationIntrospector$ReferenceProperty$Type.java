package com.fasterxml.jackson.databind;

public final class AnnotationIntrospector$ReferenceProperty$Type extends Enum {
   public static final AnnotationIntrospector$ReferenceProperty$Type MANAGED_REFERENCE = new AnnotationIntrospector$ReferenceProperty$Type("MANAGED_REFERENCE", 0);
   public static final AnnotationIntrospector$ReferenceProperty$Type BACK_REFERENCE = new AnnotationIntrospector$ReferenceProperty$Type("BACK_REFERENCE", 1);
   private static final AnnotationIntrospector$ReferenceProperty$Type[] $VALUES = new AnnotationIntrospector$ReferenceProperty$Type[]{MANAGED_REFERENCE, BACK_REFERENCE};

   public static AnnotationIntrospector$ReferenceProperty$Type[] values() {
      return (AnnotationIntrospector$ReferenceProperty$Type[])$VALUES.clone();
   }

   public static AnnotationIntrospector$ReferenceProperty$Type valueOf(String var0) {
      return (AnnotationIntrospector$ReferenceProperty$Type)Enum.valueOf(AnnotationIntrospector$ReferenceProperty$Type.class, var0);
   }

   private AnnotationIntrospector$ReferenceProperty$Type(String var1, int var2) {
      super(var1, var2);
   }
}
