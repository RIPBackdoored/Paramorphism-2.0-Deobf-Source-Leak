package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector$ReferenceProperty;

class POJOPropertyBuilder$2 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$2(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public AnnotationIntrospector$ReferenceProperty withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.findReferenceType(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
