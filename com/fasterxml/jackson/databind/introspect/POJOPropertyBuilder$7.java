package com.fasterxml.jackson.databind.introspect;

class POJOPropertyBuilder$7 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$7(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public String withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.findPropertyDefaultValue(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
