package com.fasterxml.jackson.databind.introspect;

class POJOPropertyBuilder$6 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$6(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public Integer withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.findPropertyIndex(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
