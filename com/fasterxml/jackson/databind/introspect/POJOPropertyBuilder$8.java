package com.fasterxml.jackson.databind.introspect;

class POJOPropertyBuilder$8 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$8(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public ObjectIdInfo withMember(AnnotatedMember var1) {
      ObjectIdInfo var2 = this.this$0._annotationIntrospector.findObjectIdInfo(var1);
      if (var2 != null) {
         var2 = this.this$0._annotationIntrospector.findObjectReferenceInfo(var1, var2);
      }

      return var2;
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
