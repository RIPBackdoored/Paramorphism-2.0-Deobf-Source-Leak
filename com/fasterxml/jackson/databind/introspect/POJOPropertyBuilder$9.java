package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonProperty$Access;

class POJOPropertyBuilder$9 implements POJOPropertyBuilder$WithMember {
   final POJOPropertyBuilder this$0;

   POJOPropertyBuilder$9(POJOPropertyBuilder var1) {
      super();
      this.this$0 = var1;
   }

   public JsonProperty$Access withMember(AnnotatedMember var1) {
      return this.this$0._annotationIntrospector.findPropertyAccess(var1);
   }

   public Object withMember(AnnotatedMember var1) {
      return this.withMember(var1);
   }
}
