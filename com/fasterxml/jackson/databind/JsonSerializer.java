package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Iterator;

public abstract class JsonSerializer implements JsonFormatVisitable {
   public JsonSerializer() {
      super();
   }

   public JsonSerializer unwrappingSerializer(NameTransformer var1) {
      return this;
   }

   public JsonSerializer replaceDelegatee(JsonSerializer var1) {
      throw new UnsupportedOperationException();
   }

   public JsonSerializer withFilterId(Object var1) {
      return this;
   }

   public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      Class var5 = this.handledType();
      if (var5 == null) {
         var5 = var1.getClass();
      }

      var3.reportBadDefinition(var5, String.format("Type id handling not implemented for type %s (by serializer of type %s)", var5.getName(), this.getClass().getName()));
   }

   public Class handledType() {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public boolean isEmpty(Object var1) {
      return this.isEmpty((SerializerProvider)null, var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return var2 == null;
   }

   public boolean usesObjectId() {
      return false;
   }

   public boolean isUnwrappingSerializer() {
      return false;
   }

   public JsonSerializer getDelegatee() {
      return null;
   }

   public Iterator properties() {
      return ClassUtil.emptyIterator();
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectAnyFormat(var2);
   }
}
