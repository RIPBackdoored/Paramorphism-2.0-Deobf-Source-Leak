package com.fasterxml.jackson.databind.introspect;

class POJOPropertyBuilder$4 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$4(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public Boolean withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.hasRequiredMarker(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
