package com.fasterxml.jackson.databind;

public class AnnotationIntrospector$ReferenceProperty {
   private final AnnotationIntrospector$ReferenceProperty$Type _type;
   private final String _name;

   public AnnotationIntrospector$ReferenceProperty(AnnotationIntrospector$ReferenceProperty$Type var1, String var2) {
      super();
      this._type = var1;
      this._name = var2;
   }

   public static AnnotationIntrospector$ReferenceProperty managed(String var0) {
      return new AnnotationIntrospector$ReferenceProperty(AnnotationIntrospector$ReferenceProperty$Type.MANAGED_REFERENCE, var0);
   }

   public static AnnotationIntrospector$ReferenceProperty back(String var0) {
      return new AnnotationIntrospector$ReferenceProperty(AnnotationIntrospector$ReferenceProperty$Type.BACK_REFERENCE, var0);
   }

   public AnnotationIntrospector$ReferenceProperty$Type getType() {
      return this._type;
   }

   public String getName() {
      return this._name;
   }

   public boolean isManagedReference() {
      return this._type == AnnotationIntrospector$ReferenceProperty$Type.MANAGED_REFERENCE;
   }

   public boolean isBackReference() {
      return this._type == AnnotationIntrospector$ReferenceProperty$Type.BACK_REFERENCE;
   }
}
