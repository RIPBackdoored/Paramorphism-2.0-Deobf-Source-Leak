package com.fasterxml.jackson.databind.introspect;

class POJOPropertyBuilder$1 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$1(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public Class[] withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.findViews(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
